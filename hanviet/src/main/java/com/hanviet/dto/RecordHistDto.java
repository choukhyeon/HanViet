package com.shop.dto;

import com.shop.entity.Record;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RecordHistDto {

    public RecordHistDto(Record record){
        this.recordId = record.getId();
        this.recordDate =record.getRecordDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

    }

    private Long recordId;

    private String recordDate;


    private List<RecordItemDto> recordItemDtoList = new ArrayList<>();

    public void addRecordItemDto(RecordItemDto recordItemDto){
        recordItemDtoList.add(recordItemDto);
    }
}
