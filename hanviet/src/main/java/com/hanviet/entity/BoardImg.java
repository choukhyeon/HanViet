package com.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "board_img")
@Getter @Setter
public class BoardImg extends BaseEntity{

    @Id
    @Column(name = "board_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String boardImgName;

    private String boardOriName;

    private String boardImgUrl;

    private String boardRepimgYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public void updateBoardImg(String boardOriName, String boardImgName, String boardImgUrl){
        this.boardImgName = boardImgName;
        this.boardOriName = boardOriName;
        this.boardImgUrl = boardImgUrl;
    }

}
