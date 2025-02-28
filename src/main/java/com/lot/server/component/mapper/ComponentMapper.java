package com.lot.server.component.mapper;

import com.lot.server.component.domain.entity.ComponentDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ComponentMapper {

    @Select("SELECT * FROM Products WHERE Products_id = #{id}")
    ComponentDO selectProductById(@Param("id") Integer id);

    @Select("SELECT * FROM Products")
    List<ComponentDO> selectAllProducts();

    @Insert("INSERT INTO Products (Product_name, Category) " +
            "VALUES (#{productName}, #{category})")
    @Options(useGeneratedKeys = true, keyProperty = "productsId", keyColumn = "Products_id")
    void insertProduct(ComponentDO componentDO);

    @Update("UPDATE Products SET " +
            "Product_name = #{productName}, " +
            "Category = #{category} " +
            "WHERE Products_id = #{productsId}")
    void updateProduct(ComponentDO componentDO);

    @Delete("DELETE FROM Products WHERE Products_id = #{id}")
    void deleteProductById(@Param("id") Integer id);
}
