package com.example.board.controller;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.board.config.UserInfo;
import com.example.board.model.member.LoginForm;
import com.example.board.model.member.Member;
import com.example.board.model.member.MemberJoinForm;
import com.example.board.model.member.MemberUpdateForm;
import com.example.board.repository.MemberMapper;
import com.example.board.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("member")
@Controller
@RequiredArgsConstructor
public class MemberController {

	// 데이터베이스 접근을 위한 MemberMapper 필드 선언
	private final MemberService memberService;

	// 회원가입 페이지 이동
	@GetMapping("join")
	public String joinForm(Model model, @AuthenticationPrincipal UserInfo userInfo) {

		if (userInfo != null) {
			log.info("이미 로그인한 사용자");
			return "redirect:/";
		}

		log.info("회원가입 페이지");
		// joinForm.html 의 필드 세팅을 위해 model 에 빈 MemberJoinForm 객체 생성하여 저장한다
		model.addAttribute("joinForm", new MemberJoinForm());
		// member/joinForm.html 페이지를 리턴한다.
		return "member/joinForm";
	}

	// 회원가입
	// @Transactional(readOnly=true) //에러가 터지면 DB 컴파일 안하도록. BindingResult를 쓰기 때문에
	// 없어도 무방.
	@PostMapping("join")
	public String join(@Validated @ModelAttribute("joinForm") MemberJoinForm joinForm, BindingResult result) {
		// 회원가입 처리
		log.info("joinForm:{}", joinForm);
		log.info("result:{}", result);
		log.info("nickname size : {}", joinForm.getNickname().length());
		// validation 에 에러가 있으면 가입시키지 않고 member/joinForm.html 페이지로 돌아간다.
		if (result.hasErrors()) {

			return "member/joinForm";
		}

		// 사용자로부터 입력받은 아이디로 데이터베이스에서 Member 를 검색한다.
		Member member = memberService.findMember(joinForm.getMember_id());
		// 사용자 정보가 존재하면
		if (member != null) {
			log.info("이미 가입된 아이디 입니다.");
			// BindingResult 객체에 GlobalError 를 추가한다.
			result.reject("duplicate_ID", "이미 가입된 아이디 입니다.");
			// member/joinForm.html 페이지를 리턴한다.
			return "member/joinForm";
		}

		if (memberService.findMemberByNick(joinForm.getNickname()) != null) {
			log.info("닉네임 중복");
			result.reject("duplicate_nickname", "중복된 닉네임 입니다.");
			return "member/joinForm";
		}

		byte[] bytes = joinForm.getNickname().getBytes(java.nio.charset.StandardCharsets.UTF_8);
		int byteSize = bytes.length;

		if (byteSize > 20) {
			log.info("크기 20바이트 이상");
			result.reject("over20Bytes", "닉네임은 한글 6자, 영문 20자까지 가능합니다.");
			return "member/joinForm";
		}

		// MemberJoinForm 객체를 Member 타입으로 변환하여 데이터베이스에 저장한다.
		memberService.saveMember(MemberJoinForm.toMember(joinForm));
		// 메인 페이지로 리다이렉트한다.
		return "redirect:/";
	}

	// 로그인 페이지 이동
	@GetMapping("login")
	public String loginForm(Model model, @AuthenticationPrincipal UserInfo userInfo,
			@RequestParam(value = "error", required = false) boolean error,
			@RequestParam(value = "message", required = false) String message) {
		log.info("로그인 페이지");
		// log.info("error : {} ", error);
		// log.info("message : {} ", message);

		if (userInfo != null) {
			log.info("이미 로그인한 사용자");
			return "redirect:/";
		}

		if (error) {
			model.addAttribute("error", error);
			model.addAttribute("message", message);

		}

		// member/loginForm.html 에 필드 셋팅을 위해 빈 LoginForm 객체를 생성하여 model 에 저장한다.
		model.addAttribute("loginForm", new LoginForm());
		// member/loginForm.html 페이지를 리턴한다.
		return "/member/loginForm";
	}

//	@GetMapping("sessionInfo")
//	public String sessionInfo(HttpServletRequest request) {
//		//getSession()는 비워놓으면 세션을 만듭니다. false:세션이 없으면 null 리턴
//		HttpSession session = request.getSession(false);		
//		log.info("sessionId:{}", session.getId());
//		log.info("maxInactiveInterval:{}", session.getMaxInactiveInterval());
//		log.info("creationTime:{}", new Date(session.getCreationTime()));
//		log.info("lastAccessedTime:{}", new Date(session.getLastAccessedTime()));
//		return "redirect:/";
//	}

