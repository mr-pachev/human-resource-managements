package bg.softuni.human_resource_managements.service.impl;

import bg.softuni.human_resource_managements.model.dto.AddUserDTO;
import bg.softuni.human_resource_managements.model.entity.User;
import bg.softuni.human_resource_managements.model.enums.RoleName;
import bg.softuni.human_resource_managements.repository.RoleRepository;
import bg.softuni.human_resource_managements.repository.UserRepository;
import bg.softuni.human_resource_managements.service.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestClient;

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
        AddUserDTO testUser = new AddUserDTO();

        testUser.setUsername("test");
        testUser.setPassword("0000");
        testUser.setIdentificationNumber("1111111112");
        testUser.setRole(mockRoleRepository.findByRoleName(RoleName.ADMIN).toString());


        boolean addUser = toTest.addUser(testUser);
        Assertions.assertEquals(true, addUser);
    }
}
