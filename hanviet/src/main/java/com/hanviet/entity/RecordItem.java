package com.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class RecordItem extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "record_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id")
    private Record record;

    private int recordPrice;

    private int count;

    public static RecordItem createRecordItem(Item item, int count){
        RecordItem recordItem = new RecordItem();
        recordItem.setItem(item);
        recordItem.setCount(count);
        recordItem.setRecordPrice(item.getPrice());

        item.removeStock(count);
        return recordItem;
    }

    public int getTotalPrice(){
        return recordPrice * count;
    }


}
