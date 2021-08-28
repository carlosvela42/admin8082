package com.vnpt.ssdc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vnpt.ssdc.dto.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
