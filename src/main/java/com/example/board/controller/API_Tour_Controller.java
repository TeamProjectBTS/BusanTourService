package com.example.board.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.board.config.UserInfo;
import com.example.board.model.tour_spot.Tour_spotResponse.Body.Items.Item;
import com.example.board.service.ReviewService;
import com.example.board.service.T_ApiService;
import com.example.board.util.PageNavigator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("tour_spot")
@RequiredArgsConstructor
@Slf4j
public class API_Tour_Controller {
	
	private final T_ApiService t_ApiService;
	private final ReviewService reviewService;
	
	//페이징 처리를 위한 상수값
  private final int countPerPage = 10;
  private final int pagePerGroup = 5;
  
  
  @GetMapping("list")
  public String tour_spot_list(Model model,
  		@AuthenticationPrincipal UserInfo userInfo,
		  	@RequestParam(value="page", defaultValue="1") int page,
				@RequestParam(value="searchText", defaultValue="") String searchText) {
  	
  	List<Item> items = t_ApiService.getItems();
  	int startRecord = (page - 1) * countPerPage;
  	int currentRecord = 1;
  	List<Item> searchItems = new ArrayList<>();
  	
  	Collections.sort(items, Comparator.comparing(Item::getPLACE));
  	
		for(Item item : items) {
		
  		if(item.getPLACE().contains(searchText) || 
  				item.getSUBTITLE().contains(searchText) || 
  				item.getTITLE().contains(searchText)) {
	  			
  			searchItems.add(item);
	  			
  		}
		}
		
		 List<Item> showItems = new ArrayList<>();
		
		for(Item item : searchItems) {
			
				if(currentRecord <= countPerPage) {
					if(searchItems.indexOf(item) >= startRecord) {
					showItems.add(item);
					currentRecord++;
				}
			}
		}
  	
  	int total = searchItems.size();
  	log.info("total : {}", total);
  	PageNavigator navi = new PageNavigator(countPerPage, pagePerGroup, page, total);
  	log.info("navi : {}", navi);
  	model.addAttribute("items", showItems);
  	model.addAttribute("navi", navi);
    model.addAttribute("searchText", searchText);
    model.addAttribute("loginUser",userInfo);
  	return "tour_spot/list";
  }
  
  @GetMapping("read")
  public String readTour(@RequestParam(value="UC_SEQ") int UC_SEQ,
  		@AuthenticationPrincipal UserInfo userInfo,
  		Model model) {
  	
  	model.addAttribute("loginUser",userInfo);
  	List<Item> items = t_ApiService.getItems();
  	
  	for(Item item : items) {
  		if(item.getUC_SEQ() == UC_SEQ) {
  			model.addAttribute("item", item);
  		}
  	}
  	
  	reviewService.findReviews(null, 1, countPerPage);
  	return "tour_spot/read";
  }
  
  
	
	
	
	
}

