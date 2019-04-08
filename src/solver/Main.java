package solver;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		Control z = new Control();
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		AnchorPane root;
		Canvas canvas = new Canvas(1200, 440);
		Control.setcanvas(canvas);
		root = FXMLLoader.load(getClass().getResource("Xml.fxml"));
		root.getChildren().addAll(canvas);
		Scene sc = new Scene(root, 1200, 900);
		primaryStage.setScene(sc);
		primaryStage.show();

	}

}
