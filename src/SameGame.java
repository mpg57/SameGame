import javafx.application.Application;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.paint.ImagePattern;
import javafx.scene.media.AudioClip;
import javafx.scene.image.Image;
import static javafx.scene.media.AudioClip.INDEFINITE;

/**
 * SameGame.java - a class for playing Same Game
 * @author  Mia Gorczyca
 * @version 1.0
 */

/**
 * Retrieve the value of color.
 * @return A String data type.
 */


public class SameGame extends Application {

    private int rows;
    private int columns;
    private int numColors;
    private int radius;
    private GridPane gridPane;
    public Button[][] buttons;


    Color[] colorArray = {Color.MAGENTA, Color.LIMEGREEN, Color.DEEPSKYBLUE, Color.BLUEVIOLET, Color.PEACHPUFF, Color.YELLOW, Color.ORANGERED, Color.INDIGO, Color.DARKTURQUOISE, Color.SPRINGGREEN};
    Image[] imageArray = {new Image("Murdoc.png"), new Image("Noodle.png"), new Image("Russel.png"), new Image("2D.png")};

    /**
     * Class constructor intializing default characteristics for GridPane
     */
    public SameGame() {
        this.rows = 15;
        this.columns = 15;
        this.numColors = 4;
        this.radius = 25;
    }

    /**
     * Class constructor specifying input characteristics for GridPane
     */
    public SameGame(int rows, int columns, int numColors) {
        this.rows = rows;
        this.columns = columns;
        this.numColors = numColors;

    }

    /**
     * Retrieve the number of rows.
     * @return An int data type.
     */
    public int getRows() {
        return rows;
    }

    /**
     * Retrieve the number of columns.
     * @return An int data type.
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Retrieve the number of colors.
     * @return An int data type.
     */
    public int getNumColors() {
        return numColors;
    }

    /**
     * Set the number of rows.
     * @param rows A variable of type int.
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     * Set the number of columns.
     * @param columns A variable of type int.
     */
    public void setColumns(int columns) {
        this.columns = columns;
    }

    /**
     * Set the number of colors.
     * @param numColors A variable of type int.
     */
    public void setNumColors(int numColors) {
        this.numColors = numColors;
    }

    /**
     * Checks circles on buttons surrounding clicked button
     * @param x A variable of type int
     * @param y A variable of type int
     * @param checked An ArrayList of type Button
     * @returns An ArrayList of type Button
     */
    public ArrayList<Button> checkNeighbors(int x, int y, ArrayList<Button> checked) {
        Button currentButton = buttons[x][y];
        checked.add(currentButton);
        Paint checkingColor = ((Circle)currentButton.getGraphic()).getStroke();

        //check up
        if (y-1 >= 0){
            Button upButton = buttons[x][y-1];

            if (((Circle)upButton.getGraphic()).getStroke().equals(checkingColor) && !checked.contains(upButton)) {
                checkNeighbors(x, y-1, checked);
            }
        }

        //check right
        if (x+1 < this.getColumns()){
            Button rightButton = buttons[x+1][y];

            if (((Circle)rightButton.getGraphic()).getStroke().equals(checkingColor) && !checked.contains(rightButton)) {
                checkNeighbors(x+1, y, checked);
            }
        }


        //check down
        if (y+1 < this.getRows()) {
            Button downButton = buttons[x][y+1];

            if (((Circle)downButton.getGraphic()).getStroke().equals(checkingColor) && !checked.contains(downButton)) {
                checkNeighbors(x,y+1, checked);
            }
        }

        //check left
        if (x-1 >= 0){
            Button leftButton = buttons[x-1][y];

            if (((Circle)leftButton.getGraphic()).getStroke().equals(checkingColor) && !checked.contains(leftButton)) {
                checkNeighbors(x-1, y, checked);
            }
        }

        return checked;
    }

