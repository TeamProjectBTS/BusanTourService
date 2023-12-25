package com.example.board.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.board.model.member.Like;

@Mapper
public interface LikeMapper {
	
	void saveBoardLike(Like like);
	void saveReviewLike(Like like);
	void saveTour_spotLike(Like like);
	void saveRestaurantLike(Like like);
	void saveShoppingLike(Like like);
	
	List<Long> getLikedBoardIdsByMemberId(Long member_id);
	List<Long> getLikedReviewIdsByMemberId(Long member_id);
	List<Long> getLikedt_UC_SEQByMemberId(Long member_id);
	List<Long> getLikedr_UC_SEQByMemberId(Long member_id);
	List<Long> getLikeds_UC_SEQByMemberId(Long member_id);
	
	int getLikeNumberByBoardId(Long board_id);
	int getLikeNumberByReviewId(Long review_id);
	int getLikeNumberByt_UC_SEQ(Long t_UC_SEQ);
	int getLikeNumberByr_UC_SEQ(Long r_UC_SEQ);
	int getLikeNumberBys_UC_SEQ(Long s_UC_SEQ);
	
	
	
	
	
	
}
