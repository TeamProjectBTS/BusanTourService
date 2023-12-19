package com.example.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping("admin")

public class AdminController {


		@GetMapping("list")
		public String admin(//@AuthenticationPrincipal UserInfo userInfo, 
									Model model) {
			
			return "admin/list";
		}
		
		
		
		
	}


