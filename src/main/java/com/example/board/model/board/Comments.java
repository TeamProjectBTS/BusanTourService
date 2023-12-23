package com.example.board.model.board;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Comments {
	private Long comment_id;
	private Long board_id;
	private String member_id;
	private String nickname;
	private String content;
	private LocalDateTime wr_date;
	
	
	
	
	
	
	
}
