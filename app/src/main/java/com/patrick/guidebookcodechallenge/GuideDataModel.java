package com.patrick.guidebookcodechallenge;

/**
 * Holds the data for a guide generated from the request.
 */
public class GuideDataModel {
    private String startDate;
    private String objType;
    private String endDate;
    private String name;
    private String loginRequired;
    private String url;
    private String city;
    private String state;
    private String icon;

    // initialize data to empty in case JSON object is empty.
    GuideDataModel(){
        startDate = "";
        objType = "";
        endDate = "";
        name = "";
        loginRequired = "";
        url = "";
        city = "";
        state = "";
        icon = "";
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getObjType() {
        return objType;
    }

    public void setObjType(String objType) {
        this.objType = objType;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginRequired() {
        return loginRequired;
    }

    public void setLoginRequired(String loginRequired) {
        this.loginRequired = loginRequired;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}
