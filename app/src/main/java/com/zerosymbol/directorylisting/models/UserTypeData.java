
package com.zerosymbol.directorylisting.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserTypeData {

    @SerializedName("Status")
    @Expose
    private String status;

    @SerializedName("data")
    @Expose
    private List<UserType> data = null;

    @SerializedName("Result Code")
    @Expose
    private String resultCode;
    @SerializedName("Result Description")
    @Expose
    private String resultDescription;
    @SerializedName("TID")
    @Expose
    private String tID;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<UserType> getData() {
        return data;
    }

    public void setData(List<UserType> data) {
        this.data = data;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getTID() {
        return tID;
    }

    public void setTID(String tID) {
        this.tID = tID;
    }

    public String getResultDescription() {
        return resultDescription;
    }

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }
}
