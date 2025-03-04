package com.lot.server.category.mapper;

import com.lot.server.category.domain.entity.CategoryDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Select("SELECT * FROM category WHERE category_id = #{id}")
    CategoryDO selectCategoryById(@Param("id") Integer id);

    @Select("SELECT * FROM category")
    List<CategoryDO> selectAllCategories();

    @Insert("INSERT INTO category (category_name, available_number, usage_number, borrow_number) " +
            "VALUES (#{categoryName}, #{availableNumber}, #{usageNumber}, #{borrowNumber})")
    @Options(useGeneratedKeys = true, keyProperty = "categoryId", keyColumn = "category_id")
    void insertCategory(CategoryDO categoryDO);

    @Update("UPDATE category SET " +
            "category_name = #{categoryName}, " +
            "available_number = #{availableNumber}, " +
            "usage_number = #{usageNumber}, " +
            "borrow_number = #{borrowNumber} " +
            "WHERE category_id = #{categoryId}")
    void updateCategory(CategoryDO categoryDO);

    @Delete("DELETE FROM category WHERE category_id = #{id}")
    void deleteCategoryById(@Param("id") Integer id);
}