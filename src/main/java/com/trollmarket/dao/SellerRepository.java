package com.trollmarket.dao;

import com.trollmarket.dto.utility.DropdownDTO;
import com.trollmarket.dto.profile.ProfileInfoDTO;
import com.trollmarket.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SellerRepository extends JpaRepository<Seller, Long> {

    @Query("""
            SELECT new com.trollmarket.dto.profile.ProfileInfoDTO(
                sel.id, sel.username, sel.name, sel.address, sel.balance)
            FROM Seller AS sel
            WHERE username = :username
            """)
    ProfileInfoDTO getProfile(@Param("username") String username);

    @Query("""
            SELECT sel.id
            FROM Seller AS sel
            WHERE username = :username
            """)
    Long getSellerId(@Param("username") String username);

    @Query("""
            SELECT new com.trollmarket.dto.utility.DropdownDTO(
                sel.name, sel.name)
            FROM Seller AS sel
            """)
    List<DropdownDTO> getSellerDropdown();
}
