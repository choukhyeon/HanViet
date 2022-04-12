package com.hanviet.repository;

import com.hanviet.dto.BoardSearchDto;
import com.hanviet.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {
    Page<Board> getAdminBoardPage(BoardSearchDto boardSearchDto, Pageable pageable);
}
