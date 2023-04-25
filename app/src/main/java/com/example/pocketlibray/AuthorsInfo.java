package com.example.pocketlibray;

import java.util.ArrayList;

public class AuthorsInfo {
    private String name;
    private String birthDate;
    private boolean isDead;
    private String authorImgUrl;
    private String biography;
    private String deathDate;
    private String rating;
    private ArrayList<String> authorsLanguagesArrayList;

    public AuthorsInfo(String name, String birthDate, boolean isDead, String authorImgUrl, String biography, String deathDate) {
        this.name = name;
        this.birthDate = birthDate;
        this.isDead = isDead;
        this.authorImgUrl = authorImgUrl;
        this.biography = biography;
        this.deathDate = deathDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public boolean getStatus() {
        return isDead;
    }

    public void setStatus(boolean isDead) {
        this.isDead = isDead;
    }

    public String getAuthorImgUrl() {
        return authorImgUrl;
    }

    public void setAuthorImgUrl(String authorImgUrl) {
        this.authorImgUrl = authorImgUrl;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public ArrayList<String> getLanguages() {
        return authorsLanguagesArrayList;
    }

    public void setLanguages(ArrayList<String> authorsLanguagesArrayList) {
        this.authorsLanguagesArrayList = authorsLanguagesArrayList;
    }

    public String getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(String deathDate) {
        this.deathDate = deathDate;
    }
}

