package com.treuddm.AppyNews.Menu;

/**
 * Created by vamsitallapudi on 05-Jan-16.
 */
public class CategoryModel {
    private String categoryName;
    int icon;
    boolean switchPresent,switchValue;

    public CategoryModel(){

    }

    public CategoryModel(String categoryName, int icon, boolean switchPresent) {
        this.categoryName = categoryName;
        this.icon = icon;
        this.switchPresent = switchPresent;

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

    public boolean isSwitchPresent() {
        return switchPresent;
    }

    public void setSwitchPresent(boolean switchPresent) {
        this.switchPresent = switchPresent;
    }

    public boolean isSwitchValue() {
        return switchValue;
    }

    public void setSwitchValue(boolean switchValue) {
        this.switchValue = switchValue;
    }
}
