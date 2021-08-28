package com.vnpt.ssdc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vnpt.ssdc.dto.Packages;

public interface PackageRepository extends JpaRepository<Packages, Long> {

}
