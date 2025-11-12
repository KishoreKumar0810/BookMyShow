package com.scaler.BookMyShow.dtos;

import com.scaler.BookMyShow.models.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpResponseDto {
    private Long userId;
    private ResponseStatus responseStatus;
}
