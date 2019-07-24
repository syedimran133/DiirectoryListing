
package com.zerosymbol.directorylisting.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CatUserData {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<User> data = null;
    @SerializedName("Result Code")
    @Expose
    private Integer resultCode;
    @SerializedName("TID")
    @Expose
    private Integer tID;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public Integer getTID() {
        return tID;
    }

    public void setTID(Integer tID) {
        this.tID = tID;
    }

}
