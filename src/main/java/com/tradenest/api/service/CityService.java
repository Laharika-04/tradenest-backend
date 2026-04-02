package com.tradenest.api.service;

import com.tradenest.api.entity.City;
import com.tradenest.api.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    private final CityRepository repo;

    public CityService(CityRepository repo) {
        this.repo = repo;
    }

    public List<City> getAll() {
        return repo.findAll();
    }

    public List<City> getByState(String state) {
        return repo.findByState(state);
    }

}