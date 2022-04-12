package com.shop.service;

import com.shop.entity.StoreImg;
import com.shop.repository.StoreImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreImgService {
    @Value("${storeImgLocation}")
    private String storeImgLocation;

    private final StoreImgRepository storeImgRepository;

    private final FileService fileService;

    public void saveStoreImg(StoreImg storeImg, MultipartFile storeImgFile) throws Exception{
        String storeOriImgName = storeImgFile.getOriginalFilename();
        String storeImgName = "";
        String storeImgUrl = "";

        //파일 업로드
        if(!StringUtils.isEmpty(storeOriImgName)){
            storeImgName = fileService.uploadFile(storeImgLocation,storeOriImgName,storeImgFile.getBytes());
            storeImgUrl = "/images/store/"+storeImgName;
        }

        //가게 이미지 정보 저장
        storeImg.updateStoreImg(storeOriImgName,storeImgName,storeImgUrl);
        storeImgRepository.save(storeImg);
    }

    public void updateStoreImg(Long storeImgId, MultipartFile storeImgFile) throws Exception{
        if (!storeImgFile.isEmpty()){
            StoreImg savedStoreImg = storeImgRepository.findById(storeImgId).orElseThrow(EntityNotFoundException::new);

            //기존 이미지 파일 삭제
            if(!StringUtils.isEmpty(savedStoreImg.getStoreImgName())){
                fileService.deleteFile(storeImgLocation+"/"+savedStoreImg.getStoreImgName());
            }
            String storeOriImgName = storeImgFile.getOriginalFilename();
            String storeImgName = fileService.uploadFile(storeImgLocation,storeOriImgName,storeImgFile.getBytes());
            String storeImgUrl = "/images/store/"+storeImgName;
            savedStoreImg.updateStoreImg(storeOriImgName,storeImgName,storeImgUrl);
        }
    }
}
