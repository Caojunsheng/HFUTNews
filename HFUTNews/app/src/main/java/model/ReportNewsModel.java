package model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caojunsheng on 2017/4/26.
 */

public class ReportNewsModel implements Serializable {
    private int id;
    private String date;
    private String title;
    private String content;
    private String writer;
    private String photoer;
    private String editor;
    private String url;
    private List<String> imgurl;

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

    public List<String> getImgurl() {
        return imgurl;
    }

    public void setImgurl(List<String> imgurl) {
        this.imgurl = imgurl;
    }
}
