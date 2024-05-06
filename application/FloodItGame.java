package application;
	
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Node;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class FloodItGame extends Application {
	// Declare variables
	public final int ROOT_SIZE = 700;
	public BorderPane root = new BorderPane();
	public GridPane board = new GridPane();
	public Text moves = new Text("MOVES ");
	public Text movesLeft = new Text();
	public int numMovesLeft = 0;
	public int numColours = 6;
	public int n = 6;
//	public int boardSize = n * 30;
	public Paint[] Colours = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.MAGENTA, 
							  Color.CYAN, Color.BLACK, Color.GRAY, Color.PINK, Color.WHITE};
	public ComboBox<Integer> cbGrid = new ComboBox<Integer>();
	public ComboBox<Integer> cbColours = new ComboBox<Integer>();
	Button newGame = new Button("New Game");
	Rectangle rect = new Rectangle(30, 30);
//	public Color floodZone;
	
	
	// METHOD: method for initializing the game board and display
	public void initialize() {
		// Auto-populate the moves and add to the root
		numMovesLeft = (int)Math.floor(25 * ((n + n) * numColours) / ((14 + 14) * 6));
		movesLeft.setText(String.valueOf(numMovesLeft));
		moves.setFont(Font.font("Arial", FontWeight.BOLD, 24.0));
		movesLeft.setFont(Font.font("Arial", FontWeight.BOLD, 24.0));
		HBox hbTitle = new HBox();
		hbTitle.getChildren().addAll(moves, movesLeft);
		hbTitle.setAlignment(Pos.CENTER);
		root.setTop(hbTitle);
		
		// Fill the gridpane board randomly with coloured rectangles
		board.setGridLinesVisible(true);
		int[][] grid = new int[n][n];
		for(int row = 0; row < grid.length; row++) {
			for(int col = 0; col < grid.length; col++) {
//				Rectangle rect = new Rectangle(30, 30);
				rect = new Rectangle(30, 30);
				int randomColour = (int)Math.floor(Math.random() * numColours);
				rect.setFill(Colours[randomColour]);
				board.add(rect, row, col);
			}
		}
		board.setAlignment(Pos.CENTER);
		root.setCenter(board);
	
		// Set the combobox values
		cbGrid.getItems().addAll(6, 8, 10, 12, 14, 16, 18);
		cbColours.getItems().addAll(3, 4, 5, 6, 7, 8, 9, 10, 11);
		cbGrid.setValue(12);
		cbColours.setValue(6);
		// Place them inside a horizontal box and add to the borderpane
		HBox hbox = new HBox(20);
		hbox.setPadding(new Insets(0, 0, 30, 0));
		hbox.getChildren().addAll(new Label("Grid Size (N x N):"), cbGrid, new Label("Number of Colours:"), cbColours, newGame);
		hbox.setAlignment(Pos.CENTER);
		root.setBottom(hbox);
	}
	
	
	public void userClicked() {
		
	}
	
	
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			// Create a scene
			Scene scene = new Scene(root, ROOT_SIZE, ROOT_SIZE);			
			// Set the title, scene, and show the stage
			primaryStage.setTitle("Flood It");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			// Call the initialize method
			initialize();

			// Event handler
			board.setOnMousePressed(e -> {
				// Locate the selected rectangle
				Rectangle selectedRect = (Rectangle)e.getTarget();
				
				// TODO: If the selected rectangle has a different colour from the flood zone
				if(selectedRect.getFill() != Color.RED) {
					System.out.print("RED");
				}
				
			});
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
