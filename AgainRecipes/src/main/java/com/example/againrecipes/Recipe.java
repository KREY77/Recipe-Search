package com.example.againrecipes;
import java.util.ArrayList;

public class Recipe {
    public String label;
    public String image;
    public ArrayList <String> ingredientLines;
    public double calories;
    public double totalWeight;
    public ArrayList<String> cuisineType;
    public ArrayList<String> instructions;

    public Recipe(String label, String image, ArrayList<String> ingredientLines, double calories, double totalWeight, ArrayList<String> cuisineType, ArrayList<String> instructions) {
        this.label = label;
        this.image = image;
        this.ingredientLines = ingredientLines;
        this.calories = calories;
        this.totalWeight = totalWeight;
        this.cuisineType = cuisineType;
        this.instructions = instructions;
    }
    @Override
    public String toString(){
        String allIn = "";
        allIn+="Название рецепта: "+label+"\n"+"Калории: "+calories+"\n"+"Вес: "+Math.round(totalWeight)+"\n"+"Тип кухни: "+cuisineType.get(0)+"\n"+"Ингридиенты: ";
        for(String i: ingredientLines) {
            allIn+=i+"\n";
        }
        if(instructions!=null) {
            allIn += "Инструкция: ";
            for (String i : instructions) {
                allIn += i + "\n";
            }
        }
        return allIn;
    }
}
