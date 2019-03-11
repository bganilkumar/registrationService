package com.test.service.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * User table to store the user registration details.
 */
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Column(name = "user_id")
    private Long id;
    @Setter @Getter
    @Column(unique = true)
    private String userName;
    @Setter @Getter
    private String password;
    @Setter @Getter
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @Setter @Getter
    //@Column(unique = true)
    private String ssn;
    @Getter
    @Column(name="registered_date_time")
    private LocalDateTime registeredDateTime;

    public User(){}

    public User(String userName, String password, LocalDate dateOfBirth, String ssn) {
        this.userName = userName;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.ssn = ssn;
        this.registeredDateTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", ssn='" + ssn + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userName, user.userName) &&
                Objects.equals(dateOfBirth, user.dateOfBirth) &&
                Objects.equals(ssn, user.ssn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, dateOfBirth, ssn);
    }
}
