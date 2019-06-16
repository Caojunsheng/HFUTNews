package com.news.server.model;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

/**
 * Created by caojunsheng on 2017/5/19.
 */
@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    public User() {
    }

    public User(int id) {
        this.id = id;
    }

    public User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public JSONObject AllUserToJson() {
        JSONObject object = new JSONObject();
        object.put("id", id);
        object.put("name", name);
        object.put("password", password);
        return object;
    }
}
