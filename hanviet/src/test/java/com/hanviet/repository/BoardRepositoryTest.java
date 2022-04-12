package com.hanviet.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.hanviet.constant.Location;
import com.hanviet.constant.Category;
import com.hanviet.entity.Board;
import com.hanviet.entity.QBoard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

import static com.hanviet.constant.Location.HANOI;
import static com.hanviet.constant.Category.HOTEL;
import static com.hanviet.constant.Category.MASSAGE;
import static com.hanviet.constant.Location.HANOI;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("상품 저장 테스트")
    public void createBoardTest(){
        Board board = new Board();
        board.setBoardNm("테스트 제목");
        board.setBoardDetail("테스트 내용");
        board.setBoardAddress("테스트 주소");
        board.setBoardLocation(HANOI);
        board.setBoardNum(1);
        board.setRegTime(LocalDateTime.now());
        board.setUpdateTime(LocalDateTime.now());
        Board savedBoard = boardRepository.save(board);
        System.out.println(savedBoard.toString());
    }

    public void createBoardList(){
        for(int i=1; i<=10; i++){
            Board board = new Board();
            board.setBoardNm("테스트 제목"+i);
            board.setBoardDetail("테스트 내용"+i);
            board.setBoardAddress("테스트 주소"+i);
            board.setBoardLocation(HANOI);
            board.setBoardNum(i);
            board.setRegTime(LocalDateTime.now());
            board.setUpdateTime(LocalDateTime.now());
            Board savedBoard = boardRepository.save(board);
        }
    }

    @Test
    @DisplayName("게시글 조회 테스트")
    public void findByBoardNmTest(){
        this.createBoardList();
        List<Board> boardList = boardRepository.findByBoardNm("테스트 제목1");
        for(Board board : boardList){
            System.out.println(board.toString());
        }
    }

    @Test
    @DisplayName("Queryds1테스트")
    public void queryDslTest(){
        this.createBoardList();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QBoard qBoard = QBoard.board;
        JPAQuery<Board> query = queryFactory.selectFrom(qBoard)
                .where(qBoard.boardLocation.eq(HANOI))
                .where(qBoard.boardDetail.like("%"+"테스트 게시글 내용"+"%"))
                .orderBy(qBoard.boardNum.desc());

        List<Board> boardList = query.fetch();

        for(Board board : boardList){
            System.out.println(board.toString());
        }
    }

    public void createBoardList2(){
        for(int i=1; i<=5; i++){
            Board board = new Board();
            board.setBoardNm("테스트 제목"+i);
            board.setBoardDetail("테스트 내용"+i);
            board.setBoardAddress("테스트 주소"+i);
            board.setBoardLocation(HANOI);
            board.setBoardCategory(MASSAGE);
            board.setBoardNum(i);
            board.setRegTime(LocalDateTime.now());
            board.setUpdateTime(LocalDateTime.now());
            Board savedBoard = boardRepository.save(board);
        }
        for(int i=6; i<=10; i++){
            Board board = new Board();
            board.setBoardNm("테스트 제목"+i);
            board.setBoardDetail("테스트 내용"+i);
            board.setBoardAddress("테스트 주소"+i);
            board.setBoardLocation(Location.HOCHIMIN);
            board.setBoardCategory(Category.HOTEL);
            board.setBoardNum(i);
            board.setRegTime(LocalDateTime.now());
            board.setUpdateTime(LocalDateTime.now());
            Board savedBoard = boardRepository.save(board);
        }
    }

    @Test
    @DisplayName("상품 Querydsl 조회 테스트2")
    public void queryDslTest2(){

        this.createBoardList2();

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QBoard board = QBoard.board;
        String boardDetail = "테스트 내용";
        int boardNum = 3;
        String boardLocation = "HANOI";

        booleanBuilder.and(board.boardDetail.like("%"+boardDetail+"%"));
        booleanBuilder.and(board.boardNum.gt(boardNum));

        if(StringUtils.equals(boardLocation, HANOI)){
            booleanBuilder.and(board.boardLocation.eq(HANOI));
        }

        Pageable pageable = PageRequest.of(0,5);
        Page<Board> boardPagingResult  = boardRepository.findAll(booleanBuilder,pageable);
        System.out.println("total elements:" + boardPagingResult.getTotalElements());

        List<Board> resultBoardList = boardPagingResult.getContent();
        for(Board resultBoard : resultBoardList ){
            System.out.println(resultBoard.toString());
        }
    }

    @Test
    @DisplayName("지역별, 카테고리 테스트")
    public void findByBoardLocationAndCategory(){
        this.createBoardList2();
        List<Board> boardList = boardRepository.findByBoardLocationAndBoardCategory(HANOI,MASSAGE);
        for (Board board : boardList){
            System.out.println(board.toString());
        }
    }
}