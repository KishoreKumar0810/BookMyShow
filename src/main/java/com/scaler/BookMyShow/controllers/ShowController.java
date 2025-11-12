package com.scaler.BookMyShow.controllers;

import com.scaler.BookMyShow.dtos.ShowDto;
import com.scaler.BookMyShow.models.Show;
import com.scaler.BookMyShow.services.ShowService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/show")
@PreAuthorize("hasRole('ADMIN')")
public class ShowController {
    private final ShowService showService;

    public ShowController(ShowService showService){
        this.showService = showService;
    }

    @GetMapping("/id/{showId}")
    public ResponseEntity<ShowDto> getShowById(@PathVariable("showId") Long showId){
        return ResponseEntity.ok(showService.getShowById(showId));
    }

    @GetMapping("/id/{movieId}")
    public ResponseEntity<List<ShowDto>> getShowsByMovie(@PathVariable("movieId") Long movieId){
        return ResponseEntity.ok(showService.getShowsByMovie(movieId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ShowDto>> getAllShows(){
        return ResponseEntity.ok(showService.getAllShows());
    }

    @GetMapping("/theatre/{theatreId}")
    public ResponseEntity<List<ShowDto>> getShowsByTheatre(@PathVariable("theatreId") Long theatreId){
        return ResponseEntity.ok(showService.getShowsByTheatre(theatreId));
    }
    @PostMapping
    public ResponseEntity<ShowDto> createShow(@RequestBody ShowDto showDto){
        return ResponseEntity.ok(showService.createShow(showDto));
    }

    @PutMapping("/showId/{showId}")
    public ResponseEntity<ShowDto> updateShow(@PathVariable("showId") Long showId, @RequestBody ShowDto showDto){
        return ResponseEntity.ok(showService.updateShow(showId, showDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteShow(@PathVariable Long id){
        showService.deleteShow(id);
        return ResponseEntity.ok().build();
    }
}
