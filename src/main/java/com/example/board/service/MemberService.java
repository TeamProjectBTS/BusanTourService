package com.example.board.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.board.model.board.AttachedFile;
import com.example.board.model.board.Board;
import com.example.board.model.member.Member;
import com.example.board.model.member.MemberAttachedFile;
import com.example.board.model.member.MemberProfileUpdateForm;
import com.example.board.model.member.MemberUpdateForm;
import com.example.board.model.member.RoleType;
import com.example.board.repository.BoardMapper;
import com.example.board.repository.MemberMapper;
import com.example.board.util.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class MemberService {
	
	private final FileService fileService;
	//데이터베이스 접근을 위한 BoardMapper 필드 선언
  private final MemberMapper memberMapper;
  private final PasswordEncoder passwordEncoder;
  
  public Member findMember(String member_id) {
  	return memberMapper.findMember(member_id);
  }
  
  @Transactional
	public void saveMember(Member member) {
  	String rawPassword = member.getPassword();
  	// 비크립트 방식으로 암호화
  	String encPassword = passwordEncoder.encode(rawPassword);
  	member.setPassword(encPassword);
  	log.info("encpassword : {}", encPassword);
  	
  	// 기본 ROLE 부여
  	member.setRole(RoleType.ROLE_USER);
  	
		memberMapper.saveMember(member);
	}
	
  public Member findMemberByNick(String nickname) {
  	return memberMapper.findMemberByNick(nickname);
  }


  
  @Transactional
  public void updateMember(MemberUpdateForm updateForm) {
	  String rawPassword = updateForm.getPassword();
	  
	  	if(rawPassword != null && !rawPassword.isEmpty()) {
	  		String encPassword = passwordEncoder.encode(rawPassword);
	  		updateForm.setPassword(encPassword);
	  	}
	  
	  	memberMapper.updateMember(updateForm);
}
  
  @Transactional
  public void updateBoard(MemberUpdateForm updateForm) {
	  
	  	memberMapper.updateBoard(updateForm);
}
  
  @Transactional
  public void updateReview(MemberUpdateForm updateForm) {
	  
	  	memberMapper.updateReview(updateForm);
}
  
  @Transactional
  public void updateComments(MemberUpdateForm updateForm) {
	  

	  
	  	memberMapper.updateComments(updateForm);
}

  @Transactional
	public void profileUpdate(Member member, MultipartFile file) {
		
    
	    if(file != null && file.getSize() > 0 || !file.isEmpty()) {
	    	// 첨부파일 저장
	    	MemberAttachedFile saveFile = fileService.profileAttachedFile(file);
//	    	log.info("멤버서비스:프로필업데이트:savefile:{}",saveFile);
	    	
	    	// 첨부파일 내용을 데이터베이스에 저장
	    	saveFile.setMember_id(member.getMember_id());
//	    	log.info("멤버서비스:프로필업데이트:savefile:{}",saveFile);
	    	memberMapper.memberSaveFile(saveFile);
	    	
	    	//멤버 테이블에 attached_file속성 저장
//	    	MemberAttachedFile attachedFile = memberMapper.file();
	    	member.setAttached_filename(saveFile.getSaved_filename());
	    	
	    	// member_attached_file 테이블에 저장
	    	MemberProfileUpdateForm memberProfileUpdateForm = Member.toMemberProfileUpdateForm(member);
//	    	log.info("멤버서비스:프로필업데이트:memberProfileUpdateForm:{}",memberProfileUpdateForm);
	    	memberProfileUpdateForm.setMember_id(member.getMember_id());
//	    	log.info("멤버서비스:프로필업데이트:memberProfileUpdateForm:{}",memberProfileUpdateForm);
	    	memberMapper.updateMemberProfile(memberProfileUpdateForm);
//	    	log.info("멤버서비스:프로필업데이트 완료");
	    	

	    	
	    	
	    }
    
	}
	
	
	
}
