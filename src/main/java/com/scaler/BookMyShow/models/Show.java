package com.scaler.BookMyShow.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity(name = "shows")
public class Show extends BaseModel{

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "theatre_id", nullable = false)
    private Theatre theatre;

    @ManyToOne
    private Screen screen;

    private Date startTime;

    private Date endTime;

    private List<Feature> featureList;

    @OneToMany(mappedBy = "show", fetch = FetchType.LAZY)
    private List<Booking> bookings;
}
