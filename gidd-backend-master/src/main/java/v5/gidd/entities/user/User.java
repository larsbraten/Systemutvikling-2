package v5.gidd.entities.user;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import org.springframework.cache.interceptor.KeyGenerator;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalIdCache;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import v5.gidd.entities.Inspectable;
import v5.gidd.entities.activity.Activity;
import v5.gidd.entities.equipment.Equipment;
import v5.gidd.entities.message.Status;
import v5.gidd.entities.user.attributes.ContactInfo;
import v5.gidd.entities.user.attributes.Credentials;
import v5.gidd.entities.user.attributes.Persona;
import v5.gidd.entities.user.attributes.Quality;
import v5.gidd.entities.activity.waitList.ActivityWaitList;
import v5.gidd.throwable.GiddException;

import javax.persistence.*;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.awt.*;
import java.util.Collection;
import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "user")
public class User implements Inspectable, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // bruk IDENTITY hvis db er seeded ved hver kjoering
    private Long id;

    @Embedded
    private Persona persona;

    @Embedded
    private ContactInfo contactInfo;

    @Embedded
    //@JsonIgnore
    private Credentials credentials;

    @Column(name = "activity_level")
    @Enumerated(value = EnumType.STRING)
    private Quality activityLevel = Quality.LOW;

    @ManyToMany(mappedBy = "enrolledUsers")
//    @JsonIgnore
    private List<Activity> enrolledActivities = List.of();

    @OneToMany(mappedBy = "creator")
    @JsonIgnore
    private List<Activity> createdActivities = List.of();

    @OneToMany(mappedBy = "claimedByUser")
    @JsonIgnore
    private List<Equipment> claimedEquipment = List.of();

    @Column(name = "points")
    private int points;

    @Column(name = "is_verified")
    private boolean isVerified;

    @Transient
    @JsonIgnore
    private List<GrantedAuthority> authorities = List.of();

    @OneToMany(mappedBy = "activity")
    @JsonIgnore
    private List<ActivityWaitList> waitListedActivities = List.of();


    public User() {
    }

    public User(Persona persona, ContactInfo contactInfo, Credentials credentials, Quality activityLevel, int points) {
        this.persona = persona;
        this.contactInfo = contactInfo;
        this.credentials = credentials;
        this.activityLevel = activityLevel;
        this.points = points;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public Quality getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(Quality activityLevel) {
        this.activityLevel = activityLevel;
    }

    public List<Activity> getEnrolledActivities() {
        return enrolledActivities;
    }

    public void setEnrolledActivities(List<Activity> enrolledActivities) {
        this.enrolledActivities = enrolledActivities;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public static User fromId(long id) {
        User user = new User();
        user.setId(id);

        return user;
    }

    public List<ActivityWaitList> getWaitListedActivities() {
        return waitListedActivities;
    }

    public void setWaitListedActivities(List<ActivityWaitList> waitListedActivities) {
        this.waitListedActivities = waitListedActivities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return credentials.getPassword();
    }

    @Override
    public String getUsername() {
        return credentials.getEmail();
    }

    public List<Activity> getCreatedActivities() {
        return createdActivities;
    }

    public void setCreatedActivities(List<Activity> createdActivities) {
        this.createdActivities = createdActivities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isVerified;
    }

    @JsonIgnore
    public List<Equipment> getClaimedEquipment() {
        return claimedEquipment;
    }

    @JsonIgnore
    public void setClaimedEquipment(List<Equipment> claimedEquipment) {
        this.claimedEquipment = claimedEquipment;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public void removePoints(int points) {
        this.points -= points;
    }

    @Override
    public void inspect() {
        persona.inspect();
        credentials.inspect();
        contactInfo.inspect();
        enrolledActivities.forEach(Activity::inspect);
        createdActivities.forEach(Activity::inspect);
        waitListedActivities.forEach(ActivityWaitList::inspect);
        claimedEquipment.forEach(Equipment::inspect);
        if(credentials.getPassword().equals(persona.getFirstName())
                ||credentials.getPassword().equals(persona.getSurName())
                ||credentials.getPassword().equals(persona.getFirstName() + persona.getSurName()))
            throw new GiddException(Status.INVALID_PASSWORD, "Password cannot be same as name");
    }
}