    /**
     * Moves all button colors above "empty" buttons down
     */
    public void shiftDown() {
        for (int col = 0; col < getColumns(); col++) {
            for (int row = getRows() - 2; row >= 0; row--) {
                if (ButtonBelowIsEmpty(row, col)) {
                    buttons[row + 1][col].setGraphic(buttons[row][col].getGraphic());

                    Circle grayCircle = new Circle(radius, Color.LIGHTGRAY);
                    grayCircle.setStroke(Color.LIGHTGRAY);
                    buttons[row][col].setGraphic(grayCircle);


                }
            }
        }
    }

    /**
     * Moves all button colors to the right of "empty" button columns to the left
     */
    public void shiftLeft() {
        for (int i = 0; i < getColumns() - 1; i++) {
            if (columnEmpty(i)) {
                // For every column right of an empty column
                for (int j = i + 1; j < getColumns(); j++) {
                    // For every row in the column
                    for (int row = 0; row < getRows(); row++) {
                        buttons[row][j-1].setGraphic(buttons[row][j].getGraphic());

                        Circle grayCircle = new Circle(radius, Color.LIGHTGRAY);
                        grayCircle.setStroke(Color.LIGHTGRAY);
                        buttons[row][j].setGraphic(grayCircle);

                    }
                }
            }
        }

    }

    /**
     * Checks if column is empty
     * @param col A variable of type int
     * @returns A boolean variable
     */
    public boolean ButtonBelowIsEmpty(int row, int col) {
        if (! ((Circle)buttons[row+1][col].getGraphic()).getFill().equals(Color.LIGHTGRAY)) {
            return false;
        }
        return true;
    }

    public boolean columnEmpty(int col) {
        for (int i = 0; i < getRows(); i++) {
            if (!((Circle)buttons[i][col].getGraphic()).getFill().equals(Color.LIGHTGRAY)) {
                return false;
            }
        }
        return true;
    }
    /**
     * Starts audio
     */
    public void startMusic(String musicFileName) {
        int s = INDEFINITE;
        AudioClip audio = new AudioClip(getClass().getResource(musicFileName).toExternalForm());
        audio.setVolume(0.5f);
        audio.setCycleCount(s);
        audio.play();
    }

    /**
     * Checks if column is empty
     * @param primaryStage A variable of type Stage
     */
    @Override
    public void start(Stage primaryStage) {

        gridPane = new GridPane();

        buttons = new Button[this.getRows()][this.getColumns()];

        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                Button button = new Button();
                button.setOnAction((e) -> {
                    Button b = (Button) e.getSource();
                    int x = GridPane.getRowIndex(b);
                    int y = GridPane.getColumnIndex(b);

                    ArrayList<Button> buttons = checkNeighbors(x, y, new ArrayList<Button>());

                    if (buttons.size() > 1) {
                        for (Button currentb : buttons) {
                            Circle circle = new Circle(radius);
                            circle.setFill(Color.LIGHTGRAY);
                            circle.setStroke(Color.LIGHTGRAY);
                            currentb.setGraphic(circle);
                        }
                    }
                    for (int size = 0; size < buttons.size(); size++) {
                        this.shiftDown();
                        this.shiftLeft();
                    }



                });

                buttons[i][j] = button;
                buttons[i][j].setPrefSize(60, 60);
                Circle circle = new Circle(radius);
                circle.setStroke(colorArray[(int) (Math.random() * this.getNumColors())]);
                // Set image
                Image settingImage = imageArray[Arrays.asList(colorArray).indexOf(circle.getStroke())];
                circle.setFill(new ImagePattern(settingImage));

                buttons[i][j].setGraphic(circle);
                gridPane.add(buttons[i][j], j, i);


            }
        }


        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.show();
        startMusic("FeelGoodInc.wav");
    }


    /**
     * Main method that starts a new same game instance
     * @param args The input commands of type String[]
     */
    public static void main(String[] args) {
        launch(args);
    }
}