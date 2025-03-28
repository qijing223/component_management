package com.lot.server.component.mapper;

import com.lot.server.component.domain.entity.ComponentDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ComponentMapper {

    // 插入 Component（使用自增主键）
    @Insert("INSERT INTO part (part_id, status, product_name, product_id, cost, part_name) " +
            "VALUES (#{partId}, #{status}, #{productName}, #{productId}, #{cost}, #{partName})")
    //@Options(useGeneratedKeys = true, keyProperty = "partId", keyColumn = "part_id")
    int insertProduct(ComponentDO product);

    // 根据 part_id 查询
    @Select("SELECT part_id, status, product_name, product_id, cost, part_name FROM part WHERE part_id = #{id}")
    ComponentDO getProductById(@Param("id") Integer id);

    // 更新 Component
    @Update("UPDATE part SET " +
            "status = #{status}, " +
            "product_name = #{productName}, " +
            "product_id = #{productId}, " +
            "cost = #{cost}, " +
            "part_name = #{partName} " +
            "WHERE part_id = #{partId}")
    int updateProduct(ComponentDO product);

    // 删除 Component
    @Delete("DELETE FROM part WHERE part_id = #{id}")
    int deleteProductById(@Param("id") Integer id);

    // 查询所有 Component
    @Select("SELECT part_id, status, product_name, product_id, cost, part_name FROM part")
    List<ComponentDO> selectAllProducts();
}
