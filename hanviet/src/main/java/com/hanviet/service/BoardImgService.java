package com.hanviet.service;

import com.hanviet.entity.BoardImg;
import com.hanviet.repository.BoardImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityExistsException;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardImgService {

    @Value("${boardImgLocation}")
    private String boardImgLocation;

    private final BoardImgRepository boardImgRepository;

    private final FileService fileService;

    public void saveBoardImg(BoardImg boardImg, MultipartFile boardImgFile) throws Exception{
        String boardOriName = boardImgFile.getOriginalFilename();
        String boardImgName = "";
        String boardImgUrl = "";

        //파일 업로드
        if(!StringUtils.isEmpty(boardOriName)){
            boardImgName = fileService.uploadFile(boardImgLocation, boardOriName, boardImgFile.getBytes());
            boardImgUrl = "/images/board/"+ boardImgName;
        }

        //게시판 사진 정보저장
        boardImg.updateBoardImg(boardOriName,boardImgName,boardImgUrl);
        boardImgRepository.save(boardImg);
    }

    public void updateBoardImg(Long boardImgId, MultipartFile boardImgFile) throws Exception{
        if (!boardImgFile.isEmpty()){
            BoardImg savedBoardImg = boardImgRepository.findById(boardImgId).orElseThrow(EntityExistsException::new);
            //기존 이미지 파일 삭제
            if (!StringUtils.isEmpty(savedBoardImg.getBoardImgName())){
                fileService.deleteFile(boardImgLocation+"/"+savedBoardImg.getBoardImgName());
            }

            String boardOriName = boardImgFile.getOriginalFilename();
            String boardImgName = fileService.uploadFile(boardImgLocation,boardOriName,boardImgFile.getBytes());
            String boardImgUrl = "/images/board/" + boardImgName;
            savedBoardImg.updateBoardImg(boardOriName,boardImgName,boardImgUrl);
        }

    }
}
