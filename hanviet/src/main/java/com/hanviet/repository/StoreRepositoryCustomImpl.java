package com.hanviet.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.hanviet.constant.Category;
import com.hanviet.constant.Location;
import com.hanviet.dto.MainStoreDto;
import com.hanviet.dto.QMainStoreDto;
import com.hanviet.dto.StoreSearchDto;
import com.hanviet.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class StoreRepositoryCustomImpl implements StoreRepositoryCustom{

    private JPAQueryFactory queryFactory;

    public StoreRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchStoreLocationEq(Location searchStoreLocation){
        return searchStoreLocation == null ? null : QStore.store.storeLocation.eq(searchStoreLocation);
    }
    private BooleanExpression searchStoreCategoryEq(Category searchStoreCategory){
        return searchStoreCategory == null ? null : QStore.store.storeCategory.eq(searchStoreCategory);
    }

    private BooleanExpression regDtsAfter(String searchDateType){
        LocalDateTime dateTime = LocalDateTime.now();

        if(StringUtils.equals("all",searchDateType) || searchDateType == null){
            return null;
        }else if(StringUtils.equals("1d",searchDateType)){
            dateTime = dateTime.minusDays(1);
        }else if(StringUtils.equals("1w",searchDateType)){
            dateTime = dateTime.minusWeeks(1);
        }else if(StringUtils.equals("1m",searchDateType)){
            dateTime = dateTime.minusMonths(1);
        }else if(StringUtils.equals("6m",searchDateType)){
            dateTime = dateTime.minusMonths(16);
        }
        return QStore.store.regTime.after(dateTime);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery){
        if(StringUtils.equals("storeNm",searchBy)){
            return QStore.store.storeNm.like("%"+searchQuery+"%");
        }else if(StringUtils.equals("createdBy",searchBy)){
            return QStore.store.createdBy.like("%"+searchQuery+"%");
        }
        return null;
    }

    @Override
    public Page<Store> getAdminStorePage(StoreSearchDto storeSearchDto, Pageable pageable) {
        QueryResults<Store> results = queryFactory
                .selectFrom(QStore.store)
                .where(regDtsAfter(storeSearchDto.getSearchDateType()),
                searchStoreLocationEq(storeSearchDto.getStoreLocation()),
                searchStoreCategoryEq(storeSearchDto.getStoreCategory()),
                searchByLike(storeSearchDto.getSearchBy(),
                storeSearchDto.getSearchQuery()))
                .orderBy(QStore.store.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<Store> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content,pageable,total);

    }

    private BooleanExpression storeNmLike(String searchQuery) {
        return StringUtils.isEmpty(searchQuery)? null : QStore.store.storeNm.like("%"+searchQuery+"%");
    }

    @Override
    public Page<MainStoreDto> getMainStorePage(StoreSearchDto storeSearchDto, Pageable pageable) {
        QStore store = QStore.store;
        QStoreImg storeImg = QStoreImg.storeImg;
        QueryResults<MainStoreDto> results = queryFactory.select(
                        new QMainStoreDto(
                                store.id,
                                store.storeNm,
                                store.storeDetail,
                                storeImg.storeImgUrl,
                                store.storeLocation,
                                store.storeCategory,
                                store.storeOpenStatus)
                )
                .from(storeImg)
                .join(storeImg.store, store)
                .where(storeImg.storeRepImgYn.eq("Y"))
                .where(storeNmLike(storeSearchDto.getSearchQuery()))
                .orderBy(store.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<MainStoreDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content,pageable,total);
    }


}
