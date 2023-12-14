package com.example.board.model.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RoleType {
	ROLE_USER("사용자"),
	ROLE_ADMIN("관리자"),
	ROLE_BANNED_USER("허용되지 않은 사용자");
	
	private final String description;
	
}
