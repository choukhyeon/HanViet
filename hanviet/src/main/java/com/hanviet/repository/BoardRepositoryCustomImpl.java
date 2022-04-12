package com.shop.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.constant.Category;
import com.shop.constant.Location;
import com.shop.dto.BoardSearchDto;
import com.shop.entity.Board;
import com.shop.entity.QBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class BoardRepositoryCustomImpl implements BoardRepositoryCustom{
    private JPAQueryFactory queryFactory;

    public BoardRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchLocationEq(Location boardLocation){
        return boardLocation == null ? null : QBoard.board.boardLocation.eq(boardLocation);
    }
    private BooleanExpression searchCategoryEq(Category category){
        return category == null ? null : QBoard.board.boardCategory.eq(category);
    }

    private BooleanExpression regDtsAfter(String searchDateType){
        LocalDateTime dateTime = LocalDateTime.now();

        if (StringUtils.equals("all", searchDateType) || searchDateType == null){
            return null;
        }else if (StringUtils.equals("1d",searchDateType)){
            dateTime = dateTime.minusDays(1);
        }else if (StringUtils.equals("1w",searchDateType)){
            dateTime = dateTime.minusWeeks(1);
        }else if (StringUtils.equals("1m",searchDateType)){
            dateTime = dateTime.minusMonths(1);
        }else if (StringUtils.equals("6m",searchDateType)){
            dateTime = dateTime.minusMonths(6);
        }
        return QBoard.board.regTime.after(dateTime);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery){
        if (StringUtils.equals("boardNm",searchBy)){
            return QBoard.board.boardNm.like("%"+searchQuery+"%");
        }else if (StringUtils.equals("createdBy", searchBy)){
            return QBoard.board.createdBy.like("%"+searchQuery+"%");
        }
        return null;
    }

    @Override
    public Page<Board> getAdminBoardPage(BoardSearchDto boardSearchDto, Pageable pageable) {
        QueryResults<Board> results = queryFactory
                .selectFrom(QBoard.board)
                .where(regDtsAfter(boardSearchDto.getSearchDateType()),
                        searchLocationEq(boardSearchDto.getBoardLocation()),
                        searchCategoryEq(boardSearchDto.getBoardCategory()),
                        searchByLike(boardSearchDto.getSearchBy(),
                                boardSearchDto.getSearchQuery()))
                .orderBy(QBoard.board.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<Board> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content,pageable,total);
    }
}
