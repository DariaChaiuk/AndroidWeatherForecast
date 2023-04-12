package com.weatherforecast;

public class DayInfo {
    public String date;
    public double maxTemp;
    public double minTemp;
    public double avgTemp;
    public String condition;
    public int weatherImg;

    public DayInfo(String date, double maxTemp, double minTemp, double avgTemp, String condition) {
        this.date = date;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.avgTemp = avgTemp;
        this.condition = condition;
        switch (condition){
            case "Patchy rain possible":
            case "Heavy rain":
            case "Moderate rain":
                weatherImg = R.drawable.rainy;
                break;
            case "Cloudy":
            case "Overcast":
                weatherImg = R.drawable.cloudy;
                break;
            case "Partly cloudy":
                weatherImg = R.drawable.partly_cloudy;
                break;
            case "Patchy light drizzle":
                weatherImg = R.drawable.drizzle;
                break;
            default:
                weatherImg = R.drawable.sunny;
                break;
        }
    }

    public int getWeatherImg() {
        return weatherImg;
    }

    public void setWeatherImg(int weatherImg) {
        this.weatherImg = weatherImg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getAvgTemp() {
        return avgTemp;
    }

    public void setAvgTemp(double avgTemp) {
        this.avgTemp = avgTemp;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

}
