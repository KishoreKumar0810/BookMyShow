package com.scaler.BookMyShow.services;

import com.scaler.BookMyShow.dtos.RegionDto;
import com.scaler.BookMyShow.exceptions.RegionNotFoundException;
import com.scaler.BookMyShow.models.Region;
import com.scaler.BookMyShow.repositories.RegionRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RegionService {
    private final RegionRepository regionRepository;
    public RegionService(RegionRepository regionRepository){
        this.regionRepository = regionRepository;
    }

    public RegionDto getRegionById(Long id){
        Optional<Region> regionOptional = regionRepository.findById(id);
        if(regionOptional.isEmpty()){
            throw new RegionNotFoundException(id);
        }
        return convertToDto(regionOptional.get());
    }

    public RegionDto getRegionByName(String name){
        Optional<Region> regionOptional = regionRepository.findRegionByName(name);
        if(regionOptional.isEmpty()){
            throw new RegionNotFoundException(name);
        }
        return convertToDto(regionOptional.get());
    }

    public List<RegionDto> getAllRegions(){
        List<Region> regions = regionRepository.findAll();
        if(regions.isEmpty()){
            throw new RegionNotFoundException("No regions found");
        }
        return regions.stream()
                .map(this::convertToDto)
                .toList();
    }

    public RegionDto createRegion(RegionDto regionDto){
        Region region = new Region();
        region.setName(regionDto.getName());
        regionRepository.save(region);
        return convertToDto(region);
    }

    public RegionDto updateRegion(Long id, RegionDto regionDto){
        Optional<Region> regionOptional = regionRepository.findById(id);
        if(regionOptional.isEmpty()){
            throw new RegionNotFoundException(id);
        }
        Region region = regionOptional.get();
        region.setName(regionDto.getName());
        return convertToDto(regionRepository.save(region));
    }

    public void deleteRegion(Long id){
        Optional<Region> regionOptional = regionRepository.findById(id);
        if(regionOptional.isEmpty()){
            throw new RegionNotFoundException(id);
        }
        regionRepository.deleteById(id);
    }

    private RegionDto convertToDto(Region region){
        RegionDto regionDto = new RegionDto();
        regionDto.setId(region.getId());
        regionDto.setName(region.getName());
        return regionDto;
    }
}
