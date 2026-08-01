package com.CrmService.mapperImpl;

import org.springframework.stereotype.Component;

import com.CrmService.dto.CategoryDto;
import com.CrmService.mapper.CategoryMapper;
import com.CrmService.model.Category;

@Component
public class CategoryMapperImpl implements CategoryMapper {

	@Override
	public CategoryDto categoryToCategoryDto(Category category) {
		CategoryDto dto = new CategoryDto();
		dto.setCId(category.getCId());
		dto.setCategory(category.getCategory());
		return dto;
		
	}

	@Override
	public Category categoryDtoToCategory(CategoryDto dto) {
		Category category = new Category();
		category.setCategory(dto.getCategory());
		return category;
		
	}

}
