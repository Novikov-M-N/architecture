package com.github.novikovmn.architect.controller;

import com.github.novikovmn.architect.domain.Category;
import com.github.novikovmn.architect.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping({"category", "category/"})
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping({"", "/"})
    public void categoryList() {
        List<Category> categories = categoryService.getAll();
        for (Category category : categories) {
            System.out.println(category.toString());
        }
    }
}
