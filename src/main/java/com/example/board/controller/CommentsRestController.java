package com.example.board.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.board.config.UserInfo;
import com.example.board.model.board.Board;
import com.example.board.model.board.Comments;
import com.example.board.model.member.Member;
import com.example.board.repository.BoardMapper;
import com.example.board.repository.CommentsMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("comments")
@RequiredArgsConstructor
@Slf4j
public class CommentsRestController {
	
	private final CommentsMapper commentsMapper;
	private final BoardMapper boardMapper;
	
	// 리플 등록
	@PostMapping("{board_id}") 
	public ResponseEntity<String> writeComment(@AuthenticationPrincipal UserInfo userInfo,
																					@ModelAttribute Comments comment,
																					@PathVariable Long board_id) {
		
		
		comment.setMember_id(userInfo.getMember().getMember_id());
		comment.setBoard_id(board_id);
		comment.setNickname(userInfo.getMember().getNickname());
		log.info("comment : {}", comment);
		
		commentsMapper.saveComment(comment);
		
		return ResponseEntity.ok("등록 성공");
	}
	
	// 리플 읽기
	@GetMapping("{board_id}/{comment_id}")
	public ResponseEntity<Comments> findComment(@PathVariable Long board_id,
																				@PathVariable Long comment_id){
		Comments comment = commentsMapper.findComment(comment_id);
		
		return ResponseEntity.ok(comment);
	}
	
	// 리플 목록
	@GetMapping("{board_id}")
	public ResponseEntity<List<Comments>> findComments(@PathVariable Long board_id,
																								Model model){
		List<Comments> comments = commentsMapper.findComments(board_id);
		for(Comments comment : comments) {
			log.info("comments : {}", comment);
		}
		
		
		return ResponseEntity.ok(comments);
	}
	
	// 리플 수정
	@PutMapping("{board_id}/{comment_id}")
	public ResponseEntity<Comments> updateComment(@AuthenticationPrincipal UserInfo userInfo,
																					@PathVariable Long board_id,
																					@PathVariable Long comment_id,
																					@ModelAttribute Comments comment){
		Comments findReply = commentsMapper.findComment(comment_id);
		// 수정권한이 있는지 체크	
		if(userInfo.getMember().getMember_id().equals(findReply.getMember_id())) {
			// 리플 수정
			comment.setMember_id(userInfo.getMember().getMember_id());
			comment.setBoard_id(board_id);
			comment.setComment_id(comment_id);
			comment.setNickname(userInfo.getMember().getNickname());
			commentsMapper.updateComment(comment);
		}
		
		return ResponseEntity.ok(comment);
	}
	
	// 리플 삭제
	@DeleteMapping("{board_id}/{comment_id}")
	public ResponseEntity<String> removeReply(@AuthenticationPrincipal UserInfo userInfo,
																						@PathVariable Long board_id,
																						@PathVariable Long comment_id) {
		Comments reply = commentsMapper.findComment(comment_id);
		// 삭제 권한이 있는지 체크
		if(userInfo.getMember().getMember_id().equals(reply.getMember_id())) {
			
			commentsMapper.removeComment(comment_id);
		}
		//
		
		return ResponseEntity.ok("삭제 완료");
	}
	
	
	
	
	
	
	
	
	
}
