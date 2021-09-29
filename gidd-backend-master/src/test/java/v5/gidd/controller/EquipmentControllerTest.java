package v5.gidd.controller;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
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
import v5.gidd.GiddApplication;

import v5.gidd.entities.message.StrictIndex;
import v5.gidd.entities.message.Message;
import v5.gidd.entities.message.Status;
import v5.gidd.entities.message.StrictIndex;

import v5.gidd.service.EquipmentService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = MOCK, classes = GiddApplication.class)
@AutoConfigureMockMvc
class EquipmentControllerTest {
    @MockBean
    private EquipmentService equipmentService;

    @InjectMocks
    private EquipmentController equipmentController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {

    }

    @Test
    @WithMockUser
    void contextLoads() {
        assertThat(equipmentController).isNotNull();
        assertThat(mockMvc).isNotNull();
    }

    @Test
    @WithMockUser
    void testClaimingEquipmentSuccess() throws Exception {
        when(equipmentService.claimEquipment(eq(new StrictIndex(1))))
                .thenReturn(new Message<>(Status.Ok, "Ok", null));

        MockHttpServletResponse res = mockMvc
                .perform(put("/equipment/1/claim")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @WithMockUser
    void testClaimingEquipmentFailOnAlreadyClaimed() throws Exception {
        when(equipmentService.claimEquipment(eq(new StrictIndex(1))))
                .thenReturn(new Message<>(Status.EQUIPMENT_ALREADY_CLAIMED, "Equipment already claimed", null));

        MockHttpServletResponse res = mockMvc
                .perform(put("/equipment/1/claim")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(res.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(res.getContentAsString()).contains("EQUIPMENT_ALREADY_CLAIMED");
        assertThat(res.getContentAsString()).contains("Equipment already claimed");
    }

    @Test
    @WithMockUser
    void testUnclaimingEquipmentSuccess() throws Exception {
        when(equipmentService.unclaimEquipment(eq(new StrictIndex(1))))

                .thenReturn(new Message<>(Status.Ok, "Ok", null));

        MockHttpServletResponse res = mockMvc
                .perform(delete("/equipment/1/claim")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @WithMockUser
    void testUnclaimingEquipmentFailNotEnrolledToActivity() throws Exception {
        when(equipmentService.unclaimEquipment(eq(new StrictIndex(1))))
                .thenReturn(new Message<>(Status.EQUIPMENT_NOT_ENROLLED, "User is not enrolled to activity", null));

        MockHttpServletResponse res = mockMvc
                .perform(delete("/equipment/1/claim")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(res.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(res.getContentAsString()).contains("EQUIPMENT_NOT_ENROLLED");
        assertThat(res.getContentAsString()).contains("User is not enrolled to activity");
    }
}

