package com.shop4e.shop.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class User extends Audit {

  private String username;
  private String firstName;
  private String lastName;
  private String password;
  @Column(unique = true)
  private String email;
  @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
  @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
  private Set<Role> roles = new HashSet<>();
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Address> addresses;
  private String phoneNumber;
  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Photo profilePicture;
  @Column(name = "is_verified")
  private Boolean verified = false;

  public User() {
  }

  public String getUsername() {
    return username;
  }

  public User setUsername(String username) {
    this.username = username;
    return this;
  }

  public String getFirstName() {
    return firstName;
  }

  public User setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public String getLastName() {
    return lastName;
  }

  public User setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public User setPassword(String password) {
    this.password = password;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public User setEmail(String email) {
    this.email = email;
    return this;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public User setRoles(Set<Role> roles) {
    this.roles = roles;
    return this;
  }

  public Set<Address> getAddresses() {
    return addresses;
  }

  public User setAddresses(Set<Address> addresses) {
    this.addresses = addresses;
    return this;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public User setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  public Photo getProfilePicture() {
    return profilePicture;
  }

  public User setProfilePicture(Photo profilePicture) {
    this.profilePicture = profilePicture;
    return this;
  }

  public Boolean isVerified() {
    return verified;
  }

  public User setVerified(Boolean verified) {
    this.verified = verified;
    return this;
  }
}
