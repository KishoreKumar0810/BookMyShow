package com.scaler.BookMyShow.dtos;

import com.scaler.BookMyShow.models.Feature;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ScreenDto {
    private Long id;
    private String name;
    private List<Feature> features;
    private List<Long> seatIds;
    private Long theatreId;
}

