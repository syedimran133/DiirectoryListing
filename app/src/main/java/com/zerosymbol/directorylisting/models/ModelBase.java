package com.zerosymbol.directorylisting.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelBase {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Result Code")
    @Expose
    private String resultCode;
    @SerializedName("Result Description")
    @Expose
    private String resultDescription;
    @SerializedName("Result Namespace")
    @Expose
    private String resultNamespace;
    @SerializedName("app_key")
    @Expose
    private String appKey;
    @SerializedName("type_id")
    @Expose
    private String typeId;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("session_id")
    @Expose
    private String sessionId;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("TID")
    @Expose
    private String tID;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultDescription() {
        return resultDescription;
    }

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }

    public String getResultNamespace() {
        return resultNamespace;
    }

    public void setResultNamespace(String resultNamespace) {
        this.resultNamespace = resultNamespace;
    }

    public String getTID() {
        return tID;
    }

    public void setTID(String tID) {
        this.tID = tID;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }
}