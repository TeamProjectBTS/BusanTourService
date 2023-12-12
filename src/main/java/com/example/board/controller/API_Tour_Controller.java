package com.example.board.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.model.tour_spot.Tour_spotResponse;
import com.example.board.model.tour_spot.Tour_spotResponse.Body.Items;
import com.example.board.model.tour_spot.Tour_spotResponse.Body.Items.Item;
import com.example.board.service.T_ApiService;

import jdk.internal.org.jline.utils.Log;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("tour_spot")
@RequiredArgsConstructor
@Slf4j
public class API_Tour_Controller {
	
	private final T_ApiService t_ApiService;

  @GetMapping("list")
  public String tour_spot_list(Model model) {
  	Tour_spotResponse ts_list = t_ApiService.xmlToJavaObject();
  	List<Item> items = ts_list
  		.getBody()
  		.getItems()
  		.getItem();
  	for(Item item : items) {
  		if(item.getPLACE().equals("화명수목원")) {
  			log.info("item : {}", item.toString());
  		}
  	}
  		
  	return "/tour_spot/list";
  }
  
	
	
	
	
}