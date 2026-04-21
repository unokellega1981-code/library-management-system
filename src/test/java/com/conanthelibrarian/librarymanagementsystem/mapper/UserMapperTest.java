package com.conanthelibrarian.librarymanagementsystem.mapper;

import com.conanthelibrarian.librarymanagementsystem.constants.Role;
import com.conanthelibrarian.librarymanagementsystem.dto.UserDTO;
import com.conanthelibrarian.librarymanagementsystem.entity.User;
import com.conanthelibrarian.librarymanagementsystem.exception.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    UserMapper userMapper = new UserMapper();

    @Test
    void shouldConvertEntityToDTO() {

        // GIVEN
        User user = new User();
        user.setId(1);
        user.setName("Juan");
        user.setEmail("juan@test.com");
        user.setPassword("1234");
        user.setRole(Role.MEMBER);

        // WHEN
        UserDTO userDTO = userMapper.toDTO(user);

        // THEN
        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getName(), userDTO.getName());
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertEquals(user.getPassword(), userDTO.getPassword());
        assertEquals(user.getRole(), userDTO.getRole());
    }

    @Test
    void shouldConvertDTOToEntity() {

        // GIVEN
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setName("Maria");
        userDTO.setEmail("maria@test.com");
        userDTO.setPassword("abcd");
        userDTO.setRole(Role.MEMBER);

        // WHEN
        User user = userMapper.toEntity(userDTO);

        // THEN
        assertEquals(userDTO.getId(), user.getId());
        assertEquals(userDTO.getName(), user.getName());
        assertEquals(userDTO.getEmail(), user.getEmail());
        assertEquals(userDTO.getPassword(), user.getPassword());
        assertEquals(userDTO.getRole(), user.getRole());
    }

    @Test
    void shouldReturnNullWhenEntityIsNull() {

        // WHEN
        UserDTO userDTO = userMapper.toDTO(null);

        // THEN
        assertNull(userDTO);
    }

    @Test
    void shouldThrowExceptionWhenDTOIsNull() {

        // THEN
        assertThrows(BadRequestException.class, () -> userMapper.toEntity(null));
    }
}
