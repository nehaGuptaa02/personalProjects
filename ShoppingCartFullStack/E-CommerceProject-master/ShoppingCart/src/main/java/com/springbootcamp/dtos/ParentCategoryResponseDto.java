package com.springbootcamp.dtos;

public class ParentCategoryResponseDto
{
    private Long Id;
    private String categoryName;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "ParentCategoryResponseDto{" +
                "Id=" + Id +
                ", categoryName=" + categoryName +
                '}';
    }
}
