package com.scaler.BookMyShow.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.lang.model.util.ElementScanner14;
import java.util.List;

@Getter
@Setter
@Entity
public class Screen extends BaseModel{
    private String name;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.LAZY)
    private List<Feature> features;

    @OneToMany
    private List<Seat> seats;

    @ManyToOne
    @JoinColumn(name = "theatre_id") // creates theatre_id foreign key in the Screen table
    private Theatre theatre;

}
