
package com.example.board.controller;

import com.example.board.config.UserInfo;
import com.example.board.model.review.Review;
import com.example.board.model.review.ReviewAttachedFile;
import com.example.board.model.review.ReviewUpdateForm;
import com.example.board.model.review.ReviewWriteForm;
import com.example.board.repository.ReviewMapper;
import com.example.board.service.ReviewService;
import com.example.board.util.FileService;
import com.example.board.util.PageNavigator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@RequestMapping("review")
@RequiredArgsConstructor
@Controller
public class ReviewController {

    // 데이터베이스 접근을 위한 BoardMapper 필드 선언
    private final ReviewMapper reviewMapper;
    private final FileService fileService;
    private final ReviewService reviewService;
    @Value("${file.upload.path}")
    private String uploadPath;
    
    // 페이징 처리를 위한 상수값
    private final int countPerPage = 10;
    private final int pagePerGroup = 5;

    // BoardMapper 객체 필드 주입(생성자 주입 방식)
//    public BoardController(BoardMapper boardMapper) {
//        this.boardMapper = boardMapper;
//    }

    // 글쓰기 페이지 이동
    @GetMapping("write")
    public String writeForm(Model model,
    		@AuthenticationPrincipal UserInfo userInfo) {
        
        model.addAttribute("writeForm", new ReviewWriteForm());
        
        return "review/write";
    }

    // 게시글 쓰기
    @PostMapping("write")
    public String write(@AuthenticationPrincipal UserInfo userInfo,
                        @Validated @ModelAttribute("writeForm") ReviewWriteForm reviewWriteForm,
                        @RequestParam(required=false) List<MultipartFile> files,
                        BindingResult result) {
       
//        log.info("board: {}", boardWriteForm);
//        log.info("file : {}", file);
        log.info("userInfo : {}", userInfo);
        // validation 에러가 있으면 board/write.html 페이지를 다시 보여준다.
        if (result.hasErrors()) {
            return "/review/write.html";
        }

        // 파라미터로 받은 BoardWriteForm 객체를 Board 타입으로 변환한다.
        Review review = ReviewWriteForm.toReview(reviewWriteForm);
        
        // board 객체에 로그인한 사용자의 아이디를 추가한다.
        review.setMember_id(userInfo.getMember().getMember_id());
        review.setNickname(userInfo.getMember().getNickname());
        
      	reviewService.saveReview(review, files);
        
        
        
        // board/list 로 리다이렉트한다.
        return "redirect:/review/list";
    }

    // 게시글 전체 보기
    @GetMapping("list")
    public String list(

	  		@RequestParam(value="page", defaultValue="1") int page,
			 @RequestParam(value="searchText", defaultValue="") String searchText,
	     Model model) {
    	log.info("검색어 : {}", searchText);

    	int total = reviewService.getTotal(searchText);
    	
      PageNavigator navi = new PageNavigator(countPerPage, pagePerGroup, page, total);
      log.info("페이지 정보 : {}", navi);
      
      // 데이터베이스에 저장된 모든 Board 객체를 리스트 형태로 받는다.
      List<Review> reviews = reviewService.findReviews(searchText, navi.getStartRecord(), navi.getCountPerPage());
      // Board 리스트를 model 에 저장한다.
      model.addAttribute("boards", reviews);
      model.addAttribute("navi", navi);
      model.addAttribute("searchText", searchText);
      // board/list.html 를 찾아서 리턴한다.
      return "review/list";
    }
    	
    // 게시글 읽기
    @GetMapping("read")
    public String read(@RequestParam Long review_id,
    		@AuthenticationPrincipal UserInfo userInfo,
                       Model model) {

        log.info("id: {}", review_id);
        
        // board_id 에 해당하는 게시글을 데이터베이스에서 찾는다.
        Review review = reviewService.readReview(review_id);
        // board_id에 해당하는 게시글이 없으면 리스트로 리다이렉트 시킨다.
        if (review == null || review_id == null) {
            log.info("게시글 없음");
            return "redirect:/review/list";
        }
        
        List<ReviewAttachedFile> attachedFiles = reviewService.findFilesByReviewId(review_id);
//        log.info("첨부파일 : {}", attachedFile);
        model.addAttribute("files", attachedFiles);
        
        // 모델에 Board 객체를 저장한다.
        model.addAttribute("review", review);
        
        // board/read.html 를 찾아서 리턴한다.
        return "review/read";
    }

