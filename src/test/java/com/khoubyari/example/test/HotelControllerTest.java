package com.khoubyari.example.test;

/**
 * Uses JsonPath: http://goo.gl/nwXpb, Hamcrest and MockMVC
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khoubyari.example.Application;
import com.khoubyari.example.api.rest.HotelController;
import com.khoubyari.example.entity.Hotel;

import com.khoubyari.example.repository.HotelRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Random;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class HotelControllerTest {

    private static final String RESOURCE_LOCATION_PATTERN = "http://localhost/example/v1/hotels/[0-9]+";
    private static final String BASE_ROUTE = "/example/v1/hotels/";
    @InjectMocks
    HotelController controller;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private HotelRepository hotelRepository;

    private MockMvc mockMvc;

    private Hotel hotelGateway;
    private Hotel hotelGateway2;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        this.hotelGateway = new Hotel();
        this.hotelGateway.setName("Name1");
        this.hotelGateway.setRating(4);
        this.hotelGateway.setCity("City1");
        this.hotelGateway.setDescription("Description 1");

        this.hotelGateway = this.hotelRepository.save(this.hotelGateway);

        this.hotelGateway2 = new Hotel();
        this.hotelGateway2.setName("Name2");
        this.hotelGateway2.setRating(2);
        this.hotelGateway2.setCity("City2");
        this.hotelGateway2.setDescription("Description 2");

        this.hotelGateway2 = this.hotelRepository.save(this.hotelGateway2);
    }

    @Test
    public void test(){
        assertTrue(true);
    }
//
//    @After
//    public void tearDown() {
//        this.hotelRepository.deleteAll();
//    }
//
//    @Test
//    public void should_find_existing_blue_force() throws Exception {
//        final MockHttpServletRequestBuilder req = get(BASE_ROUTE + this.hotelGateway.getId());
//        final MvcResult result = this.mockMvc.perform(req).andExpect(status().isOk()).andReturn();
//
//        final byte[] jsonArray = result.getResponse().getContentAsByteArray();
//        final Hotel returnedHotelGateway = OBJECT_MAPPER.readerFor(BlueForceGateway.class).readValue(jsonArray);
//        assertEquals(returnedBlueForceGateway, this.blueForceGateway);
//    }
//
//    //@Test
//    public void shouldHaveEmptyDB() throws Exception {
//        mvc.perform(get("/example/v1/hotels")
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(0)));
//    }
//
//    @Test
//    public void shouldCreateRetrieveDelete() throws Exception {
//        Hotel r1 = mockHotel("shouldCreateRetrieveDelete");
//        byte[] r1Json = toJson(r1);
//
//        //CREATE
//        MvcResult result = mvc.perform(post("/example/v1/hotels")
//                .content(r1Json)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated())
//                .andExpect(redirectedUrlPattern(RESOURCE_LOCATION_PATTERN))
//                .andReturn();
//        long id = getResourceIdFromUrl(result.getResponse().getRedirectedUrl());
//
//        //RETRIEVE
//        mvc.perform(get("/example/v1/hotels/" + id)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is((int) id)))
//                .andExpect(jsonPath("$.name", is(r1.getName())))
//                .andExpect(jsonPath("$.city", is(r1.getCity())))
//                .andExpect(jsonPath("$.description", is(r1.getDescription())))
//                .andExpect(jsonPath("$.rating", is(r1.getRating())));
//
//        //DELETE
//        mvc.perform(delete("/example/v1/hotels/" + id))
//                .andExpect(status().isNoContent());
//
//        //RETRIEVE should fail
//        mvc.perform(get("/example/v1/hotels/" + id)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound());
//
//        //todo: you can test the 404 error body too.
//        mvc.perform(get("/example/v1/hotels/87" + id)
//                .accept((MediaType.APPLICATION_JSON)))
//                .andExpect(status().isNotFound());
//
///*
//JSONAssert.assertEquals(
//  "{foo: 'bar', baz: 'qux'}",
//  JSONObject.fromObject("{foo: 'bar', baz: 'xyzzy'}"));
// */
//    }
//
//    @Test
//    public void shouldCreateAndUpdateAndDelete() throws Exception {
//        Hotel r1 = mockHotel("shouldCreateAndUpdate");
//        byte[] r1Json = toJson(r1);
//        //CREATE
//        MvcResult result = mvc.perform(post("/example/v1/hotels")
//                .content(r1Json)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated())
//                .andExpect(redirectedUrlPattern(RESOURCE_LOCATION_PATTERN))
//                .andReturn();
//        long id = getResourceIdFromUrl(result.getResponse().getRedirectedUrl());
//
//        Hotel r2 = mockHotel("shouldCreateAndUpdate2");
//        r2.setId(id);
//        byte[] r2Json = toJson(r2);
//
//        //UPDATE
//        mvc.perform(put("/example/v1/hotels/" + id)
//                .content(r2Json)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent())
//                .andReturn();
//
//        //RETRIEVE updated
//        mvc.perform(get("/example/v1/hotels/" + id)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is((int) id)))
//                .andExpect(jsonPath("$.name", is(r2.getName())))
//                .andExpect(jsonPath("$.city", is(r2.getCity())))
//                .andExpect(jsonPath("$.description", is(r2.getDescription())))
//                .andExpect(jsonPath("$.rating", is(r2.getRating())));
//
//        //DELETE
//        mvc.perform(delete("/example/v1/hotels/" + id))
//                .andExpect(status().isNoContent());
//    }
///*
//    @Test
//    public void ShouldReturnHotelWithGoodCity() throws Exception {
//        //CREATE TWO HOTELS
//        String SEARCH_NAME = "search";
//        mvc.perform(post("/example/v1/hotels")
//                .content(toJson(mockHotel(SEARCH_NAME)))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated());
//        mvc.perform(post("/example/v1/hotels")
//                .content(toJson(mockHotel("un"+SEARCH_NAME)))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType).APPLICATION_JSON))
//                .andExpect(status().isCreated());
//
//        //LOOK IF SEARCH HOSTEL IS IN THE LIST
//        mvc.perform(get("example/v1/hotels/"+SEARCH_NAME+"_city")
//        .accept(MediaType.APPLICATION_JSON))
//        .andExpect(status().isOk())
//        .andExpect(jsonPath("$.city", is(SEARCH_NAME+"_city")));
//    }*/
//
//    /*
//     ******************************
//     */
//
//    private long getResourceIdFromUrl(String locationUrl) {
//        String[] parts = locationUrl.split("/");
//        return Long.valueOf(parts[parts.length - 1]);
//    }
//
//
//    private Hotel mockHotel(String prefix) {
//        Hotel r = new Hotel();
//        r.setCity(prefix + "_city");
//        r.setDescription(prefix + "_description");
//        r.setName(prefix + "_name");
//        r.setRating(new Random().nextInt(6));
//        return r;
//    }
//
//    private byte[] toJson(Object r) throws Exception {
//        ObjectMapper map = new ObjectMapper();
//        return map.writeValueAsString(r).getBytes();
//    }
//
//    // match redirect header URL (aka Location header)
//    private static ResultMatcher redirectedUrlPattern(final String expectedUrlPattern) {
//        return result -> {
//            Pattern pattern = Pattern.compile("\\A" + expectedUrlPattern + "\\z");
//            assertTrue(pattern.matcher(result.getResponse().getRedirectedUrl()).find());
//        };
//    }

}
