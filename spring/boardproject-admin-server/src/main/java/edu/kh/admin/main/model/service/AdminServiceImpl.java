package edu.kh.admin.main.model.service;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.admin.common.util.Utility;
import edu.kh.admin.main.model.dto.Board;
import edu.kh.admin.main.model.dto.Member;
import edu.kh.admin.main.model.mapper.AdminMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class AdminServiceImpl implements AdminService {

	private final AdminMapper mapper;
	private final BCryptPasswordEncoder bcrypt;

	// 관리자 로그인
	@Override
	public Member login(Member inputMember) {

		Member loginMember = mapper.login(inputMember.getMemberEmail());

		if (loginMember == null)
			return null;

		if (!bcrypt.matches(inputMember.getMemberPw(), loginMember.getMemberPw())) {
			return null;
		}

		loginMember.setMemberPw(null);
		return loginMember;
	}

	// 탈퇴 회원 조회
	@Override
	public List<Member> selectWithdrawnMemberList() {
		return mapper.selectWithdrawnMemberList();
	}

	// 탈퇴 회원 복구
	@Override
	public int restoreMember(int memberNo) {
		return mapper.restoreMember(memberNo);
	}

	// 삭제된 게시글 목록 조회
	@Override
	public List<Board> selectDeleteBoardList() {
		return mapper.selectDeleteBoardList();
	}

	// 삭제 게시글 복구
	@Override
	public int restoreBoard(int boardNo) {
		return mapper.restoreBoard(boardNo);
	}

	// 새로운 회원 조회
	@Override
	public List<Member> getNewMember() {
		return mapper.getNewMember();
	}

	// 최대 조회수 게시글 조회
	@Override
	public Board maxReadCount() {
		return mapper.maxReadCount();
	}

	// 최대 좋아요 게시글 조회
	@Override
	public Board maxLikeCount() {
		return mapper.maxLikeCount();
	}

	// 최대 댓글 수 게시글 조회
	@Override
	public Board maxCommentCount() {
		return mapper.maxCommentCount();
	}

	// 관리자 계정 목록 조회
	@Override
	public List<Member> adminAccountList() {
		return mapper.adminAccountList();
	}

	// 관리자 계정 생성
	@Override
	public String createAdminAccount(Member member) {
		
		// 1. 영어(대소문자), 숫자 6자리 난수로 만든 비밀번호를 암호화 한 값 구하기
		String rawPw = Utility.generatePassword(); // 평문 비번
		String encPw = bcrypt.encode(rawPw); // 암호화된 비번 
		
		// 2. member에 암호화된 비밀번호 세팅
		member.setMemberPw(encPw);
		
		// 3. DB에 암호화된 비밀번호가 세팅된 member를 전달하여 계정 발급(INSERT)
		int result = mapper.createAdminAccount(member);
		
		// 4. 계정 발급 정상처리 되었다면, 발급된 (평문)비밀번호 리턴하기
		if(result > 0) {
			return rawPw;
			
		} else {
			return null;
		}
	}
}
