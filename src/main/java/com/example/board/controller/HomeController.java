package com.example.board.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.board.config.UserInfo;
import com.example.board.model.member.MemberJoinForm;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {

	//메인페이지 이동
	@GetMapping("/")
  public String home(@AuthenticationPrincipal UserInfo userInfo,
  									Model model) {
		model.addAttribute("loginUser", userInfo);
		
	  return "index";
  }
	
	//about 페이지 이동
	@GetMapping("about")
  public String about(@AuthenticationPrincipal UserInfo userInfo,
  									Model model) {
		model.addAttribute("loginUser", userInfo);
		
	  return "about";
  }
	
//deals 페이지 이동
	@GetMapping("deals")
  public String deals(@AuthenticationPrincipal UserInfo userInfo,
  									Model model) {
		model.addAttribute("loginUser", userInfo);
		
	  return "deals";
  }
	
//joinForm 페이지 이동
	@GetMapping("joinForm")
  public String joinForm (@AuthenticationPrincipal UserInfo userInfo,
  									Model model) {
		model.addAttribute("loginUser", userInfo);
		
	  return "joinForm";
  }
	
// loginForm페이지 이동
	@GetMapping("loginForm")
	public String loginForm(@AuthenticationPrincipal UserInfo userInfo,
	  									Model model) {
	model.addAttribute("loginUser", userInfo);
			
		  return "loginForm";
	 }
	
//	@GetMapping("join")
//	public String joinForm(Model model) {
//		// joinForm.html 의 필드 세팅을 위해 model 에 빈 MemberJoinForm 객체 생성하여 저장한다
//		model.addAttribute("joinForm", new MemberJoinForm());
//		// member/joinForm.html 페이지를 리턴한다.
//		return "member/joinForm";
//	}
	

	
	@GetMapping("admin")
	public String adminHome(@AuthenticationPrincipal UserInfo userInfo, Model model) {
		model.addAttribute("adminUser", userInfo);
		
		return "/admin/index";
	}
	
}
