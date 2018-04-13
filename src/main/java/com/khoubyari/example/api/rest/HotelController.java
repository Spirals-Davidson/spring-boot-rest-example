package com.khoubyari.example.api.rest;

import com.khoubyari.example.entity.Hotel;
import com.khoubyari.example.exception.DataFormatException;
import com.khoubyari.example.service.HotelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/*
 * Demonstrates how to set up RESTful API endpoints using Spring MVC
 */

@RestController
@RequestMapping("/example/v1/hotels")
@Api(tags = {"hotels"})
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping
    @ApiOperation(value = "Create a hotel resource.", notes = "Returns the URL of the new resource in the Location header.")
    public Hotel create(@RequestBody @ApiParam(value = "The hotel to create", required = true) final Hotel hotel) {
        return this.hotelService.create(hotel);
    }

    @GetMapping
    @ApiOperation(value = "Get a paginated list of all hotels.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
    public Page<Hotel> getAll(final Pageable pageable) {
        return this.hotelService.getAll(pageable);
    }

    @GetMapping("/byCity/{city}")
    @ApiOperation(value = "Get juste one hotel on the city", notes = "The Hotel on the city you want")
    public Page<Hotel> getHotelByCity(@ApiParam(value = "The city of the hotel.", required = true)
                               @PathVariable("city") String city, final Pageable pageable) {
        return this.hotelService.getByCity(city, pageable);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Get a single hotel.", notes = "You have to provide a valid hotel ID.")
    public Hotel getOne(@ApiParam(value = "The ID of the hotel.", required = true)
                   @PathVariable("id") Long id){
        return this.hotelService.get(id);
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "Update a hotel resource.", notes = "You have to provide a valid hotel ID in the URL and in the payload. The ID attribute can not be updated.")
    public Hotel updateHotel(@ApiParam(value = "The ID of the existing hotel resource.", required = true) @PathVariable("id") Long id,
                             @RequestBody Hotel hotel) {
        if (id != hotel.getId()) throw new DataFormatException("ID doesn't match!");
        return this.hotelService.update(hotel);
    }

    //todo: @ApiImplicitParams, @ApiResponses
    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Delete a hotel resource.", notes = "You have to provide a valid hotel ID in the URL. Once deleted the resource can not be recovered.")
    public void deleteHotel(@ApiParam(value = "The ID of the existing hotel resource.", required = true)
                            @PathVariable("id") Long id) {
        this.hotelService.delete(id);
    }
}
