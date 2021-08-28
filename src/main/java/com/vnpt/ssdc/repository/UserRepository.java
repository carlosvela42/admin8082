package com.vnpt.ssdc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vnpt.ssdc.dto.Packages;
import com.vnpt.ssdc.dto.Users;

public interface UserRepository extends JpaRepository<Users, Long> {

}
