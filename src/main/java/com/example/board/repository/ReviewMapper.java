package com.example.board.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.example.board.model.board.AttachedFile;
import com.example.board.model.board.Board;
import com.example.board.model.review.Review;
import com.example.board.model.review.ReviewAttachedFile;

@Mapper
public interface ReviewMapper {
	void saveReview(Review board);
	List<Review> findReviews(String searchText, RowBounds rowBounds);
	Review findReview(Long review_id);
	void updateReview(Review updateReview);
	void removeReview(Long review_id);
	void saveFile(ReviewAttachedFile attachedFile);
	List<ReviewAttachedFile> findFilesByReviewId(Long review_id);
	ReviewAttachedFile findFileByReviewAttachedFileId(Long id);
	int getTotal(String searchText);
	void removeReviewAttachedFile(Long attached_file_id);
	
	
}
