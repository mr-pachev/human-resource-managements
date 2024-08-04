package bg.softuni.human_resource_managements.service.impl;

import bg.softuni.human_resource_managements.model.dto.AddUserDTO;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private ModelMapper mockModelMapper;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private RoleRepository mockRoleRepository;

    @Mock
    private PasswordEncoder mockPasswordEncoder;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private User testUser;
    private UserDTO userDTO;
    private AddUserDTO addUserDTO;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("Pachev");
        testUser.setRole(new Role(RoleName.ADMIN));

        userDTO = new UserDTO();
        userDTO.setUserId(1L);
        userDTO.setUsername("Pachev");
        userDTO.setRole("ADMIN");

        addUserDTO = new AddUserDTO();
        addUserDTO.setUsername("Pachev");
        addUserDTO.setIdentificationNumber("1234567890");
        addUserDTO.setPassword("password");
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
    void testIsExistUser_UserExists() {
        when(mockUserRepository.findAll()).thenReturn(List.of(testUser));
        when(mockModelMapper.map(testUser, UserDTO.class)).thenReturn(userDTO);

        boolean result = userServiceImpl.isExistUser(userDTO.getUsername());

        assertTrue(result);
    }

    @Test
    void testIsExistUser_UserDoesNotExist() {
        when(mockUserRepository.findAll()).thenReturn(new ArrayList<>());

        boolean result = userServiceImpl.isExistUser(userDTO.getUsername());

        assertFalse(result);
    }

    @Test
    void testAddUser() {
        when(mockModelMapper.map(addUserDTO, User.class)).thenReturn(testUser);
        when(mockUserRepository.count()).thenReturn(0L);
        when(mockRoleRepository.findByRoleName(RoleName.ADMIN)).thenReturn(new Role(RoleName.ADMIN));
        when(mockPasswordEncoder.encode(addUserDTO.getPassword())).thenReturn("encodedPassword");

        userServiceImpl.addUser(addUserDTO);

        verify(mockUserRepository, times(1)).save(testUser);
        assertEquals("encodedPassword", testUser.getPassword());
        assertEquals(RoleName.ADMIN, testUser.getRole().getRoleName());
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
    void testEditUser() {
        when(mockUserRepository.findByIdentificationNumber(userDTO.getIdentificationNumber()))
                .thenReturn(Optional.of(testUser));
        when(mockRoleRepository.findByRoleName(RoleName.ADMIN)).thenReturn(new Role(RoleName.ADMIN));
        when(mockUserRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        userServiceImpl.editUser(userDTO);

        verify(mockUserRepository, times(1)).save(testUser);
        assertEquals(RoleName.ADMIN, testUser.getRole().getRoleName());
    }

    @Test
    void testRemoveUser() {
        doNothing().when(mockUserRepository).deleteById(1L);

        userServiceImpl.removeUser(1L);

        verify(mockUserRepository, times(1)).deleteById(1L);
    }
}
