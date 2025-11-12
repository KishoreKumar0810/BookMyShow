package com.scaler.BookMyShow.controllers;

import com.scaler.BookMyShow.dtos.TheatreDto;
import com.scaler.BookMyShow.models.Theatre;
import com.scaler.BookMyShow.services.TheatreService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theatre")
@PreAuthorize("hasRole('ADMIN')")
public class TheatreController {
    private final TheatreService theatreService;
    public TheatreController(TheatreService theatreService){
        this.theatreService = theatreService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<TheatreDto> getTheatreById(@PathVariable("id") Long id){
        return ResponseEntity.ok(theatreService.getTheatreById(id));
    }

    @GetMapping("/id/{name}")
    public ResponseEntity<TheatreDto> getTheatreByName(@PathVariable("name") String name){
        return ResponseEntity.ok(theatreService.getTheatreByName(name));
    }

    @GetMapping("/all")
    public ResponseEntity<List<TheatreDto>> getAllTheatres(){
        return ResponseEntity.ok(theatreService.getAllTheatres());
    }

    @PostMapping("/create")
    public ResponseEntity<TheatreDto> createTheatre(@RequestBody TheatreDto theatreDto){
        return ResponseEntity.ok(theatreService.createTheatre(theatreDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TheatreDto> createTheatre(@PathVariable("id") Long id, @RequestBody TheatreDto theatreDto){
        return ResponseEntity.ok(theatreService.createTheatre(theatreDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTheatre(@PathVariable("id") Long id){
        theatreService.deleteTheatre(id);
        return ResponseEntity.noContent().build();
    }
}
