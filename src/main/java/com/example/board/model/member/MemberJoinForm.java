package com.example.board.model.member;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * -@Size : 문자열의 길이
 * -@NotNull : null 불가
 * -@NotEmpty : null, "" 불가
 * -@NotBlank : null, "", " " 불가
 * -@Past : 과거 날짜만 가능
 * -@PastOrPresent : 오늘을 포함한 과거 날짜만 가능
 * -@Future : 미래 날짜만 가능
 * -@Pattern : 정규식 사용
 * -@Max : 최댓값
 * -@Min : 최솟값
 * -@Validated : 해당 Object의 Validation을 실행.
 */
@Data
public class MemberJoinForm {
	@Size(min = 6, max = 20, message = "아이디는 6~20사이로 입력해주세요.")
	private String member_id;
	@Size(min = 6, max = 20 , message = "비밀번호는 6~20사이로 입력해주세요.")
	private String password;
	@NotEmpty(message = "닉네임을 입력해주세요")
	private String nickname;
	@NotEmpty(message = "이름을 입력해주세요")
	private String name;
	@NotEmpty(message = "이메일을 입력해주세요")
	private String email;
	@NotEmpty(message = "전화번호를 입력해주세요")
	private String phone;
	
	public static Member toMember(MemberJoinForm memberJoinForm) {
		Member member = new Member();
		member.setMember_id(memberJoinForm.getMember_id());
		member.setPassword(memberJoinForm.getPassword());
		member.setNickname(memberJoinForm.getNickname());
		member.setName(memberJoinForm.getName());
		member.setEmail(memberJoinForm.getEmail());
		member.setPhone(memberJoinForm.getPhone());
		return member;
	}
	
	
	
	
	
	
}
