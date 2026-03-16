package com.conanthelibrarian.librarymanagementsystem.mapper;

import com.conanthelibrarian.librarymanagementsystem.constants.Role;
import com.conanthelibrarian.librarymanagementsystem.dto.UserDTO;
import com.conanthelibrarian.librarymanagementsystem.entity.User;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para UserMapper.
 * <p>
 * Verifica la conversión entre User y UserDTO.
 */
class UserMapperTest {

    /**
     * Comprueba conversión Entity → DTO.
     */
    @Test
    void shouldConvertEntityToDTO() {

        User user = new User();
        user.setId(1);
        user.setName("Juan");
        user.setEmail("juan@test.com");
        user.setPassword("1234");
        user.setRole(Role.MEMBER);

        UserDTO dto = UserMapper.toDTO(user);

        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getName(), dto.getName());
        assertEquals(user.getEmail(), dto.getEmail());
        assertEquals(user.getPassword(), dto.getPassword());
        assertEquals(user.getRole(), dto.getRole());
    }

    /**
     * Comprueba conversión DTO → Entity.
     */
    @Test
    void shouldConvertDTOToEntity() {

        UserDTO dto = new UserDTO();
        dto.setId(1);
        dto.setName("Maria");
        dto.setEmail("maria@test.com");
        dto.setPassword("abcd");
        dto.setRole(Role.MEMBER);

        User user = UserMapper.toEntity(dto);

        assertEquals(dto.getId(), user.getId());
        assertEquals(dto.getName(), user.getName());
        assertEquals(dto.getEmail(), user.getEmail());
        assertEquals(dto.getPassword(), user.getPassword());
        assertEquals(dto.getRole(), user.getRole());
    }

    /**
     * Comprueba que devuelve null si la entidad es null.
     */
    @Test
    void shouldReturnNullWhenEntityIsNull() {

        UserDTO dto = UserMapper.toDTO(null);

        assertNull(dto);
    }

    /**
     * Comprueba que devuelve null si el DTO es null.
     */
    @Test
    void shouldReturnNullWhenDTOIsNull() {

        User user = UserMapper.toEntity(null);

        assertNull(user);
    }
}
