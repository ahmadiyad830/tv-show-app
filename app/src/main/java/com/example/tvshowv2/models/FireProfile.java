package com.example.tvshowv2.models;

public class FireProfile {
    private String name, wanted, showed;

    public String getName() {
        return name;
    }

    public String getWanted() {
        return wanted;
    }

    public String getShowed() {
        return showed;
    }

    public FireProfile() {
    }

    public FireProfile(String name, String wanted, String showed) {
        this.name = name;
        this.wanted = wanted;
        this.showed = showed;
    }
}
