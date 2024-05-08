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
	public GridPane board; // Game board to hold all the squares
	public Text moves = new Text("MOVES ");
	public Text movesLeft = new Text(); // To dynamically display the number of moves left
	public int numMovesLeft = 0; // To calculate the number of moves left
	public int numColours = 3; // Number of colours chosen for the game
	public int n = 4; // To track the n x n grid size
	public Rectangle[][] rectangles; // To hold the rectangles in the grid
	public int[][] floodZone; // To hold the boolean-like values of the floodZone
	public ComboBox<Integer> cbGrid = new ComboBox<Integer>();
	public ComboBox<Integer> cbColours = new ComboBox<Integer>();
	public Button newGame = new Button("New Game");
	public Rectangle rect;
	public Paint floodColour;
	public Paint[] Colours = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.MAGENTA, 
			  Color.CYAN, Color.BLACK, Color.GRAY, Color.PINK, Color.WHITE};
	Scene scene = new Scene(root, ROOT_SIZE, ROOT_SIZE);	 // TESTING
	Rectangle selectedRect; // TESTING
	public boolean[][] visited = new boolean[n][n]; // To track what cells have been visiting when updating the floodZone
	
	
	// METHOD: method for initializing the game board and display
	public void initialize() {
		// Auto-populate the moves and add to the root
		numMovesLeft = (int)Math.floor(25 * ((n + n) * numColours) / ((14 + 14) * 6));
		movesLeft.setText(String.valueOf(numMovesLeft));
		
		// Fill the gridpane game board randomly with coloured rectangles
		board = new GridPane();
		board.setGridLinesVisible(true);
		rectangles = new Rectangle[n][n];
		for(int row = 0; row < rectangles.length; row++) {
			for(int col = 0; col < rectangles[row].length; col++) {
				// Calculate a random number and set the new rectangle to that numbered colour
				int randomColour = (int)Math.floor(Math.random() * numColours);
				rect = new Rectangle(30, 30, Colours[randomColour]);
				// Set the rectangle grid map to this rectangle
				rectangles[row][col] = rect;
				// Add the rectangle to the game board
				board.add(rect, col, row);
			}
		}
		
		// Align and add the board to the root
		board.setAlignment(Pos.CENTER);
		root.setCenter(board);
	
		// Set the combobox values
		cbGrid.setValue(n);
		cbColours.setValue(numColours);
		
		// Locate and set the flood zone colour of the board
		floodColour = rectangles[0][0].getFill();

		// Initialize the floodZone to all false (0)
		floodZone = new int[n][n];
		for(int row = 0; row < floodZone.length; row++) {
			for(int col = 0; col < floodZone[row].length; col++) {
				floodZone[row][col] = 0;
			}
		}
		
		// Set the floodZone[0][0] square to true (1) and update any other adjacent similar-coloured squares
		updateFloodZone(0, 0);
		
		// Print out the flood zone (testing)
		getFloodZone();
		System.out.println();
	}
	
	
	// TESTING: prints out floodZone
	public void getFloodZone() {
		for(int row = 0; row < floodZone.length; row++) {
			for(int col = 0; col < floodZone[row].length; col++) {
				System.out.print(floodZone[row][col] + " ");
			}
			System.out.println();
		}
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
	
	
	// METHOD: for changing the colour of the flood zone
	public void changeFloodColour() {
		for(int row = 0; row < floodZone.length; row++) {
			for(int col = 0; col < floodZone[row].length; col++) {
				if(floodZone[row][col] == 1) {
					rectangles[row][col].setFill(floodColour);
				}
			}
		}
	}

	
	// METHOD: recursive method to update the floodZone matrix values
	public void updateFloodZone(int row, int col) {		
		// Base Case -- if the cell is out of boundaries, the colours don't match, or the cell has been visited already
		if(!isInBoundaries(row, col) || floodColour != rectangles[row][col].getFill() || visited[row][col]) {
			return;
		}
		
		// Process the current cell		
		visited[row][col] = true;
		floodZone[row][col] = 1;
		
		// Recurse
		updateFloodZone(row - 1, col); // ABOVE
		updateFloodZone(row + 1, col); // BELOW
		updateFloodZone(row, col - 1); // LEFT
		updateFloodZone(row, col + 1); // RIGHT
	}
	
	
	// METHOD: method to determine if we have a winner or not
	public boolean isWinner() {
		// Loop through the rows and cols to see if there are rectangles left not in the floodZone
		for(int row = 0; row < floodZone.length; row++) {
			for(int col = 0; col < floodZone[row].length; col++) {
				if(floodZone[row][col] == 0) {
					return false;
				}
			}
		}
		return true;
	}
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			// Create a scene
			primaryStage.setTitle("Flood It");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			// Set combobox values and place inside a hbox and add to the borderpane
			cbGrid.getItems().addAll(4, 6, 8, 10, 12, 14, 16, 18);
			cbColours.getItems().addAll(3, 4, 5, 6, 7, 8, 9, 10, 11);
			HBox hbox = new HBox(20);
			hbox.setPadding(new Insets(0, 0, 30, 0));
			hbox.getChildren().addAll(new Label("Grid Size (N x N):"), cbGrid, new Label("Number of Colours:"), cbColours, newGame);
			hbox.setAlignment(Pos.CENTER);
			root.setBottom(hbox);
			
			// Add moves to the root
			moves.setFont(Font.font("Arial", FontWeight.BOLD, 24.0));
			movesLeft.setFont(Font.font("Arial", FontWeight.BOLD, 24.0));
			HBox hbTitle = new HBox();
			hbTitle.getChildren().addAll(moves, movesLeft);
			hbTitle.setAlignment(Pos.CENTER);
			root.setTop(hbTitle);
			
			// Call the initialize method
			initialize();

			// Event handler for game board
			board.setOnMousePressed(e -> {
				// Locate the selected rectangle
				selectedRect = (Rectangle)e.getTarget();
				int selectedRow = 0;
				int selectedCol = 0;
				
				// Locate the selected rectangle in the rectangles array
				for(int row = 0; row < rectangles.length; row++) {
					for(int col = 0; col < rectangles[row].length; col++) {
						if(selectedRect == rectangles[row][col]) {
							selectedRow = row;
							selectedCol = col;
						}
					}	
				}
				
				
				// If the selected rectangle has a different colour from the flood zone
				if(selectedRect.getFill() != floodColour && isFloodAdjacent(selectedRow, selectedCol)) {
					// Set the flood colour to the new colour
					floodColour = selectedRect.getFill();
					changeFloodColour();
					
					// Reset the visited array
					visited = new boolean[n][n];
					
					// Set the floodZone
					updateFloodZone(0, 0);
					
					getFloodZone();
					System.out.println();
					
					// Decrease moves output
					numMovesLeft--;
					
					// TODO: If no more moves left
					if(numMovesLeft == 0 && !isWinner()) {
						moves.setText("");
						movesLeft.setText("GAME OVER");	
					} else {
						if(numMovesLeft == 0 && isWinner()) {
							moves.setText("");
							movesLeft.setText("WINNER");	
						} else {
							movesLeft.setText(String.valueOf(numMovesLeft));
						}
					}
				}
			});
			
			// Event handler for grid dropdown
			newGame.setOnAction(e -> {
				// Set the grid size from user input dropdown selection
				n = cbGrid.getValue();
				// Set the number of colours from user input dropdown selection
				numColours = cbColours.getValue();
				// Call the initialize method
				initialize();
				start(primaryStage);
			});
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
