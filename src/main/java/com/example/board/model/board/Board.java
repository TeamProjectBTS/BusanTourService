package com.example.board.model.board;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Board {
	
	/*
	 * board_id number primary key,-- 게시글 번호
    member_id references member(member_id) on delete cascade,  // 컨트롤러에서 loginMember.getMember_id() 메서드로 받아오기
    nickname references member(nickname) on delete cascade,  // 컨트롤러에서 loginMember.getNickname() 메서드로 받아오기
    b_title varchar2(100) not null, -- 게시글 이름
    b_contents varchar2(4000) not null, -- 게시글 내용 
    b_view_count int default 0, -- 뷰 카운트
    b_like_count int default 0, -- 좋아요 카운트
    b_com_count int default 0, -- 댓글 카운트
    wr_date date DEFAULT sysdate -- 글 작성시간 (현재시간 가져오기)
	 * */

	private Long board_id; //게시물 아이디
	private String b_title; //글 제목
	private String b_contents; //내용
	private String member_id; //작성자
	private String nickname; //닉네임
	private Long b_view_count; //뷰 카운트
	private Long b_like_count; //좋아요 카운트
	private Long b_com_count; //댓글 카운트
	private LocalDate wr_date; //작성일
	
	public void addB_view_count() {
		this.b_view_count++;
	}
	
	public void addB_like_count() {
		this.b_like_count++;
	}
	
	public void addB_com_count() {
		this.b_com_count++;
	}
	
	public static BoardUpdateForm toBoardUpdateForm(Board board) {
		BoardUpdateForm boardUpdateForm = new BoardUpdateForm();
		boardUpdateForm.setBoard_id(board.getBoard_id());
		boardUpdateForm.setB_title(board.getB_title());
		boardUpdateForm.setB_contents(board.getB_contents());
		boardUpdateForm.setMember_id(board.getMember_id());
		boardUpdateForm.setNickname(board.getNickname());
		boardUpdateForm.setB_view_count(board.getB_view_count());
		boardUpdateForm.setB_like_count(board.getB_like_count());
		boardUpdateForm.setB_com_count(board.getB_com_count());
		boardUpdateForm.setWr_date(board.getWr_date());
		
		return boardUpdateForm;
	}
	
	
	
	
	
}
