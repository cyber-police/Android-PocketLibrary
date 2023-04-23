package com.example.pocketlibray;

public class AuthorsInfo {
    private String name;
    private String birthDate;
    private boolean isDead;
    private String deathDate;
    private String rating;

    public AuthorsInfo(String name, String birthDate, boolean isDead, String deathDate) {
        this.name = name;
        this.birthDate = birthDate;
        this.isDead = isDead;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(String deathDate) {
        this.deathDate = deathDate;
    }
}

