package com.khoubyari.example.repository;

import com.khoubyari.example.entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Repository can be used to delegate CRUD operations against the data source: http://goo.gl/P1J8QH
 */
public interface HotelRepository extends PagingAndSortingRepository<Hotel, Long> {
    Page<Hotel> findHotelByCity(String city, Pageable pageable);
    Page<Hotel> findAll(Pageable pageable);
}
