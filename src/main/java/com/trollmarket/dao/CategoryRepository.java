package com.trollmarket.dao;

import com.trollmarket.dto.utility.DropdownDTO;
import com.trollmarket.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("""
            SELECT new com.trollmarket.dto.utility.DropdownDTO(
                cat.id, cat.name)
            FROM Category as cat
            """)
    List<DropdownDTO> getDropdown();
}
