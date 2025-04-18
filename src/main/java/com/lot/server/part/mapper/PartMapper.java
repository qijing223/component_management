package com.lot.server.part.mapper;

import com.lot.server.part.domain.entity.PartDO;
import com.lot.server.part.domain.entity.PartStatus;
import com.lot.server.part.domain.model.PartDTO;
import com.lot.server.part.domain.model.ReturnedDTO;
import com.lot.server.product.mapper.JsonMapTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PartMapper {

// Insert Component (using auto-increment primary key)
    @Insert("INSERT INTO part (part_number, part_id, part_name, borrowed_employee_id, status, cost, product_id) " +
            "VALUES (#{partNumber}, #{partId}, #{partName}, #{borrowedEmployeeId}, CAST(#{status} AS part_status), #{cost}, #{productId})")
    @Results({
            @Result(property = "productId", column = "product_id", typeHandler = JsonMapTypeHandler.class)
    })
    int insertPart(PartDTO product);

    // Query by part_id
    @Select("SELECT part_number, part_id, part_name, borrowed_employee_id, status, cost, product_id FROM part WHERE part_id = #{id}")
    @Results({
            @Result(property = "productId", column = "product_id", typeHandler = JsonMapTypeHandler.class)
    })
    PartDTO getPartById(@Param("id") Integer id);

    @Select("SELECT * FROM part WHERE status = CAST(#{status} AS part_status)")
    @Results({
            @Result(property = "productId", column = "product_id", typeHandler = JsonMapTypeHandler.class)
    })
    List<PartDTO> selectByStatus(@Param("status") String status);


    @Update("UPDATE part SET " +
            "part_number = #{partNumber}, " +
            "part_id = #{partId}, " +
            "part_name = #{partName}, " +
            "borrowed_employee_id = #{borrowedEmployeeId}, " +
            "status = CAST(#{status} AS part_status), " +
            "cost = #{cost}, " +
            "product_id = #{status} " +
            "WHERE part_id = #{partId}")
    @Results({
            @Result(property = "productId", column = "product_id", typeHandler = JsonMapTypeHandler.class)
    })
    int updatePart(PartDTO part);

    // Delete Component
    @Delete("DELETE FROM part WHERE part_id = #{id}")
    @Results({
            @Result(property = "productId", column = "product_id", typeHandler = JsonMapTypeHandler.class)
    })
    int deletePartById(@Param("id") Integer id);

    // Query all Components
    @Select("SELECT part_number, part_id, part_name, borrowed_employee_id, status, cost, product_id FROM part")
    @Results({
            @Result(property = "productId", column = "product_id", typeHandler = JsonMapTypeHandler.class)
    })
    List<PartDTO> selectAllParts();

    @Select("SELECT part_number, part_id, part_name, borrowed_employee_id, status, cost, product_id FROM part WHERE borrowed_employee_id = #{userId} AND status = 'borrow-out'")
    @Results({
            @Result(property = "productId", column = "product_id", typeHandler = JsonMapTypeHandler.class)
    })
    List<PartDTO> selectBorrowedById(@Param("userId") Integer userId);

    @Select("SELECT part_id, part_name, return_time, borrow_employee_id FROM returned_part WHERE borrow_employee_id = #{userId}")
    @Results({
            @Result(property = "productId", column = "product_id", typeHandler = JsonMapTypeHandler.class)
    })
    List<ReturnedDTO> selectReturnedById(@Param("userId") Integer userId);

    @Update("UPDATE part SET borrowed_employee_id = #{userId} WHERE part_id = #{partId}")
    @Results({
            @Result(property = "productId", column = "product_id", typeHandler = JsonMapTypeHandler.class)
    })
    void updateBorrowedByPartId(@Param("userId") Integer userId, @Param("partId") Integer partId);

    @Update("UPDATE part SET borrowed_employee_id = null WHERE part_id = #{partId}")
    @Results({
            @Result(property = "productId", column = "product_id", typeHandler = JsonMapTypeHandler.class)
    })
    void deleteBorrowedByPartId(@Param("partId") Integer partId);

    @Insert("INSERT INTO returned_part (part_id, part_name, return_time, borrow_employee_id) VALUES (#{partId}, #{partName}, #{returnTime}, #{borrowEmployeeId})")
    @Results({
            @Result(property = "productId", column = "product_id", typeHandler = JsonMapTypeHandler.class)
    })
    void insertReturned(ReturnedDTO returnedDTO);

    // Query components by product ID
    @Select("SELECT part_number, part_id, part_name, borrowed_employee_id, status, cost, product_id FROM part WHERE product_id = #{productId}")
    @Results({
            @Result(property = "productId", column = "product_id", typeHandler = JsonMapTypeHandler.class)
    })
    List<PartDTO> selectByProductId(@Param("productId") Integer productId);
}
