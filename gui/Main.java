package gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		ComboBox<String> myComboBox = new ComboBox<String>();
	    myComboBox.getItems().addAll("A","B","C","D","E");
	    myComboBox.setEditable(true);        
	    
	    Group root = (Group) scene.getRoot();
	    root.getChildren().add(myComboBox);
		Parent root;
		try {
			
			root = FXMLLoader.load(getClass().getResource("Xml.fxml"));
			Scene sc = new Scene(root, 800, 700);
			primaryStage.setScene(sc);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
