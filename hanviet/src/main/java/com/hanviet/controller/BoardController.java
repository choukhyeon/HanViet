package com.shop.controller;

import com.shop.dto.BoardFormDto;
import com.shop.dto.BoardSearchDto;
import com.shop.entity.Board;
import com.shop.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping(value = "/admin/board/new")
    public String boardForm(Model model){
        model.addAttribute("boardFormDto", new BoardFormDto());
        return "board/boardForm";
    }

    @PostMapping(value = "/admin/board/new")
    public String boardNew(@Valid BoardFormDto boardFormDto, BindingResult bindingResult, Model model, @RequestParam("boardImgFile")List<MultipartFile> boardImgFileList){
        if(bindingResult.hasErrors()){
            return "board/boardForm";
        }
        if (boardImgFileList.get(0).isEmpty() && boardFormDto.getId() == null){
            model.addAttribute("errorMessage","첫번째 게시물 사진은 필수로 등록해야합니다.");
            return "board/boardForm";
        }

        try {
            boardService.saveBoard(boardFormDto, boardImgFileList);
        }catch (Exception e){
            model.addAttribute("errorMessage","게시물 등록 중 에러가 발생했습니다.");
            return "board/boardForm";
        }
        return "redirect:/";
    }

    @GetMapping(value = "/admin/board/{boardId}")
    public String itemDtl(@PathVariable("boardId") Long boardId, Model model){
        try{
            BoardFormDto boardFormDto = boardService.getBoardDtl(boardId);
            model.addAttribute("boardFormDto", boardFormDto);
        }catch (EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재하지 않는 게시물 입니다.");
            model.addAttribute("boardFormDto", new BoardFormDto());
            return "board/boardForm";
        }
        return "board/boardForm";
    }

    @PostMapping(value = "/admin/board/{boardId}")
    public String boardUpdate(@Valid BoardFormDto boardFormDto, BindingResult bindingResult, @RequestParam("boardImgFile") List<MultipartFile> boardImgFileList, Model model){
        if(bindingResult.hasErrors()){
            return "board/boardForm";
        }
        if (boardImgFileList.get(0).isEmpty() && boardFormDto.getId() == null){
            model.addAttribute("errorMessage", "첫번째 게시글 이미지는 필수 입력값입니다.");
            return "board/boardForm";
        }
        try {
            boardService.updateBoard(boardFormDto, boardImgFileList);
        }catch (Exception e){
            model.addAttribute("errorMessage","게시글 수정 중 에러가 발생 하였습니다.");
            return "/board/boardForm";
        }
        return "redirect:/";
    }

    @GetMapping(value = {"/admin/boards","/admin/boards/{page}"})
    public String boardManage(BoardSearchDto boardSearchDto, @PathVariable("page")Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent()? page.get() : 0,5);
        Page<Board> boards = boardService.getAdminBoardPage(boardSearchDto,pageable);
        model.addAttribute("boards", boards);
        model.addAttribute("boardSearchDto", boardSearchDto);
        model.addAttribute("maxPage", 5);
        return "board/boardMng";
    }
    //게시판
    @GetMapping(value = {"/boards/information","/boards/information/{page}"})
    public String boardUser(BoardSearchDto boardSearchDto, @PathVariable("page")Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent()? page.get() : 0,10);
        Page<Board> boards = boardService.getAdminBoardPage(boardSearchDto,pageable);
        model.addAttribute("boards", boards);
        model.addAttribute("boardSearchDto", boardSearchDto);
        model.addAttribute("maxPage", 10);
        return "information/info";
    }
    //세부내용
    @GetMapping(value = "/info/{boardId}")
    public String massageDtl(Model model, @PathVariable("boardId") Long boardId){
        BoardFormDto boardFormDto = boardService.getBoardDtl(boardId);
        model.addAttribute("board",boardFormDto);
        return "information/infoDtl";
    }
}
