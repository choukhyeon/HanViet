package com.shop.controller;


import com.shop.dto.*;
import com.shop.entity.Item;
import com.shop.entity.Store;
import com.shop.service.StoreService;
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
public class StoreController {

    private final StoreService storeService;

    @GetMapping(value = "/admin/store/new")
    public String storeForm(Model model){
        model.addAttribute("storeFormDto", new StoreFormDto());
        return "store/storeForm";
    }

    @PostMapping(value = "/admin/store/new")
    public String storeNew(@Valid StoreFormDto storeFormDto , BindingResult bindingResult, Model model,
                           @RequestParam("storeImgFile")List<MultipartFile> storeImgFileList){

        if(bindingResult.hasErrors()){
            return "store/storeForm";
        }
        if(storeImgFileList.get(0).isEmpty() && storeFormDto.getId() == null){
            model.addAttribute("errorMessage", "첫번째 가게 이미지는 필수 입력 값입니다.");
        }
        try{
            storeService.saveStore(storeFormDto,storeImgFileList);
        }catch (Exception e){
            model.addAttribute("errorMessage", "가게 등록중 오류가 발생 하였습니다.");
            return "store/storeForm";
        }
        return "redirect:/";
    }

    @GetMapping(value = "admin/store/{storeId}")
    public String storeDtl(@PathVariable("storeId") Long storeId, Model model){

        try{
            StoreFormDto storeFormDto = storeService.getStoreDtl(storeId);
            model.addAttribute("storeFormDto", storeFormDto);
        }catch (EntityNotFoundException e){
            model.addAttribute("errorMessage","존재하지 않는 가게입니다.");
            model.addAttribute("storeFormDto", new StoreFormDto());
            return "store/storeForm";
        }
        return "store/storeForm";
    }

    @PostMapping(value = "admin/store/{storeId}")
    public String storeUpdate(@Valid StoreFormDto storeFormDto, BindingResult bindingResult, @RequestParam("storeImgFile") List<MultipartFile>
                              storeImgFileList, Model model){
        if(bindingResult.hasErrors()){
            return "store/storeForm";
        }
        if (storeImgFileList.get(0).isEmpty() && storeFormDto.getId() == null){
            model.addAttribute("errorMessage", "첫번째 가게 이미지는 필수 입니다.");
            return "store/storeForm";
        }
        try{
            storeService.updateStore(storeFormDto,storeImgFileList);
        }catch (Exception e){
            model.addAttribute("errorMessage","가게 이미지 수정중 오류가 발생하였습니다.");
            return "store/storeForm";
        }
        return "redirect:/";
    }

    @GetMapping(value = {"/admin/stores","/admin/stores/{page}"})
    public String storeManage(StoreSearchDto storeSearchDto, @PathVariable("page") Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
        Page<Store> stores = storeService.getAdminStorePage(storeSearchDto, pageable);
        model.addAttribute("stores", stores);
        model.addAttribute("storeSearchDto", storeSearchDto);
        model.addAttribute("maxPage", 5);
        return "store/storeMng";
    }

    @GetMapping(value = "/store/{storeId}")
    public String storeDtl(Model model, @PathVariable("storeId") Long storeId){
        StoreFormDto storeFormDto = storeService.getStoreDtl(storeId);
        model.addAttribute("store",storeFormDto);
        return "store/storeDtl";
    }
}
