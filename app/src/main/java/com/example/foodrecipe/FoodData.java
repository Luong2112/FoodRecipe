package com.example.foodrecipe;

public class FoodData {
    private String itemName;
    private String itemIngredient;
    private String itemCook;
    private String itemImage;

    //private String postBy;

    public FoodData(String itemName, String itemIngredient, String itemCook, String itemImage) {
        this.itemName = itemName;
        this.itemIngredient = itemIngredient;
        this.itemCook = itemCook;
        this.itemImage = itemImage;
    }

    public FoodData() {
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemCook() {
        return itemCook;
    }

    public String getItemIngredient() {
        return itemIngredient;
    }

    public String getItemImage() {
        return itemImage;
    }

}
