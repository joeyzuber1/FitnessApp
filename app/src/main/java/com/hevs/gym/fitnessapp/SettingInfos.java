package com.hevs.gym.fitnessapp;

/**
 * Created by Joey on 21.04.2017.
 */

public class SettingInfos {

    public static int getResource(String id) {
        switch (Integer.valueOf(id))
        {
            case 12:
                return R.style.small_text;
            case 18:
                return R.style.medium_text;
            case 22:
                return R.style.large_text;
            default:
                return R.style.small_text;
        }
    }
    public static int getResourceColor(String id) {
        switch (id)
        {
            case "black":
                return R.style.black_text;
            case "red":
                return R.style.red_text;
            case "green":
                return R.style.green_text;
            case "blue":
                return R.style.blue_text;
            default:
                return R.style.black_text;
        }
    }
}
