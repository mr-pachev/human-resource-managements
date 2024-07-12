package bg.softuni.human_resource_managements.service.impl;

import bg.softuni.human_resource_managements.model.dto.UserDTO;
import bg.softuni.human_resource_managements.model.entity.Role;
import bg.softuni.human_resource_managements.model.entity.User;
import bg.softuni.human_resource_managements.model.enums.RoleName;
import bg.softuni.human_resource_managements.repository.RoleRepository;
import bg.softuni.human_resource_managements.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private ModelMapper mockModelMapper;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private RoleRepository mockRoleRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private User testUser;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L); // Set user id for testing
        testUser.setUsername("Pachev");
        testUser.setRole(new Role(RoleName.ADMIN));

        userDTO = new UserDTO();
        userDTO.setUserId(1L); // Set user id for testing
        userDTO.setUsername("Pachev");
        userDTO.setRole("ADMIN");
    }

    @Test
    void testMap() {
        when(mockModelMapper.map(testUser, UserDTO.class)).thenReturn(userDTO);

        UserDTO resultUserDTO = userServiceImpl.map(testUser);

        assertEquals(userDTO.getUsername(), resultUserDTO.getUsername());
        assertEquals(userDTO.getRole(), resultUserDTO.getRole());
    }

    @Test
    void testReMapUser() {
        when(mockUserRepository.findByUsername(userDTO.getUsername())).thenReturn(Optional.of(testUser));
        when(mockRoleRepository.findByRoleName(RoleName.ADMIN)).thenReturn(new Role(RoleName.ADMIN));

        User resultUser = userServiceImpl.reMapUser(userDTO);

        assertEquals(userDTO.getUsername(), resultUser.getUsername());
        assertEquals(userDTO.getRole(), resultUser.getRole().getRoleName().name());
    }

    @Test
    void testGetUserDetails() {
        when(mockUserRepository.findById(1L)).thenReturn(Optional.of(testUser));

        when(mockModelMapper.map(testUser, UserDTO.class)).thenReturn(userDTO);

        UserDTO resultUserDTO = userServiceImpl.getUserDetails(1L);

        assertEquals(userDTO.getUsername(), resultUserDTO.getUsername());
        assertEquals(userDTO.getRole(), resultUserDTO.getRole());
    }
    @Test
    void testRemoveUser() {
        doNothing().when(mockUserRepository).deleteById(1L);

        userServiceImpl.removeUser(1L);

        verify(mockUserRepository, times(1)).deleteById(1L);
    }
    @Test
    void testGetAllUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(testUser);

        when(mockUserRepository.findAll()).thenReturn(userList);
        when(mockModelMapper.map(testUser, UserDTO.class)).thenReturn(userDTO);

        List<UserDTO> resultUserDTOS = userServiceImpl.getAllUsers();

        assertEquals(1, resultUserDTOS.size());
        assertEquals(userDTO.getUsername(), resultUserDTOS.get(0).getUsername());
        assertEquals(userDTO.getRole(), resultUserDTOS.get(0).getRole());
    }

    @Test
    void testEditUser() {
        when(mockUserRepository.findByUsername(userDTO.getUsername())).thenReturn(Optional.of(testUser));
        when(mockRoleRepository.findByRoleName(RoleName.ADMIN)).thenReturn(new Role(RoleName.ADMIN));
        when(mockUserRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0)); // Mock save() to return the saved entity

        userServiceImpl.editUser(userDTO);

        verify(mockUserRepository, times(1)).save(any(User.class)); // Verify that save() was called once with any User argument
    }
}
