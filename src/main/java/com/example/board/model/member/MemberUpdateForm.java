package com.example.board.model.member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;
@Data
public class MemberUpdateForm {
	
	private String member_id;
	@Size(min = 6, max = 20 , message = "비밀번호는 6~20사이로 입력해주세요.")
	private String password;
	@Pattern(regexp = "^[a-zA-Z0-9]{4,20}$",  message="닉네임은 영문 및 숫자 4~20자로만 가능합니다.")
	@NotEmpty(message = "닉네임을 입력해주세요")
	private String nickname;
	@NotEmpty(message = "전화번호를 입력해주세요")
	@Pattern(regexp = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", message="전화번호를 정확히 입력해주세요")
	private String phone;
	@NotEmpty(message = "이메일을 입력해주세요")
	@Email
	private String email;
	
	
}
