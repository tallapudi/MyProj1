package com.scienstechnologies.newsfeed.Menu;

/**
 * Created by vamsitallapudi on 07-Jan-16.
 */
public class SettingsModel {
    private String settingName;
    int icon;
    boolean switchPresent;
    boolean switchValue;

    public SettingsModel(){

    }

    public SettingsModel(String settingName, int icon, boolean switchPresent, boolean switchValue){
        this.settingName = settingName;
        this.icon = icon;
        this.switchPresent = switchPresent;
        this.switchValue = switchValue;
    }


    public String getSettingName() {
        return settingName;
    }

    public void setSettingName(String settingName) {
        this.settingName = settingName;
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
        this.switchPresent = switchPresent;//true
    }

    public boolean isSwitchValue() {
        return switchValue;
    }

    public void setSwitchValue(boolean switchValue) {
        this.switchValue = switchValue;
    }
}
