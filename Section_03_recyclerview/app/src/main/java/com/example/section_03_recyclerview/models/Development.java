package com.example.section_03_recyclerview.models;

public class Development {

    public String name;
    public int banner;



    public Development() {
    }

    public Development(String name, int banner){
        this.name = name;
        this.banner = banner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBanner() {
        return banner;
    }

    public void setBanner(int banner) {
        this.banner = banner;
    }
}
