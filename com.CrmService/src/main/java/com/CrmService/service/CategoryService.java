package com.CrmService.service;


import java.util.List;

import com.CrmService.dto.CategoryDto;
import com.CrmService.dto.Message;

public interface CategoryService {
	public Message<CategoryDto>AddCategory(CategoryDto request);
	public Message<CategoryDto>UpdateCategory(CategoryDto request);
	public Message<CategoryDto>DeleteCategory(int cId);
	public Message<CategoryDto>GetCategoryById(int cId);
	public List<Message<CategoryDto>>GetAllCategories();
	

}
