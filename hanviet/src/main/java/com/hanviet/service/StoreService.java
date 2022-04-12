package com.shop.service;

import com.shop.dto.*;
import com.shop.entity.Store;
import com.shop.entity.StoreImg;
import com.shop.repository.StoreImgRepository;
import com.shop.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final StoreImgService storeImgService;
    private final StoreImgRepository storeImgRepository;

    public Long saveStore(StoreFormDto storeFormDto, List<MultipartFile> storeImgFileList) throws Exception{
        //가게 등록
        Store store = storeFormDto.createStore();
        storeRepository.save(store);

        //이미지 등록
        for (int i=0;i<storeImgFileList.size();i++){
            StoreImg storeImg = new StoreImg();
            storeImg.setStore(store);
            if(i==0)
                storeImg.setStoreRepImgYn("Y");
            else
                storeImg.setStoreRepImgYn("N");
            storeImgService.saveStoreImg(storeImg,storeImgFileList.get(i));
        }
        return store.getId();
    }

    @Transactional(readOnly = true)
    public StoreFormDto getStoreDtl(Long storeId){

        List<StoreImg> storeImgList = storeImgRepository.findByStoreIdOrderByIdAsc(storeId);
        List<StoreImgDto> storeImgDtoList = new ArrayList<>();
        for (StoreImg storeImg: storeImgList){
            StoreImgDto storeImgDto = StoreImgDto.of(storeImg);
            storeImgDtoList.add(storeImgDto);
        }

        Store store = storeRepository.findById(storeId).orElseThrow(EntityNotFoundException::new);
        StoreFormDto storeFormDto = StoreFormDto.of(store);
        storeFormDto.setStoreImgDtoList(storeImgDtoList);
        return storeFormDto;
    }

    public Long updateStore(StoreFormDto storeFormDto, List<MultipartFile> storeImgFileList) throws Exception{

        //가게 수정
        Store store = storeRepository.findById(storeFormDto.getId()).orElseThrow(EntityNotFoundException::new);
        store.updateStore(storeFormDto);

        List<Long> storeImgIds = storeFormDto.getStoreImgIds();

        //이미지 등록
        for (int i=0;i<storeImgFileList.size();i++){
            storeImgService.updateStoreImg(storeImgIds.get(i),storeImgFileList.get(i));
        }
        return store.getId();
    }

    @Transactional(readOnly = true)
    public Page<MainStoreDto> getMainStorePage(StoreSearchDto storeSearchDto, Pageable pageable) {
        return storeRepository.getMainStorePage(storeSearchDto,pageable);
    }

    public Page<Store> getAdminStorePage(StoreSearchDto storeSearchDto, Pageable pageable) {
        return storeRepository.getAdminStorePage(storeSearchDto,pageable);
    }

}
