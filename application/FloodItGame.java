package application;
	
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
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


public class FloodItGame extends Application {
	// Declare variables
	public BorderPane root = new BorderPane();
	public GridPane board = new GridPane();
	public Text movesLeft = new Text();
	public int ROOT_SIZE = 600;
	public Paint[] Colours = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.MAGENTA, 
							  Color.BLACK, Color.CYAN, Color.GRAY, Color.PINK, Color.WHITE};
	public int colourMAX = 6;
	public ComboBox<String> cbGrid = new ComboBox<String>();
	public ComboBox<Integer> cbColours = new ComboBox<Integer>();
	Button newGame = new Button("New Game");
	
	
	// METHOD: method for initializing the game
	public void initialize() {
		// Auto-populate the moves, grid, and combo values
		Text moves = new Text("MOVES ");
		movesLeft.setText("25");
		HBox hbTitle = new HBox();
		hbTitle.getChildren().addAll(moves, movesLeft);
		hbTitle.setAlignment(Pos.CENTER);
		root.setTop(hbTitle);
		
		// Create a gridpane for the game board and fill the board randomly
		board.setGridLinesVisible(true);
		int[][] gl = new int[colourMAX][colourMAX];
		for(int row = 0; row < gl.length; row++) {
			for(int col = 0; col < gl.length; col++) {
				Rectangle r = new Rectangle(30, 30);
				int randomColour = (int)Math.floor(Math.random() * (colourMAX - 0 + 1) + 0);
				r.setFill(Colours[randomColour]);
				board.add(r, row, col);
			}
		}
		board.setAlignment(Pos.CENTER);
		root.setCenter(board);
	
		// Set the combobox values
		cbGrid.getItems().addAll("6x6", "8x8", "12x12", "14x14", "18x18");
		cbColours.getItems().addAll(3, 4, 5, 6, 7, 8, 9, 10, 11);
		cbGrid.setValue("6x6");
		cbColours.setValue(6);
		// Place them inside a horizontal box and add to the borderpane
		HBox hbox = new HBox(20);
		hbox.setPadding(new Insets(0, 0, 30, 0));
		hbox.getChildren().addAll(new Label("Grid Size"), cbGrid, new Label("Number of Colours"), cbColours, newGame);
		hbox.setAlignment(Pos.CENTER);
		root.setBottom(hbox);
	}
	
	
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			// Create a root pane and scene
//			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, ROOT_SIZE, ROOT_SIZE);
			
			// Create a text for number of moves left
//			Text moves = new Text("MOVES ");
//			HBox hbTitle = new HBox();
//			hbTitle.getChildren().addAll(moves, movesLeft);
//			hbTitle.setAlignment(Pos.CENTER);
//			root.setTop(hbTitle);
//			root.setAlignment(hbTitle, Pos.CENTER);
			
			// Create a gridpane for the game board and fill the board randomly
//			GridPane board = new GridPane();
//			board.setGridLinesVisible(true);
//			int[][] gl = new int[6][6];
//			for(int row = 0; row < gl.length; row++) {
//				for(int col = 0; col < gl.length; col++) {
//					Rectangle r = new Rectangle(30, 30);
//					int randomColour = (int)Math.floor(Math.random() * (ColourMAX - 0 + 1) + 0);
//					r.setFill(Colours[randomColour]);
//					board.add(r, row, col);
//				}
//			}
//			board.setAlignment(Pos.CENTER);
//			root.setCenter(board);
			
			// Create game setup controls
//			ComboBox<String> cbGrid = new ComboBox<String>();
//			cbGrid.getItems().addAll("6x6", "8x8", "12x12", "14x14", "18x18");
//			ComboBox<Integer> cbColours = new ComboBox<Integer>();
//			cbColours.getItems().addAll(3, 4, 5, 6, 7, 8, 9, 10, 11);
//			Button newGame = new Button("New Game");
			// Place them inside a horizontal box and add to the borderpane
//			HBox hbox = new HBox(20);
//			hbox.setPadding(new Insets(0, 0, 30, 0));
//			hbox.getChildren().addAll(new Label("Grid Size"), cbGrid, new Label("Number of Colours"), cbColours, newGame);
//			hbox.setAlignment(Pos.CENTER);
//			root.setBottom(hbox);
			
			
			// Set the title, scene, and show the stage
			primaryStage.setTitle("Flood It");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			// Call the initialize method
			initialize();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
