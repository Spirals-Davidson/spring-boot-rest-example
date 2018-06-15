package com.khoubyari.example.test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khoubyari.example.Application;
import com.khoubyari.example.entity.Hotel;
import com.khoubyari.example.test.helper.PageHelper;
import com.khoubyari.example.repository.HotelRepository;
import com.khoubyari.example.test.listener.MyTestListener;
import com.khoubyari.example.test.listener.MyTestRunner;
import com.khoubyari.example.test.listener.NonStaticSetUpListener;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MyTestRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class HotelControllerTest extends MyTestListener {
    private static final String BASE_ROUTE = "/example/v1/hotels/";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private HotelRepository hotelRepository;

    private MockMvc mockMvc;
    private TestContextManager testContextManager;

    private Hotel hotel;
    private Hotel hotel1;


    private static byte[] toJson(Object r) throws Exception {
        return OBJECT_MAPPER.writeValueAsString(r).getBytes();
    }

    public void beforeClassSetup() throws Exception {
        testContextManager = new TestContextManager(getClass());
        testContextManager.prepareTestInstance(this);

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Before
    public void setUp() throws Exception {
        testContextManager = new TestContextManager(getClass());
        testContextManager.prepareTestInstance(this);

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        this.hotel = new Hotel();
        this.hotel.setName("Name1");
        this.hotel.setRating(4);
        this.hotel.setCity("City1");
        this.hotel.setDescription("Description 1");

        this.hotel = this.hotelRepository.save(this.hotel);

        this.hotel1 = new Hotel();
        this.hotel1.setName("Name2");
        this.hotel1.setRating(2);
        this.hotel1.setCity("City2");
        this.hotel1.setDescription("Description 2");

        this.hotel1 = this.hotelRepository.save(this.hotel1);
    }
/*
    @After
    public void tearDown() {
        this.hotelRepository.deleteAll();
    }
*/
    @Test
    public void should_create_hotel() throws Exception {
        Hotel hotelCreate = new Hotel();
        hotelCreate.setName("Create");
        hotelCreate.setRating(4);
        hotelCreate.setCity("Create");
        hotelCreate.setDescription("Create !!");

        //CREATE
        final MvcResult result = mockMvc.perform(post(BASE_ROUTE)
                .content(toJson(hotelCreate))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();


        final byte[] jsonArray = result.getResponse().getContentAsByteArray();
        final Hotel returnedHotel = OBJECT_MAPPER.readerFor(Hotel.class).readValue(jsonArray);
        //INSERT ID TO hotelCreate
        hotelCreate.setId(returnedHotel.getId());

        assertEquals(hotelCreate, returnedHotel);
    }

    @Test
    public void should_find_existing_hotel() throws Exception {
        final MockHttpServletRequestBuilder req = get(BASE_ROUTE + this.hotel.getId());
        final MvcResult result = this.mockMvc.perform(req).andExpect(status().isOk()).andReturn();

        final byte[] jsonArray = result.getResponse().getContentAsByteArray();
        final Hotel returnedHotel = OBJECT_MAPPER.readerFor(Hotel.class).readValue(jsonArray);
        assertEquals(this.hotel, returnedHotel);
    }

    @Test
    public void should_update_existing_hotel() throws Exception {
        final Hotel updateHotel = new Hotel();
        updateHotel.setDescription("MAJ Desc");
        updateHotel.setCity(hotel.getCity());
        updateHotel.setRating(hotel.getRating());
        updateHotel.setName(hotel.getName());
        updateHotel.setId(hotel.getId());

        final MvcResult result = mockMvc.perform(put(BASE_ROUTE + this.hotel.getId())
                .content(toJson(updateHotel))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        final byte[] jsonArray = result.getResponse().getContentAsByteArray();
        final Hotel returnedHotel = OBJECT_MAPPER.readerFor(Hotel.class).readValue(jsonArray);

        assertEquals(updateHotel, returnedHotel);
    }

    /*
        @Test(expected = NestedServletException.class)
        public void should_fail_updating_if_hotel_not_exist() throws Exception {
            log.info();("timestamp="+getTimestamp()+";testname=fail_updating_if_hotel_not_exist;startorend=start");
            final Hotel updateHotel = new Hotel();
            updateHotel.setDescription("MAJ Desc");
            updateHotel.setCity(hotel.getCity());
            updateHotel.setRating(hotel.getRating());
            updateHotel.setName(hotel.getName());
            updateHotel.setId(hotel.getId());

            mockMvc.perform(put(BASE_ROUTE + -1)
                    .content(toJson(updateHotel))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andReturn();
            log.info();("timestamp="+getTimestamp()+";testname=fail_updating_if_hotel_not_exist;startorend=end");
        }
    */
    @Test
    public void should_delete_existing_hotel() throws Exception {
        final MockHttpServletRequestBuilder req = delete(BASE_ROUTE + this.hotel.getId());
        this.mockMvc.perform(req).andExpect(status().isOk());
    }

    @Test
    public void should_return_all_paginated_hotel() throws Exception {
        final MvcResult result = this.mockMvc.perform(get(BASE_ROUTE)).andExpect(status().isOk()).andReturn();

        final byte[] jsonArray = result.getResponse().getContentAsByteArray();
        final Page<Hotel> returnedHotels = OBJECT_MAPPER.readerFor(new TypeReference<PageHelper<Hotel>>() {
        }).readValue(jsonArray);
        assertEquals(2, returnedHotels.getTotalElements());
    }

    @Test
    public void should_return_hotel_find_by_city() throws Exception {
        final MvcResult result = this.mockMvc.perform(get(BASE_ROUTE + "/byCity/" + hotel1.getCity())).andExpect(status().isOk()).andReturn();

        final byte[] jsonArray = result.getResponse().getContentAsByteArray();
        final Page<Hotel> returnedHotels = OBJECT_MAPPER.readerFor(new TypeReference<PageHelper<Hotel>>() {
        }).readValue(jsonArray);
        assertEquals(1, returnedHotels.getTotalElements());

        Hotel returnedHotel = new Hotel();
        if (returnedHotels.iterator().hasNext()) returnedHotel = returnedHotels.iterator().next();
        assertEquals(hotel1, returnedHotel);
    }


}
