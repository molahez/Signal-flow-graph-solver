package solver;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.shape.Line;

public class Control {

	private static String x1;
	private static String y1;
	private static String x2;
	private static String y2;
	private static String nodes;
	private static String type;
	private static int count = 0; // to check all branches are drawn
	private static Canvas cs;
	private static GraphicsContext graphicsContext;
	private static Boolean[] check; // It's used to check that no duplicate branches
	private static Boolean[][] check1; // It's used to make the other curve between any 2 points
	private static Boolean[][] check11; // It's used to make the other curve between any 2 points
	private static String[][] check111; // It's used to check that curves are not same direction
	private static int[][] map;
	private static String[][] map_S;
	private CalculationLogic logic;

	private static Boolean selfflag = false;

	public static void setcanvas(Canvas c) {
		Control.cs = c;
		graphicsContext = c.getGraphicsContext2D();
		initDraw(graphicsContext);
		
	}

	public static void setMyVariable(String x1, String y1, String x2, String y2, String nodes, String flag) {
		Control.x1 = x1;
		Control.y1 = y1;
		Control.x2 = x2;
		Control.y2 = y2;
		Control.nodes = nodes;
		Control.type = flag;
	}

	public static String getMyVariable1() {
		return x1;
	}

	public static String getMyVariable6() {
		return type;
	}

	public static String getMyVariable2() {
		return y1;
	}

	public static String getMyVariable3() {
		return x2;
	}

	public static String getMyVariable4() {
		return y2;
	}

	public static String getMyVariable5() {
		return nodes;
	}

	@FXML
	private ComboBox op1;

	@FXML
	private ComboBox op2;

	ObservableList<String> zz = (ObservableList<String>) FXCollections.observableArrayList("right", "left");

	ObservableList<String> zz1 = (ObservableList<String>) FXCollections.observableArrayList("line", "arc");

	@FXML
	private TextField text1;

	@FXML
	private TextField text2;
	@FXML
	private TextField text3;

	@FXML
	private TextField text5;

	@FXML
	private TextArea text6;
	@FXML
	private TextField tx1;

	@FXML
	private TextField tx3;

	@FXML
	private TextField tx2;
	@FXML
	private TextField tx4;

	@FXML
	void text1(ActionEvent event) {

	}

	@FXML
	private Line l1;

	@FXML
	private Button btn1;

	@FXML
	private Button btn5;

	@FXML
	private Button btn2;

	@FXML
	void list(ActionEvent event) {
		String need = (String) op1.getValue();
		System.out.println(need);

	}

	@FXML
	void list1(ActionEvent event) {

		String need = (String) op2.getValue();
		System.out.println(need);

	}

