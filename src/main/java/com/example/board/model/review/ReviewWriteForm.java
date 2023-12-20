package com.example.board.model.review;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;



import lombok.Data;

@Data
public class ReviewWriteForm {
	@NotBlank
	private String sort; // 관광지, 음식점, 호텔 중 택1
	@NotEmpty
	private String rv_title;
	@NotEmpty
	private String rv_content;
	private Long star; // 별점 (기본 10점)
	private Long UC_SEQ;
	
	
	public static Review toReview(ReviewWriteForm reviewWriteForm) {
		Review review = new Review();
		review.setUC_SEQ(reviewWriteForm.getUC_SEQ());
		review.setSort(reviewWriteForm.getSort());
		review.setRv_title(reviewWriteForm.getRv_title());
		review.setRv_content(reviewWriteForm.getRv_content());
		review.setStar(reviewWriteForm.getStar());
		
		return review;
	}

	
	
	
	
	
	
}
