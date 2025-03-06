package com.lot.server.component.mapper;

import com.lot.server.component.domain.entity.ComponentDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ComponentMapper {

    @Select("SELECT * FROM Products WHERE Products_id = #{id}")
    @Results({
            @Result(column = "Products_id", property = "productsId"),
            @Result(column = "Product_name", property = "productName"),
            @Result(column = "Category", property = "category"),
            @Result(column = "status", property = "status", typeHandler = ComponentStatusTypeHandler.class) // 处理 status
    })
    ComponentDO selectProductById(@Param("id") Integer id);

    @Select("SELECT * FROM Products")
    @Results({
            @Result(column = "Products_id", property = "productsId"),
            @Result(column = "Product_name", property = "productName"),
            @Result(column = "Category", property = "category"),
            @Result(column = "status", property = "status", typeHandler = ComponentStatusTypeHandler.class) // 处理 status
    })
    List<ComponentDO> selectAllProducts();

    @Insert("INSERT INTO Products (Product_name, Category, status) " +
            "VALUES (#{productName}, #{category}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "productsId", keyColumn = "Products_id")
    void insertProduct(@Param("componentDO") ComponentDO componentDO);

    @Update("UPDATE Products SET " +
            "Product_name = #{productName}, " +
            "Category = #{category}, " +
            "status = #{status} " +
            "WHERE Products_id = #{productsId}")
    void updateProduct(@Param("componentDO") ComponentDO componentDO);

    @Delete("DELETE FROM Products WHERE Products_id = #{id}")
    void deleteProductById(@Param("id") Integer id);
}
