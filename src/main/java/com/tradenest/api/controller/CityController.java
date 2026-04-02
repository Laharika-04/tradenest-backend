package com.tradenest.api.controller;

import com.tradenest.api.entity.City;
import com.tradenest.api.service.CityService;
import com.tradenest.api.util.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    private final CityService service;

    public CityController(CityService service) {
        this.service = service;
    }

    @GetMapping
    public ApiResponse<List<City>> getAll() {
        return ApiResponse.ok(service.getAll());
    }

    @GetMapping("/state/{state}")
    public ApiResponse<List<City>> getByState(@PathVariable String state) {
        return ApiResponse.ok(service.getByState(state));
    }

}