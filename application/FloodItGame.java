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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class FloodItGame extends Application {
	// Declare variables
	public final int ROOT_SIZE = 700; // Size of the Java window
	public BorderPane root = new BorderPane();
	public GridPane board = new GridPane(); // Game board to hold all the squares
	public Text moves = new Text("MOVES ");
	public Text movesLeft = new Text(); // To dynamically display the number of moves left
	public int numMovesLeft = 0; // To calculate the number of moves left
	public int numColours = 4; // Number of colours chosen for the game
	public int n = 6; // To track the n x n grid size
	public Rectangle[][] grid; // To hold the rectangles in the grid
	public ComboBox<Integer> cbGrid = new ComboBox<Integer>();
	public ComboBox<Integer> cbColours = new ComboBox<Integer>();
	public Button newGame = new Button("New Game");
	public Rectangle rect;
	public Paint floodColour;
	public Paint[] Colours = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.MAGENTA, 
			  Color.CYAN, Color.BLACK, Color.GRAY, Color.PINK, Color.WHITE};
	
	public int[][] floodZone = new int[n][n]; // TESTING
	
	
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
		
		// Fill the gridpane game board randomly with coloured rectangles
		board.setGridLinesVisible(true);
		grid = new Rectangle[n][n];
		for(int row = 0; row < grid.length; row++) {
			for(int col = 0; col < grid.length; col++) {
				// Calculate a random number and set the new rectangle to that numbered colour
				int randomColour = (int)Math.floor(Math.random() * numColours);
				rect = new Rectangle(30, 30, Colours[randomColour]);
				// Set the rectangle grid map to this rectangle
				grid[row][col] = rect;
				// Add the rectangle to the game board
				board.add(rect, row, col);
			}
		}
		// Align and add the board to the root
		board.setAlignment(Pos.CENTER);
		root.setCenter(board);
	
		// Set the combobox values
		cbGrid.getItems().addAll(6, 8, 10, 12, 14, 16, 18);
		cbColours.getItems().addAll(3, 4, 5, 6, 7, 8, 9, 10, 11);
		cbGrid.setValue(n);
		cbColours.setValue(numColours);
		// Place them inside a horizontal box and add to the borderpane
		HBox hbox = new HBox(20);
		hbox.setPadding(new Insets(0, 0, 30, 0));
		hbox.getChildren().addAll(new Label("Grid Size (N x N):"), cbGrid, new Label("Number of Colours:"), cbColours, newGame);
		hbox.setAlignment(Pos.CENTER);
		root.setBottom(hbox);
		
		// Locate and set the flood zone colour of the board
		floodColour = grid[0][0].getFill();
		
		// Initialize the floodZone to all false
		for(int row = 0; row < floodZone.length; row++) {
			for(int col = 0; col < floodZone[row].length; col++) {
				floodZone[row][col] = 0;
			}
		}
		
		// Set the floodZone[0][0] square to true
		floodZone[0][0] = 1;
		
		// Locate any other adjacent similar-coloured squares
		setFloodZone();
		
		getFloodZone();
		
		
		
		
		
		
		
		
//		System.out.println("RED " + new Rectangle(10, 10, Color.RED).getFill());
//		System.out.println("ORANGE " + new Rectangle(10, 10, Color.ORANGE).getFill());
//		System.out.println("YELLOW " + new Rectangle(10, 10, Color.YELLOW).getFill());
//		System.out.println("GREEN" + new Rectangle(10, 10, Color.GREEN).getFill());
//		System.out.println("BLUE " + new Rectangle(10, 10, Color.BLUE).getFill());
//		System.out.println("MAGENTA " + new Rectangle(10, 10, Color.MAGENTA).getFill());
//		System.out.println("CYAN " + new Rectangle(10, 10, Color.CYAN).getFill());
//		System.out.println("BLACK " + new Rectangle(10, 10, Color.BLACK).getFill());
//		System.out.println("GRAY " + new Rectangle(10, 10, Color.GRAY).getFill());
//		System.out.println("PINK " + new Rectangle(10, 10, Color.PINK).getFill());
//		System.out.println("WHITE " + new Rectangle(10, 10, Color.WHITE).getFill());
//		System.out.println(floodZone.getFill() == Color.RED);
//		System.out.println(floodZone.getFill() == Color.YELLOW);
//		System.out.println(floodZone.getFill() == Color.BLUE);
//		System.out.println(floodZone.getFill() == Color.GREEN);
//		System.out.println(floodZone.getFill() == Color.MAGENTA);
//		System.out.println(floodZone.getFill() == Color.ORANGE);
//		System.out.println(floodZone.getFill() == Color.BLACK);
//		System.out.println(floodZone.getFill() == Color.WHITE);
	}
	
	
	
	// METHOD: method to check if a square that is clicked on is adjacent to the flood zone
	public boolean isFloodAdjacent(int row, int col) {
		// Above
		if(isInBoundaries(row - 1, col) && floodZone[row - 1][col] == 1) {
			return true;
		}
		// Below
		if(isInBoundaries(row + 1, col) && floodZone[row + 1][col] == 1) {
			return true;
		}
		// Left
		if(isInBoundaries(row, col - 1) && floodZone[row][col - 1] == 1) {
			return true;
		}
		// Right
		if(isInBoundaries(row, col + 1) && floodZone[row][col + 1] == 1) {
			return true;
		}
		// Otherwise, false
		return false;
	}
	
	
	// METHOD: method to check if the rectangle selected is within boundaries of the board
	public boolean isInBoundaries(int row, int col) {
		if (row < 0 || col < 0 || row > n - 1 || col > n - 1) {
            return false;
        }
        return true;
	}
	
	
	// METHOD: method to set the squares of the flood zone
	public void setFloodZone() {
		for(int row = 0; row < floodZone.length; row++) {
			for(int col = 0; col < floodZone[row].length; col++) {
				// If the fill colours match, and the rectangle is adjacent to the flood zone
				if(grid[row][col].getFill() == floodColour && isFloodAdjacent(row, col)) {
					// Set floodZone to include this new value
					floodZone[col][row] = 1; // TODO: Big fix: WHY are the row / col backwards?
				}
			}
		}
	}
	
	
	// METHOD: displays the flood zone
	public void getFloodZone() {
		for(int row = 0; row < floodZone.length; row++) {
			for(int col = 0; col < floodZone[row].length; col++) {
				System.out.print(floodZone[row][col] + " ");
			}
			System.out.println();
		}
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
//				if(selectedRect.getFill() != Color.RED) {
//					System.out.print(selectedRect.getFill());
//				}
			});
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
