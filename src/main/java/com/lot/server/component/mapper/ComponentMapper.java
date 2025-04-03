package com.lot.server.component.mapper;

import com.lot.server.component.domain.entity.ComponentDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ComponentMapper {

    // Insert Component (using auto-increment primary key)
    @Insert("INSERT INTO part (part_id, status, product_name, product_id, cost, part_name) " +
            "VALUES (#{partId}, #{status}, #{productName}, #{productId}, #{cost}, #{partName})")
    //@Options(useGeneratedKeys = true, keyProperty = "partId", keyColumn = "part_id")
    int insertProduct(ComponentDO product);

    // Query by part_id
    @Select("SELECT part_id, status, product_name, product_id, cost, part_name FROM part WHERE part_id = #{id}")
    ComponentDO getProductById(@Param("id") Integer id);

    @Select("SELECT * FROM part WHERE status = #{status}")
    List<ComponentDO> selectByStatus(@Param("status") String status);

    // Update Component
    @Update("UPDATE part SET " +
            "status = #{status}, " +
            "product_name = #{productName}, " +
            "product_id = #{productId}, " +
            "cost = #{cost}, " +
            "part_name = #{partName} " +
            "WHERE part_id = #{partId}")
    int updateProduct(ComponentDO product);

    // Delete Component
    @Delete("DELETE FROM part WHERE part_id = #{id}")
    int deleteProductById(@Param("id") Integer id);

    // Query all Components
    @Select("SELECT part_id, status, product_name, product_id, cost, part_name FROM part")
    List<ComponentDO> selectAllProducts();

    // Query components by product ID
    @Select("SELECT part_id, status, product_name, product_id, cost, part_name FROM part WHERE product_id = #{productId}")
    List<ComponentDO> selectByProductId(@Param("productId") Integer productId);
}
