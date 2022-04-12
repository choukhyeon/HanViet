package com.hanviet.service;

import com.hanviet.constant.Location;
import com.hanviet.dto.BoardFormDto;
import com.hanviet.entity.Board;
import com.hanviet.entity.BoardImg;
import com.hanviet.repository.BoardImgRepository;
import com.hanviet.repository.BoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class BoardServiceTest {

    @Autowired
    BoardService boardService;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BoardImgRepository boardImgRepository;

    List<MultipartFile> createMultipartFiles() throws Exception{
        List<MultipartFile> multipartFileList = new ArrayList<>();

        for (int i=0;i<5;i++){
            String path = "D:/hanviet/board/";
            String imageName = "image"+i+".jpg";
            MockMultipartFile multipartFile = new MockMultipartFile(path,imageName,"imge/jpg",new byte[]{1,2,3,4});
            multipartFileList.add(multipartFile);
        }
        return multipartFileList;
    }

    @Test
    @DisplayName("게시판 등록 테스트")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void saveBoard() throws Exception{
        BoardFormDto boardFormDto = new BoardFormDto();
        boardFormDto.setBoardNm("테스트 제목");
        boardFormDto.setBoardDetail("테스트 내용");
        boardFormDto.setBoardAddress("테스트 주소");
        boardFormDto.setBoardLocation(Location.HANOI);

        List<MultipartFile> multipartFileList = createMultipartFiles();
        Long boardId = boardService.saveBoard(boardFormDto,multipartFileList);

        List<BoardImg> boardImgList = boardImgRepository.findByBoardIdOrderByIdAsc(boardId);
        Board board = boardRepository.findById(boardId).orElseThrow(EntityExistsException::new);

        assertEquals(boardFormDto.getBoardNm(), board.getBoardNm());
        assertEquals(boardFormDto.getBoardDetail(), board.getBoardDetail());
        assertEquals(boardFormDto.getBoardAddress(), board.getBoardAddress());
        assertEquals(multipartFileList.get(0).getOriginalFilename(),boardImgList.get(0).getBoardOriName());
    }

}