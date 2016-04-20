package com.ga.gradtech.Cards.Facebook;

import java.util.List;

/**
 * Created by samsiu on 4/19/16.
 */
public class FacebookFeedObject {

    List<FbData> data;
    Paging paging;

    public List<FbData> getData() {
        return data;
    }

    public void setData(List<FbData> data) {
        this.data = data;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public class FbData{
        String message;
        String created_time;
        String id;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCreated_time() {
            return created_time;
        }

        public void setCreated_time(String created_time) {
            this.created_time = created_time;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public class Paging{
        String previous;
        String next;
    }

}
