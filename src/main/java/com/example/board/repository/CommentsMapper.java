package com.example.board.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.board.model.board.Comments;

@Mapper
public interface CommentsMapper {
	// 리플 등록
	void saveComment(Comments comment);
	// 리플 읽기
	Comments findComment(Long comment_id);
	// 리플 목록
	List<Comments> findComments(Long board_id);
	// 리플 수정
	void updateComment(Comments comment);
	// 리플 삭제
	void removeComment(Long comment_id);
}
