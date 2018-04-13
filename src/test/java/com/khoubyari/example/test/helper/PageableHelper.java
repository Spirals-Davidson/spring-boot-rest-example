package com.khoubyari.example.test.helper;

import org.springframework.data.domain.PageRequest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PageableHelper extends PageRequest {

    @JsonCreator
    public PageableHelper(
            @JsonProperty("sort") final SortHelper sort,
            // The following properties are needed by Jackson but those attributes are calculated from the other attributes
            @JsonProperty("offset") final int offset,
            @JsonProperty("pageSize") final int pageSize,
            @JsonProperty("pageNumber") final int pageNumber,
            @JsonProperty("paged") final boolean paged,
            @JsonProperty("unpaged") final boolean unpaged) {
        super(pageNumber, pageSize, sort);
    }
}
