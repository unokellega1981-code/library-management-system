package com.conanthelibrarian.librarymanagementsystem.service;

import com.conanthelibrarian.librarymanagementsystem.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> getAllUsers();

    UserDTO getUserById(Integer id);

    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser(Integer id, UserDTO userDTO);

    void deleteUser(Integer id);

    List<UserDTO> getUsersWithMoreThanXActiveLoans(int x);

    List<UserDTO> getUsersWithMoreThanXTotalLoans(int x);

    UserDTO getUserByName(String name);
}
