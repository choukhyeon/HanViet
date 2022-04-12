package com.hanviet.service;

import com.hanviet.constant.Location;
import com.hanviet.constant.Category;
import com.hanviet.dto.StoreFormDto;
import com.hanviet.entity.Store;
import com.hanviet.entity.StoreImg;
import com.hanviet.repository.StoreImgRepository;
import com.hanviet.repository.StoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class StoreServiceTest {

    @Autowired
    StoreService storeService;
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    StoreImgRepository storeImgRepository;

    List<MultipartFile> createMultipartFiles() throws Exception{
        List<MultipartFile> multipartFileList = new ArrayList<>();

        for (int i=0;i<5;i++){
            String path = "D:/hanviet/store/";
            String imageName = "image"+".jpg";
            MockMultipartFile multipartFile = new MockMultipartFile(path, imageName,"image/jpg", new byte[]{1,2,3,4});
            multipartFileList.add(multipartFile);
        }
        return multipartFileList;
    }

    @Test
    @DisplayName("가게 등록 테스트")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void saveStore() throws Exception{
        StoreFormDto storeFormDto = new StoreFormDto();
        storeFormDto.setStoreNm("테스트 가게");
        storeFormDto.setStoreDetail("테스트 내용");
        storeFormDto.setStoreAddress("테스트 주소");
        storeFormDto.setStoreLocation(Location.HANOI);
        storeFormDto.setStoreCategory(Category.HOTEL);

        List<MultipartFile> multipartFileList = createMultipartFiles();
        Long storeId = storeService.saveStore(storeFormDto,multipartFileList);

        List<StoreImg> storeImgList = storeImgRepository.findByStoreIdOrderByIdAsc(storeId);
        Store store = storeRepository.findById(storeId).orElseThrow(EntityNotFoundException::new);

        assertEquals(storeFormDto.getStoreNm(),store.getStoreNm());
        assertEquals(storeFormDto.getStoreDetail(),store.getStoreDetail());
        assertEquals(storeFormDto.getStoreAddress(),store.getStoreAddress());
        assertEquals(storeFormDto.getStoreLocation(),store.getStoreLocation());
        assertEquals(storeFormDto.getStoreCategory(),store.getStoreCategory());
        assertEquals(multipartFileList.get(0).getOriginalFilename(),storeImgList.get(0).getStoreOriImgName());
    }
}
