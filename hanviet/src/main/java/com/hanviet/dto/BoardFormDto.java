package com.hanviet.dto;

import com.hanviet.constant.Category;
import com.hanviet.constant.Location;
import com.hanviet.entity.Board;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class BoardFormDto {
    private Long id;

    @NotBlank(message = "제목은 입력해야 합니다.")
    private String boardNm;

    @NotBlank(message = "상세 내용은 입력해야 합니다.")
    private String boardDetail;

    @NotBlank(message = "주소는 입력해야합니다.")
    private String boardAddress;

    private Location boardLocation;

    private Category boardCategory;

    private List<BoardImgDto> boardImgDtoList = new ArrayList<>();

    private List<Long> boardImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Board createBoard(){
        return modelMapper.map(this, Board.class);
    }
    public static BoardFormDto of(Board board){
        return modelMapper.map(board, BoardFormDto.class);
    }

}
