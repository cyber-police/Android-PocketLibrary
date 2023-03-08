package com.example.pocketlibray;

import java.util.ArrayList;

public class NestedInfo {
    // Declaration of the variables
    private String ParentItemTitle;
    private ArrayList<DocumentsInfo> documentsInfoArrayList;

    // Constructor of the class
    // to initialize the variables
    public NestedInfo(String ParentItemTitle, ArrayList<DocumentsInfo> documentsInfoArrayList) {
        this.ParentItemTitle = ParentItemTitle;
        this.documentsInfoArrayList = (ArrayList) documentsInfoArrayList.clone();
    }

    // Getter and Setter methods
    // for each parameter
    public String getParentItemTitle() {
        return ParentItemTitle;
    }

    public void setParentItemTitle(String parentItemTitle) {
        ParentItemTitle = parentItemTitle;
    }

    public ArrayList<DocumentsInfo> getChildItemList() {
        return documentsInfoArrayList;
    }

    public void setChildItemList(ArrayList<DocumentsInfo> documentsInfoArrayList) {
        this.documentsInfoArrayList = documentsInfoArrayList;
    }
}
