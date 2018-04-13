package com.khoubyari.example.service;

import com.khoubyari.example.entity.Hotel;
import com.khoubyari.example.repository.HotelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/*
 * Sample service to demonstrate what the API would use to get things done
 */
@Service
public class HotelService {

    private static final Logger log = LoggerFactory.getLogger(HotelService.class);

    @Autowired
    private HotelRepository hotelRepository;

    public HotelService() {
    }

    public Hotel create(final Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public Hotel get(long id) {
        return hotelRepository.findOne(id);
    }

    public Hotel update(final Hotel hotel) {
        Hotel hotelFromDb = get(hotel.getId());
        hotelFromDb.setCity(hotel.getCity());
        hotelFromDb.setDescription(hotel.getDescription());
        hotelFromDb.setName(hotel.getName());
        hotelFromDb.setRating(hotel.getRating());
        return hotelRepository.save(hotelFromDb);
    }

    public void delete(long id) {
        hotelRepository.delete(id);
    }

    public Page<Hotel> getAll(final Pageable pageable) {
        return hotelRepository.findAll(pageable);
    }

    public Page<Hotel> getByCity(final String city, final Pageable pageable){
        return hotelRepository.findHotelByCity(city, pageable);
    }
}
