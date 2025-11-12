package com.scaler.BookMyShow.services;

import com.scaler.BookMyShow.dtos.ScreenDto;
import com.scaler.BookMyShow.exceptions.ScreenNotFoundException;
import com.scaler.BookMyShow.exceptions.TheatreNotFoundException;
import com.scaler.BookMyShow.models.Screen;
import com.scaler.BookMyShow.models.Seat;
import com.scaler.BookMyShow.models.Theatre;
import com.scaler.BookMyShow.repositories.ScreenRepository;
import com.scaler.BookMyShow.repositories.SeatRepository;
import com.scaler.BookMyShow.repositories.TheatreRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ScreenService {
    private final ScreenRepository screenRepository;
    private final TheatreRepository theatreRepository;
    private final SeatRepository seatRepository;
    public ScreenService(ScreenRepository screenRepository,
                         TheatreRepository theatreRepository,
                         SeatRepository seatRepository) {
        this.screenRepository = screenRepository;
        this.theatreRepository = theatreRepository;
        this.seatRepository = seatRepository;
    }

    public ScreenDto getScreenById(Long id) {
        Optional<Screen> screenOptional = screenRepository.findById(id);
        if(screenOptional.isEmpty()){
            throw new ScreenNotFoundException(id);
        }
        return convertToDto(screenOptional.get());
    }

    public ScreenDto getScreenByName(String name) {
        Optional<Screen> screenOptional = screenRepository.findScreenByName(name);
        if(screenOptional.isEmpty()){
            throw new ScreenNotFoundException(name);
        }
        return convertToDto(screenOptional.get());
    }

    public List<ScreenDto> getAllScreens() {
        List<Screen> screens = screenRepository.findAll();
        return screens.stream()
                .map(this::convertToDto)
                .toList();
    }

    public ScreenDto createScreen(ScreenDto screenDto) {
        List<Seat> seats = seatRepository.findAllById(screenDto.getSeatIds());
        if(seats.isEmpty()){
            throw new RuntimeException("Seat not found with id: " + screenDto.getSeatIds().get(0));
        }
        Optional<Theatre> theatreOptional = theatreRepository.findById(screenDto.getTheatreId());
        if(theatreOptional.isEmpty()){
            throw new RuntimeException("Theatre not found with id: " + screenDto.getTheatreId());
        }
        Screen screen = new Screen();
        screen.setName(screenDto.getName());
        screen.setFeatures(screenDto.getFeatures());
        screen.setSeats(seats);
        screen.setTheatre(theatreOptional.get());
        return convertToDto(screenRepository.save(screen));
    }

    public ScreenDto updateScreen(Long id, ScreenDto screenDto) {
        Optional<Screen> screenOptional = screenRepository.findById(id);
        if(screenOptional.isEmpty()){
            throw new ScreenNotFoundException(id);
        }

        List<Seat> seats = seatRepository.findAllById(screenDto.getSeatIds());
        if(seats.isEmpty()){
            throw new RuntimeException("Seats not found");
        }

        Optional<Theatre> theatreOptional = theatreRepository.findById(screenDto.getTheatreId());
        if(theatreOptional.isEmpty()){
            throw new TheatreNotFoundException(screenDto.getTheatreId());
        }

        Screen screen = screenOptional.get();
        screen.setName(screenDto.getName());
        screen.setFeatures(screenDto.getFeatures());
        screen.setSeats(seats);
        screen.setTheatre(theatreOptional.get());
        return convertToDto(screenRepository.save(screen));
    }

    public void deleteScreen(Long id) {
        Optional<Screen> screenOptional = screenRepository.findById(id);
        if(screenOptional.isEmpty()){
            throw new ScreenNotFoundException(id);
        }
        screenRepository.deleteById(id);
    }

    private ScreenDto convertToDto(Screen screen) {
        ScreenDto screenDto = new ScreenDto();
        screenDto.setId(screen.getId());
        screenDto.setName(screen.getName());
        screenDto.setFeatures(screen.getFeatures());
        screenDto.setSeatIds(screen.getSeats().stream()
                .map(Seat::getId)
                .toList());
        screenDto.setTheatreId(screen.getTheatre().getId());
        return screenDto;
    }
}

