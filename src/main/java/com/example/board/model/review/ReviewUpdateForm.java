package com.example.board.model.review;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ReviewUpdateForm {
	
	private Long review_id;
	private Long UC_SEQ; // 
	private String info_place;
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
	private boolean fileRemoved;

	public static Review toReview(ReviewUpdateForm reviewUpdateForm) {
		Review review = new Review();
		review.setReview_id(reviewUpdateForm.getReview_id());
		review.setUC_SEQ(reviewUpdateForm.getUC_SEQ());
		review.setInfo_place(reviewUpdateForm.getInfo_place());
		review.setMember_id(reviewUpdateForm.getMember_id());
		review.setNickname(reviewUpdateForm.getNickname());
		review.setSort(reviewUpdateForm.getSort());
		return review;
	}
	
	
	
	
	
}
