package com.example.againrecipes;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.Certificate;

public class HelloApplication extends Application {
    private RecipeObject recipeSearch(String ingridient,String comboBox, String min, String  max){
        String link = "https://api.edamam.com/api/recipes/v2?app_id=105f8fb7&app_key=4d92afc615644ebbe1203944f6a54a25&type=any&q="+ingridient+"&calories="+min+"-"+max;
        if(!comboBox.equals("Any")){
            link+="&cuisineType="+comboBox;
        }
        try {
            URL urlLink = new URL(link);
            HttpsURLConnection connection = (HttpsURLConnection) urlLink.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String word = "";
            StringBuilder stringBuilder = new StringBuilder();
            while ((word=bufferedReader.readLine())!=null){
                stringBuilder.append(word);
            }
            bufferedReader.close();
            Gson gson = new Gson();
            RecipeObject recipeObject =gson.fromJson(stringBuilder.toString(),RecipeObject.class);
            connection.disconnect();
            return recipeObject;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    RecipeObject app;
    int index=0;

    @Override
    public void start(Stage stage) throws IOException {
        ObservableList <String> forType = FXCollections.observableArrayList("Any","American", "Asian", "British", "Caribbean", "Central Europe", "Chinese", "Eastern Europe", "French", "Indian", "Italian", "Japanese", "Kosher", "Mediterranean", "Mexican", "Middle Eastern", "Nordic", "South American", "South East Asian");
        ComboBox <String> comboBox = new ComboBox<String>(forType);
        comboBox.setValue("Any");
        Label forBox = new Label("Тип кухни:");
        TextField minCalories = new TextField();
        minCalories.setText("0");
        TextField maxCalories = new TextField();
        maxCalories.setText("1000");
        Label calories = new Label("Диапозон в калориях:");
        Label minToMax = new Label("-");
        TextField areaForRecipe = new TextField();
        Button buttonForSend = new Button("Поиск по главному ингриденту");
        TextArea textArea = new TextArea();
        buttonForSend.setOnAction(e->{
            String box = comboBox.getValue().toString();
            String min = minCalories.getText();
            String max = maxCalories.getText();
            String textFromField = areaForRecipe.getText();
            if(!textFromField.isEmpty()&&Double.parseDouble(min)>=0&&Double.parseDouble(max)<=10000) {
                app = recipeSearch(textFromField, box, min, max);
                textArea.clear();
                if (app != null) {
                    textArea.appendText(app.hits.get(index).recipe.toString());
                }
            }
        });
        Button next = new Button("Следущий рецепт");
        next.setOnAction(e->{
            if(app!=null&&index<app.hits.size()-1){
                textArea.clear();
                textArea.appendText(app.hits.get(++index).recipe.toString());
            }
        });
        Button prev = new Button("Предыдущий рецепт");
        prev.setOnAction(e->{
            if(index>0) {
                if (app != null) {
                    textArea.clear();
                    textArea.appendText(app.hits.get(--index).recipe.toString());
                }
            }
        });
        HBox list = new HBox(prev,next);
        HBox combo = new HBox(forBox,comboBox);
        HBox forCalories = new HBox(calories,minCalories,minToMax,maxCalories);
        VBox area = new VBox(areaForRecipe, buttonForSend,combo,forCalories,textArea,list);
        BorderPane root = new BorderPane(area);
        Scene scene = new Scene(root, 320, 240);
        stage.setTitle("RecipeSearcher");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}