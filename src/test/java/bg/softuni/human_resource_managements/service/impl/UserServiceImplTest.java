package bg.softuni.human_resource_managements.service.impl;

import bg.softuni.human_resource_managements.model.dto.AddUserDTO;
import bg.softuni.human_resource_managements.repository.RoleRepository;
import bg.softuni.human_resource_managements.repository.UserRepository;
import bg.softuni.human_resource_managements.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

class UserServiceImplTest {
    private UserServiceImpl toTest;

    private ModelMapper mockModelMapper;
    private PasswordEncoder mockPasswordEncoder;
    private UserRepository mockUserRepository;
    private EmployeeService mockEmployeeService;
    private RoleRepository mockRoleRepository;
    private RestClient mockUsersRestClient;

    @BeforeEach
    void setUp(){
        mockModelMapper = Mockito.mock(ModelMapper.class);
        mockPasswordEncoder = Mockito.mock(PasswordEncoder.class);
        mockUserRepository = Mockito.mock(UserRepository.class);
        mockEmployeeService = Mockito.mock(EmployeeService.class);
        mockRoleRepository = Mockito.mock(RoleRepository.class);
        mockUsersRestClient = Mockito.mock(RestClient.class);

        toTest = new UserServiceImpl(mockModelMapper, mockPasswordEncoder,
                mockUserRepository, mockEmployeeService,
                mockRoleRepository, mockUsersRestClient);
    }

    @Test
    void addUser() {
        List<AddUserDTO> testUsers = new ArrayList<>();
        AddUserDTO testUser1 = new AddUserDTO();

        testUser1.setUsername("test");
        testUser1.setPassword("0000");
        testUser1.setIdentificationNumber("1111111112");
        testUser1.setRole("ADMIN");

        testUsers.add(testUser1);

        AddUserDTO testUser2 = new AddUserDTO();

        testUser2.setUsername("test");
        testUser2.setPassword("0000");
        testUser2.setIdentificationNumber("1111111112");
        testUser2.setRole("ADMIN");

        testUsers.add(testUser2);

        boolean
    }
}
