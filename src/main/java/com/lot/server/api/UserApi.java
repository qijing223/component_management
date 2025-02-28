package com.lot.server.api;


import com.lot.server.user.domain.entity.UserEntity;
import com.lot.server.user.domain.model.UserDTO;
import com.lot.server.user.service.UserService;
import com.lot.server.common.bean.ResultTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/user")
public class  UserApi {
    @Autowired
    private UserService userService;
    

    @GetMapping("/{id}")
    public ResultTO<UserDTO> getUserById(Long id)
    {
        UserDTO user=userService.getUserById(id);
        return user!=null? ResultTO.success(user):ResultTO.error(404,"User not found");
    }
    @GetMapping
    public ResultTO<List<UserDTO>> getAllUsers()
    {
        List<UserDTO> users=userService.getAllUsers();
        return ResultTO.success(users);
    }

    @PostMapping
    public ResultTO<String> addUser(@RequestBody UserDTO user)
    {
        boolean result=userService.addUser(user);
        return userService.addUser(user) ? ResultTO.<String>success("User added") : ResultTO.error("Failed to add user");
    }
    @PutMapping
    public ResultTO<String> updateUser(@RequestBody UserDTO user) {
        return userService.updateUser(user) ? ResultTO.success("User updated") : ResultTO.error("Failed to update user");
    }

    @DeleteMapping("/{id}")
    public ResultTO<String> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id) ? ResultTO.success("User deleted") : ResultTO.error("Failed to delete user");
    }
}
