package com.conanthelibrarian.librarymanagementsystem.controller;

import com.conanthelibrarian.librarymanagementsystem.dto.UserDTO;
import com.conanthelibrarian.librarymanagementsystem.entity.User;
import com.conanthelibrarian.librarymanagementsystem.mapper.UserMapper;
import com.conanthelibrarian.librarymanagementsystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        log.info("GET /api/users - Recuperando todos los usuarios");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) {
        log.info("GET /api/users/{} - Buscando usuario por ID", id);
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping("/createuser")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        log.info("POST /api/users/createuser - Creando usuario: {}", userDTO);
        User user = UserMapper.toEntity(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createUser(userDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
        log.info("PUT /api/users/{} - Actualizando usuario con datos: {}", id, userDTO);
        User user = UserMapper.toEntity(userDTO);
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        log.warn("DELETE /api/users/{} - Eliminando usuario", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/active-loans/more-than/{x}")
    public ResponseEntity<List<UserDTO>> getUsersWithMoreThanXActiveLoans(@PathVariable Integer x) {
        log.info("GET /api/users/active-loans/more-than/{} - Usuarios con más préstamos activos", x);
        return ResponseEntity.ok(userService.getUsersWithMoreThanXActiveLoans(x));
    }

    @GetMapping("/total-loans/more-than/{x}")
    public ResponseEntity<List<UserDTO>> getUsersWithMoreThanXTotalLoans(@PathVariable Integer x) {
        log.info("GET /api/users/total-loans/more-than/{} - Usuarios con más préstamos totales", x);
        return ResponseEntity.ok(userService.getUsersWithMoreThanXTotalLoans(x));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<UserDTO> getUserByName(@PathVariable String name) {
        log.info("GET /api/users/name/{} - Buscando usuario por nombre", name);
        return ResponseEntity.ok(userService.getUserByName(name));
    }
}
