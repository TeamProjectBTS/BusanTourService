package com.example.board.model.board;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class BoardWriteForm {
	@NotBlank
	private String b_title; //글 제목
	@NotBlank
	private String b_contents; //내용
	
	public static Board toBoard(BoardWriteForm boardWriteForm) {
		Board board = new Board();
		board.setB_title(boardWriteForm.getB_title());
		board.setB_contents(boardWriteForm.getB_contents());
		return board;
	}
}
