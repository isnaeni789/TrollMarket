package com.trollmarket.service;

import com.trollmarket.dao.*;
import com.trollmarket.dto.merchandise.ProductMerchandiseGridDTO;
import com.trollmarket.dto.merchandise.ProductMerchandiseInfoDTO;
import com.trollmarket.dto.product.SingleProductDTO;
import com.trollmarket.dto.product.UpsertProductDTO;
import com.trollmarket.dto.utility.DropdownDTO;
import com.trollmarket.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchandiseService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private BuyerRepository buyerRepository;
    
    /*Menu Merchandise for Seller*/
    public Page<ProductMerchandiseGridDTO> getProductMerchandiseGrid(Integer pageNumber){
        String username = getUserLogin();
        var pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("id"));
        return productRepository.findBySellerUsername(username, pageable);
    }
    
    public ProductMerchandiseInfoDTO getProductInfo(Long id){
        return productRepository.findMerchandiseInfoProduct(id);
    }
    
    public UpsertProductDTO getUpdate(Long id){
        var entity = productRepository.findById(id).get();
        return new UpsertProductDTO(entity.getId(), entity.getName(), entity.getSellerId(),
                entity.getCategoryId(), entity.getDescription(), entity.getPrice(),
                entity.getDiscontinue());
    }
    
    public SingleProductDTO saveProduct(UpsertProductDTO dto){
        Product entity = new Product(dto.getId(), dto.getName(),
                dto.getSellerId(), dto.getCategoryId(), dto.getDescription(),
                dto.getPrice(), dto.getDiscontinue());
        productRepository.save(entity);
        return SingleProductDTO.builder().id(entity.getId())
                .name(entity.getName()).sellerId(entity.getSellerId())
                .categoryId(entity.getCategoryId()).description(entity.getDescription())
                .price(entity.getPrice()).discontinue(entity.getDiscontinue())
                .build();
    }
    
    public Long delete(Long id){
        Long dependencies = getDependencies(id);
        if (dependencies < 1){ productRepository.deleteById(id); }
        return dependencies;
    }
    
    public Long getDependencies(Long id){ return productRepository.countDependencies(id); }
    public List<DropdownDTO> getCategoryDropdown(){
        return categoryRepository.getDropdown();
    }
    
    public void discontinue(Long id){
        Product product = productRepository.findById(id).get();
        product.setDiscontinue(true);
        productRepository.save(product);
    }
    
    public Long getSellerId() {
        String username = getUserLogin();
        return sellerRepository.getSellerId(username);
    }
    public Long getBuyerId() {
        String username = getUserLogin();
        return buyerRepository.getBuyerId(username);
    }
    private String getUserLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
