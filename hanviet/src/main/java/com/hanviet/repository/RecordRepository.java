package com.shop.repository;

import com.shop.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {

    @Query("select o from Record o " +
            "where o.member.email = :email " +
            "order by o.recordDate desc"
    )
    List<Record> findRecords(@Param("email") String email, Pageable pageable);

    @Query("select count(o) from Record o " +
            "where o.member.email = :email"
    )
    Long countRecord(@Param("email") String email);
}
