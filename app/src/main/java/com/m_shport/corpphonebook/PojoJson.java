package com.m_shport.corpphonebook;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PojoJson {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("list")
    @Expose
    private List<ContactList> list = null;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<ContactList> getList() {
        return list;
    }

    public void setList(List<ContactList> list) {
        this.list = list;
    }
}
