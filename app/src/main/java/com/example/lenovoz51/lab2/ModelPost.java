package com.example.lenovoz51.lab2;

/**
 * Created by Lenovo Z51 on 2018-11-06.
 */

public class ModelPost {

    private int id;
    private int userId;
    private String title;
    private String bodyText;
    private int size;

    public ModelPost(){
    }

    public ModelPost(int id, int userId, String title, String bodyText, int size){
        this.id = id;
        this.userId =userId;
        this.title=title;
        this.bodyText = bodyText;
        this.size = size;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
