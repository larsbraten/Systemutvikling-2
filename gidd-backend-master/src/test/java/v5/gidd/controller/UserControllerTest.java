package v5.gidd.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import v5.gidd.GiddApplication;
import v5.gidd.entities.message.Message;
import v5.gidd.entities.message.Status;
import v5.gidd.entities.user.User;
import v5.gidd.service.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;


@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = MOCK, classes = GiddApplication.class)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @InjectMocks
    private UserController userController;


    @BeforeEach
    public void setup() {

    }

    @Test
    @WithMockUser
    public void contextLoads() {
        assertThat(userController).isNotNull();
        assertThat(mockMvc).isNotNull();
    }

    @Test
    @WithMockUser
    void testCreateUser() throws Exception {
        Gson gson = new Gson();
        User user = new User();

        String json = gson.toJson(user);
        when(userService.registerUser(Mockito.any(User.class))).thenReturn(new Message<>(Status.REGISTRATION_SUCCESS, "Successfully registered a user", null));

        RequestBuilder request = MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        MockHttpServletResponse res = mockMvc.perform(request)
                .andReturn()
                .getResponse();

        assertThat(res.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(res.getContentAsString()).contains("REGISTRATION_SUCCESS");
    }

    @Test
    @WithMockUser
    void testCreateUserThatAlreadyExist() throws Exception {
        Gson gson = new Gson();
        User user = new User();

        String json = gson.toJson(user);
        when(userService.registerUser(Mockito.any(User.class))).thenReturn(new Message<>(Status.REGISTRATION_ERROR,"User already exists", null));

        RequestBuilder request = MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON);
        MockHttpServletResponse res = mockMvc.perform(request).andReturn().getResponse();

        assertThat(res.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(res.getContentAsString()).contains("User already exists");
    }

    /*@Test
    @WithMockUser
    void testVerifyUser() throws Exception {
        String key = "key";

        when(userService.verifyUser(new TokenKey())).thenReturn(new Message<>(Status.Ok, "Successfully verified user", null));

        RequestBuilder request = MockMvcRequestBuilders.post("/user/verify/{key}", key)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        MockHttpServletResponse res = mockMvc.perform(request)
                .andReturn()
                .getResponse();

        assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value());
    }*/

    @Test
    @WithMockUser
    void testModifyUser() throws Exception {
        Gson gson = new Gson();
        User user = new User();

        String json = gson.toJson(user);
        when(userService.alterUser(Mockito.any(User.class))).thenReturn(new Message<>(Status.Ok, "Successfully modified user", null));

        RequestBuilder request = MockMvcRequestBuilders.put("/user").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON);
        MockHttpServletResponse res = mockMvc.perform(request).andReturn().getResponse();

        assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(res.getContentAsString()).contains("Successfully modified user");
    }

    @Test
    @WithMockUser
    void testModifyAUserDoesNotExist() throws Exception {
        Gson gson = new Gson();
        User user = new User();

        String json = gson.toJson(user);
        when(userService.alterUser(Mockito.any(User.class))).thenReturn(new Message<>(Status.USER_NOT_FOUND,"User does not exist", null));

        RequestBuilder request = MockMvcRequestBuilders.put("/user").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON);
        MockHttpServletResponse res = mockMvc.perform(request).andReturn().getResponse();

        assertThat(res.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(res.getContentAsString()).contains("User does not exist");
    }

    @Test
    @WithMockUser
    void testDeleteUser() throws Exception {
        Gson gson = new Gson();
        User user = new User();

        String json = gson.toJson(user);
        when(userService.deleteUser(Mockito.any(User.class))).thenReturn(new Message<>(Status.Ok, "Successfully deleted user", null));

        RequestBuilder request = MockMvcRequestBuilders.delete("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        MockHttpServletResponse res = mockMvc.perform(request)
                .andReturn()
                .getResponse();

        assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(res.getContentAsString()).contains("Successfully deleted user");

    }
}
