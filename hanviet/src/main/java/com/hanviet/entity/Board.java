package com.shop.entity;

import com.shop.constant.Category;
import com.shop.constant.Location;
import com.shop.dto.BoardFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "board")
@Getter
@Setter
@ToString

public class Board extends BaseEntity{

    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 50)
    private String boardNm;     //제목

    @Column(nullable = false)
    private String boardDetail;

    @Column(nullable = false)
    private String boardAddress;

    @Column(columnDefinition = "integer default 0")
    private int boardNum;   //조회수

    @Enumerated(EnumType.STRING)
    private Location boardLocation; //지역 게시글

    @Enumerated(EnumType.STRING)
    private Category boardCategory;  //카테고리

    public void updateBoard(BoardFormDto boardFormDto){
        this.boardNm = boardFormDto.getBoardNm();
        this.boardDetail = boardFormDto.getBoardDetail();
        this.boardAddress = boardFormDto.getBoardAddress();
        this.boardLocation = boardFormDto.getBoardLocation();
    }
}

