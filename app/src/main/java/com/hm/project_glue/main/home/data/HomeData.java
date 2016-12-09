package com.hm.project_glue.main.home.data;

/**
 * Created by jongkook on 2016. 12. 7..
 */

public class HomeData {
    private String group_name;

    private String id;

    private String group_image;

    private String post_count;

    private String user_count;

    private String last_updated;

    private String[] members;

    private String master;

    public String getGroup_name ()
    {
        return group_name;
    }

    public void setGroup_name (String group_name)
    {
        this.group_name = group_name;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getGroup_image ()
    {
        return group_image;
    }

    public void setGroup_image (String group_image)
    {
        this.group_image = group_image;
    }

    public String getPost_count ()
    {
        return post_count;
    }

    public void setPost_count (String post_count)
    {
        this.post_count = post_count;
    }

    public String getUser_count ()
    {
        return user_count;
    }

    public void setUser_count (String user_count)
    {
        this.user_count = user_count;
    }

    public String getLast_updated ()
    {
        return last_updated;
    }

    public void setLast_updated (String last_updated)
    {
        this.last_updated = last_updated;
    }

    public String[] getMembers ()
    {
        return members;
    }

    public void setMembers (String[] members)
    {
        this.members = members;
    }

    public String getMaster ()
    {
        return master;
    }

    public void setMaster (String master)
    {
        this.master = master;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [group_name = "+group_name+", id = "+id+", group_image = "+group_image+", post_count = "+post_count+", user_count = "+user_count+", last_updated = "+last_updated+", members = "+members+", master = "+master+"]";
    }
}
