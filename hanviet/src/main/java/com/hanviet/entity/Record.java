package com.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "records")
@Getter @Setter
public class Record extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "record_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime recordDate; //기록일

    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecordItem> recordItems = new ArrayList<>();

    public void addRecordItem(RecordItem recordItem){
        recordItems.add(recordItem);
        recordItem.setRecord(this);
    }

    public static Record createRecord(Member member, List<RecordItem> recordItemList){
        Record record = new Record();
        record.setMember(member);
        for (RecordItem recordItem : recordItemList){
            record.addRecordItem(recordItem);
        }
        record.setRecordDate(LocalDateTime.now());
        return record;
    }

    public int getTotalPrice(){
        int totalPrice = 0;
        for (RecordItem recordItem : recordItems){
            totalPrice += recordItem.getTotalPrice();
        }
        return totalPrice;
    }

}
