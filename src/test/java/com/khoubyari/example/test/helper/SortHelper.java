package com.khoubyari.example.test.helper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Sort;

public class SortHelper extends Sort {

    @JsonCreator
    public SortHelper(
            @JsonProperty("sorted") final boolean sorted,
            @JsonProperty("unsorted") final boolean unsorted) {
        super(new Order[0]);
    }
}
