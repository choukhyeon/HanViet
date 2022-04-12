package com.shop.repository;

import com.querydsl.core.BooleanBuilder;
import com.shop.constant.Location;
import com.shop.constant.Category;
import com.shop.entity.QStore;
import com.shop.entity.Store;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class StoreRepositoryTest {

    @Autowired
    StoreRepository storeRepository;

    public void createStoreList(){
        for (int i=1; i<=10; i++){
            Store store = new Store();
            store.setStoreNm("테스트 가게"+i);
            store.setStoreDetail("가게 상세 내용"+i);
            store.setStoreAddress("가게 주소"+i);
            store.setStoreLocation(Location.HANOI);
            store.setStoreCategory(Category.HOTEL);
            Store savedStore = storeRepository.save(store);
        }
    }

    @Test
    @DisplayName("가게명 조회")
    public void findByStoreNmTest(){
        this.createStoreList();
        List<Store> storeList = storeRepository.findByStoreNm("테스트 가게1");
        for (Store store : storeList){
            System.out.println(store.toString());
        }
    }



    public void createStoreList2(){
        for (int i=1; i<=5; i++){
            Store store = new Store();
            store.setStoreNm("테스트 가게"+i);
            store.setStoreDetail("가게 상세 내용"+i);
            store.setStoreAddress("가게 주소"+i);
            store.setStoreLocation(Location.HANOI);
            store.setStoreCategory(Category.HOTEL);
            Store savedStore = storeRepository.save(store);
        }
        for (int i=6; i<=10; i++){
            Store store = new Store();
            store.setStoreNm("테스트 가게"+i);
            store.setStoreDetail("가게 상세 내용"+i);
            store.setStoreAddress("가게 주소"+i);
            store.setStoreLocation(Location.BACNHIN);
            store.setStoreCategory(Category.MASSAGE);
            Store savedStore = storeRepository.save(store);
        }
    }

    @Test
    @DisplayName("가게 Querydsl 조회 테스트")
    public void queryDslTest(){
        this.createStoreList2();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QStore store = QStore.store;
        String storeDetail = "테스트 가게 상세 설명";
        String boardLocation = "HANOI";

        booleanBuilder.and(store.storeDetail.like("%"+storeDetail+"%"));

        if (StringUtils.equals(boardLocation,Location.HANOI)){
            booleanBuilder.and(store.storeLocation.eq(Location.HANOI));
        }

        Pageable pageable = PageRequest.of(0,5);
        Page<Store> storePagingResult = storeRepository.findAll(booleanBuilder,pageable);
        System.out.println("total elements:"+ storePagingResult.getTotalElements());

        List<Store> resultStoreList = storePagingResult.getContent();
        for (Store resultStore : resultStoreList){
            System.out.println(resultStore.toString());
        }

    }
}
