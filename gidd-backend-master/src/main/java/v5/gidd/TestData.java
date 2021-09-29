package v5.gidd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import v5.gidd.entities.activity.Activity;
import v5.gidd.entities.activity.attributes.ActivityIcon;
import v5.gidd.entities.activity.attributes.ActivityLevel;
import v5.gidd.entities.activity.attributes.ActivityStatus;
import v5.gidd.entities.activity.attributes.Location;
import v5.gidd.entities.equipment.Equipment;
import v5.gidd.entities.user.User;
import v5.gidd.entities.user.attributes.ContactInfo;
import v5.gidd.entities.user.attributes.Credentials;
import v5.gidd.entities.user.attributes.Persona;
import v5.gidd.entities.user.attributes.Quality;
import v5.gidd.repo.ActivityRepo;
import v5.gidd.repo.EquipmentRepo;
import v5.gidd.repo.UserRepo;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Component
public class TestData {
    private final boolean TESTDATA_ENABLED = true;

    @Autowired
    private ActivityRepo activityRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EquipmentRepo equipmentRepo;

    @PostConstruct
    private void postConstruct() {
        if (!TESTDATA_ENABLED) return;



        User admin = new User(
                new Persona("Admin", "Adminov", LocalDate.now()),
                new ContactInfo("admin@admin.no", "12345678"),
                new Credentials("admin@admin.no", "admin123"),
                Quality.VERY_HIGH, 50);

        User test = new User(
                new Persona("Test", "Testov", LocalDate.now()),
                new ContactInfo("test@test.no", "12345679"),
                new Credentials("test@test.no", "test1234"),
                Quality.VERY_HIGH, 0);

        User user = new User(
                new Persona("Haakon", "Den Guddommelige", LocalDate.now()),
                new ContactInfo("haakon@haakon.no", "12345670"),
                new Credentials("haakon@haakon.no", "12345678"),
                Quality.VERY_HIGH, 20);

        User u1 = new User(
                new Persona("Digg", "Gidder", LocalDate.now()),
                new ContactInfo("email1@e.com", "12345672"),
                new Credentials("email1@e.com", "pass1"),
                Quality.VERY_HIGH, 500);

        User u2 = new User(
                new Persona("Jane", "Doe", LocalDate.now()),
                new ContactInfo("email2@e.com", "12345672"),
                new Credentials("email2@e.com", "pass2"),
                Quality.HIGH, 600);

        User u3 = new User(
                new Persona("John", "Doe", LocalDate.now()),
                new ContactInfo("email3@e.com", "12345672"),
                new Credentials("email3@e.com", "pass3"),
                Quality.HIGH, 1000);

        User u4 = new User(
                new Persona("Jompa", "Tormann", LocalDate.now()),
                new ContactInfo("email4@e.com", "12345671"),
                new Credentials("email4@e.com", "pass4"),
                Quality.LOW, 10000);

        User u5 = new User(
                new Persona("Johnny", "Fredrikstad", LocalDate.now()),
                new ContactInfo("email5@e.com", "15345671"),
                new Credentials("email5@e.com", "pass5"),
                Quality.MEDIUM, 11000);

        List<Equipment> el1 = new ArrayList<>();
        el1.add(new Equipment("God stemning"));
        el1.add(new Equipment("Fotball"));
        List<Equipment> el2 = new ArrayList<>();
        el2.add(new Equipment("Fotball"));
        List<Equipment> el3 = new ArrayList<>();
        el3.add(new Equipment("Fotball"));
        List<Equipment> el4 = new ArrayList<>();
        el4.add(new Equipment("Kokebok"));
        List<Equipment> el5 = new ArrayList<>();
        el5.add(new Equipment("God stemning"));
        el5.add(new Equipment("Fotball"));
        List<Equipment> el6 = new ArrayList<>();
        el6.add(new Equipment("Fotball"));
        List<Equipment> el7 = new ArrayList<>();
        el7.add(new Equipment("Fotball"));
        List<Equipment> el8 = new ArrayList<>();
        el8.add(new Equipment("Kokebok"));

        List<String> il1 = new ArrayList<>();
        il1.add("Hiking");
        List<String> il2 = new ArrayList<>();
        il2.add("Eksamen");
        List<String> il3 = new ArrayList<>();
        il3.add("Prat");
        List<String> il4 = new ArrayList<>();
        il4.add("60s");

        Activity a1 = new Activity(
                "Disqus Forum", new Location(
                "Gløshaugen", "Norway", "Trondheim",
                10.402488119205492,
                63.4194193106922),
                20, "", admin, el4, ActivityLevel.LOW, ActivityIcon.RUNNING, il3,
                LocalDateTime.of(2021, Month.MAY, 5, 12, 0),
                LocalDateTime.of(2021, Month.MAY, 5, 18, 0),
                LocalDateTime.now(),
                ActivityStatus.ACTIVE);

        Activity a2 = new Activity(
                "Mushroom Club Meeting", new Location(
                "Gløshaugen", "Norway", "Trondheim",
                10.402488119205492,
                63.4194193106922),
                5, "", admin, el3, ActivityLevel.MEDIUM, ActivityIcon.VOLLEYBALL, il4,
                LocalDateTime.of(2021, Month.MAY, 5, 8, 0),
                LocalDateTime.of(2021, Month.MAY, 5, 22, 0),
                LocalDateTime.now(),
                ActivityStatus.ACTIVE);

        Activity a3 = new Activity(
                "Study sesj", new Location(
                "Studenterhytta NTNUI", "Norway", "Trondheim",
                10.26838101171219,
                63.416616309965896),
                10,"", admin, el2, ActivityLevel.HIGH, ActivityIcon.SKIING, il2,
                LocalDateTime.of(2020, Month.MAY, 6, 10, 0),
                LocalDateTime.of(2020, Month.MAY, 6, 16, 0),
                LocalDateTime.now(),
                ActivityStatus.ACTIVE);

        Activity a4 = new Activity(
                "Fjelltur", new Location(
                "Machu Picchu", "Peru", "Aguas Calientes",
                -72.54498436033785,
                -13.16281733531003),
                7, "", admin, el1, ActivityLevel.HIGH, ActivityIcon.RUNNING, il1,
                LocalDateTime.of(2021, Month.JUNE, 6, 7, 0),
                LocalDateTime.of(2021, Month.JUNE, 6, 18, 0),
                LocalDateTime.now(),
                ActivityStatus.ACTIVE);
        Activity a5 = new Activity(
                "Disqus Forum1", new Location(
                "Gløshaugen", "Norway", "Trondheim",
                10.402488119205492,
                63.4194193106922),
                20,"", u4, el5, ActivityLevel.LOW, ActivityIcon.VOLLEYBALL, il3,
                LocalDateTime.of(2021, Month.MAY, 7, 12, 0),
                LocalDateTime.of(2021, Month.MAY, 7, 18, 0),
                LocalDateTime.now(),
                ActivityStatus.ACTIVE);

        Activity a6 = new Activity(
                "Mushroom Club Meeting1", new Location(
                "Gløshaugen", "Norway", "Trondheim",
                10.402488119205492,
                63.4194193106922),
                5,"", u3, el6, ActivityLevel.MEDIUM, ActivityIcon.SKIING, il4,
                LocalDateTime.of(2021, Month.MAY, 7, 8, 0),
                LocalDateTime.of(2021, Month.MAY, 8, 22, 0),
                LocalDateTime.now(),
                ActivityStatus.ACTIVE);

        Activity a7 = new Activity(
                "Study sesj1", new Location(
                "Studenterhytta NTNUI", "Norway", "Trondheim",
                10.26838101171219,
                63.416616309965896),
                1,"", u2, el7, ActivityLevel.HIGH, ActivityIcon.RUNNING, il2,
                LocalDateTime.of(2021, Month.MAY, 1, 10, 0),
                LocalDateTime.of(2021, Month.MAY, 1, 16, 0),
                LocalDateTime.now(),
                ActivityStatus.ACTIVE);

        Activity a8 = new Activity(
                "Fjelltur1", new Location(
                "Machu Picchu1", "Peru", "Aguas Calientes",
                -72.54498436033785,
                -13.16281733531003),
                7,"", u1, el7, ActivityLevel.HIGH, ActivityIcon.RUNNING, il1,
                LocalDateTime.of(2021, Month.JUNE, 11, 7, 0),
                LocalDateTime.of(2021, Month.JUNE, 11, 18, 0),
                LocalDateTime.now(),
                ActivityStatus.ACTIVE);

        // Set equipment
        a1.setEquipment(el1);
        a2.setEquipment(el2);
        a3.setEquipment(el3);
        a4.setEquipment(el4);
        a5.setEquipment(el5);
        a6.setEquipment(el6);
        a7.setEquipment(el7);
        a8.setEquipment(el8);

        List<Activity> al1 = new ArrayList<>();
        al1.add(a1);
        al1.add(a2);
        al1.add(a3);
        al1.add(a4);
        List<Activity> al2 = new ArrayList<>();
        al2.add(a4);
        al2.add(a3);
        List<Activity> al3 = new ArrayList<>();
        al3.add(a4);
        al3.add(a3);
        List<Activity> al4 = new ArrayList<>();
        al4.add(a1);
        al4.add(a2);
        List<Activity> al5 = new ArrayList<>();
        al5.add(a2);
        List<Activity> al6 = new ArrayList<>();
        al6.add(a5);
        al6.add(a6);
        /*al6.add(a7);
        al6.add(a8);*/

        u1.setEnrolledActivities(al1);
        u2.setEnrolledActivities(al2);
        u3.setEnrolledActivities(al3);
        u4.setEnrolledActivities(al4);
        u5.setEnrolledActivities(al5);
        admin.setEnrolledActivities(al6);

        List<User> ul1 = new ArrayList<>();
        ul1.add(u1);
        ul1.add(u4);
        List<User> ul2 = new ArrayList<>();
        ul2.add(u1);
        ul2.add(u4);
        ul2.add(u5);
        List<User> ul3 = new ArrayList<>();
        ul3.add(u1);
        ul3.add(u2);
        ul3.add(u3);
        List<User> ul4 = new ArrayList<>();
        ul4.add(u1);
        ul4.add(u2);
        ul4.add(u3);
        List<User> ul5 = new ArrayList<>();
        ul5.add(admin);
        List<User> ul6 = new ArrayList<>();
        ul6.add(admin);
        List<User> ul7 = new ArrayList<>();
        //ul7.add(admin);
        List<User> ul8 = new ArrayList<>();
        //ul8.add(admin);

        a1.setEnrolledUsers(ul1);
        a2.setEnrolledUsers(ul2);
        a3.setEnrolledUsers(ul3);
        a4.setEnrolledUsers(ul4);
        a5.setEnrolledUsers(ul5);
        a6.setEnrolledUsers(ul6);
        a7.setEnrolledUsers(ul7);
        a8.setEnrolledUsers(ul8);

        userRepo.save(test);
        userRepo.save(admin);
        userRepo.save(user);
        userRepo.save(u1);
        userRepo.save(u2);
        userRepo.save(u3);
        userRepo.save(u4);
        userRepo.save(u5);

        activityRepo.save(a1);
        activityRepo.save(a2);
        activityRepo.save(a3);
        activityRepo.save(a4);
        activityRepo.save(a5);
        activityRepo.save(a6);
        activityRepo.save(a7);
        activityRepo.save(a8);
    }
}
