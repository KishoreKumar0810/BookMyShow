package com.scaler.BookMyShow.dtos;

import com.scaler.BookMyShow.models.Feature;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ShowDto {
    private Long id;
    private Long movieId;
    private Long theatreId;
    private Long screenId;
    private Date startTime;
    private Date endTime;
    private List<Feature> featureList;
}
