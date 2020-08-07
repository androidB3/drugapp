package com.example.drugapp.Model;

public class Drug {
    private String drugTitle;
    private String drugActive;
    private int drugImageId;

    public Drug(String drugTitle, String drugActive, int drugImageId) {
        this.drugTitle = drugTitle;
        this.drugActive = drugActive;
        this.drugImageId = drugImageId;
    }

    public String getDrugTitle() {
        return drugTitle;
    }

    public void setDrugTitle(String drugTitle) {
        this.drugTitle = drugTitle;
    }

    public String getDrugActive() {
        return drugActive;
    }

    public void setDrugActive(String drugActive) {
        this.drugActive = drugActive;
    }

    public int getDrugImageId() {
        return drugImageId;
    }

    public void setDrugImageId(int drugImageId) {
        this.drugImageId = drugImageId;
    }
}
