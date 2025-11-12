package com.scaler.BookMyShow.controllers;

import com.scaler.BookMyShow.dtos.ScreenDto;
import com.scaler.BookMyShow.services.ScreenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/screens")
@PreAuthorize("hasRole('ADMIN')")
public class ScreenController {
    private final ScreenService screenService;
    public ScreenController(ScreenService screenService){
        this.screenService = screenService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ScreenDto> getScreenById(@PathVariable("id") Long id){
        return ResponseEntity.ok(screenService.getScreenById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ScreenDto> getScreenByName(@PathVariable("name") String name){
        return ResponseEntity.ok(screenService.getScreenByName(name));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ScreenDto>> getAllScreens(){
        return ResponseEntity.ok(screenService.getAllScreens());
    }

    @PostMapping("/create")
    public ResponseEntity<ScreenDto> createScreen(@RequestBody ScreenDto screenDto){
        return ResponseEntity.ok(screenService.createScreen(screenDto));
    }

    @PutMapping("/update")
    public ResponseEntity<ScreenDto> updateScreen(@PathVariable("id") Long id, @RequestBody ScreenDto screenDto){
        return ResponseEntity.ok(screenService.updateScreen(id, screenDto));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteScreen(@PathVariable("id") Long id){
        screenService.deleteScreen(id);
        return ResponseEntity.ok().build();
    }
}
