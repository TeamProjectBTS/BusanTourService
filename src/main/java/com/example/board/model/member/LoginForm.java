package com.example.board.model.member;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class LoginForm {
	@Size(min = 6, max = 20)
	private String member_id;
	@Size(min = 6, max = 20)
	private String password;
}
