package com.trollmarket.dao;

import com.trollmarket.dto.merchandise.ProductMerchandiseGridDTO;
import com.trollmarket.dto.merchandise.ProductMerchandiseInfoDTO;
import com.trollmarket.dto.shop.ProductShopGridDTO;
import com.trollmarket.dto.shop.ProductShopInfoDTO;
import com.trollmarket.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    /*Query for Merchandise Menu Seller*/
    @Query("""
            SELECT new com.trollmarket.dto.merchandise.ProductMerchandiseGridDTO(
                pro.id, pro.sellerId, pro.name, cat.name, pro.discontinue)
            FROM Product AS pro
                JOIN pro.category AS cat
                JOIN pro.seller AS sel
            WHERE sel.username = :username
            """)
    Page<ProductMerchandiseGridDTO> findBySellerUsername(@Param("username") String username,
                                                         Pageable pageable);

    @Query("""
            SELECT new com.trollmarket.dto.merchandise.ProductMerchandiseInfoDTO(
                pro.id, pro.name, cat.name, pro.description, pro.price, pro.discontinue)
            FROM Product AS pro
                JOIN pro.category AS cat
                JOIN pro.seller AS sel
            WHERE pro.id = :id
            """)
    ProductMerchandiseInfoDTO findMerchandiseInfoProduct(@Param("id") Long id);
    @Query("""
            SELECT COUNT(pro.id)
            FROM Order AS ord
                JOIN ord.product AS pro
            WHERE pro.id = :id
            """)
    Long countDependencies(@Param("id") Long id);
    /*----------------------------------------------------------------------------*/


    /*Query for Shop Menu Buyer*/
    @Query("""
            SELECT new com.trollmarket.dto.shop.ProductShopGridDTO(
                pro.id, pro.name, pro.price)
            FROM Product AS pro
                JOIN pro.category AS cat
            WHERE pro.discontinue = FALSE
                AND pro.name LIKE %:name%
                AND cat.name LIKE %:category%
                AND pro.description LIKE %:description%
            """)
    Page<ProductShopGridDTO> findAllShopProduct(@Param("name") String name,
                                                @Param("category") String category,
                                                @Param("description") String description,
                                                Pageable pageable);

    @Query("""
            SELECT new com.trollmarket.dto.shop.ProductShopInfoDTO(
                pro.id, pro.name, cat.name, pro.description, pro.price, 
                sel.name)
            FROM Product AS pro
                JOIN pro.category AS cat
                JOIN pro.seller AS sel
            WHERE pro.id = :id
            """)
    ProductShopInfoDTO findShopInfoProduct(@Param("id") Long id);
    /*----------------------------------------------------------------------------*/

}
