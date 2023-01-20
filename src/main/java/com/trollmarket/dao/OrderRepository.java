package com.trollmarket.dao;

import com.trollmarket.dto.cart.CartGridDTO;
import com.trollmarket.dto.history.HistoryGridDTO;
import com.trollmarket.dto.profile.ProfileTransactionHistory;
import com.trollmarket.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    /*----------------------Query for Profile Menu---------------------------------*/
    @Query("""
            SELECT new com.trollmarket.dto.profile.ProfileTransactionHistory(
                ord.purchaseDate, pro.name, ord.quantity, ship.name, ord.unitPrice, 
                ship.price)
            FROM Order AS ord
                JOIN ord.product AS pro
                JOIN ord.shipper AS ship
                JOIN pro.seller AS sel
                JOIN ord.buyer AS buy
            WHERE (buy.username = :username OR sel.username = :username)
                AND ord.purchaseDate != NULL
            """)
    Page<ProfileTransactionHistory> findProfileTransaction(@Param("username") String username, Pageable pageable);
    /*--------------------------------------------------------------------------------------------*/

    /*----------------------Query for Cart Menu Buyer---------------------------------*/
    @Query("""
            SELECT new com.trollmarket.dto.cart.CartGridDTO(
                ord.id, sel.id, pro.name, ord.quantity, ship.name, sel.name,
                pro.price, ship.price)
            FROM Order AS ord
                JOIN ord.product AS pro
                JOIN ord.shipper AS ship
                JOIN pro.seller AS sel
                JOIN ord.buyer AS buy
            WHERE buy.username = :username
                AND ord.purchaseDate IS NULL
            """)
    List<CartGridDTO> findByBuyer(@Param("username") String username);
    /*--------------------------------------------------------------------------------------------*/

    /*----------------------Query for History Menu Admin---------------------------------*/
    @Query("""
            SELECT new com.trollmarket.dto.history.HistoryGridDTO(
                ord.purchaseDate, sel.name, buy.name, pro.name, ord.quantity, 
                ship.name, ord.unitPrice, ship.price)
            FROM Order AS ord
                JOIN ord.product AS pro
                JOIN ord.shipper AS ship
                JOIN pro.seller AS sel
                JOIN ord.buyer AS buy
            WHERE ord.purchaseDate != NULL
                AND buy.name LIKE %:buyer%
                AND sel.name LIKE %:seller%
            """)
    Page<HistoryGridDTO> findHistory(@Param("buyer") String buyer, @Param("seller") String seller,
                                     Pageable pageable);
    /*--------------------------------------------------------------------------------------------*/


    /*----------------------Query for Check Add Cart Shop Menu Buyer---------------------------------*/
    @Query("""
            SELECT COUNT(ord.id)
            FROM Order AS ord
                JOIN ord.product AS pro
                JOIN ord.buyer AS buy
            WHERE ord.purchaseDate IS NULL
                AND buy.id = :buyerId
                AND pro.id = :productId
            """)
    Long countCart(@Param("buyerId") Long buyerId, @Param("productId") Long productId);

    @Query("""
            SELECT ord.id
            FROM Order AS ord
                JOIN ord.product AS pro
                JOIN ord.buyer AS buy
            WHERE ord.purchaseDate IS NULL
                AND buy.id = :buyerId
                AND pro.id = :productId
            """)
    Long orderId(@Param("buyerId") Long buyerId, @Param("productId") Long productId);

    /*--------------------------------------------------------------------------------------------*/

}
