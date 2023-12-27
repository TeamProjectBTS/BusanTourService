package com.example.board.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.board.model.member.Like;

@Mapper
public interface LikeMapper {
	
	void saveBoardLike(Like like);
	void saveReviewLike(Like like);
	void saveTour_spotLike(Like like);
	void saveRestaurantLike(Like like);
	void saveShoppingLike(Like like);
	
	void deleteBoardLike(@Param("board_id") Long board_id, @Param("member_id") String member_id);
	void deleteReviewLike(@Param("review_id")Long review_id, @Param("member_id")String member_id);
	void deleteTour_spotLike(@Param("t_UC_SEQ")Long t_UC_SEQ, @Param("member_id")String member_id);
	void deleteRestaurantLike(@Param("r_UC_SEQ")Long r_UC_SEQ, @Param("member_id")String member_id);
	void deleteShoppingLike(@Param("s_UC_SEQ")Long s_UC_SEQ, @Param("member_id")String member_id);
	
	List<Long> getLikedBoardIdsByMemberId(@Param("member_id")String member_id);
	List<Long> getLikedReviewIdsByMemberId(@Param("member_id")String member_id);
	List<Long> getLikedt_UC_SEQByMemberId(@Param("member_id")String member_id);
	List<Long> getLikedr_UC_SEQByMemberId(@Param("member_id")String member_id);
	List<Long> getLikeds_UC_SEQByMemberId(@Param("member_id")String member_id);
	
	Like getLikeByBoardAndMemberId(@Param("board_id")Long board_id, @Param("member_id")String member_id);
	Like getLikeByReviewAndMemberId(@Param("review_id") Long review_id, @Param("member_id")String member_id);
	Like getLikeByt_UC_SEQAndMemberId(@Param("t_UC_SEQ") Long t_UC_SEQ, @Param("member_id")String member_id);
	Like getLikeByr_UC_SEQAndMemberId(@Param("r_UC_SEQ") Long r_UC_SEQ, @Param("member_id")String member_id);
	Like getLikeBys_UC_SEQAndMemberId(@Param("s_UC_SEQ") Long s_UC_SEQ, @Param("member_id")String member_id);
	
	int getLikeNumberByBoardId(@Param("board_id")Long board_id);
	int getLikeNumberByReviewId(@Param("review_id") Long review_id);
	int getLikeNumberByt_UC_SEQ(@Param("t_UC_SEQ") Long t_UC_SEQ);
	int getLikeNumberByr_UC_SEQ(@Param("r_UC_SEQ") Long r_UC_SEQ);
	int getLikeNumberBys_UC_SEQ(@Param("s_UC_SEQ") Long s_UC_SEQ);
	
	
	
	
	
	
}
