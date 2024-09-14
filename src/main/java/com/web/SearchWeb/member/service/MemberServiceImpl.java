package com.web.SearchWeb.member.service;


import com.web.SearchWeb.board.dao.BoardDao;
import com.web.SearchWeb.board.domain.Board;
import com.web.SearchWeb.member.dao.MemberDao;
import com.web.SearchWeb.member.domain.Member;
import com.web.SearchWeb.member.dto.MemberDto;
import com.web.SearchWeb.member.dto.MemberUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


//서비스 로직, 트랜잭션 처리
@Service
public class MemberServiceImpl implements MemberService{

    private final MemberDao memberDao;
    private final BoardDao boardDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public MemberServiceImpl(MemberDao memberDao, BCryptPasswordEncoder bCryptPasswordEncoder, BoardDao boardDao) {
        this.memberDao = memberDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.boardDao = boardDao;
    }


    /**
     *  회원가입
     */
    public void joinProcess(MemberDto member) {
        // 비밀번호 암호화 및 사용자 저장 로직
        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        member.setRole("ROLE_USER");
        memberDao.joinProcess(member);
    }


    /**
     *  회원번호로 찾기
     */
    public Member findByMemberId(int memberId){
        return memberDao.findByMemberId(memberId);
    }


    /**
     *  로그인 아이디로 찾기
     */
    public Member findByUserName(String username){
        return memberDao.findByUserName(username);
    }


    /**
     *  비밀번호 확인
     */
    public boolean isPasswordMatching(MemberDto memberDto) {
        return memberDto.getPassword().equals(memberDto.getConfirmPassword());
    }


    /**
     *  회원 수정
     */
    @Override
    @Transactional
    public int updateMember(int memberId, MemberUpdateDto memberUpdateDto) {

        // 1. 회원 정보 수정
        int result = memberDao.updateMember(memberId, memberUpdateDto);

        // 2. 회원 정보 수정이 성공했을 경우, 게시글의 회원정보 업데이트
        if (result == 1) {
            List<Board> boards = boardDao.selectBoardListByMemberId(memberId);
            for (Board board : boards) {
                board.setJob(memberUpdateDto.getJob());
                board.setMajor(memberUpdateDto.getMajor());
                boardDao.updateBoardProfile(board.getBoardId(), memberUpdateDto.getJob(), memberUpdateDto.getMajor());
            }
        }

        return result;
    }
}