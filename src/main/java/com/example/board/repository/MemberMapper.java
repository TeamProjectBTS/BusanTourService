package com.example.board.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.board.model.board.AttachedFile;
import com.example.board.model.member.Member;
import com.example.board.model.member.MemberAttachedFile;
import com.example.board.model.member.MemberProfileUpdateForm;
import com.example.board.model.member.MemberUpdateForm;

@Mapper
public interface MemberMapper {
	void saveMember(Member member);
	Member findMember(String member_id); 
	Member findMemberByNick(String nickname);
	void updateMember(MemberUpdateForm updateForm);
	void updateBoard(MemberUpdateForm updateForm);
	void updateReview(MemberUpdateForm updateForm);
	void updateComments(MemberUpdateForm updateForm);
	void memberSaveFile(MemberAttachedFile memberAttachedFile);
	void updateMemberProfile(MemberProfileUpdateForm memberProfileUpdateForm);
	
}

