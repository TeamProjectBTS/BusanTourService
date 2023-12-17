package com.example.board.model.review;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewAttachedFile {
	private Long attached_file_id;
	private Long review_id;
	private String original_filename;
	private String saved_filename;
	private Long file_size;
	
	public ReviewAttachedFile(String original_filename, String saved_filename, Long file_size) {
		this.original_filename = original_filename;
		this.saved_filename = saved_filename;
		this.file_size = file_size;
	}
	
	
}
