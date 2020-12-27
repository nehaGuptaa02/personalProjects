package com.springbootcamp.dtos;

import java.util.Set;

public class CategoryMetadataFieldDto extends BaseDto
{
    private String name;

    private Set<String> values;

    public Set<String> getValues() {
        return values;
    }

    public void setValues(Set<String> values) {
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
