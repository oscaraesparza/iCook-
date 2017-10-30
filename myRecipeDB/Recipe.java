package com.example.edske.recipedb;

/**
 * Created by edske on 10/23/2017.
 */

public class Recipe {
    protected String recipeName;
    protected Ingredients[] recipeIngred =new Ingredients[10];
/*--------------ingredients class------------*/
    protected class Ingredients{
        public double amount;
        public String ingredName;

        public Ingredients(double i, String n){
            amount=i;
            ingredName=n;
        }

        public void setIngredName(String n){
            ingredName=n;
        }

        public void setAmount(double i){
            amount=i;
        }
    }
/*--------------------------------------------*/

    public Recipe (){
        recipeName=null;
        for(int i=0; i<10; i++){
            recipeIngred[i]=null;
        }
    }

    public void getRecipeName(String n){
        recipeName=n;
    }

    public void addIngred(Ingredients ingred){
        int i=0;
        while(recipeIngred[i]!=null){
            i++;
        }
        recipeIngred[i]=ingred;
    }
}
