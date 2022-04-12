package com.hanviet.repository;

import com.hanviet.dto.MainStoreDto;
import com.hanviet.dto.StoreSearchDto;
import com.hanviet.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoreRepositoryCustom {
    Page<Store> getAdminStorePage(StoreSearchDto storeSearchDto, Pageable pageable);

    Page<MainStoreDto> getMainStorePage(StoreSearchDto storeSearchDto, Pageable pageable);
}
