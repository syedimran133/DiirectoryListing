
package com.zerosymbol.directorylisting.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("email")
    @Expose
    private String email;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
