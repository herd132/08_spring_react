package edu.kh.admin.main.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.admin.main.model.dto.Board;
import edu.kh.admin.main.model.dto.Member;

@Mapper
public interface AdminMapper {

	/** 관리자 로그인
	 * @param memberEmail
	 * @return
	 */
	Member login(String memberEmail);
	
	/** 탈퇴 회원 조회
	 * @return 
	 */
	List<Member> selectWithdrawnMemberList();

	/** 탈퇴 회원 복구
	 * @param memberNo
	 * @return
	 */
	int restoreMember(int memberNo);

	/** 삭제된 게시글 목록 조회
	 * @return
	 */
	List<Board> selectDeleteBoardList();

	/** 삭제 게시글 복구
	 * @param boardNo
	 * @return
	 */
	int restoreBoard(int boardNo);

	/** 새로운 회원 조회
	 * @return
	 */
	List<Member> getNewMember();

	/** 최대 조회수 게시글 조회
	 * @return
	 */
	Board maxReadCount();

	/** 최대 좋아요 게시글 조회
	 * @return
	 */
	Board maxLikeCount();

	/** 최대 댓글 수 게시글 조회
	 * @return
	 */
	Board maxCommentCount();

	/** 관리자 계정 목록 조회
	 * @return
	 */
	List<Member> adminAccountList();
	
	/** 관리자 계정 생성
	 * @param member 
	 * @return
	 */
	int createAdminAccount(Member member);
	
}
