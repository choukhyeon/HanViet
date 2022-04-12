package com.shop.repository;

import com.shop.entity.RecordItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordItemRepository extends JpaRepository<RecordItem, Long> {
}
