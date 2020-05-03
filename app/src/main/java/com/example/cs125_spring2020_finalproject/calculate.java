package com.example.cs125_spring2020_finalproject;

public class calculate {
    private static final double calorie = 2000.0;
    private static final double activity = 7.0;
    private double cholesterol = 300;
    private double fat = 20;
    private static final double carbohydrate = 275.0;

    private static double n(double weight, double height, int gender) {
        if (gender == 0) {
            return 10 * weight + 625 * height - activity + 5;
        }
        if (gender == 1) {
            return 10 * weight + 625 * height - activity - 161;
        }
        return 0;
    }
    public static String diabete(double carb, double weight, double height, int gender) {
        double n = n(weight, height, gender) / calorie;
        double carbneed = n * carbohydrate;
        double tem = carb / carbneed;
        double partion = (double) Math.round(tem * 100);
        if (partion <= 100) {
            return "Carbohydrate intake " + carb + ". Reaches " + partion
                    + "% daily value." + System.getProperty ("line.separator")
                    + "Low risk for diabetes." + "carb need is " + carbneed;
        }
        if (partion <= 125) {
            return "Carbohydrate intake " + carb + "reaches " + partion + "%. Risk exists for diabetes";
        }
        return "Carbohydrate intake " + carb + "reaches " + partion + "%. High risk for diabetes";
    }

    public int hypertension() {
        return 0;
    }
}
