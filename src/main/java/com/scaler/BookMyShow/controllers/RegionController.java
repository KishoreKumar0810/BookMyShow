package com.scaler.BookMyShow.controllers;

import com.scaler.BookMyShow.dtos.RegionDto;
import com.scaler.BookMyShow.services.RegionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/regions")
@PreAuthorize("hasRole('ADMIN')")
public class RegionController {
    private final RegionService regionService;

    public RegionController(RegionService regionService){
        this.regionService = regionService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<RegionDto> getRegionById(@PathVariable("id") Long id){
        return ResponseEntity.ok(regionService.getRegionById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<RegionDto> getRegionByName(@PathVariable("name") String name){
        return ResponseEntity.ok(regionService.getRegionByName(name));
    }

    @GetMapping("/all")
    public ResponseEntity<List<RegionDto>> getAllRegions(){
        return ResponseEntity.ok(regionService.getAllRegions());
    }

    @PostMapping("/create")
    public ResponseEntity<RegionDto> createRegion(@RequestBody RegionDto regionDto){
        return ResponseEntity.ok(regionService.createRegion(regionDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RegionDto> updateRegion(@PathVariable("id") Long id, @RequestBody RegionDto regionDto){
        return ResponseEntity.ok(regionService.updateRegion(id, regionDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegion(@PathVariable("id") Long id){
        regionService.deleteRegion(id);
        return ResponseEntity.noContent().build();
    }
}
