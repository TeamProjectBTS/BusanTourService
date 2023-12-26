package com.example.board.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.board.config.UserInfo;
import com.example.board.model.board.Board;
import com.example.board.model.board.BoardWriteForm;
import com.example.board.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ProfileController {
	
	private final MemberService memberService;
	

	 
	 @PostMapping("/uploadProfilePicture")
	    public String write(@AuthenticationPrincipal UserInfo userInfo,
	                        @RequestParam(required=false) MultipartFile file,
	                        Model model
	                        ) {
	       
	        log.info("file : {}", file);
	        
	        log.info("userInfo : {}", userInfo);
	        
	       
//	        memberAttachedFile 테이블에 저장했음.
	        memberService.profileUpdate(userInfo.getMember(), file);
	        
	        // board/list 로 리다이렉트한다.
	        return "redirect:/member/mypage";
	    }
	 
	 
	 
	 
	 
}
