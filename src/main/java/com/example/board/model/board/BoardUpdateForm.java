package com.example.board.model.board;

import lombok.Data;

import javax.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BoardUpdateForm {
    private Long board_id;
    @NotBlank
    private String b_title;
    @NotBlank
    private String b_contents;
    private String member_id;
    private String nickname;
    private Long b_view_count;
    private Long b_like_count;
    private Long b_com_count;
    private LocalDate wr_date;
    private boolean fileRemoved;
    
    public static Board toBoard(BoardUpdateForm updateBoard) {
  		Board board = new Board();
  		board.setBoard_id(updateBoard.getBoard_id());
  		board.setB_title(updateBoard.getB_title());
  		board.setB_contents(updateBoard.getB_contents());
  		board.setMember_id(updateBoard.getMember_id());
  		board.setNickname(updateBoard.getNickname());
  		board.setB_view_count(updateBoard.getB_view_count());
  		board.setB_like_count(updateBoard.getB_like_count());
  		board.setB_com_count(updateBoard.getB_com_count());
  		board.setWr_date(updateBoard.getWr_date());
  		
  		return board;
  	}

}