	@GetMapping("login-success")
	public String loginSuccess(@AuthenticationPrincipal UserInfo userInfo) {

		if (userInfo == null) {
			log.info("로그인하지 않은 사용자");
			return "redirect:/member/login";
		}

		log.info("로그인 성공, userInfo : {}", userInfo.getMember());

		return "redirect:/";
	}

	@PostMapping("login-failed")
	public String loginFailed(BindingResult result) {
		if (result.hasErrors()) {
			return "/member/login";
		}
		log.info("로그인 실패");

		return "redirect:/";
	}

	@GetMapping("logout")
	public String logout(HttpServletResponse response, HttpServletRequest request) {
		log.info("로그아웃 호출");
		// 전과 같은 이름을 만들면서 값을 null로
		// Cookie cookie = new Cookie("cookieLoginId", null);
		// cookie.setPath("/");
		// cookie.setMaxAge(0); //쿠키 유지시간
		// response.addCookie(cookie);

		// 세션초기화 첫번째 방법
		// HttpSession session = request.getSession(false);
		// session.setAttribute("loginMember", null);
		// 세션초기화 두번째 방법
		// session.invalidate();

		// Request 객체에서 Session 정보를 가져온다.
		HttpSession session = request.getSession(false);
		// 세션이 존재하면 세션의 모든 데이터를 리셋한다.
		if (session != null) {
			session.invalidate();
		}
		// 메인 페이지로 리다이렉트 한다.
		return "redirect:/";
	}

	@GetMapping("mypage")
	public String mypage(@AuthenticationPrincipal UserInfo userInfo, Model model) {
		
		Member member = memberService.findMember(userInfo.getMember().getMember_id());
		
		
		MemberUpdateForm updateForm = new MemberUpdateForm();
		updateForm.setMember_id(member.getMember_id());
		updateForm.setEmail(member.getEmail());
		updateForm.setNickname(member.getNickname());
		updateForm.setPassword(member.getPassword());
		updateForm.setPhone(member.getPhone());
		
		model.addAttribute("loginUser", userInfo);
		model.addAttribute("mypage", updateForm);
		log.info("현재멤버입니다:{}",member);
		return "member/mypage";
	}

	@PostMapping("mypage")
	public String mypageUpdate(@AuthenticationPrincipal UserInfo userInfo,
			@Validated @ModelAttribute("mypage") MemberUpdateForm updateForm,
			BindingResult result,
			Model model
			) {
		log.info("userInfo : {}", userInfo);
		if (result.hasErrors()) {
			model.addAttribute("loginUser",userInfo);
			return "member/mypage";
		}
		Member member = memberService.findMember(userInfo.getMember().getMember_id());

		memberService.updateComments(updateForm);
		memberService.updateBoard(updateForm);
		memberService.updateReview(updateForm);
		memberService.updateMember(updateForm);
		
		return "redirect:/member/logout";
	}

	@GetMapping("myboard")
	public String myboard(@AuthenticationPrincipal UserInfo userInfo, Model model) {
		model.addAttribute("loginUser", userInfo);
		return "member/myboard";
	}

	@GetMapping("myreview")
	public String myreview(@AuthenticationPrincipal UserInfo userInfo, Model model) {
		model.addAttribute("loginUser", userInfo);
		return "member/myreview";
	}

	@GetMapping("mylike")
	public String mylike(@AuthenticationPrincipal UserInfo userInfo, Model model) {
		model.addAttribute("loginUser", userInfo);
		return "member/mylike";
	}

}
