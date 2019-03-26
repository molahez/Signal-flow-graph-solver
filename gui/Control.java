package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Control {
	
	
	@FXML
	private Button op2;

	@FXML
	ComboBox comboBox = new ComboBox(options);
	comboBox.setItems(options);
	
	ObservableList<String> options = 
		    FXCollections.observableArrayList(
		        "1",
		        "2",
		        "3"
		    );
		ComboBox comboBox = new ComboBox(options);
		
		comboBox.getItems().addAll(
			    "4",
			    "5",
			    "6"
			);



}
