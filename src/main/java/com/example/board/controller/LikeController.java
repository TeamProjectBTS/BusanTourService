package com.example.board.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.model.board.Board;
import com.example.board.model.member.Like;
import com.example.board.repository.LikeMapper;
import com.example.board.service.BoardService;
import com.example.board.service.ReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/like")
@Slf4j
@RequiredArgsConstructor
public class LikeController {
	
	private final LikeMapper likeMapper;
	private final BoardService boardService;
	private final ReviewService reviewService;
	
	@PostMapping("{board_id}/{member_id}")
	public ResponseEntity<String> saveLikeInBoard(@PathVariable Long board_id,
			@PathVariable String member_id) {
		
		Like like = new Like();
		like.setMember_id(member_id);
		like.setBoard_id(board_id);
		likeMapper.saveBoardLike(like);
		int likeNumber = likeMapper.getLikeNumberByBoardId(board_id);
		
		Board board = boardService.findBoard(board_id);
		board.setB_like_count((long)likeNumber);
		boardService.updateBoard(board, false, null);
		
		return ResponseEntity.ok("좋아요 등록");
	}
	
	@GetMapping("{board_id}")
	public ResponseEntity<Integer> getLikeNumbers(@PathVariable Long board_id) {
		
		int likeNumber = likeMapper.getLikeNumberByBoardId(board_id);
		
		return ResponseEntity.ok(likeNumber);
	}
	
	@GetMapping("{board_id}/{member_id}")
	public ResponseEntity<Boolean> likedOrNot(@PathVariable Long board_id,
			@PathVariable String member_id) {
		
		Like like = likeMapper.getLikeByBoardAndMemberId(board_id, member_id);
		
		if(like == null) {
			return ResponseEntity.ok(false);
		}
		
		return ResponseEntity.ok(true);
	}
	
	@DeleteMapping("{board_id}/{member_id}")
	public ResponseEntity<String> deleteBoardLike(@PathVariable Long board_id,
			@PathVariable String member_id) {
		likeMapper.deleteBoardLike(board_id, member_id);
		
		int likeNumber = likeMapper.getLikeNumberByBoardId(board_id);
		
		Board board = boardService.findBoard(board_id);
		board.setB_like_count((long)likeNumber);
		boardService.updateBoard(board, false, null);
		
		return ResponseEntity.ok("사쿠죠 칸료");
	}
	
	
	
	
	
	
}