    // 게시글 수정 페이지 이동
    @GetMapping("update")
    public String updateForm(@AuthenticationPrincipal UserInfo userInfo,
                             @RequestParam Long review_id,
                             Model model) {
        

//        log.info("id: {}", board_id);

        // board_id에 해당하는 게시글이 없거나 게시글의 작성자가 로그인한 사용자의 아이디와 다르면 수정하지 않고 리스트로 리다이렉트 시킨다.
        Review review = reviewService.findReview(review_id);
        if (review_id == null || !review.getMember_id().equals(userInfo.getMember().getMember_id())) {
            log.info("수정 권한 없음");
            return "redirect:/review/list";
        }
        
        // model 에 board 객체를 저장한다.
        model.addAttribute("review", Review.toReviewUpdateForm(review));
        
        List<ReviewAttachedFile> attachedFiles = reviewService.findFilesByReviewId(review_id);
//        log.info("첨부파일 : {}", attachedFile);
        
        model.addAttribute("files", attachedFiles);
        
        // board/update.html 를 찾아서 리턴한다.
        return "board/update";
    }

    // 게시글 수정
    @PostMapping("update")
    public String update(@AuthenticationPrincipal UserInfo userInfo,
                         @RequestParam Long review_id,
                         @Validated @ModelAttribute("review") ReviewUpdateForm updateReview,
                         @RequestParam(required=false) List<MultipartFile> files,
                         BindingResult result) {
        

//        log.info("board: {}", updateBoard);
        // validation 에 에러가 있으면 board/update.html 페이지로 돌아간다.
        if (result.hasErrors()) {
            return "review/update";
        }

        // board_id 에 해당하는 Board 정보를 데이터베이스에서 가져온다.
        Review review = reviewService.findReview(review_id);
        // Board 객체가 없거나 작성자가 로그인한 사용자의 아이디와 다르면 수정하지 않고 리스트로 리다이렉트 시킨다.
        if (review == null || !review.getMember_id().equals(userInfo.getMember().getMember_id())) {
            log.info("수정 권한 없음");
            return "redirect:/review/list";
        }
        // 제목을 수정한다.
        review.setRv_title(updateReview.getRv_title());
        // 내용을 수정한다.
        review.setRv_content(updateReview.getRv_content());
        // 수정한 Board 를 데이터베이스에 update 한다.
        reviewService.updateReview(review , updateReview.isFileRemoved(), files);
        // 수정이 완료되면 리스트로 리다이렉트 시킨다.
        return "redirect:/review/list";
    }

    // 게시글 삭제
    @GetMapping("delete")
    public String remove(@AuthenticationPrincipal UserInfo userInfo,
                         @RequestParam Long review_id) {
        
        // board_id 에 해당하는 게시글을 가져온다.
        Review review = reviewService.findReview(review_id);
        // 게시글이 존재하지 않거나 작성자와 로그인 사용자의 아이디가 다르면 리스트로 리다이렉트 한다.
        if (review == null || !review.getMember_id().equals(userInfo.getMember().getMember_id())) {
            log.info("삭제 권한 없음");
            return "redirect:/review/list";
        }
        
        // 파일 존재 여부 체크
        List<ReviewAttachedFile> attachedFiles = reviewService.findFilesByReviewId(review_id);
        
        if(attachedFiles != null) {
        	for(ReviewAttachedFile file : attachedFiles) {
        		
        		reviewService.removeReviewAttachedFile(file.getAttached_file_id());
        	}
        }
        
        // 게시글을 삭제한다.
        reviewMapper.removeReview(review_id);
        // board/list 로 리다이렉트 한다.
        return "redirect:/review/list";
    }
    
    @GetMapping("download/{id}")
    public ResponseEntity<Resource> download(@PathVariable Long attached_file_id) throws MalformedURLException {
    	// 첨부파일 아이디로 첨부파일 정보를 가져온다
    	ReviewAttachedFile attachedFile = reviewService.findFileByAttachedFileId(attached_file_id);
    	
    	// 다운로드 하려는 파일의 절대경로 값을 만든다
    	String fullPath = uploadPath + "/" + attachedFile.getSaved_filename();
    	
    	UrlResource resource = new UrlResource("file:" + fullPath);
    	
    	// 한글 파일명이 깨지지 않도록 UTF_8로 파일명을 인코딩한다
    	String encodingFileName = UriUtils.encode(attachedFile.getOriginal_filename(), 
    																						StandardCharsets.UTF_8);
    	
    	// 응답헤더에 담을 Content Disposition 값을 생성한다
    	String contentDisposition = "attachment; filename=\""+ encodingFileName + "\"";
    	
    	return ResponseEntity.ok()
    			.header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
    			.body(resource);
    }
    
    
    
    
    
    
    

}
