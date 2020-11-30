package com.shiyuan.model;

public class School {
    private Long schoolId;

    private String schoolProvinces;

    private String schoolCity;

    private String schoolName;

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolProvinces() {
        return schoolProvinces;
    }

    public void setSchoolProvinces(String schoolProvinces) {
        this.schoolProvinces = schoolProvinces;
    }

    public String getSchoolCity() {
        return schoolCity;
    }

    public void setSchoolCity(String schoolCity) {
        this.schoolCity = schoolCity;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
}