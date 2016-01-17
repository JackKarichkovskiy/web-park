/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.dao.entities;

import java.util.Date;
import java.util.UUID;
import org.webpark.dao.annotation.Primary;
import org.webpark.dao.annotation.Stored;
import org.webpark.dao.annotation.utils.converters.Converters;

/**
 * Entity that represents user in system.
 *
 * @author Karichkovskiy Yevhen
 */
@Stored(name = "users")
public class User {

    /**
     * ID of user.
     */
    @Primary
    @Stored(name = "id", converter = Converters.UUIDConverter)
    private UUID id;

    /**
     * Login name of user.
     */
    @Stored(name = "username")
    private String username;

    /**
     * Password of user.
     */
    @Stored(name = "password")
    private String password;

    /**
     * Email of user.
     */
    @Stored(name = "email")
    private String email;

    /**
     * The date of creating current user.
     */
    @Stored(name = "create_time", converter = Converters.DateConverter)
    private Date createTime;

    /**
     * Role of user.
     */
    @Stored(name = "role", converter = Converters.RolesConverter)
    private Roles role;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", userName=" + username + ", password=" + password + ", email=" + email + ", createTime=" + createTime + ", role=" + role + '}';
    }

    /**
     * Possible roles for user in system.
     */
    public enum Roles {

        ADMIN, OWNER, FORESTER, GUEST;
    }

}
