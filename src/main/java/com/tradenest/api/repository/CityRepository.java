package com.tradenest.api.repository;

import com.tradenest.api.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {

    List<City> findByState(String state);

}