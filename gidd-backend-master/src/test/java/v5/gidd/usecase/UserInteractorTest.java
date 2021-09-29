package v5.gidd.usecase;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import v5.gidd.GiddApplication;
import v5.gidd.entities.message.Status;
import v5.gidd.entities.user.User;
import v5.gidd.entities.user.attributes.ContactInfo;
import v5.gidd.entities.user.attributes.Credentials;
import v5.gidd.entities.user.attributes.Persona;
import v5.gidd.entities.user.attributes.Quality;
import v5.gidd.repo.UserRepo;
import v5.gidd.entities.message.Status;
import v5.gidd.service.TokenService;
import v5.gidd.throwable.GiddException;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = MOCK, classes = GiddApplication.class)
public class UserInteractorTest {

    @InjectMocks
    UserInteractor interactor;

    @Mock
    TokenService tokenService;

    @Mock
    UserRepo userRepo;


    @BeforeEach
    public void setUp() {
        User user = new User(new Persona("Ole", "Nilsen", LocalDate.now()),
                new ContactInfo("email1@e.com", "12345672"), new Credentials("test@test.no", "12345678"), Quality.VERY_LOW, 0);
        Optional<User> optional = Optional.of(user);
        Mockito.when(userRepo.findUserByCredentialsEmail("test@test.no")).thenReturn(optional);
        Mockito.when(userRepo.findUserByCredentialsEmail("123@test.no")).thenReturn(Optional.empty());
    }

    @Test
    void TestFailureOnUserAlreadyExists() {
        User user = new User(new Persona("Ole", "Nilsen", LocalDate.now()),
                new ContactInfo("email1@e.com", "12345672"),
                new Credentials("test@test.no", "12345678"), Quality.VERY_LOW, 0);
        GiddException e = assertThrows(GiddException.class, () -> interactor.registerUser(userRepo, tokenService, user));

        String expectedMessage = "User already exists";
        Status expectedStatus = Status.REGISTRATION_ERROR;


        String actualMessage = e.getDescription();
        Status status = e.getStatus();


        assertEquals(actualMessage, expectedMessage);
        assertEquals(status, expectedStatus);
    }
}
