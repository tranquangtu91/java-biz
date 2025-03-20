package com.base.admin.infrastructure.adapter.in.rest.category;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.base.admin.application.service.category.CategoryService;
import com.base.admin.domain.dto.category.GetCategoryDataDto;
import com.base.admin.domain.entity.category.Category;
import com.base.admin.domain.entity.category.CategoryData;
import com.base.common.application.utils.convert.object.ObjectUtils;
import com.base.common.domain.dto.generalresponse.GeneralResponse;
import com.base.common.infrastructure.adapter.in.rest.entity.impl.BaseEntityController;

@RestController()
@RequestMapping(path = "/api/v1/category")
public class CategoryController extends BaseEntityController<Category> {
    CategoryService categoryService;

    CategoryController(@Autowired CategoryService entityService) {
        this.entityService = entityService;
        this.categoryService = entityService;
        this.domainClass = Category.class;
        getExcludeFields = Arrays.asList("updatedBy", "updatedAt", "deleted");
    }

    @GetMapping("/{id:\\d+}/data")
    public ResponseEntity<?> data(@PathVariable Long id) {
        Category item = entityService.get(id, null);

        if (item == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<CategoryData> data = item.getData();

        String[] __getExcludeFields = Arrays.copyOf(getExcludeFields.toArray(), getExcludeFields.size(), String[].class);

        GeneralResponse gr = new GeneralResponse();
        gr.setValue(data);
        return new ResponseEntity<>(ObjectUtils.modifyData(gr, __getExcludeFields), HttpStatus.OK);
    }

    @GetMapping(path = { "/getCategoryData", "/get-category-data", "/data" })
    public ResponseEntity<?> getCategoryData(@RequestParam Map<String, Object> params) {
        GetCategoryDataDto dto = (GetCategoryDataDto) ObjectUtils.mapToObject(GetCategoryDataDto.class, params);
        List<CategoryData> data = categoryService.getCategoryData(dto);

        String[] __getExcludeFields = Arrays.copyOf(getExcludeFields.toArray(), getExcludeFields.size(), String[].class);

        GeneralResponse gr = new GeneralResponse();
        gr.setValue(data);
        return new ResponseEntity<>(ObjectUtils.modifyData(gr, __getExcludeFields), HttpStatus.OK);
    }
}
