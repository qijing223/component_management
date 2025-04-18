package com.lot.server.product.mapper;

import com.lot.server.product.domain.model.ProductDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {

    // Query by product_id
    @Select("SELECT * FROM Product WHERE product_id = #{id}")
    @Results({
            @Result(property = "partList", column = "part_list", typeHandler = JsonMapTypeHandler.class)
    })
    ProductDTO selectProductById(@Param("id") Integer id);

    // Query by product_name
    @Select("SELECT * FROM Product WHERE product_name = #{name}")
    @Results({
            @Result(property = "partList", column = "part_list", typeHandler = JsonMapTypeHandler.class)
    })
    ProductDTO selectProductByName(@Param("name") String name);

    // Query all products
    @Select("SELECT * FROM Product")
    @Results({
            @Result(property = "partList", column = "part_list", typeHandler = JsonMapTypeHandler.class)
    })
    List<ProductDTO> selectAllProducts();

    // Insert new product
    @Insert("INSERT INTO Product (product_id, product_name, number_part_in_stock, total_cost, number_part_check_out, lead_time, part_list) " +
            "VALUES (#{productId}, #{productName}, #{numberPartInStock}, #{totalCost}, #{numberPartCheckOut}, #{leadTime}, #{partList, jdbcType=VARCHAR, typeHandler=com.lot.server.product.mapper.JsonMapTypeHandler})")
    @Options(useGeneratedKeys = true, keyProperty = "productId", keyColumn = "product_id")
    int insertProduct(ProductDTO productDTO);

    // Update product information
    @Update("UPDATE Product SET " +
            "product_name = #{productName}, " +
            "number_part_in_stock = #{numberPartInStock}, " +
            "total_cost = #{totalCost}, " +
            "number_part_check_out = #{numberPartCheckOut}, " +
            "lead_time = #{leadTime}, " +
            "part_list = CAST(#{partList, jdbcType=VARCHAR, typeHandler=com.lot.server.product.mapper.JsonMapTypeHandler} AS json) " +
            "WHERE product_id = #{productId}")
    int updateProduct(ProductDTO productDTO);

    // Delete product by ID
    @Delete("DELETE FROM Product WHERE product_id = #{id}")
    @Results({
            @Result(property = "partList", column = "part_list", typeHandler = JsonMapTypeHandler.class)
    })
    int deleteProductById(@Param("id") Integer id);

}