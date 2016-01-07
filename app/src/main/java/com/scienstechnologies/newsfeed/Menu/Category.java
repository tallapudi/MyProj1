package com.scienstechnologies.newsfeed.Menu;

/**
 * Created by vamsitallapudi on 05-Jan-16.
 */
public class Category {
    private String categoryName;
    int icon;

    public Category(){

    }

    public Category(String categoryName, int icon) {
        this.categoryName = categoryName;
        this.icon = icon;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
