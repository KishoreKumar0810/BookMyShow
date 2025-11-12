package com.scaler.BookMyShow.controllers;

import com.scaler.BookMyShow.models.Movie;
import com.scaler.BookMyShow.services.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movie")
@PreAuthorize("hasRole('ADMIN')")
public class MovieController {
    private final MovieService movieService;
    public MovieController(MovieService movieService){
        this.movieService = movieService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable("id") Long id){
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @GetMapping("/{title}")
    public ResponseEntity<Movie> getMovieByTitle(@PathVariable("title") String title){
        return ResponseEntity.ok(movieService.getMovieByTitle(title));
    }

    @GetMapping("/{genre}")
    public ResponseEntity<Movie> getMovieByGenre(@PathVariable("genre") String genre){
        return ResponseEntity.ok(movieService.getMovieByGenre(genre));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Movie>>getAllMovies(){
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @PostMapping("/create")
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie){
        return ResponseEntity.ok(movieService.createMovie(movie));
    }

    @PutMapping("/update/{movieId}")
    public ResponseEntity<Movie> updateMovie(Long movieId, Movie movie){
        return ResponseEntity.ok(movieService.updateMovie(movieId, movie));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable("id") Long id){
        movieService.deleteMovie(id);
        return ResponseEntity.ok().build();
    }
}
