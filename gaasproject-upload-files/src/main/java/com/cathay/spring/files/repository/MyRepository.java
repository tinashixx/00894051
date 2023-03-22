package com.cathay.spring.files.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cathay.spring.files.entity.MyEntity;

public interface MyRepository extends JpaRepository<MyEntity, Long> {
}
