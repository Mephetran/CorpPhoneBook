package com.m_shport.corpphonebook;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactList {

    @SerializedName("sn")
    @Expose
    private String sn;
    @SerializedName("givenname")
    @Expose
    private String givenname;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("department")
    @Expose
    private String department;
    @SerializedName("mobile")
    @Expose
    private String mobile;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getGivenname() {
        return givenname;
    }

    public void setGivenname(String givenname) {
        this.givenname = givenname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
