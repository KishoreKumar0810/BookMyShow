package com.scaler.BookMyShow.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
public class Movie extends BaseModel{
    private String title;
    private String genre;
    private Duration duration;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate releasedAt;
    private List<String> language;
}
