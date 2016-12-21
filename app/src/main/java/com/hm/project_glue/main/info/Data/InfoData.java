package com.hm.project_glue.main.info.data;

import java.util.ArrayList;

/**
 * Created by HM on 2016-12-15.
 */

public class InfoData
{
    private String id;

    private String phone_number;

    private String email;

    private String name;

    private String image;

    private ArrayList<My_group> my_group;

    private String password;

    private boolean comment_noti;

    private boolean post_noti;

    private boolean like_noti;

    public boolean getComment_noti() {
        return comment_noti;
    }

    public void setComment_noti(boolean comment_noti) {
        this.comment_noti = comment_noti;
    }

    public boolean getPost_noti() {
        return post_noti;
    }

    public void setPost_noti(boolean post_noti) {
        this.post_noti = post_noti;
    }

    public boolean getLike_noti() {
        return like_noti;
    }

    public void setLike_noti(boolean like_noti) {
        this.like_noti = like_noti;
    }

    public InfoData(){

    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getPhone_number ()
    {
        return phone_number;
    }

    public void setPhone_number (String phone_number)
    {
        this.phone_number = phone_number;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getImage ()
{
    return image;
}

    public void setImage (String image)
    {
        this.image = image;
    }

    public ArrayList<My_group> getMy_group ()
    {
        return my_group;
    }

    public void setMy_group (ArrayList<My_group> my_group)
    {
        this.my_group = my_group;
    }

    public String getPassword ()
    {
        return password;
    }

    public void setPassword (String password)
    {
        this.password = password;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", phone_number = "+phone_number+", email = "+email+", name = "+name+", image = "+image+", my_group = "+my_group+", password = "+password+"]";
    }
}