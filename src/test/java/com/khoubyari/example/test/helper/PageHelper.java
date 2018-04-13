package com.khoubyari.example.test.helper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

public class PageHelper<T> extends PageImpl<T> {

    @JsonCreator
    public PageHelper(@JsonProperty("content") final List<T> content,
                      @JsonProperty("number") final int number,
                      @JsonProperty("size") final int size,
                      @JsonProperty("totalElements") final Long totalElements,
                      @JsonProperty("sort") final SortHelper sort,
                      // The following properties are needed by Jackson but those attributes are calculated from the other attributes
                      @JsonProperty("totalPages") final long totalPages,
                      @JsonProperty("first") final boolean first,
                      @JsonProperty("last") final boolean last,
                      @JsonProperty("numberOfElements") final long numberOfElements,
                      @JsonProperty("pageable") final PageableHelper pageable) {
        super(content, new PageRequest(number, size, sort), totalElements);
    }
}