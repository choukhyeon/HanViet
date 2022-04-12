package com.hanviet.service;

import com.hanviet.dto.RecordDto;
import com.hanviet.dto.RecordHistDto;
import com.hanviet.dto.RecordItemDto;
import com.hanviet.entity.*;
import com.hanviet.repository.ItemImgRepository;
import com.hanviet.repository.ItemRepository;
import com.hanviet.repository.MemberRepository;
import com.hanviet.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RecordService {
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final RecordRepository recordRepository;
    private final ItemImgRepository itemImgRepository;

    public Long record(RecordDto recordDto, String email){
        Item item = itemRepository.findById(recordDto.getItemId()).orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByEmail(email);

        List<RecordItem> recordItemList = new ArrayList<>();
        RecordItem recordItem = RecordItem.createRecordItem(item, recordDto.getCount());
        recordItemList.add(recordItem);

        Record record = Record.createRecord(member, recordItemList);
        recordRepository.save(record);

        return record.getId();
    }

    @Transactional(readOnly = true)
    public Page<RecordHistDto> getRecordList(String email, Pageable pageable){

        List<Record> records = recordRepository.findRecords(email, pageable);
        Long totalCount = recordRepository.countRecord(email);

        List<RecordHistDto> recordHistDtos = new ArrayList<>();

        for (Record record : records){
            RecordHistDto recordHistDto = new RecordHistDto(record);
            List<RecordItem> recordItems = record.getRecordItems();
            for (RecordItem recordItem : recordItems){
                ItemImg itemImg = itemImgRepository.findByItemIdAndRepImgYn(recordItem.getItem().getId(),"Y");
                RecordItemDto recordItemDto = new RecordItemDto(recordItem, itemImg.getImgUrl());
                recordHistDto.addRecordItemDto(recordItemDto);
            }
            recordHistDtos.add(recordHistDto);
        }
        return new PageImpl<RecordHistDto>(recordHistDtos, pageable, totalCount);
    }

    public Long records(List<RecordDto> recordDtoList, String email){

        Member member = memberRepository.findByEmail(email);
        List<RecordItem> recordItemList = new ArrayList<>();

        for (RecordDto recordDto : recordDtoList){
            Item item = itemRepository.findById(recordDto.getItemId()).orElseThrow(EntityNotFoundException::new);

            RecordItem recordItem = RecordItem.createRecordItem(item, recordDto.getCount());
            recordItemList.add(recordItem);
        }
        Record record = Record.createRecord(member, recordItemList);
        recordRepository.save(record);
        return record.getId();

    }
}
