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
import v5.gidd.entities.activity.Activity;
import v5.gidd.entities.message.Message;
import v5.gidd.entities.message.Status;
import v5.gidd.entities.message.StrictIndex;
import v5.gidd.service.ActivityService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = MOCK, classes = GiddApplication.class)
@AutoConfigureMockMvc
class ActivityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActivityService activityService;

    @InjectMocks
    private ActivityController activityController;



    @BeforeEach
    public void setup() {

    }

    @Test
    @WithMockUser
    void contextLoads() {
        assertThat(activityController).isNotNull();
        assertThat(mockMvc).isNotNull();
    }

    @Test
    @WithMockUser
    void testGetActivities() throws Exception {
        List<Activity> activities = new ArrayList<>();

        Activity activity = new Activity();

        activities.add(activity);

        when(activityService.getActivities()).thenReturn(new Message<>(Status.ACTIVITY_SUCCESS, "Successfully retrieved all activities", activities));

        MockHttpServletResponse res = mockMvc.perform(get("/activity").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(res.getContentAsString()).contains("ACTIVITY_SUCCESS");
    }

    @Test
    @WithMockUser
    void testGetActivity() throws Exception {

        Activity activity = new Activity();

        StrictIndex transmission = new StrictIndex(1);
        StrictIndex transmissionFail = new StrictIndex(2);

        when(activityService.getActivity(transmission)).thenReturn(new Message<>(Status.ACTIVITY_SUCCESS, "Successfully retrieved all activities", activity));
        when(activityService.getActivity(transmissionFail)).thenReturn(new Message<>(Status.ACTIVITY_NOT_FOUND, "Cannot find activity with ID: " + 2, null));

        MockHttpServletResponse resSuccess = mockMvc.perform(get("/activity/1").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        MockHttpServletResponse resEmpty = mockMvc.perform(get("/activity/2").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertThat(resSuccess.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(resSuccess.getContentAsString()).contains("ACTIVITY_SUCCESS");
        assertThat(resEmpty.getContentAsString()).contains("Cannot find activity with ID: 2");
        assertThat(resEmpty.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assert (resSuccess.getContentAsString().length() > resEmpty.getContentAsString().length());
    }

    @Test
    @WithMockUser
    void modifyActivity() throws Exception {
        Activity activity = new Activity();
        when(activityService.alterActivity(activity)).thenReturn(new Message<>(Status.Ok, "Successfully modified activity", null));
    }

    @Test
    @WithMockUser
    void deleteActivity() throws Exception {
        StrictIndex transmission = new StrictIndex(1);

        /* Test behavior if activity is found */
        when(activityService.deleteActivity(transmission)).thenReturn(new Message<>(Status.Ok, "Successfully deleted activity", null));
        MockHttpServletResponse res = mockMvc.perform(delete("/activity/" + transmission.getRequestId()).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value());

        /* Test behavior if activity is not found */
        when(activityService.deleteActivity(transmission)).thenReturn(new Message<>(Status.ACTIVITY_NOT_FOUND, "Could not find activity", null));
        MockHttpServletResponse res1 = mockMvc.perform(delete("/activity/" + transmission.getRequestId()).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        assertThat(res1.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @WithMockUser
    void cancelActivity() throws Exception {
        StrictIndex transmission = new StrictIndex(1);
        StrictIndex transmission2 = new StrictIndex(2);
        StrictIndex transmission3 = new StrictIndex(3);

        /* Tests behavior when canceling an activity */
        when(activityService.cancelActivity(transmission)).thenReturn(new Message<>(Status.Ok, "Successfully cancelled activity", null));
        MockHttpServletResponse res = mockMvc.perform(put("/activity/" + transmission.getRequestId() + "/cancel").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value());

        /* Tests behavior when trying to cancel an activity which has already been cancelled */
        when(activityService.cancelActivity(transmission2)).thenReturn(new Message<>(Status.ACTIVITY_ALREADY_CANCELLED, "Could not find activity", null));
        MockHttpServletResponse res1 = mockMvc.perform(put("/activity/" + transmission2.getRequestId() + "/cancel").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        assertThat(res1.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        /* Tests behavior when trying to cancel an activity which does not exist */
        when(activityService.cancelActivity(transmission3)).thenReturn(new Message<>(Status.ACTIVITY_NOT_FOUND, "Could not find activity", null));
        MockHttpServletResponse res2 = mockMvc.perform(put("/activity/" + transmission3.getRequestId() + "/cancel").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        assertThat(res2.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }




    @Test
    @WithMockUser
    void reactivateActivity() throws Exception {

        /* Tests behavior when reactivating activity */
        StrictIndex transmission = new StrictIndex(1);
        when(activityService.reactivateActivity(transmission)).thenReturn(new Message<>(Status.Ok, "Activity reactivated", null));
        MockHttpServletResponse res = mockMvc.perform(put("/activity/" + transmission.getRequestId() + "/reactivate").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value());

        /* Tests behavior when trying to reactivate an activity which is already reactivated */
        StrictIndex transmission1 = new StrictIndex(2);
        when(activityService.reactivateActivity(transmission1)).thenReturn(new Message<>(Status.ACTIVITY_ALREADY_ACTIVE, "This activity is already activated", null));
        MockHttpServletResponse res1 = mockMvc.perform(put("/activity/" + transmission1.getRequestId() + "/reactivate").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        assertThat(res1.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    @WithMockUser
    void testGetEnrolledActivitiesByUser() throws Exception {
        List<Activity> activityList = new ArrayList<>();
        activityList.add(new Activity());

        when(activityService.getEnrolledActivities())
                .thenReturn(new Message<>(Status.Ok, "Ok", activityList));

        MockHttpServletResponse res = mockMvc
                .perform(get("/activities/enrolled")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @WithMockUser
    void testGetOrganizedActivitiesByUser() throws Exception {
        List<Activity> activityList = new ArrayList<>();
        activityList.add(new Activity());

        when(activityService.getOrganizedActivities())
                .thenReturn(new Message<>(Status.Ok, "Ok", activityList));

        MockHttpServletResponse res = mockMvc
                .perform(get("/activities/organized")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @WithMockUser
    void testJoinActivitySuccess() throws Exception {
        when(activityService.joinActivity(any()))
                .thenReturn(new Message<>(Status.Ok, "Ok", null));

        MockHttpServletResponse res = mockMvc
                .perform(post("/activities/1/join")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @WithMockUser
    void testLeaveActivitySuccess() throws Exception {
        when(activityService.leaveActivity(any()))
                .thenReturn(new Message<>(Status.Ok, "Ok", null));

        MockHttpServletResponse res = mockMvc
                .perform(delete("/activities/1/join")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @WithMockUser
    void testGetUniqueLocations() throws Exception {
        List<String> uniqueLocations = new ArrayList<>();
        uniqueLocations.add("Trondheim");
        uniqueLocations.add("Bergen");

        when(activityService.getUniqueLocations())
                .thenReturn(new Message<>(Status.Ok, "Ok", uniqueLocations));

        MockHttpServletResponse res = mockMvc
                .perform(get("/activities/locations/unique")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @WithMockUser
    void testGetUniqueInterests() throws Exception {
        List<String> uniqueInterests = new ArrayList<>();
        uniqueInterests.add("fotball");
        uniqueInterests.add("fjellklatring");

        when(activityService.getUniqueInterests())
                .thenReturn(new Message<>(Status.Ok, "Ok", uniqueInterests));

        MockHttpServletResponse res = mockMvc
                .perform(get("/activities/interests/unique")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @WithMockUser
    void testUpdateActivitySuccess() throws Exception {
        when(activityService.updateActivity(any(), any()))
                .thenReturn(new Message<>(Status.Ok, "Ok", null));

        MockHttpServletResponse res = mockMvc
                .perform(put("/activity/1/update")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"test\" }"))
                .andReturn()
                .getResponse();

        assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @WithMockUser
    void testActivityCheckinSuccess() throws Exception {

        when(activityService.getPointsFromActivity(any(), any()))
                .thenReturn(new Message<>(Status.Ok, "Ok", null));

        MockHttpServletResponse res = mockMvc
                .perform(post("/activities/1/checkin")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"longitude\": 63, \"latitude\": 13 }"))
                .andReturn()
                .getResponse();

        assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @WithMockUser
    void testActivityRegistrationSuccess() throws Exception {
        when(activityService.registerActivity(any()))
                .thenReturn(new Message<>(Status.ACTIVITY_CREATED, "Ok", new StrictIndex(1L)));

        MockHttpServletResponse res = mockMvc
                .perform(post("/activity")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"test\" }"))
                .andReturn()
                .getResponse();

        assertThat(res.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

}

