package com.CrmService.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CrmService.model.Category;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {


	Optional<Category> getBycategory(String trim);

}
