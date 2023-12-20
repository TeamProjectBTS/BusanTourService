package com.example.board.model.review;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.example.board.model.board.Board;
import com.example.board.model.board.BoardUpdateForm;

import lombok.Data;

@Data
public class Review {
	@Id 
	private Long review_id;
	private Long UC_SEQ; // 
	private String member_id;
	private String nickname;
	private String sort; // 관광지, 음식점, 호텔 중 택1
	private String rv_title;
	private String rv_content;
	private Long star; // 별점 (기본 10점)
	private Long rv_view_count; // 조회수
	private Long rv_like_count; // 좋아요수
	private Long rv_com_count; // 댓글수
	private LocalDateTime wr_date;
	
	public void addRv_view_count() {
		this.rv_view_count++;
	}
	
	public void addRv_like_count() {
		this.rv_like_count++;
	}
	
	public void addRv_com_count() {
		this.rv_com_count++;
	}
	
	public static ReviewUpdateForm toReviewUpdateForm(Review review) {
		ReviewUpdateForm reviewUpdateForm = new ReviewUpdateForm();
		reviewUpdateForm.setReview_id(review.getReview_id());
		reviewUpdateForm.setRv_title(review.getRv_title());
		reviewUpdateForm.setRv_content(review.getRv_content());
		reviewUpdateForm.setMember_id(review.getMember_id());
		reviewUpdateForm.setNickname(review.getNickname());
		reviewUpdateForm.setRv_view_count(review.getRv_view_count());
		reviewUpdateForm.setRv_like_count(review.getRv_like_count());
		reviewUpdateForm.setRv_com_count(review.getRv_com_count());
		reviewUpdateForm.setWr_date(review.getWr_date());
		
		return reviewUpdateForm;
	}
	
	
	
	
	
}
