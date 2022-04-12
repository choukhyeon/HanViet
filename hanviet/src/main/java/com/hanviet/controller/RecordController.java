package com.shop.controller;

import com.shop.dto.RecordDto;
import com.shop.dto.RecordHistDto;
import com.shop.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @PostMapping(value = "/record")
    public @ResponseBody ResponseEntity record(@RequestBody @Valid RecordDto recordDto, BindingResult bindingResult, Principal principal){

        if (bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors){
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String email = principal.getName();
        Long recordId;

        try{
            recordId = recordService.record(recordDto,email);
        }catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(recordId,HttpStatus.OK);
    }

    @GetMapping(value = {"/records", "/records/{page}"})
    public String recordHist(@PathVariable("page")Optional<Integer> page, Principal principal, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0,4);

        Page<RecordHistDto> recordHistDtoList = recordService.getRecordList(principal.getName(), pageable);

        model.addAttribute("records", recordHistDtoList);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", 5);
        return "record/recordHist";
    }

}
