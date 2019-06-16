package com.news.server.model;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

/**
 * Created by caojunsheng on 2017/4/23.
 */
@Repository("hfutReportNews")
@Entity
@Table(name = "reportnews")
public class HFUTReportNews {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date")
    private String date;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "writer")
    private String writer;

    @Column(name = "photoer")
    private String photoer;

    @Column(name = "editor")
    private String editor;

    @Column(name = "url")
    private String url;

    public HFUTReportNews() {
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getPhotoer() {
        return photoer;
    }

    public void setPhotoer(String photoer) {
        this.photoer = photoer;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public JSONObject ReportNewsToJSON() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("date", date);
        json.put("title", title);
        json.put("content", content);
        json.put("writer", writer);
        json.put("photoer", photoer);
        json.put("editor", editor);
        json.put("url", url);
        return json;
    }
}
