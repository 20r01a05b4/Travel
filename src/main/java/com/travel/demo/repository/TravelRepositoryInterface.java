package com.travel.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.demo.model.Agency;

public interface TravelRepositoryInterface  extends JpaRepository<Agency, Integer>{

}
