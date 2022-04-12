package com.shop.dto;

import com.shop.entity.BoardImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Setter @Getter
public class BoardImgDto {

    private Long id;

    private String boardImgName;

    private String boardOriName;

    private String boardImgUrl;

    private String boardRepimgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    public static BoardImgDto of(BoardImg boardImg){
        return modelMapper.map(boardImg,BoardImgDto.class);
    }
}
