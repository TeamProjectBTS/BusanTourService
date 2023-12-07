package com.example.board.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.board.config.UserInfo;
import com.example.board.model.member.MemberJoinForm;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("restaurant")
public class RestaurantController {

	@GetMapping("list")
	public String restaurantList(@AuthenticationPrincipal UserInfo userInfo, 
								Model model) {
		
		return "restaurant/list";
	}
	
	
	
	
}
