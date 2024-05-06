package application;
	
import java.awt.GridLayout;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


public class FloodItGame extends Application {
	public int ROOT_SIZE = 600;
	
//	public enum Colours6 {
//		RED, ORANGE, YELLOW, GREEN, BLUE, MAGENTA
//	}
	
	public Paint[] Colours = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.MAGENTA, Color.BLACK, Color.CYAN, Color.GRAY, Color.PINK, Color.WHITE};
	public int ColourMAX = 6;
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			// Create a root pane and scene
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, ROOT_SIZE, ROOT_SIZE);
			
			// Create a text for number of moves left
			Text moves = new Text("MOVES ");
//			moves.set
			root.setTop(moves);
			root.setAlignment(moves, Pos.CENTER);
			
			// Create a gridpane for the game board and fill the board randomly
			GridPane board = new GridPane();
			board.setGridLinesVisible(true);
			int[][] gl = new int[6][6];
			for(int row = 0; row < gl.length; row++) {
				for(int col = 0; col < gl.length; col++) {
					Rectangle r = new Rectangle(30, 30);
					int randomColour = (int)Math.floor(Math.random() * (ColourMAX - 0 + 1) + 0);
					r.setFill(Colours[randomColour]);
					board.add(r, row, col);
				}
			}
			board.setAlignment(Pos.CENTER);
			root.setCenter(board);
			
			// Create game setup controls
			ComboBox<String> cbGrid = new ComboBox<String>();
			cbGrid.getItems().addAll("6x6", "8x8", "12x12", "14x14", "18x18");
			ComboBox<Integer> colours = new ComboBox<Integer>();
			Button newGame = new Button("New Game");
			// Place them inside a horizontal box and add to the borderpane
			HBox hbox = new HBox(20);
			hbox.setPadding(new Insets(0, 0, 30, 0));
			hbox.getChildren().addAll(new Label("Grid Size"), cbGrid, new Label("Number of Colours"), colours, newGame);
			hbox.setAlignment(Pos.CENTER);
			root.setBottom(hbox);
			
			
			// Set the title, scene, and show the stage
			primaryStage.setTitle("Flood It");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
