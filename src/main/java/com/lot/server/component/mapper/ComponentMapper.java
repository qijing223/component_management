package com.lot.server.component.mapper;
import com.lot.server.component.domain.entity.ComponentStatus;
import org.springframework.stereotype.Repository;
import com.lot.server.component.domain.entity.ComponentDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
@Repository
public interface ComponentMapper {

    @Select("SELECT * FROM products WHERE product_id = #{id}")
    @Results({
            @Result(column = "product_id", property = "productsId"),
            @Result(column = "category_id", property = "category"),
            @Result(column = "status", property = "status", typeHandler = ComponentStatusTypeHandler.class)
    })
    ComponentDO getProductById(@Param("id") Integer id);

    @Select("SELECT * FROM products")
    @Results({
            @Result(column = "product_id", property = "productsId"),
            @Result(column = "category_id", property = "category"),
            @Result(column = "status", property = "status", typeHandler = ComponentStatusTypeHandler.class)
    })
    List<ComponentDO> selectAllProducts();

    @Insert("INSERT INTO products (product_id, category_id, status) " +
            "VALUES (#{productsId}, #{category}, LOWER(#{status, typeHandler=com.lot.server.component.mapper.ComponentStatusTypeHandler}))")
    @Options(useGeneratedKeys = true, keyProperty = "productsId", keyColumn = "product_id")
    void insertProduct(ComponentDO componentDO);

//    @Update("UPDATE products SET category_id = #{category}, status = #{status} WHERE product_id = #{productsId}")
//    void updateProduct(ComponentDO componentDO);

    @Update("UPDATE products SET " +
            "category_id = #{componentDO.category}, " +
            "status = LOWER(#{componentDO.status, typeHandler=com.lot.server.component.mapper.ComponentStatusTypeHandler}) " +
            "WHERE product_id = #{componentDO.productsId}")
    void updateProduct(@Param("componentDO") ComponentDO componentDO);


    @Delete("DELETE FROM products WHERE product_id = #{id}")
    void deleteProductById(@Param("id") Integer id);
}
