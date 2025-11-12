package com.scaler.BookMyShow.services;

import com.scaler.BookMyShow.dtos.ShowDto;
import com.scaler.BookMyShow.exceptions.MovieNotFoundException;
import com.scaler.BookMyShow.exceptions.ScreenNotFoundException;
import com.scaler.BookMyShow.exceptions.ShowNotFoundException;
import com.scaler.BookMyShow.exceptions.TheatreNotFoundException;
import com.scaler.BookMyShow.models.*;
import com.scaler.BookMyShow.repositories.MovieRepository;
import com.scaler.BookMyShow.repositories.ScreenRepository;
import com.scaler.BookMyShow.repositories.ShowRepository;
import com.scaler.BookMyShow.repositories.TheatreRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ShowService {
    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;
    private final TheatreRepository theatreRepository;
    private final ScreenRepository screenRepository;

    public ShowService(ShowRepository showRepository,
                       MovieRepository movieRepository,
                       TheatreRepository theatreRepository,
                       ScreenRepository screenRepository){
        this.showRepository = showRepository;
        this.movieRepository = movieRepository;
        this.theatreRepository = theatreRepository;
        this.screenRepository = screenRepository;
    }

    public ShowDto getShowById(Long showId){
        Optional<Show> showOptional = showRepository.findById(showId);
        if(showOptional.isEmpty()){
            throw new ShowNotFoundException(showId);
        }
        return convertToDto(showOptional.get());
    }

    public List<ShowDto> getShowsByMovie(Long movieId){
        List<Show> shows = showRepository.findByMovie_Id(movieId);
        if(shows.isEmpty()){
            throw new ShowNotFoundException(movieId);
        }
        return shows.stream()
                .map(this::convertToDto)
                .toList();
    }

    public List<ShowDto> getShowsByTheatre(Long theatreId){
        List<Show> shows = showRepository.findByTheatre_Id(theatreId);
        if(shows.isEmpty()){
            throw new ShowNotFoundException(theatreId);
        }
        return shows.stream()
                .map(this::convertToDto)
                .toList();
    }

    public List<ShowDto> getAllShows(){
        return showRepository.findAll().stream()
                .map(this::convertToDto)
                .toList();
    }

    public ShowDto createShow(ShowDto showDto){
        Optional<Movie> movieOptional = movieRepository.findById(showDto.getMovieId());
        if(movieOptional.isEmpty()){
            throw new MovieNotFoundException(showDto.getMovieId());
        }
        Optional<Theatre> theatreOptional = theatreRepository.findById(showDto.getTheatreId());
        if(theatreOptional.isEmpty()){
            throw new TheatreNotFoundException(showDto.getTheatreId());
        }
        Optional<Screen> screenOptional = screenRepository.findById(showDto.getScreenId());
        if(screenOptional.isEmpty()){
            throw new ScreenNotFoundException(showDto.getScreenId());
        }
        Show show = new Show();
        show.setMovie(movieOptional.get());
        show.setTheatre(theatreOptional.get());
        show.setScreen(screenOptional.get());
        show.setStartTime(showDto.getStartTime());
        show.setEndTime(showDto.getEndTime());
        show.setFeatureList(showDto.getFeatureList());
        Show savedShow = showRepository.save(show);
        return convertToDto(savedShow);
    }

    public ShowDto updateShow(Long showId, ShowDto showDto){
        Optional<Show> showOptional = showRepository.findById(showId);
        if(showOptional.isEmpty()){
            throw new ShowNotFoundException(showId);
        }
        Optional<Movie> movieOptional = movieRepository.findById(showDto.getMovieId());
        if(movieOptional.isEmpty()){
            throw new MovieNotFoundException(showDto.getMovieId());
        }
        Optional<Theatre> theatreOptional = theatreRepository.findById(showDto.getTheatreId());
        if(theatreOptional.isEmpty()){
            throw new TheatreNotFoundException(showDto.getTheatreId());
        }
        Optional<Screen> screenOptional = screenRepository.findById(showDto.getScreenId());
        if(screenOptional.isEmpty()){
            throw new ScreenNotFoundException(showDto.getScreenId());
        }
        Show show = showOptional.get();
        show.setMovie(movieOptional.get());
        show.setTheatre(theatreOptional.get());
        show.setScreen(screenOptional.get());
        show.setStartTime(showDto.getStartTime());
        show.setEndTime(showDto.getEndTime());
        show.setFeatureList(showDto.getFeatureList());

        Show updatedShow = showRepository.save(show);
        return convertToDto(updatedShow);
    }

    public void deleteShow(Long showId){
        Optional<Show> showOptional = showRepository.findById(showId);
        if(showOptional.isEmpty()){
            throw new ShowNotFoundException(showId);
        }
        Show show = showOptional.get();
        if (!show.getBookings().isEmpty()) {
            throw new RuntimeException("Cannot delete show with existing bookings");
        }
        showRepository.delete(show);
    }

    private ShowDto convertToDto(Show show){
        ShowDto showDto = new ShowDto();
        showDto.setId(show.getId());
        showDto.setMovieId(show.getMovie().getId());
        showDto.setTheatreId(show.getTheatre().getId());
        showDto.setScreenId(show.getScreen().getId());
        showDto.setStartTime(show.getStartTime());
        showDto.setEndTime(show.getEndTime());
        showDto.setFeatureList(show.getFeatureList());
        return showDto;
    }
}
