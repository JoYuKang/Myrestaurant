package com.example.myrestaurant.wishlist.repository;


import com.example.myrestaurant.wishlist.entity.WishListEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WishListRepositoryTest {

    @Autowired
    private WishListRepository wishListRepository;

    private WishListEntity create() {
        var wishList = new WishListEntity();
        wishList.setTitle("title");
        wishList.setAddress("address");
        wishList.setCategory("category");
        wishList.setImageLink("imageLink");
        wishList.setHomePageLink("pageLink");
        wishList.setLastVisitDate(null);
        wishList.setRoadAddress("roadAddress");
        wishList.setVisit(false);
        wishList.setVisitCount(0);
        return wishList;
    }

    @Test
    public void saveTest() {
        var WishListEntity = create();
        var expected = wishListRepository.save(WishListEntity);

        Assertions.assertNotNull(expected);
        Assertions.assertEquals(1, expected.getIndex());


    }
    @Test
    public void updateTest() {
        var WishListEntity = create();
        var expected = wishListRepository.save(WishListEntity);

        expected.setTitle("update test");
        var saveEntity = wishListRepository.save(expected);

        Assertions.assertEquals("update test",saveEntity.getTitle());
        Assertions.assertEquals(1, wishListRepository.findAll().size());


    }

    @Test
    public void findByIdTest() {
        var WishListEntity = create();
        wishListRepository.save(WishListEntity);

        var expected = wishListRepository.findById(1);

        Assertions.assertTrue(expected.isPresent());
        Assertions.assertEquals(1, expected.get().getIndex());

    }

    @Test
    public void deleteTest() {
        var WishListEntity = create();
        wishListRepository.save(WishListEntity);

        wishListRepository.deleteById(1);
        int count = wishListRepository.findAll().size();

        Assertions.assertEquals(0, count);

    }

    @Test
    public void listAllTest() {
        var WishListEntity1 = create();
        wishListRepository.save(WishListEntity1);

        var WishListEntity2 = create();
        wishListRepository.save(WishListEntity2);

        int count = wishListRepository.findAll().size();

        Assertions.assertEquals(2, count);
    }

}