	@FXML
	void ac1(ActionEvent event) {
		String imagePath = "file:req1.png";
		Image image = new Image(imagePath);

		String imagePath2 = "file:req2.png";
		Image image2 = new Image(imagePath2);

		String imagePath3 = "file:req11.png";
		Image image3 = new Image(imagePath3);

		String imagePath4 = "file:req22.png";
		Image image4 = new Image(imagePath4);
		if ((Integer.parseInt(text1.getText()) > Integer.parseInt(text5.getText()))
				|| (Integer.parseInt(text2.getText()) > Integer.parseInt(text5.getText()))) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setHeaderText("Error");
			errorAlert.setContentText("It's out of bound of given nodes");
			errorAlert.showAndWait();
			text1.setText("");
			text2.setText("");
			text3.setText("");

		} else if ((Integer.parseInt(text2.getText())) < (Integer.parseInt(text1.getText()))) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setHeaderText("Error");
			errorAlert.setContentText("Nodes are added in ascending order ONLY!");
			errorAlert.showAndWait();
			text1.setText("");
			text2.setText("");
			text3.setText("");
		} else {
			if (op2.getValue() == "line") {
				count++;
				if (Math.abs(Integer.parseInt(text1.getText()) - Integer.parseInt(text2.getText())) != 1) {
					count--;
					Alert errorAlert = new Alert(AlertType.ERROR);
					errorAlert.setHeaderText("Error");
					errorAlert.setContentText("Enter 2 adjacent nodes");
					errorAlert.showAndWait();
					text1.setText("");
					text2.setText("");
					text3.setText("");
				} else {
					if (op1.getValue().equals("left")) {
						count--;
						Alert errorAlert = new Alert(AlertType.ERROR);
						errorAlert.setHeaderText("Error");
						errorAlert.setContentText("right direction only in lines");
						errorAlert.showAndWait();
						text1.setText("");
						text2.setText("");
						text3.setText("");
					} else {
						if (check[Math.min(Integer.parseInt(text1.getText()), Integer.parseInt(text2.getText()))
								- 1] == false) {
							setMyVariable(text1.getText(), text2.getText(), text3.getText(), (String) op1.getValue(),
									"", "line");
							if (op1.getValue().equals("right")) {
								map[Integer.parseInt(text1.getText()) - 1][Integer.parseInt(text2.getText()) - 1] = 1;
								map_S[Integer.parseInt(text1.getText()) - 1][Integer.parseInt(text2.getText())
										- 1] = text3.getText();
							} else {
								map[Integer.parseInt(text2.getText()) - 1][Integer.parseInt(text1.getText()) - 1] = 1;
								map_S[Integer.parseInt(text2.getText()) - 1][Integer.parseInt(text1.getText())
										- 1] = text3.getText();
							}

							check[Math.min(Integer.parseInt(text1.getText()), Integer.parseInt(text2.getText()))
									- 1] = true;
							text1.setText("");
							text2.setText("");
							text3.setText("");

						} else {
							count--;
							Alert errorAlert = new Alert(AlertType.ERROR);
							errorAlert.setHeaderText("Error");
							errorAlert.setContentText("Branch is drawn before");
							errorAlert.showAndWait();
							text1.setText("");
							text2.setText("");
							text3.setText("");

						}

					}
				}
			} else if (op2.getValue() == "arc") {

				if (count == Integer.parseInt(text5.getText()) - 1) {
					if (Integer.parseInt(text1.getText()) == Integer.parseInt(text2.getText())) {
						selfflag = true;
						setMyVariable(text1.getText(), text2.getText(), text3.getText(), (String) op1.getValue(), "",
								"arc");
						if (op1.getValue().equals("right")) {
							map[Integer.parseInt(text1.getText()) - 1][Integer.parseInt(text2.getText()) - 1] = 1;
							map_S[Integer.parseInt(text1.getText()) - 1][Integer.parseInt(text2.getText()) - 1] = text3
									.getText();
						} else {
							map[Integer.parseInt(text2.getText()) - 1][Integer.parseInt(text1.getText()) - 1] = 1;
							map_S[Integer.parseInt(text2.getText()) - 1][Integer.parseInt(text1.getText()) - 1] = text3
									.getText();
						}

						text1.setText("");
						text2.setText("");
						text3.setText("");

					} else {
						if (Math.abs(Integer.parseInt(text1.getText()) - Integer.parseInt(text2.getText())) == 1) {
							if (((String) op1.getValue()).equals("right")) {
								Alert errorAlert = new Alert(AlertType.ERROR);
								errorAlert.setHeaderText("Error");
								errorAlert.setContentText(
										"No Arc in right direction can be drawn between 2 adjacent nodes");
								errorAlert.showAndWait();

								text1.setText("");
								text2.setText("");
								text3.setText("");
							} else {
								setMyVariable(text1.getText(), text2.getText(), text3.getText(),
										(String) op1.getValue(), "", "arc");
								if (op1.getValue().equals("right")) {
									map[Integer.parseInt(text1.getText()) - 1][Integer.parseInt(text2.getText())
											- 1] = 1;
									map_S[Integer.parseInt(text1.getText()) - 1][Integer.parseInt(text2.getText())
											- 1] = text3.getText();
								} else {
									map[Integer.parseInt(text2.getText()) - 1][Integer.parseInt(text1.getText())
											- 1] = 1;
									map_S[Integer.parseInt(text2.getText()) - 1][Integer.parseInt(text1.getText())
											- 1] = text3.getText();
								}
								text1.setText("");
								text2.setText("");
								text3.setText("");
							}

						} else {
							setMyVariable(text1.getText(), text2.getText(), text3.getText(), (String) op1.getValue(),
									"", "arc");
							if (op1.getValue().equals("right")) {
								map[Integer.parseInt(text1.getText()) - 1][Integer.parseInt(text2.getText()) - 1] = 1;
								map_S[Integer.parseInt(text1.getText()) - 1][Integer.parseInt(text2.getText())
										- 1] = text3.getText();
							} else {
								map[Integer.parseInt(text2.getText()) - 1][Integer.parseInt(text1.getText()) - 1] = 1;
								map_S[Integer.parseInt(text2.getText()) - 1][Integer.parseInt(text1.getText())
										- 1] = text3.getText();
							}
							text1.setText("");
							text2.setText("");
							text3.setText("");
						}

					}

				} else {
					text1.setText("");
					text2.setText("");
					text3.setText("");
					Alert errorAlert = new Alert(AlertType.ERROR);
					errorAlert.setHeaderText("Error");
					errorAlert.setContentText("Arcs are added after branches are added,branches left"
							+ String.valueOf(Integer.parseInt(text5.getText()) - 1 - count));
					errorAlert.showAndWait();
				}
			} else {

				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setHeaderText("Error");
				errorAlert.setContentText("Please choose line or arc");
				errorAlert.showAndWait();
				text1.setText("");
				text2.setText("");
				text3.setText("");
			}

			if (Control.getMyVariable6().equals("line")) {

				graphicsContext.beginPath();
				graphicsContext.moveTo((Integer.parseInt(Control.getMyVariable1()) + 1) * 100, 200);

				graphicsContext.lineTo((Integer.parseInt(Control.getMyVariable2()) + 1) * 100, 200);

				graphicsContext.strokeText(Control.getMyVariable3(),
						(((Integer.parseInt(Control.getMyVariable2()) + 1) * 100)
								+ (Integer.parseInt(Control.getMyVariable1()) + 1) * 100) / 2,
						190);
				graphicsContext.stroke();
				if (Control.getMyVariable4().equals("right")) {
					graphicsContext
							.drawImage(image,
									(((Integer.parseInt(Control.getMyVariable2()) + 1) * 100)
											+ (Integer.parseInt(Control.getMyVariable1()) + 1) * 100) / 2 - 20,
									175, 50, 50);
				} else if (Control.getMyVariable4().equals("left")) {
					graphicsContext
							.drawImage(image2,
									(((Integer.parseInt(Control.getMyVariable2()) + 1) * 100)
											+ (Integer.parseInt(Control.getMyVariable1()) + 1) * 100) / 2 - 20,
									175, 50, 50);
				}
			} else if (Control.getMyVariable6().equals("arc")) {

				if (selfflag && !check11[Integer.parseInt(Control.getMyVariable1()) - 1][Integer
						.parseInt(Control.getMyVariable2()) - 1]) {
					graphicsContext.beginPath();
					graphicsContext.moveTo((Integer.parseInt(Control.getMyVariable1()) + 1) * 100, 200);
					graphicsContext.arc((Integer.parseInt(Control.getMyVariable1()) + 1) * 100, 175, 25, 25, 270, 360);
					graphicsContext.strokeText(Control.getMyVariable3(),
							((Integer.parseInt(Control.getMyVariable2()) + 1) * 100), 175);
					graphicsContext.stroke();
					check11[Integer.parseInt(Control.getMyVariable2()) - 1][Integer.parseInt(Control.getMyVariable1())
							- 1] = true;
					selfflag = false;
					if (Control.getMyVariable4().equals("right")) {
						graphicsContext.drawImage(image3,
								(((Integer.parseInt(Control.getMyVariable2()) + 1) * 100) - 40), 125, 50, 50);
					} else if (Control.getMyVariable4().equals("left")) {
						graphicsContext.drawImage(image4,
								(((Integer.parseInt(Control.getMyVariable2()) + 1) * 100) - 20), 125, 50, 50);
					}

				} else if (check11[Integer.parseInt(Control.getMyVariable1()) - 1][Integer
						.parseInt(Control.getMyVariable2()) - 1]) {
					Alert errorAlert = new Alert(AlertType.ERROR);
					errorAlert.setHeaderText("Error");
					errorAlert.setContentText("Maximum number of curves has been achieved per node");
					errorAlert.showAndWait();
				} else {
					if (!check1[Integer.parseInt(Control.getMyVariable1()) - 1][Integer
							.parseInt(Control.getMyVariable2()) - 1]
							&& !check11[Integer.parseInt(Control.getMyVariable1()) - 1][Integer
									.parseInt(Control.getMyVariable2()) - 1]) {

						graphicsContext.beginPath();
						graphicsContext.moveTo((Integer.parseInt(Control.getMyVariable1()) + 1) * 100, 200);
						graphicsContext.arc(
								(((Integer.parseInt(Control.getMyVariable2()) + 1) * 100)
										+ ((Integer.parseInt(Control.getMyVariable1()) + 1) * 100)) / 2,
								200,
								Math.abs(Integer.parseInt(Control.getMyVariable2())
										- Integer.parseInt(Control.getMyVariable1())) * 50,
								Math.abs(Integer.parseInt(Control.getMyVariable2())
										- Integer.parseInt(Control.getMyVariable1())) * 50,
								180, 180);
						graphicsContext.strokeText(Control.getMyVariable3(),
								(((Integer.parseInt(Control.getMyVariable2()) + 1) * 100)
										+ (Integer.parseInt(Control.getMyVariable1()) + 1) * 100) / 2,
								212 + Math.abs(Integer.parseInt(Control.getMyVariable2())
										- Integer.parseInt(Control.getMyVariable1())) * 50);
						graphicsContext.stroke();
						check1[Integer.parseInt(Control.getMyVariable1()) - 1][Integer
								.parseInt(Control.getMyVariable2()) - 1] = true;
						check1[Integer.parseInt(Control.getMyVariable2()) - 1][Integer
								.parseInt(Control.getMyVariable1()) - 1] = true;
						check111[Integer.parseInt(Control.getMyVariable1()) - 1][Integer
								.parseInt(Control.getMyVariable2()) - 1] = Control.getMyVariable4();
						check111[Integer.parseInt(Control.getMyVariable2()) - 1][Integer
								.parseInt(Control.getMyVariable1()) - 1] = Control.getMyVariable4();

						if (Control.getMyVariable4().equals("right")) {
							graphicsContext.drawImage(image3,
									(((Integer.parseInt(Control.getMyVariable2()) + 1) * 100)
											+ (Integer.parseInt(Control.getMyVariable1()) + 1) * 100) / 2 - 30,
									175 + Math.abs(Integer.parseInt(Control.getMyVariable2())
											- Integer.parseInt(Control.getMyVariable1())) * 50,
									50, 50);
						} else if (Control.getMyVariable4().equals("left")) {
							graphicsContext.drawImage(image4,
									(((Integer.parseInt(Control.getMyVariable2()) + 1) * 100)
											+ (Integer.parseInt(Control.getMyVariable1()) + 1) * 100) / 2 - 20,
									175 + Math.abs(Integer.parseInt(Control.getMyVariable2())
											- Integer.parseInt(Control.getMyVariable1())) * 50,
									50, 50);
						}

					} else if (check1[Integer.parseInt(Control.getMyVariable1()) - 1][Integer
							.parseInt(Control.getMyVariable2()) - 1]
							&& !check11[Integer.parseInt(Control.getMyVariable1()) - 1][Integer
									.parseInt(Control.getMyVariable2()) - 1]) {// above
						if (check111[Integer.parseInt(Control.getMyVariable1()) - 1][Integer
								.parseInt(Control.getMyVariable2()) - 1].equals(Control.getMyVariable4())) {
							Alert errorAlert = new Alert(AlertType.ERROR);
							errorAlert.setHeaderText("Error");
							errorAlert.setContentText("The dircection of this arc can't be same to the below arc");
							errorAlert.showAndWait();

						} else {

							graphicsContext.beginPath();
							graphicsContext.moveTo((Integer.parseInt(Control.getMyVariable1()) + 1) * 100, 200);
							graphicsContext.arc(
									(((Integer.parseInt(Control.getMyVariable2()) + 1) * 100)
											+ ((Integer.parseInt(Control.getMyVariable1()) + 1) * 100)) / 2,
									200,
									Math.abs(Integer.parseInt(Control.getMyVariable2())
											- Integer.parseInt(Control.getMyVariable1())) * 50,
									Math.abs(Integer.parseInt(Control.getMyVariable2())
											- Integer.parseInt(Control.getMyVariable1())) * 50,
									360, 180);
							graphicsContext.strokeText(Control.getMyVariable3(),
									(((Integer.parseInt(Control.getMyVariable2()) + 1) * 100)
											+ (Integer.parseInt(Control.getMyVariable1()) + 1) * 100) / 2,
									212 - Math.abs(Integer.parseInt(Control.getMyVariable2())
											- Integer.parseInt(Control.getMyVariable1())) * 50);
							graphicsContext.stroke();
							check1[Integer.parseInt(Control.getMyVariable1()) - 1][Integer
									.parseInt(Control.getMyVariable2()) - 1] = true;
							check1[Integer.parseInt(Control.getMyVariable2()) - 1][Integer
									.parseInt(Control.getMyVariable1()) - 1] = true;
							check11[Integer.parseInt(Control.getMyVariable1()) - 1][Integer
									.parseInt(Control.getMyVariable2()) - 1] = true;
							check11[Integer.parseInt(Control.getMyVariable2()) - 1][Integer
									.parseInt(Control.getMyVariable1()) - 1] = true;

							if (Control.getMyVariable4().equals("right")) {
								graphicsContext.drawImage(image3,
										(((Integer.parseInt(Control.getMyVariable2()) + 1) * 100)
												+ (Integer.parseInt(Control.getMyVariable1()) + 1) * 100) / 2 - 30,
										175 - Math.abs(Integer.parseInt(Control.getMyVariable2())
												- Integer.parseInt(Control.getMyVariable1())) * 50,
										50, 50);
							} else if (Control.getMyVariable4().equals("left")) {
								graphicsContext.drawImage(image4,
										(((Integer.parseInt(Control.getMyVariable2()) + 1) * 100)
												+ (Integer.parseInt(Control.getMyVariable1()) + 1) * 100) / 2 - 20,
										175 - Math.abs(Integer.parseInt(Control.getMyVariable2())
												- Integer.parseInt(Control.getMyVariable1())) * 50,
										50, 50);
							}
						}
					} else {
						count--;
						Alert errorAlert = new Alert(AlertType.ERROR);
						errorAlert.setHeaderText("Error");
						errorAlert.setContentText("Maximum number of curves has been achieved between 2 nodes");
						errorAlert.showAndWait();
					}

				}
			}
		}

	}

	@FXML
	void ac2(ActionEvent event) {

		text1.setDisable(false);
		text2.setDisable(false);
		text3.setDisable(false);
		btn1.setDisable(false);
		btn2.setDisable(false);
		setMyVariable("", "", "", "", text5.getText(), "");
		text5.setDisable(true);
		btn5.setDisable(true);
		check = new Boolean[Integer.parseInt(text5.getText()) - 1];
		check1 = new Boolean[(Integer.parseInt(text5.getText()))][(Integer.parseInt(text5.getText()))];
		check11 = new Boolean[(Integer.parseInt(text5.getText()))][(Integer.parseInt(text5.getText()))];
		check111 = new String[(Integer.parseInt(text5.getText()))][(Integer.parseInt(text5.getText()))];
		map = new int[(Integer.parseInt(text5.getText()))][(Integer.parseInt(text5.getText()))];
		map_S = new String[(Integer.parseInt(text5.getText()))][(Integer.parseInt(text5.getText()))];
		int i = Integer.parseInt(Control.getMyVariable5());

		for (int j = 0; j < i; j++) {
			for (int k = 0; k < i; k++) {
				check1[j][k] = false;
				check11[j][k] = false;
				check111[j][k] = " ";
				map_S[j][k] = "";
				map[j][k] = 0;

			}
		}

		for (int j = 0; j < i - 1; j++) {
			check[j] = false;
		}
		for (int j = 1; j < i; j++) {
			graphicsContext.setLineDashes(3);
			graphicsContext.beginPath();
			graphicsContext.moveTo((j + 1) * 100, 200);
			graphicsContext.lineTo((j + 2) * 100, 200);
			graphicsContext.stroke();

		}
		for (int j = 1; j < i; j++) {
			graphicsContext.setLineDashes(0);
			graphicsContext.strokeText(Integer.toString(j), (j + 1) * 100, 200);
			graphicsContext.strokeText(Integer.toString(j + 1), (j + 2) * 100, 200);

		}
	}

	@FXML
	void ac3(ActionEvent event) {
		logic = new CalculationLogic(map, map_S);
		text6.setText(logic.getOverall());
		text6.appendText("\n");
		text6.appendText("\n");
		text6.appendText(logic.forwardPathString());
		text6.appendText("\n");
		text6.appendText("\n");
		text6.appendText(logic.getLoopsGain());
		text6.appendText("\n");
		text6.appendText("\n");
		text6.appendText(logic.nonTouchingLoops());
		text6.appendText("\n");
		text6.appendText("\n");
		text6.appendText(logic.getDeltaForward());
	}

	private static void initDraw(GraphicsContext gc) {
		double canvasWidth = gc.getCanvas().getWidth();
		double canvasHeight = gc.getCanvas().getHeight();

		gc.setFill(Color.LIGHTGRAY);
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(5);

		gc.fill();
		gc.strokeRect(0, // x of the upper left corner
				0, // y of the upper left corner
				canvasWidth, // width of the rectangle
				canvasHeight); // height of the rectangle

		gc.setFill(Color.RED);
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(1);

	}

	@FXML
	private void initialize() {
		text6.setEditable(false);
		text1.setDisable(true);
		text2.setDisable(true);
		text3.setDisable(true);
		btn1.setDisable(true);
		btn2.setDisable(true);
		op1.setValue("Direction");
		op1.setItems((ObservableList<?>) zz);
		op2.setValue("type");
		op2.setItems((ObservableList<?>) zz1);

	}

}
