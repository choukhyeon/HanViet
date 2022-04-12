package com.hanviet.repository;

import com.hanviet.entity.RecordItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordItemRepository extends JpaRepository<RecordItem, Long> {
}
