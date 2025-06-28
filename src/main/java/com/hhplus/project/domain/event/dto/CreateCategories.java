package com.hhplus.project.domain.event.dto;


import com.hhplus.project.domain.event.Category;

import java.util.List;

public record CreateCategories() {

    public record Command(
            List<CategoryInfo> categoryInfoList
    ){}

    public record Info(
            List<Long> categoryIds
    ){}

    public record CategoryInfo(
            String name,
            Long parentId,
            int sort
    ){
        public Category toDomain(){
            return new Category(null,name,true,parentId,sort);
        }
    }


}
