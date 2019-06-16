package com.news.server.model;

import javax.persistence.*;

/**
 * Created by caojunsheng on 2017/5/22.
 */
@Entity
@Table(name = "advice")
public class Advice {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date")
    private  String date;

    @Column(name = "content")
    private String content;

    @Column(name = "adviceuser")
    private String adviceuser;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAdviceuser() {
        return adviceuser;
    }

    public void setAdviceuser(String adviceuser) {
        this.adviceuser = adviceuser;
    }
}
