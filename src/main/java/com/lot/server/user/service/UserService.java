package com.lot.server.user.service;

import com.lot.server.user.domain.model.UserDTO;
import java.util.List;
public interface UserService {
    UserDTO getUserById(Long id);
    List<UserDTO> getAllUsers();
    boolean addUser(UserDTO user);
    boolean updateUser(UserDTO user);
    boolean deleteUser(Long id);
}
