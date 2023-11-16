package id.co.mii.serverapp.controllers;

import id.co.mii.serverapp.models.Category;
import id.co.mii.serverapp.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
@AllArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
  private CategoryService categoryService;

  @GetMapping
  public ResponseEntity<List<Category>> getAll() {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(categoryService.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Category> getById(@PathVariable Integer id) {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(categoryService.getById(id));
  }
}
