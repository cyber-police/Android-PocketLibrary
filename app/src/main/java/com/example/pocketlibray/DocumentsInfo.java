package com.example.pocketlibray;

import java.util.ArrayList;

public class DocumentsInfo {
    private String title;
    private String language;
    private String category;
    private String type;
    private int readTimes;
    private boolean availability;
    private ArrayList<String> authors;
    private float rating;
    private String imageUrl;
    private int yearOfPublishing;
    private String description;
    private int pageCount;
    private float price;
    private boolean isFree;
    private String previewLink;
    private String buyLink;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getReadTimes() {
        return readTimes;
    }

    public void setReadTimes(int readTimes) {
        this.readTimes = readTimes;
    }

    public boolean getAvailability() {
        return availability;
    }

    public void setSubtitle(boolean availability) {
        this.availability = availability;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getPublishedDate() {
        return yearOfPublishing;
    }

    public void setPublishedDate(int yearOfPublishing) {
        this.yearOfPublishing = yearOfPublishing;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean getIsFree() {
        return isFree;
    }

    public void setIsFree(boolean isFree) {
        this.isFree = isFree;
    }

    public String getPreviewLink() {
        return previewLink;
    }

    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
    }

    public String getBuyLink() {
        return buyLink;
    }

    public void setBuyLink(String buyLink) {
        this.buyLink = buyLink;
    }

    // creating a constructor class for our BookInfo
    public DocumentsInfo(String title, String language, String category, String type, int readTimes, boolean availability, ArrayList<String> authors, float rating, String imageUrl,
                         int yearOfPublishing, String description, int pageCount, float price,
                         boolean isFree) {
        this.title = title;
        this.language = language;
        this.category = category;
        this.type = type;
        this.readTimes = readTimes;
        this.availability = availability;
        this.authors = authors;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.yearOfPublishing = yearOfPublishing;
        this.description = description;
        this.pageCount = pageCount;
        this.price = price;
        this.isFree = isFree;
    }
}
