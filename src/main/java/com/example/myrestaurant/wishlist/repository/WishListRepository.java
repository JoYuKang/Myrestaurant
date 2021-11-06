package com.example.myrestaurant.wishlist.repository;

import com.example.myrestaurant.db.MemoryDbRepositoryAbstract;
import com.example.myrestaurant.wishlist.entity.WishListEntity;
import org.springframework.stereotype.Repository;

@Repository //jpa Repository 나중에 변경하기
public class WishListRepository extends MemoryDbRepositoryAbstract<WishListEntity> {



}
