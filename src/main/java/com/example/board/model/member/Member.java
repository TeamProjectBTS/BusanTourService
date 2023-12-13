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
}
