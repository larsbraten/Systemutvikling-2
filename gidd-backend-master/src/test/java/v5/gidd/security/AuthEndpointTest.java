package v5.gidd.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import v5.gidd.GiddApplication;
import v5.gidd.service.UserService;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = MOCK, classes = GiddApplication.class)
@AutoConfigureMockMvc

public class AuthEndpointTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(username = "sergio_martinez0311@hotmail.com",password = "test1234")
    public void TestEndpointWithCredentials() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/auth");
        mvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    public void TestEndpointWithoutCredentials() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/auth");
        mvc.perform(request)
                .andExpect(status().is(401));
    }
}
