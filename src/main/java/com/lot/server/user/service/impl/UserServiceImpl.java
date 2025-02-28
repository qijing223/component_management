package com.lot.server.user.service.impl;

import com.lot.server.user.domain.entity.UserEntity;
import com.lot.server.user.domain.model.UserDTO;
import com.lot.server.user.mapper.UserMapper;
import com.lot.server.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDTO getUserById(Long id){
        UserEntity user=userMapper.getUserById(id);
        return user!=null? convertToDTO(user):null;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userMapper.getAllUsers()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean addUser(UserDTO user) {
        UserEntity entity = convertToEntity(user);
        return userMapper.addUser(entity) > 0;
    }

    @Override
    public boolean updateUser(UserDTO user) {
        UserEntity entity = convertToEntity(user);
        return userMapper.updateUser(entity) > 0;
    }

    @Override
    public boolean deleteUser(Long id) {
        return userMapper.deleteUser(id) > 0;
    }

    private UserDTO convertToDTO(UserEntity entity) {
        return new UserDTO(entity.getId(), entity.getUsername(), entity.getEmail(), entity.getRole());
    }

    private UserEntity convertToEntity(UserDTO dto) {
        UserEntity entity = new UserEntity();
        entity.setId(dto.getId());
        entity.setUsername(dto.getUsername());
        entity.setEmail(dto.getEmail());
        entity.setRole(dto.getRole());
        return entity;
    }
}
