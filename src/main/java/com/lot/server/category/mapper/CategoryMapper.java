package com.lot.server.category.mapper;

import com.lot.server.category.domain.entity.CategoryDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {

    // 根据 product_id 查询
    @Select("SELECT * FROM Product WHERE product_id = #{id}")
    CategoryDO selectCategoryById(@Param("id") Integer id);

    // 根据 product_name 查询
    @Select("SELECT * FROM Product WHERE product_name = #{name}")
    CategoryDO selectCategoryByName(@Param("name") String name);

    // 查询所有产品
    @Select("SELECT * FROM Product")
    List<CategoryDO> selectAllCategories();

    // 插入新产品
    @Insert("INSERT INTO Product (product_name, number_part_check_out, number_part_in_stock, total_cost) " +
            "VALUES (#{productName}, #{numberPartCheckOut}, #{numberPartInStock}, #{totalCost})")
    @Options(useGeneratedKeys = true, keyProperty = "productId", keyColumn = "product_id")
    int insertCategory(CategoryDO categoryDO);

    // 更新产品信息
    @Update("UPDATE Product SET " +
            "product_name = #{productName}, " +
            "number_part_check_out = #{numberPartCheckOut}, " +
            "number_part_in_stock = #{numberPartInStock}, " +
            "total_cost = #{totalCost} " +
            "WHERE product_id = #{productId}")
    int updateCategory(CategoryDO categoryDO);

    // 根据 ID 删除产品
    @Delete("DELETE FROM Product WHERE product_id = #{id}")
    int deleteCategoryById(@Param("id") Integer id);
}