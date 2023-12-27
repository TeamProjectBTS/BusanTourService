package com.example.board.model.member;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Member {
	private String member_id;
	private String password;
	private String nickname;
	private String name;
	private String phone;
	private String email;
	private String attached_filename;
	private RoleType role;
	
	
	public static MemberUpdateForm toUpdateMember (Member member) {
		MemberUpdateForm memberUpdateForm = new MemberUpdateForm();
		    memberUpdateForm.setPassword(member.getPassword());
		    memberUpdateForm.setNickname(member.getNickname());
		    memberUpdateForm.setPhone(member.getPhone());
		    memberUpdateForm.setEmail(member.getEmail());
		
		return memberUpdateForm;
	}
	
	public static MemberProfileUpdateForm toMemberProfileUpdateForm (Member member) {
		MemberProfileUpdateForm memberProfileUpdateForm = new MemberProfileUpdateForm();
		memberProfileUpdateForm.setAttached_filename(member.getAttached_filename());
		return memberProfileUpdateForm;
	}
}