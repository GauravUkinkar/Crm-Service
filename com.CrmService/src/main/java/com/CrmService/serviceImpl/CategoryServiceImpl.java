//package com.Pandoza_Admin.serviceImpl;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import com.Pandoza_Admin.Dto.CategoryDto;
//import com.Pandoza_Admin.Dto.Message;
//import com.Pandoza_Admin.Mapper.CategoryMapper;
//import com.Pandoza_Admin.Repository.CategoryRepository;
//import com.Pandoza_Admin.model.Category;
//import com.Pandoza_Admin.service.CategoryService;
//import com.Pandoza_Admin.util.Constants;
//
//import lombok.RequiredArgsConstructor;
//
//@Service
//public class CategoryServiceImpl implements CategoryService {
//	private CategoryRepository categoryrepository;
//	private CategoryMapper categorymapperimpl;
//	
//	
//	public CategoryServiceImpl(CategoryRepository categoryrepository, CategoryMapper categorymapperimpl) {
//		super();
//		this.categoryrepository = categoryrepository;
//		this.categorymapperimpl = categorymapperimpl;
//	}
//
//	@Override
//	public Message<CategoryDto> AddCategory(CategoryDto request) {
//		Message<CategoryDto> response = new Message<>();
//		try {
//			if(request == null) {
//				response.setStatus(HttpStatus.BAD_REQUEST);
//				response.setResponseMessage(Constants.INVALID_CATEGORY_DATA);
//				return response;
//			} 
//			Category category = new Category();
//			category.setCatageory(request.getCatageory());
//			categoryrepository.save(category);
////			Category category = categorymapperimpl.categoryDtoToCategory(request);
////			categoryrepository.save(category);
////			CategoryDto dto = categorymapperimpl.categoryToCategoryDto(category);
////			
//			response.setStatus(HttpStatus.OK);
//			response.setResponseMessage(Constants.CATEGORY_ADDED);
//			return response;
//	       } catch (Exception e) {    
//	     	response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
//		    response.setResponseMessage(e.getMessage());
//		    return response;
//			
//		}
//		
//	}
//
//	@Override
//	public Message<CategoryDto> UpdateCategory(CategoryDto request) {
//		Message<CategoryDto> response = new Message<>();
//		Category category = null;
//		try {
//			if(category == null) {
//				response.setStatus(HttpStatus.BAD_REQUEST);
//				response.setResponseMessage(Constants.INVALID_CATEGORY_DATA);
//				return response;
//			}
//			category.setCatageory(request.getCatageory());
//			
//			categoryrepository.save(category);
//			CategoryDto dto = categorymapperimpl.categoryToCategoryDto(category);
//			
//			response.setStatus(HttpStatus.OK);
//			response.setResponseMessage(Constants.CATEGORY_UPDATED);
//			response.setData(dto);
//			return response;
//		} catch (Exception e) {
//			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
//			response.setResponseMessage(Constants.SOMETHING_WENT_WRONG);
//			return response;
//		}
//		
//	}
//
//	@Override
//	public Message<CategoryDto> DeleteCategory(int cId) {
//		Message<CategoryDto> response = new Message<>();
//		try {
//			Category category = new Category();
//			category = categoryrepository.getById(cId);
//			if(category == null) {
//				response.setStatus(HttpStatus.BAD_REQUEST);
//				response.setResponseMessage(Constants.CATEGORY_NOT_FOUND);
//				return response;
//			}
//			CategoryDto dto = categorymapperimpl.categoryToCategoryDto(category);
//			categoryrepository.deleteById(cId);
//			response.setStatus(HttpStatus.OK);
//			response.setResponseMessage(Constants.CATEGORY_DELETED);
//			response.setData(dto);
//			return response;
//		} catch (Exception e) {
//			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
//			response.setResponseMessage(Constants.SOMETHING_WENT_WRONG);
//			return response;
//		}
//		
//	}
//
//	@Override
//	public Message<CategoryDto> GetCategoryById(int cId) {
//		Message<CategoryDto> response = new Message<>();
//		try {
//			Category category = new Category();
//			category = categoryrepository.getById(cId);
//			
//			if(category == null) {
//				response.setStatus(HttpStatus.BAD_REQUEST);
//				response.setResponseMessage(Constants.CATEGORY_NOT_FOUND);
//				return response;
//			}
//			CategoryDto dto = categorymapperimpl.categoryToCategoryDto(category);
//			response.setStatus(HttpStatus.OK);
//			response.setResponseMessage(Constants.CATEGORY_FOUND);
//			response.setData(dto);
//			return response;
//		} catch (Exception e) {
//			System.err.println("Error fetching Category:" +e.getMessage());
//			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
//			response.setResponseMessage(Constants.SOMETHING_WENT_WRONG);
//			return response;
//		}
//		
//	}
//
//	@Override
//	public List<Message<CategoryDto>> GetAllCategories() {
//		List<Message<CategoryDto>> message = new ArrayList<>();
//		try {
//			List<Category> categories = categoryrepository.findAll();
//			for(Category category : categories) {
//				CategoryDto dto = categorymapperimpl.categoryToCategoryDto(category);
//				message.add(new Message<CategoryDto>(HttpStatus.OK,"Category found successfully",dto));
//			}
//			return message;
//		} catch (Exception e) {
//			message.add(new Message<CategoryDto>(HttpStatus.INTERNAL_SERVER_ERROR,
//					Constants.SOMETHING_WENT_WRONG +e.getMessage(),null));
//			return message;
//		}
//		}
//
//	}
package com.CrmService.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.CrmService.dto.CategoryDto;
import com.CrmService.dto.Message;
import com.CrmService.mapper.CategoryMapper;
import com.CrmService.model.Category;
import com.CrmService.repository.CategoryRepository;
import com.CrmService.service.CategoryService;
import com.CrmService.util.Constants;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryrepository;
    private final CategoryMapper categorymapperimpl;

    @Override
    public Message<CategoryDto> AddCategory(CategoryDto request) {
        Message<CategoryDto> response = new Message<>();
        Category category=null;
        try {
             // Check for duplicate category
             Optional<Category> existingCategory = categoryrepository.getBycategory(request.getCategory().trim());
             if (existingCategory.isPresent()) {
                 response.setStatus(HttpStatus.CONFLICT);
                 response.setResponseMessage("Category already exists");
                 return response;
             }
            category = categorymapperimpl.categoryDtoToCategory(request);
            category = categoryrepository.save(category);

            response.setStatus(HttpStatus.OK);
            response.setResponseMessage(Constants.CATEGORY_ADDED);
            response.setData(categorymapperimpl.categoryToCategoryDto(category));
            return response;
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setResponseMessage(e.getMessage());
            return response;
        }
    }

    @Override
    public Message<CategoryDto> UpdateCategory(CategoryDto request) {
        Message<CategoryDto> response = new Message<>();
        try {
            Category category = categoryrepository.getById(request.getCId());
            if (category == null) {
                response.setStatus(HttpStatus.BAD_REQUEST);
                response.setResponseMessage(Constants.CATEGORY_NOT_FOUND);
                return response;
            }

            category.setCategory(request.getCategory());
            categoryrepository.save(category);

            CategoryDto dto = categorymapperimpl.categoryToCategoryDto(category);
            response.setStatus(HttpStatus.OK);
            response.setResponseMessage(Constants.CATEGORY_UPDATED);
            response.setData(dto);
            return response;
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setResponseMessage(e.getMessage());
            return response;
        }
    }

    @Override
    public Message<CategoryDto> DeleteCategory(int cId) {
        Message<CategoryDto> response = new Message<>();
        try {
            Category category = categoryrepository.findById(cId).orElse(null);
            if (category == null) {
                response.setStatus(HttpStatus.BAD_REQUEST);
                response.setResponseMessage(Constants.CATEGORY_NOT_FOUND);
                return response;
            }

            categoryrepository.deleteById(cId);

            response.setStatus(HttpStatus.OK);
            response.setResponseMessage(Constants.CATEGORY_DELETED);
            return response;
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setResponseMessage(Constants.SOMETHING_WENT_WRONG);
            return response;
        }
    }

    @Override
    public Message<CategoryDto> GetCategoryById(int cId) {
        Message<CategoryDto> response = new Message<>();
        try {
            Category category = categoryrepository.findById(cId).orElse(null);
            if (category == null) {
                response.setStatus(HttpStatus.BAD_REQUEST);
                response.setResponseMessage(Constants.CATEGORY_NOT_FOUND);
                return response;
            }

            CategoryDto dto = categorymapperimpl.categoryToCategoryDto(category);
            response.setStatus(HttpStatus.OK);
            response.setResponseMessage(Constants.CATEGORY_FOUND);
            response.setData(dto);
            return response;
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setResponseMessage(Constants.SOMETHING_WENT_WRONG);
            return response;
        }
    }

    @Override
    public List<Message<CategoryDto>> GetAllCategories() {
        List<Message<CategoryDto>> messageList = new ArrayList<>();
        try {
            List<Category> categories = categoryrepository.findAll();
            for (Category category : categories) {
                CategoryDto dto = categorymapperimpl.categoryToCategoryDto(category);
                messageList.add(new Message<>(HttpStatus.OK, Constants.CATEGORY_FOUND, dto));
            }
            return messageList;
        } catch (Exception e) {
            messageList.add(new Message<>(HttpStatus.INTERNAL_SERVER_ERROR,
                    Constants.SOMETHING_WENT_WRONG + ": " + e.getMessage(), null));
            return messageList;
        }
    }
}

//		
//
//
