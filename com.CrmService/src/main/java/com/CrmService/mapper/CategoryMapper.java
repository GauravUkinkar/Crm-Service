package com.CrmService.mapper;

import com.CrmService.dto.CategoryDto;
import com.CrmService.model.Category;

public interface CategoryMapper {
	public CategoryDto categoryToCategoryDto (Category category);
	public Category categoryDtoToCategory (CategoryDto dto);

}
