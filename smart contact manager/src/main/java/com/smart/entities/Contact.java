package com.smart.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="CONTACT")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cid;
    
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotBlank
    @Size(min = 1, message = "Second name is required")
    private String secoundName;
    
    @NotBlank
    private String work;
    
    @NotBlank
    @Email(message = "Invalid email address")
    private String email;
    
    @NotBlank
    private String phone;
    
    @NotBlank
    @Column(length = 50000)
    private String description;

    @ManyToOne()
    private User user;

    public Contact(int cid, String name, String secoundName, String work, String email, String phone,
                   String description, User user) {
        super();
        this.cid = cid;
        this.name = name;
        this.secoundName = secoundName;
        this.work = work;
        this.email = email;
        this.phone = phone;
        this.description = description;
        this.user = user;
    }

    public Contact() {
        super();
    }

    // Getters and setters

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecoundName() {
        return secoundName;
    }

    public void setSecoundName(String secoundName) {
        this.secoundName = secoundName;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
