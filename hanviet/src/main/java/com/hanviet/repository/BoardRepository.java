package com.shop.repository;

import com.shop.constant.Location;
import com.shop.constant.Category;
import com.shop.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> , QuerydslPredicateExecutor<Board>, BoardRepositoryCustom {

    List<Board> findByBoardNm(String boardNm);

    List<Board> findByBoardLocationAndBoardCategory(Location boardLocation, Category BoardCategory);
}
