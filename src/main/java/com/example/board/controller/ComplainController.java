package com.example.board.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.board.config.UserInfo;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping("complain")

public class ComplainController {


		@GetMapping("list")
		public String complain(@AuthenticationPrincipal UserInfo userInfo, 
									Model model) {
			model.addAttribute("loginUser", userInfo);
			return "complain/list";
		}
		
		
		
		
	}


