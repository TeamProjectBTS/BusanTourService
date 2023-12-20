package com.example.board.model.review;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ReviewUpdateForm {
	
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
	private boolean fileRemoved;

	
	
	
	
	
	
}
