package com.hm.project_glue.main.list.data;

/**
 * Created by HM on 2016-12-03.
 */

public class Remote {


    public class PostData
    {
        private Photos[] photos;
        private String content;
        private String uploaded_user;
        private String group;

        public Photos[] getPhotos ()
        {
            return photos;
        }

        public void setPhotos (Photos[] photos)
        {
            this.photos = photos;
        }

        public String getContent ()
        {
            return content;
        }

        public void setContent (String content)
        {
            this.content = content;
        }

        public String getUploaded_user ()
        {
            return uploaded_user;
        }

        public void setUploaded_user (String uploaded_user)
        {
            this.uploaded_user = uploaded_user;
        }

        public String getGroup ()
        {
            return group;
        }

        public void setGroup (String group)
        {
            this.group = group;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [photos = "+photos+", content = "+content+", uploaded_user = "+uploaded_user+", group = "+group+"]";
        }
    }
    public class Photos
    {
        private Photo photo;

        private String pk;

        public Photo getPhoto ()
        {
            return photo;
        }

        public void setPhoto (Photo photo)
        {
            this.photo = photo;
        }

        public String getPk ()
        {
            return pk;
        }

        public void setPk (String pk)
        {
            this.pk = pk;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [photo = "+photo+", pk = "+pk+"]";
        }
    }
    public class Photo
    {
        private String thumbnail;

        private String medium_thumbnail;

        private String full_size;

        private String small_thumbnail;

        public String getThumbnail ()
        {
            return thumbnail;
        }

        public void setThumbnail (String thumbnail)
        {
            this.thumbnail = thumbnail;
        }

        public String getMedium_thumbnail ()
        {
            return medium_thumbnail;
        }

        public void setMedium_thumbnail (String medium_thumbnail)
        {
            this.medium_thumbnail = medium_thumbnail;
        }

        public String getFull_size ()
        {
            return full_size;
        }

        public void setFull_size (String full_size)
        {
            this.full_size = full_size;
        }

        public String getSmall_thumbnail ()
        {
            return small_thumbnail;
        }

        public void setSmall_thumbnail (String small_thumbnail)
        {
            this.small_thumbnail = small_thumbnail;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [thumbnail = "+thumbnail+", medium_thumbnail = "+medium_thumbnail+", full_size = "+full_size+", small_thumbnail = "+small_thumbnail+"]";
        }
    }

}
