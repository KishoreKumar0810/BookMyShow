package com.scaler.BookMyShow.controllers;

import com.scaler.BookMyShow.dtos.SeatTypeDto;
import com.scaler.BookMyShow.services.SeatTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seat-types")
@PreAuthorize("hasRole('ADMIN')")
public class SeatTypeController {
    private final SeatTypeService seatTypeService;

    public SeatTypeController(SeatTypeService seatTypeService){
        this.seatTypeService = seatTypeService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<SeatTypeDto> getSeatTypeById(@PathVariable("id") Long id){
        return ResponseEntity.ok(seatTypeService.getSeatTypeById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<SeatTypeDto> getSeatTypeByName(@PathVariable("name") String name){
        return ResponseEntity.ok(seatTypeService.getSeatTypeByName(name));
    }

    @GetMapping("/all")
    public ResponseEntity<List<SeatTypeDto>> getAllSeatTypes(){
        return ResponseEntity.ok(seatTypeService.getAllSeatTypes());
    }

    @PostMapping("/create")
    public ResponseEntity<SeatTypeDto> createSeatType(@RequestBody SeatTypeDto seatTypeDto){
        return ResponseEntity.ok(seatTypeService.createSeatType(seatTypeDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SeatTypeDto> updateSeatType(@PathVariable("id") Long id, @RequestBody SeatTypeDto seatTypeDto){
        return ResponseEntity.ok(seatTypeService.updateSeatType(id, seatTypeDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSeatType(@PathVariable("id") Long id){
        seatTypeService.deleteSeatType(id);
        return ResponseEntity.ok().build();
    }
}
