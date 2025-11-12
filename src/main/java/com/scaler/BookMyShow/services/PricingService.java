package com.scaler.BookMyShow.services;

import com.scaler.BookMyShow.models.Show;
import com.scaler.BookMyShow.models.ShowSeat;
import com.scaler.BookMyShow.models.ShowSeatType;
import com.scaler.BookMyShow.repositories.ShowSeatRepository;
import com.scaler.BookMyShow.repositories.ShowSeatTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PricingService {
    private ShowSeatRepository showRepository;
    private final ShowSeatTypeRepository showSeatTypeRepository;

    public PricingService(ShowSeatTypeRepository showSeatTypeRepository){
        this.showSeatTypeRepository = showSeatTypeRepository;
    }

    public int calculatePrice(List<ShowSeat> showSeatList, Show show){
        List<ShowSeatType> showSeatTypeList = showSeatTypeRepository.findAllByShow(show);
        int amount = 0;
        for(ShowSeat showSeat: showSeatList) {
            for(ShowSeatType showSeatType: showSeatTypeList) {
                if(showSeat.getSeat().getSeatType().equals(showSeatType.getSeatType())) {
                    amount += showSeatType.getPrice();
                }
            }
        }
        return amount;
    }
}
