package com.trollmarket.dao;

import com.trollmarket.dto.shipper.ShipperGridDTO;
import com.trollmarket.dto.utility.DropdownDTO;
import com.trollmarket.entity.Shipper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShipperRepository extends JpaRepository<Shipper, Long> {

    @Query("""
            SELECT new com.trollmarket.dto.shipper.ShipperGridDTO(
                ship.id, ship.name, ship.price, ship.isActive)
            FROM Shipper AS ship
            """)
    Page<ShipperGridDTO> findAllShipper(Pageable pageable);

    @Query("""
            SELECT new com.trollmarket.dto.utility.DropdownDTO(
                ship.id, ship.name)
            FROM Shipper AS ship
            WHERE ship.isActive != FALSE
            """)
    List<DropdownDTO> getShipmentDropdown();

    @Query("""
            SELECT COUNT(ship.id)
            FROM Order AS ord
                JOIN ord.shipper AS ship
            WHERE ship.id = :id
            """)
    Long countDependencies(@Param("id") Long id);
}
