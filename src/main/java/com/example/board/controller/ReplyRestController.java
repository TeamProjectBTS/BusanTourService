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
import com.example.board.model.board.Reply;
import com.example.board.model.member.Member;
import com.example.board.repository.BoardMapper;
import com.example.board.repository.ReplyMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("reply")
@RequiredArgsConstructor
@Slf4j
public class ReplyRestController {
	
	private final ReplyMapper replyMapper;
	private final BoardMapper boardMapper;
	
	// 리플 등록
	@PostMapping("{board_id}") 
	public ResponseEntity<String> writeReply(@AuthenticationPrincipal UserInfo userInfo,
																					@ModelAttribute Reply reply,
																					@PathVariable Long board_id) {
		log.info("reply : {}", reply);
		
		reply.setMember_id(userInfo.getMember().getMember_id());
		reply.setBoard_id(board_id);
		replyMapper.saveReply(reply);
		
		return ResponseEntity.ok("등록 성공");
	}
	
	// 리플 읽기
	@GetMapping("{board_id}/{reply_id}")
	public ResponseEntity<Reply> findReply(@PathVariable Long board_id,
																				@PathVariable Long reply_id){
		Reply reply = replyMapper.findReply(reply_id);
		
		return ResponseEntity.ok(reply);
	}
	
	// 리플 목록
	@GetMapping("{board_id}")
	public ResponseEntity<List<Reply>> findReplies(@PathVariable Long board_id,
																								Model model){
		List<Reply> replies = replyMapper.findReplies(board_id);
		
		return ResponseEntity.ok(replies);
	}
	
	// 리플 수정
	@PutMapping("{board_id}/{reply_id}")
	public ResponseEntity<Reply> updateReply(@AuthenticationPrincipal UserInfo userInfo,
																					@PathVariable Long board_id,
																					@PathVariable Long reply_id,
																					@ModelAttribute Reply reply){
		Reply findReply = replyMapper.findReply(reply_id);
		// 수정권한이 있는지 체크	
		if(userInfo.getMember().getMember_id().equals(findReply.getMember_id())) {
			// 리플 수정
			reply.setMember_id(userInfo.getMember().getMember_id());
			reply.setBoard_id(board_id);
			reply.setReply_id(reply_id);
			replyMapper.updateReply(reply);
		}
		
		
		return ResponseEntity.ok(reply);
	}
	
	// 리플 삭제
	@DeleteMapping("{board_id}/{reply_id}")
	public ResponseEntity<String> removeReply(@AuthenticationPrincipal UserInfo userInfo,
																						@PathVariable Long board_id,
																						@PathVariable Long reply_id) {
		Reply reply = replyMapper.findReply(reply_id);
		// 삭제 권한이 있는지 체크
		if(userInfo.getMember().getMember_id().equals(reply.getMember_id())) {
			
			replyMapper.removeReply(reply_id);
		}
		//
		
		return ResponseEntity.ok("삭제 완료");
	}
	
	
	
	
	
	
	
	
	
}
