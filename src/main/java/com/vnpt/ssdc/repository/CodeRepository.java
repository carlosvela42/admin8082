package com.vnpt.ssdc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vnpt.ssdc.dto.Code;
import com.vnpt.ssdc.dto.Packages;

public interface CodeRepository extends JpaRepository<Code, Long> {

}
