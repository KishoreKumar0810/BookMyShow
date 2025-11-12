package com.scaler.BookMyShow.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Theatre extends BaseModel{
    private String name;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

    @OneToMany(mappedBy = "theatre")
    private List<Screen> screens;

    @OneToMany(mappedBy = "theatre", fetch = FetchType.EAGER)
    private List<Show> shows;
}
