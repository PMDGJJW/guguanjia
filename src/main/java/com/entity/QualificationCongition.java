package com.entity;

/**
 * @auth jian j w
 * @date 2020/4/13 17:15
 * @Description
 */
public class QualificationCongition {
    //四个条件
    private String startDate;

    private String endDate;

    private String type;//类型

    private String check;//状态


    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    @Override
    public String toString() {
        return "QualificationCondition{" +
                "startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", type='" + type + '\'' +
                ", check='" + check + '\'' +
                '}';
    }
}
