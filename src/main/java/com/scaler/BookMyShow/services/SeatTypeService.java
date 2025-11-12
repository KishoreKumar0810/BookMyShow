package com.scaler.BookMyShow.services;

import com.scaler.BookMyShow.dtos.SeatTypeDto;
import com.scaler.BookMyShow.exceptions.SeatTypeNotFoundException;
import com.scaler.BookMyShow.models.SeatType;
import com.scaler.BookMyShow.repositories.SeatTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeatTypeService {
    private final SeatTypeRepository seatTypeRepository;

    public SeatTypeService(SeatTypeRepository seatTypeRepository) {
        this.seatTypeRepository = seatTypeRepository;
    }

    public SeatTypeDto getSeatTypeById(Long id) {
        Optional<SeatType> seatTypeOptional = seatTypeRepository.findById(id);
        if (seatTypeOptional.isEmpty()) {
            throw new RuntimeException("SeatType not found with id: " + id);
        }
        return convertToDto(seatTypeOptional.get());
    }

    public SeatTypeDto getSeatTypeByName(String name){
        Optional<SeatType> seatTypeOptional = seatTypeRepository.findByName(name);
        if (seatTypeOptional.isEmpty()) {
            throw new RuntimeException("SeatType not found with name: " + name);
        }
        return convertToDto(seatTypeOptional.get());
    }

    public List<SeatTypeDto> getAllSeatTypes() {
        List<SeatType> seatTypes = seatTypeRepository.findAll();
        if(seatTypes.isEmpty()){
            throw new SeatTypeNotFoundException();
        }
        return seatTypes.stream()
                .map(this::convertToDto)
                .toList();
    }

    public SeatTypeDto createSeatType(SeatTypeDto seatTypeDto) {
        SeatType seatType = new SeatType();
        seatType.setName(seatTypeDto.getName());
        SeatType saved = seatTypeRepository.save(seatType);
        seatTypeDto.setId(saved.getId());
        return seatTypeDto;
    }

    public SeatTypeDto updateSeatType(Long id, SeatTypeDto dto) {
        SeatType seatType = seatTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SeatType not found with id: " + id));
        seatType.setName(dto.getName());
        SeatType updated = seatTypeRepository.save(seatType);
        return convertToDto(updated);
    }

    public void deleteSeatType(Long id) {
        Optional<SeatType> seatTypeOpt = seatTypeRepository.findById(id);
        if( seatTypeOpt.isEmpty()){
            throw new SeatTypeNotFoundException(id);
        }
        seatTypeRepository.deleteById(id);
    }

    private SeatTypeDto convertToDto(SeatType seatType) {
        SeatTypeDto dto = new SeatTypeDto();
        dto.setId(seatType.getId());
        dto.setName(seatType.getName());
        return dto;
    }
}
