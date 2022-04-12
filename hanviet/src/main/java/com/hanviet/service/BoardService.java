package com.hanviet.service;

import com.hanviet.dto.BoardFormDto;
import com.hanviet.dto.BoardImgDto;
import com.hanviet.dto.BoardSearchDto;
import com.hanviet.entity.Board;
import com.hanviet.entity.BoardImg;
import com.hanviet.repository.BoardImgRepository;
import com.hanviet.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardImgService boardImgService;
    private final BoardImgRepository boardImgRepository;

    public Long saveBoard(BoardFormDto boardFormDto, List<MultipartFile> boardImgFileList) throws Exception{

        //게시판 작성
        Board board = boardFormDto.createBoard();
        boardRepository.save(board);

        //이미지등록
        for (int i=0; i<boardImgFileList.size();i++){
            BoardImg boardImg = new BoardImg();
            boardImg.setBoard(board);
            if(i==0)
                boardImg.setBoardRepimgYn("Y");
            else
                boardImg.setBoardRepimgYn("N");
            boardImgService.saveBoardImg(boardImg,boardImgFileList.get(i));


        }
        return board.getId();
    }

    @Transactional(readOnly = true)
    public BoardFormDto getBoardDtl(Long boardId){

        List<BoardImg> boardImgList = boardImgRepository.findByBoardIdOrderByIdAsc(boardId);
        List<BoardImgDto> boardImgDtoList = new ArrayList<>();
        for (BoardImg boardImg : boardImgList){
            BoardImgDto boardImgDto = BoardImgDto.of(boardImg);
            boardImgDtoList.add(boardImgDto);
        }
        Board board = boardRepository.findById(boardId).orElseThrow(EntityExistsException::new);
        BoardFormDto boardFormDto = BoardFormDto.of(board);
        boardFormDto.setBoardImgDtoList(boardImgDtoList);
        return boardFormDto;
    }

    public Long updateBoard(BoardFormDto boardFormDto, List<MultipartFile> boardImgFileList) throws Exception{

        //게시글 수정
        Board board = boardRepository.findById(boardFormDto.getId()).orElseThrow(EntityExistsException::new);
        board.updateBoard(boardFormDto);

        List<Long> boardImgIds = boardFormDto.getBoardImgIds();

        //이미지 등록
        for (int i=0;i<boardImgFileList.size();i++){
            boardImgService.updateBoardImg(boardImgIds.get(i),boardImgFileList.get(i));
        }
        return board.getId();
    }

    @Transactional(readOnly = true)
    public Page<Board> getAdminBoardPage(BoardSearchDto boardSearchDto, Pageable pageable){
        return boardRepository.getAdminBoardPage(boardSearchDto,pageable);
    }

}
