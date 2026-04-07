package com.conanthelibrarian.librarymanagementsystem.controller;

import com.conanthelibrarian.librarymanagementsystem.dto.UserDTO;
import com.conanthelibrarian.librarymanagementsystem.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping("/createuser")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createUser(userDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/active-loans/more-than/{x}")
    public ResponseEntity<List<UserDTO>> getUsersWithMoreThanXActiveLoans(@PathVariable Integer x) {
        return ResponseEntity.ok(userService.getUsersWithMoreThanXActiveLoans(x));
    }

    @GetMapping("/total-loans/more-than/{x}")
    public ResponseEntity<List<UserDTO>> getUsersWithMoreThanXTotalLoans(@PathVariable Integer x) {
        return ResponseEntity.ok(userService.getUsersWithMoreThanXTotalLoans(x));
    }

    @GetMapping("/name/{name}")
    public UserDTO getUserByName(@PathVariable String name) {

        return userService.getUserByName(name);
    }
}
