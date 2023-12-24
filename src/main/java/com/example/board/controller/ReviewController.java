
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

import org.apache.ibatis.annotations.Param;
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
    		@RequestParam(value="UC_SEQ") Long UC_SEQ,
    		@AuthenticationPrincipal UserInfo userInfo) {
        ReviewWriteForm writeForm = new ReviewWriteForm();
        writeForm.setUC_SEQ(UC_SEQ);
        model.addAttribute("writeForm", writeForm);
        model.addAttribute("loginUser", userInfo);
        return "review/write";
    }

    // 게시글 쓰기
    @PostMapping("write")
    public String write(@AuthenticationPrincipal UserInfo userInfo,
                        @Validated @ModelAttribute("writeForm") ReviewWriteForm reviewWriteForm,
                        BindingResult result,
                        @RequestParam(required=false) List<MultipartFile> files,
                        Model model
                        ) {
       

        log.info("userInfo : {}", userInfo);
        // validation 에러가 있으면 board/write.html 페이지를 다시 보여준다.
        if (result.hasErrors()) {
        	model.addAttribute("loginUser",userInfo);
            return "review/write";
        }

        // 파라미터로 받은 BoardWriteForm 객체를 Board 타입으로 변환한다.
        Review review = ReviewWriteForm.toReview(reviewWriteForm);
        
        // board 객체에 로그인한 사용자의 아이디를 추가한다.
        review.setMember_id(userInfo.getMember().getMember_id());
        review.setNickname(userInfo.getMember().getNickname());
        
      	reviewService.saveReview(review, files);
        
        
        
        // review/list 로 리다이렉트한다.
        return "redirect:/review/list";
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
        model.addAttribute("loginUser", userInfo);
        // board/update.html 를 찾아서 리턴한다.
        return "review/update";
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
            return "/review/update.html";
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
    
    
    
    
    
    
    
    

}
