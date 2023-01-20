package com.trollmarket.dao;

import com.trollmarket.dto.utility.DropdownDTO;
import com.trollmarket.dto.profile.ProfileInfoDTO;
import com.trollmarket.entity.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {

    @Query("""
            SELECT new com.trollmarket.dto.profile.ProfileInfoDTO(
                buy.id, buy.username, buy.name, buy.address, buy.balance)
            FROM Buyer AS buy
            WHERE username = :username
            """)
    ProfileInfoDTO getProfile(@Param("username") String username);

    @Query("""
            SELECT new com.trollmarket.dto.utility.DropdownDTO(
                buy.name, buy.name)
            FROM Buyer AS buy
            """)
    List<DropdownDTO> getBuyerDropdown();

    @Query("""
            SELECT buy.id
            FROM Buyer AS buy
            WHERE username = :username
            """)
    Long getBuyerId(@Param("username") String username);
}
