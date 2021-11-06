package com.example.myrestaurant.wishlist.service;

import com.example.myrestaurant.naver.NaverClient;
import com.example.myrestaurant.naver.dto.SearchImageReq;
import com.example.myrestaurant.naver.dto.SearchLocalReq;
import com.example.myrestaurant.wishlist.dto.WishListDto;
import com.example.myrestaurant.wishlist.entity.WishListEntity;
import com.example.myrestaurant.wishlist.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishListService {

    // 네이버 아이디 비번 가져오기
    private final NaverClient naverClient;
    // 디비 가져오기
    private final WishListRepository wishListRepository;

    public WishListDto search(String query) {

        //지역 검색
        var searchLocalReq = new SearchLocalReq();
        searchLocalReq.setQuery(query);

        var searchLocalRes = naverClient.searchLocal(searchLocalReq);
        if (searchLocalRes.getTotal() > 0) {
            // 첫번쩨 아이템 꺼내기
            var localItem = searchLocalRes.getItems().stream().findFirst().get();
            // 정규식 사용해서 문자만 나오게 정리
            var imageQuery = localItem.getTitle().replaceAll("<[^>]*>", "");

            var searchImageReq = new SearchImageReq();

            searchImageReq.setQuery(imageQuery);
            //이미지 검색
            var searchImageRes = naverClient.searchImage(searchImageReq);

            if (searchImageRes.getTotal() > 0) {
                var imageItem = searchImageRes.getItems().stream().findFirst().get();

                // 값이 존재한다면 결과를 리턴
                var result = new WishListDto();
                result.setTitle(localItem.getTitle());
                result.setCategory(localItem.getCategory());
                result.setAddress(localItem.getAddress());
                result.setRoadAddress(localItem.getRoadAddress());
                result.setHomePageLink(localItem.getLink());
                result.setImageLink(imageItem.getLink());

                return result;
            }

        }

        return new WishListDto();
    }


    public WishListDto add(WishListDto wishListDto) {
        var entity = dtoToEntity(wishListDto);

        var saveEntity = wishListRepository.save(entity);

        return entityToDto(saveEntity);
    }

    private WishListEntity dtoToEntity(WishListDto wishListDto) {
        var entity = new WishListEntity();
        entity.setIndex(wishListDto.getIndex());
        entity.setTitle(wishListDto.getTitle());
        entity.setCategory(wishListDto.getCategory());
        entity.setAddress(wishListDto.getAddress());
        entity.setHomePageLink(wishListDto.getHomePageLink());
        entity.setImageLink(wishListDto.getImageLink());
        entity.setRoadAddress(wishListDto.getRoadAddress());
        entity.setVisit(wishListDto.isVisit());
        entity.setVisitCount(wishListDto.getVisitCount());
        entity.setLastVisitDate(wishListDto.getLastVisitDate());
        return entity;
    }

    private WishListDto entityToDto(WishListEntity wishListEntity) {
        var dto = new WishListDto();
        dto.setIndex(wishListEntity.getIndex());
        dto.setTitle(wishListEntity.getTitle());
        dto.setCategory(wishListEntity.getCategory());
        dto.setAddress(wishListEntity.getAddress());
        dto.setHomePageLink(wishListEntity.getHomePageLink());
        dto.setImageLink(wishListEntity.getImageLink());
        dto.setRoadAddress(wishListEntity.getRoadAddress());
        dto.setVisit(wishListEntity.isVisit());
        dto.setVisitCount(wishListEntity.getVisitCount());
        dto.setLastVisitDate(wishListEntity.getLastVisitDate());

        return dto;
    }

    public List<WishListDto> findAll() {

        return wishListRepository.findAll()
                .stream()
                .map(it -> entityToDto(it)).
                collect(Collectors.toList());
    }

    public void delete(int index) {
        wishListRepository.deleteById(index);
    }

    public void addVisit(int index){
        var wishItem = wishListRepository.findById(index);
        // 값이 있을 때만
        if(wishItem.isPresent()){
            var item = wishItem.get();
            item.setVisit(true);
            item.setVisitCount(item.getVisitCount()+1);

        }
    }
}
