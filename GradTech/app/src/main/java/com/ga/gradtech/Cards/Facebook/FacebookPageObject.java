package com.ga.gradtech.Cards.Facebook;

import java.util.List;

/**
 * Created by samsiu on 4/21/16.
 */
public class FacebookPageObject {

    List<FbPageData> data;

    public List<FbPageData> getData() {
        return data;
    }

    public void setData(List<FbPageData> data) {
        this.data = data;
    }

    public class FbPageData {
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
}