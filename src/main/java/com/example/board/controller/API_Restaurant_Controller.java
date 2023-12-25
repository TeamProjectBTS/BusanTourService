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
import com.example.board.model.restaurant.RestaurantResponse.Body.Items.Item;
import com.example.board.model.review.Review;
import com.example.board.service.R_ApiService;
import com.example.board.service.ReviewService;
import com.example.board.util.PageNavigator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("restaurant")
@RequiredArgsConstructor
@Slf4j
public class API_Restaurant_Controller {
	
	private final R_ApiService r_ApiService;
	private final ReviewService reviewService;
	
	//페이징 처리를 위한 상수값
  private final int countPerPage = 10;
  private final int pagePerGroup = 5;
  
  
  @GetMapping("list")
  public String tour_spot_list(Model model,
  		@AuthenticationPrincipal UserInfo userInfo,
		  	@RequestParam(value="page", defaultValue="1") int page,
				@RequestParam(value="searchText", defaultValue="") String searchText
				) {
  	
  	List<Item> items = r_ApiService.getItems();
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
  	return "restaurant/list";
  }
  
  @GetMapping("read")
  public String readTour(@AuthenticationPrincipal UserInfo userInfo,
  		@RequestParam(value="UC_SEQ") Long UC_SEQ,
  		@RequestParam(value="page", defaultValue="1") int page,
			@RequestParam(value="searchTextReview", defaultValue="") String searchTextReview,
  		Model model) {
  	
  	List<Item> items = r_ApiService.getItems();
  	
  	for(Item item : items) {
  		if(item.getUC_SEQ().equals(UC_SEQ)) {
  			model.addAttribute("item", item);
  		}
  	}
  	
  	int total = reviewService.getTotalInReview(UC_SEQ, searchTextReview);

    PageNavigator navi = new PageNavigator(countPerPage, pagePerGroup, page, total);

    
    // 데이터베이스에 저장된 모든 Board 객체를 리스트 형태로 받는다.
    List<Review> reviews = new ArrayList<>(); 
    for(Review review : reviewService.findReviews(searchTextReview, navi.getStartRecord(), navi.getCountPerPage())) {
    	if(review.getUC_SEQ().equals(UC_SEQ)) {
    		reviews.add(review);
    		log.info("review : {}", review);
    	}
    }
    
    
    
  	model.addAttribute("reviews", reviews);
  	model.addAttribute("navi", navi);
    model.addAttribute("searchTextReview", searchTextReview);
  	model.addAttribute("loginUser",userInfo);
  	return "restaurant/read";
  
  
  
	
	
	
	
}
  
}


