package com.lot.server.category.mapper;

import com.lot.server.category.domain.entity.CategoryDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {

    // Query by product_id
    @Select("SELECT * FROM Product WHERE product_id = #{id}")
    CategoryDO selectCategoryById(@Param("id") Integer id);

    // Query by product_name
    @Select("SELECT * FROM Product WHERE product_name = #{name}")
    CategoryDO selectCategoryByName(@Param("name") String name);

    // Query all products
    @Select("SELECT * FROM Product")
    List<CategoryDO> selectAllCategories();

    // Insert new product
    @Insert("INSERT INTO Product (product_name, number_part_check_out, number_part_in_stock, total_cost) " +
            "VALUES (#{productName}, #{numberPartCheckOut}, #{numberPartInStock}, #{totalCost})")
    @Options(useGeneratedKeys = true, keyProperty = "productId", keyColumn = "product_id")
    int insertCategory(CategoryDO categoryDO);

    // Update product information
    @Update("UPDATE Product SET " +
            "product_name = #{productName}, " +
            "number_part_check_out = #{numberPartCheckOut}, " +
            "number_part_in_stock = #{numberPartInStock}, " +
            "total_cost = #{totalCost} " +
            "WHERE product_id = #{productId}")
    int updateCategory(CategoryDO categoryDO);

    // Delete product by ID
    @Delete("DELETE FROM Product WHERE product_id = #{id}")
    int deleteCategoryById(@Param("id") Integer id);
}