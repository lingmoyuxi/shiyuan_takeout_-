package com.shiyuan.model;

public class Mealmap {
    private Long mealmapId;

    private Long mealId;

    private Long commodityId;

    public Long getMealmapId() {
        return mealmapId;
    }

    public void setMealmapId(Long mealmapId) {
        this.mealmapId = mealmapId;
    }

    public Long getMealId() {
        return mealId;
    }

    public void setMealId(Long mealId) {
        this.mealId = mealId;
    }

    public Long getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Long commodityId) {
        this.commodityId = commodityId;
    }
}