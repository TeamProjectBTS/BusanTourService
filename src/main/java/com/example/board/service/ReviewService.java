package com.example.board.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.board.model.board.AttachedFile;
import com.example.board.model.board.Board;
import com.example.board.model.review.Review;
import com.example.board.model.review.ReviewAttachedFile;
import com.example.board.repository.BoardMapper;
import com.example.board.repository.ReviewMapper;
import com.example.board.util.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class ReviewService {
	
	//데이터베이스 접근을 위한 BoardMapper 필드 선언
  private final ReviewMapper reviewMapper;
  private final FileService fileService;
  @Value("${file.upload.path}")
  private String uploadPath;
  
  @Transactional
	public void saveReview(Review review, List<MultipartFile> files) {
  	
		if(findReview(review.getReview_id()) == null) {
			// 데이터베이스에 저장한다.
			reviewMapper.saveReview(review);
		}
		
    for(MultipartFile file : files) {
    	if(file != null && file.getSize() > 0 || !file.isEmpty()) {
	    	// 첨부파일 저장
	    	ReviewAttachedFile saveFile = fileService.saveReviewFile(file);
	    	// 첨부파일 내용을 데이터베이스에 저장
	    	saveFile.setReview_id(review.getReview_id());
	    	reviewMapper.saveFile(saveFile);
    	}
    }
	}
  
	public ReviewAttachedFile findFileByAttachedFileId(Long id) {
		ReviewAttachedFile attachedFile = reviewMapper.findFileByReviewAttachedFileId(id);
		return attachedFile;
	}
	
	public int getTotal(String searchText) {
		return reviewMapper.getTotal(searchText);
	}
	
	public List<Review> findReviews(String searchText, int startRecord, int countPerPage) {
		RowBounds rowBounds = new RowBounds(startRecord, countPerPage);
		return reviewMapper.findReviews(searchText, rowBounds);
	}
	
//	public Board findBoard(Long board_id) {
//		return boardMapper.findBoard(board_id);
//	}
	
	// 
	public Review readReview(Long review_id) {
		Review review = findReview(review_id);
		review.addRv_view_count();
		updateReview(review, false, null);
		List<ReviewAttachedFile> attachedFile = findFilesByReviewId(review_id);
		
		boolean isFileRemoved = true;
		if(attachedFile == null) {
			isFileRemoved = false;
		}
//		updateReview(review, isFileRemoved, attachedFile);
		return review;
	}


	public Review findReview(Long review_id) {
		return reviewMapper.findReview(review_id);
	}
	
	//
	@Transactional
	public void updateReview(Review updateReview, boolean isFileRemoved, List<MultipartFile> files) {
		Review review = reviewMapper.findReview(updateReview.getReview_id());
		
		if(review != null) {
			reviewMapper.updateReview(updateReview);
			// 첨부파일 정보를 가져옴
			List<ReviewAttachedFile> attachedFile = reviewMapper.findFilesByReviewId(updateReview.getReview_id());
			if(attachedFile != null && (isFileRemoved || (files != null))) {
				
				for(MultipartFile file : files) {
					if(file.getSize() > 0) {
						for(ReviewAttachedFile reviewFile : attachedFile) {
					// 파일 삭제를 요청했거나 새로운 파일이 업로드 됐다면 기존 파일을 삭제한다.
						removeReviewAttachedFile(reviewFile.getAttached_file_id());
						}
					}
				}
			}
		}
		
		// 새로 저장할 파일이 있다면 저장한다.
		if(files != null ) {
			
			for(MultipartFile file : files) {
				if(file.getSize() > 0) {
					// 첨부파일을 서버에 저장한다.
					ReviewAttachedFile savedFile = fileService.saveReviewFile(file);
					// 데이터베이스에 저장될 board_id를 세팅
					savedFile.setReview_id(updateReview.getReview_id());
					// 첨부파일 내용을 데이터베이스에 저장
					reviewMapper.saveFile(savedFile);
				}
			}
		}
	}
	
	//
	@Transactional
	public void removeReviewAttachedFile(Long attached_file_id) {
		ReviewAttachedFile attachedFile = reviewMapper.findFileByReviewAttachedFileId(attached_file_id);
		if(attachedFile != null) {
			
					String fullPath = uploadPath + "/" + attachedFile.getSaved_filename();
					fileService.deleteFile(fullPath);
				
			// 데이터베이스에서도 삭제
			reviewMapper.removeReviewAttachedFile(attached_file_id);		
		}
	}
	
	//
	public List<ReviewAttachedFile> findFilesByReviewId(Long review_id) {
		return reviewMapper.findFilesByReviewId(review_id);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
