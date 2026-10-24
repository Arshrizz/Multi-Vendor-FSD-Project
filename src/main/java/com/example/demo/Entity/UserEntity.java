package com.example.demo.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users", schema = "public")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "password_hash")
    @JsonAlias("password_hash")
    private String password_hash;

    @Column(name = "role")
    private String role;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "created_at")
    @JsonAlias("created_at")
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

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPasswordHash() { return password_hash; }
    public void setPasswordHash(String password_hash) { this.password_hash = password_hash; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return created_at; }
    public void setCreatedAt(LocalDateTime created_at) { this.created_at = created_at; }

    public LocalDate getBday() { return bday; }
    public void setBday(LocalDate bday) { this.bday = bday; }

    @Override
    public String toString() {
        return "UserEntity{" + "id=" + id + ", name='" + name + '\'' +
                ", email='" + email + '\'' + ", phone='" + phone + '\'' +
                ", password_hash='" + password_hash + '\'' + ", role='" + role + '\'' +
                ", is_active=" + status + ", created_at=" + created_at +
                ", bday=" + bday + '}';
    }
}