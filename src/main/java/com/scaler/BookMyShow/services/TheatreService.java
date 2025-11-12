package com.scaler.BookMyShow.services;

import com.scaler.BookMyShow.dtos.TheatreDto;
import com.scaler.BookMyShow.exceptions.RegionNotFoundException;
import com.scaler.BookMyShow.exceptions.ScreenNotFoundException;
import com.scaler.BookMyShow.exceptions.ShowNotFoundException;
import com.scaler.BookMyShow.exceptions.TheatreNotFoundException;
import com.scaler.BookMyShow.models.Region;
import com.scaler.BookMyShow.models.Screen;
import com.scaler.BookMyShow.models.Show;
import com.scaler.BookMyShow.models.Theatre;
import com.scaler.BookMyShow.repositories.RegionRepository;
import com.scaler.BookMyShow.repositories.ScreenRepository;
import com.scaler.BookMyShow.repositories.ShowRepository;
import com.scaler.BookMyShow.repositories.TheatreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TheatreService {
    private final TheatreRepository theatreRepository;
    private final ScreenRepository screenRepository;
    private final ShowRepository showRepository;
    private final RegionRepository regionRepository;
    public TheatreService(TheatreRepository theatreRepository,
                          ScreenRepository screenRepository,
                          ShowRepository showRepository,
                          RegionRepository regionRepository){
        this.theatreRepository = theatreRepository;
        this.screenRepository = screenRepository;
        this.showRepository = showRepository;
        this.regionRepository = regionRepository;
    }

    public TheatreDto getTheatreById(Long id){
        Optional<Theatre> theatreOptional = theatreRepository.findById(id);
        if(theatreOptional.isEmpty()){
            throw new TheatreNotFoundException(id);
        }
        return convertToDto(theatreOptional.get());
    }

    public TheatreDto getTheatreByName(String name){
        Optional<Theatre> theatreOptional = theatreRepository.findByName(name);
        if(theatreOptional.isEmpty()){
            throw new TheatreNotFoundException(name);
        }
        return convertToDto(theatreOptional.get());
    }

    public List<TheatreDto> getAllTheatres(){
        List<Theatre> theatres = theatreRepository.findAll();
        return theatres.stream()
                .map(this::convertToDto)
                .toList();
    }

    public TheatreDto createTheatre(TheatreDto theatreDto){
        List<Screen> screens = screenRepository.findAllById(theatreDto.getScreenIds());
        if(screens.isEmpty()){
            throw new ScreenNotFoundException();
        }

        List<Show> shows = showRepository.findAllById(theatreDto.getShowIds());
        if(shows.isEmpty()){
            throw new ShowNotFoundException();
        }

        Optional<Region> regionOptional = regionRepository.findById(theatreDto.getRegionId());
        if(regionOptional.isEmpty()){
            throw new RegionNotFoundException(theatreDto.getRegionId());
        }

        Theatre theatre = new Theatre();
        theatre.setName(theatreDto.getName());
        theatre.setRegion(regionOptional.get());
        theatre.setScreens(screens);
        theatre.setShows(shows);
        return convertToDto(theatreRepository.save(theatre));
    }

    public TheatreDto updateTheatre(Long id,TheatreDto theatreDto){
        List<Screen> screens = screenRepository.findAllById(theatreDto.getScreenIds());
        if(screens.isEmpty()){
            throw new ScreenNotFoundException();
        }

        List<Show> shows = showRepository.findAllById(theatreDto.getShowIds());
        if(shows.isEmpty()){
            throw new ShowNotFoundException();
        }

        Optional<Region> regionOptional = regionRepository.findById(theatreDto.getRegionId());
        if(regionOptional.isEmpty()){
            throw new RegionNotFoundException(theatreDto.getRegionId());
        }

        Optional<Theatre> theatreOptional = theatreRepository.findById(id);
        if(theatreOptional.isEmpty()){
            throw new TheatreNotFoundException(id);
        }
        Theatre theatre = theatreOptional.get();
        theatre.setName(theatreDto.getName());
        theatre.setRegion(regionOptional.get());
        theatre.setScreens(screens);
        theatre.setShows(shows);
        return convertToDto(theatreRepository.save(theatre));
    }

    public void deleteTheatre(Long id){
        Optional<Theatre> theatreOptional = theatreRepository.findById(id);
        if(theatreOptional.isEmpty()){
            throw new TheatreNotFoundException(id);
        }
        theatreRepository.deleteById(id);
    }

    private TheatreDto convertToDto(Theatre theatre){
        TheatreDto theatreDto = new TheatreDto();
        theatreDto.setId(theatre.getId());
        theatreDto.setName(theatre.getName());
        theatreDto.setRegionId(theatre.getRegion().getId());
        theatreDto.setScreenIds(
            theatre.getScreens()
                   .stream()
                   .map(Screen::getId)
                   .collect(Collectors.toList())
        );
        theatreDto.setShowIds(
                theatre.getShows()
                       .stream()
                       .map(Show::getId)
                       .collect(Collectors.toList())
        );
        return theatreDto;
    }
}
