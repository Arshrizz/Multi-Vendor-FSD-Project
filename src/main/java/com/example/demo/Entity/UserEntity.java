package com.example.demo.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "User")
public class UserEntity {

    // @JsonManagedReference
    // @OneToOne(mappedBy = "students", cascade = CascadeType.ALL)
    // private PassportEntity passport;

    // @OneToMany(mappedBy = "students", cascade = CascadeType.ALL)
    // private List<CoursesEntity> courses;

    // @JsonManagedReference
    // @ManyToMany(cascade = CascadeType.ALL)
    // @JoinTable(
    //         name = "testing2",
    //         joinColumns = @JoinColumn(name = "student_id"),
    //         inverseJoinColumns = @JoinColumn(name = "course_id") // Changed from "id"
    // )
    // private List<CoursesEntity> courses;

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "password_hash")
    private String password_hash;

    @Column(name = "role")
    private String role;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "bday")
    private LocalDate bday;

    public UserEntity() {
    }

    public UserEntity(Integer id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserEntity{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", email='" + email + '\''
                + ", phone='" + phone + '\''
                + ", password_hash='" + password_hash + '\''
                + ", role='" + role + '\''
                + ", is_active=" + status
                + ", created_at=" + created_at
                + ", bday=" + bday
                + '}';
    }
}