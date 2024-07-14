package bg.softuni.human_resource_managements.web;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bg.softuni.human_resource_managements.model.entity.Role;
import bg.softuni.human_resource_managements.model.entity.User;
import bg.softuni.human_resource_managements.model.enums.RoleName;
import bg.softuni.human_resource_managements.repository.RoleRepository;
import bg.softuni.human_resource_managements.repository.UserRepository;
import bg.softuni.human_resource_managements.service.UserService;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private WireMockServer wireMockServer;

    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer(WireMockConfiguration.options().port(8081));
        wireMockServer.start();

        wireMockServer.stubFor(get(urlPathEqualTo("/employees"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("[{\"identificationNumber\": \"2111111112\", \"name\": \"Test Employee\"}]")));
    }

    @AfterEach
    public void teardown() {
        wireMockServer.stop();
    }

    @Test
    void testRegistrationUser() throws Exception {
        mockMvc.perform(post("/registration")
                        .param("username", "test")
                        .param("identificationNumber", "2111111112")
                        .param("password", "0000")
                        .param("confirmPassword", "0000")
                        .param("role", "ADMIN")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        Optional<User> userEntityOpt = userRepository.findByUsername("test");

        Assertions.assertTrue(userEntityOpt.isPresent());

        User foundCreatUser = userEntityOpt.get();

        Assertions.assertEquals("test", foundCreatUser.getUsername());
        Assertions.assertEquals("2111111112", foundCreatUser.getIdentificationNumber());
        Assertions.assertTrue(passwordEncoder.matches("0000", foundCreatUser.getPassword()));
        Assertions.assertEquals("ADMIN", foundCreatUser.getRole().getRoleName().name());
    }
}
