package com.springbootcamp.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CategoryMetadataFieldValueCompositeKey implements Serializable
{
   private Long categoryMetadataFieldId;
    private Long categoryId;

    public CategoryMetadataFieldValueCompositeKey() {

    }

    public CategoryMetadataFieldValueCompositeKey(Long categoryMetadataFieldId, Long categoryId) {
        this.categoryMetadataFieldId = categoryMetadataFieldId;
        this.categoryId = categoryId;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }


}
