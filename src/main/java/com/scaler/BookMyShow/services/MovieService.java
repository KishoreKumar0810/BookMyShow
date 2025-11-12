package com.scaler.BookMyShow.services;

import com.scaler.BookMyShow.exceptions.MovieNotFoundException;
import com.scaler.BookMyShow.models.Movie;
import com.scaler.BookMyShow.repositories.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    public Movie getMovieById(Long id){
        Optional<Movie> movieOptional = movieRepository.findById(id);
        if(movieOptional.isEmpty()){
            throw new MovieNotFoundException(id);
        }
        return movieOptional.get();
    }

    public Movie getMovieByTitle(String title){
        Optional<Movie> movieOptional = movieRepository.findByTitle(title);
        if(movieOptional.isEmpty()){
            throw new MovieNotFoundException(title);
        }
        return movieOptional.get();
    }

    public Movie getMovieByGenre(String genre){
        Optional<Movie> movieOptional = movieRepository.findByGenre(genre);
        if(movieOptional.isEmpty()){
            throw new MovieNotFoundException(genre);
        }
        return movieOptional.get();
    }

    public List<Movie> getAllMovies(){
        return movieRepository.findAll();
    }

    public Movie createMovie(Movie movie){
        Movie newMovie = new Movie();
        newMovie.setTitle(movie.getTitle());
        newMovie.setReleasedAt(movie.getReleasedAt());
        newMovie.setLanguage(movie.getLanguage());
        newMovie.setDuration(movie.getDuration());
        newMovie.setGenre(movie.getGenre());
        return movieRepository.save(newMovie);
    }

    public Movie updateMovie(Long movieId, Movie movie){
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        if(movieOptional.isEmpty()){
            throw new MovieNotFoundException(movieId);
        }
        Movie updatedMovie = movieOptional.get();
        updatedMovie.setTitle(movie.getTitle());
        updatedMovie.setReleasedAt(movie.getReleasedAt());
        updatedMovie.setLanguage(movie.getLanguage());
        updatedMovie.setDuration(movie.getDuration());
        updatedMovie.setGenre(movie.getGenre());
        return movieRepository.save(updatedMovie);
    }

    public void deleteMovie(Long id){
        Optional<Movie> movieOptional = movieRepository.findById(id);
        if(movieOptional.isEmpty()){
            throw new MovieNotFoundException(id);
        }
        movieRepository.deleteById(id);
    }
}
