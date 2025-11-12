package com.scaler.BookMyShow.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TheatreDto {
    private Long id;
    private String name;
    private Long regionId;
    private List<Long> screenIds;
    private List<Long> showIds;
}
