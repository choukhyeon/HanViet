package com.hanviet.repository;

import com.hanviet.constant.Location;
import com.hanviet.constant.Category;
import com.hanviet.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> , QuerydslPredicateExecutor<Board>, BoardRepositoryCustom {

    List<Board> findByBoardNm(String boardNm);

    List<Board> findByBoardLocationAndBoardCategory(Location boardLocation, Category BoardCategory);
}
