package com.lot.server.user.mapper;

import com.lot.server.user.domain.entity.UserEntity;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM users WHERE id = #{id}")
    UserEntity getUserById(Long id);

    @Select("SELECT * FROM users")
    List<UserEntity> getAllUsers();

    @Insert("INSERT INTO users (username, password, email, role) VALUES (#{username}, #{password}, #{email}, #{role})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addUser(UserEntity user);

    @Update("UPDATE users SET username=#{username}, email=#{email}, role=#{role} WHERE id=#{id}")
    int updateUser(UserEntity user);

    @Delete("DELETE FROM users WHERE id=#{id}")
    int deleteUser(Long id);
}
