package com.example.board.model.board;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class BoardWriteForm {
	
	@NotBlank(message="제목을 입력해주세요")
	private String b_title;
	
	@NotBlank(message="내용을 입력해주세요")
	private String b_contents;
	
	public static Board toBoard(BoardWriteForm boardWriteForm) {
		Board board = new Board();
		board.setB_title(boardWriteForm.getB_title());
		board.setB_contents(boardWriteForm.getB_contents());
		return board;
	}
	
	
	
}
