package com.web.SearchWeb.board.dao;

import com.web.SearchWeb.board.domain.Board;
import com.web.SearchWeb.board.dto.BoardDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BoardDao {
    //게시글 생성
    public int insertBoard(int memberId, BoardDto boardDto);

    //게시글 목록 조회(최신순, 인기순)
    List<Board> selectBoardList(@Param("query") String query, @Param("sort") String sort);
}