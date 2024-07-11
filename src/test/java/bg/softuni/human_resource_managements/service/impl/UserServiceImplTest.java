package bg.softuni.human_resource_managements.service.impl;

import bg.softuni.human_resource_managements.model.dto.AddUserDTO;
import bg.softuni.human_resource_managements.model.entity.User;
import bg.softuni.human_resource_managements.repository.RoleRepository;
import bg.softuni.human_resource_managements.repository.UserRepository;
import bg.softuni.human_resource_managements.service.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestClient;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    private UserServiceImpl toTest;
    @Captor
    ArgumentCaptor<User> userArgumentCaptor;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private EmployeeService mockEmployeeService;
    @Mock
    private RoleRepository mockRoleRepository;
    @Mock
    private PasswordEncoder mockPasswordEncoder;

    @BeforeEach
    void setUp(){
        toTest = new UserServiceImpl(
                new ModelMapper(),
                mockPasswordEncoder,
                mockUserRepository,
                mockEmployeeService,
                mockRoleRepository
        );
    }

    @Test
    void addUser(){
        AddUserDTO addUserDTO = new AddUserDTO();

        addUserDTO.setUserId(1);
        addUserDTO.setUsername("test");
        addUserDTO.setRole("ADMIN");
        addUserDTO.setPassword("0000");
        addUserDTO.setConfirmPassword("0000");
        addUserDTO.setIdentificationNumber("1111111112");

        toTest.addUser(addUserDTO);

        verify(mockUserRepository).save(userArgumentCaptor.capture());

        User actualitySaveUserCapture = userArgumentCaptor.getValue();

        Assertions.assertEquals(addUserDTO.getUsername(), actualitySaveUserCapture.getUsername());
    }
}
