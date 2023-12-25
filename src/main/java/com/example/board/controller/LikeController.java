package com.example.board.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.model.member.Like;
import com.example.board.repository.LikeMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("like")
@Slf4j
@RequiredArgsConstructor
public class LikeController {
	
	private final LikeMapper likeMapper;
	
	@GetMapping("{board_id}/{member_id}")
	public ResponseEntity<String> saveLike(@PathVariable Long board_id,
			@PathVariable Long member_id) {
		
		Like like = new Like();
		like.setMember_id(member_id);
		like.setBoard_id(board_id);
		likeMapper.saveBoardLike(like);
		
		return ResponseEntity.ok("좋아요 등록");
	}
	
	
	
	
	
}
