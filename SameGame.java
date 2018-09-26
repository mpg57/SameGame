import javafx.application.Application;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ArrayList;


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

    private boolean isSameColor;
    private int count = 0;


    private GridPane gridPane;
    public Button[][] buttons;


    boolean[][] isEmpty = new boolean[12][12];

    Color[] colorArray = {Color.MAGENTA, Color.LIMEGREEN, Color.DEEPSKYBLUE, Color.BLUEVIOLET, Color.PEACHPUFF, Color.YELLOW, Color.ORANGERED, Color.INDIGO, Color.DARKTURQUOISE, Color.SPRINGGREEN};

    /**
     * Class constructor intializing default characteristics for GridPane
     */
    public SameGame() {
        this.rows = 12;
        this.columns = 12;
        this.numColors = 3;
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
        Button currentButt = buttons[x][y];
        checked.add(currentButt);
        Paint checkingColor = ((Circle)currentButt.getGraphic()).getFill();

        //check up
        if (y-1 >= 0){
            Button upButt = buttons[x][y-1];

            if (((Circle)upButt.getGraphic()).getFill().equals(checkingColor) && !checked.contains(upButt)) {
                checkNeighbors(x, y-1, checked);
            }
        }

        //check right
        if (x+1 < this.getColumns()){
            Button rightButt = buttons[x+1][y];

            if (((Circle)rightButt.getGraphic()).getFill().equals(checkingColor) && !checked.contains(rightButt)) {
                checkNeighbors(x+1, y, checked);
            }
        }


        //check down
        if (y+1 < this.getRows()) {
            Button downButt = buttons[x][y+1];

            if (((Circle)downButt.getGraphic()).getFill().equals(checkingColor) && !checked.contains(downButt)) {
                checkNeighbors(x, y+1, checked);
            }
        }

        //check left
        if (x-1 >= 0){
            Button leftButt = buttons[x-1][y];

            if (((Circle)leftButt.getGraphic()).getFill().equals(checkingColor) && !checked.contains(leftButt)) {
                checkNeighbors(x-1, y, checked);
            }
        }

        return checked;
    }

    /**
     * Moves all button colors above "empty" buttons down
     */
    public void shiftDown() {
        for (int i = 0; i < getColumns(); i++) {
            for (int j = 0; j < getRows(); j++) {
                Button butt = buttons[j][i];
                Paint color = ((Circle)butt.getGraphic()).getFill();

                if (((Circle)butt.getGraphic()).getFill().equals(Color.LIGHTGRAY)) {
                    for (int x = j; x > 0; x--) {
                        Circle newCircle = new Circle(15);
                        newCircle.setFill(((Circle)buttons[x-1][i].getGraphic()).getFill());
                        buttons[x][i].setGraphic(newCircle);
                    }
                    buttons[0][i].setGraphic(new Circle(15, Color.LIGHTGRAY));
                }
            }
        }
    }

    /**
     * Moves all button colors to the right of "empty" button columns to the left
     */
    public void shiftLeft() {
        for (int i = 0; i < getColumns(); i++) {
            boolean columnGray = true;
            for (int j = 0; j < getRows(); j++) {
                Button butt = buttons[j][i];

                if (!((Circle)butt.getGraphic()).getFill().equals(Color.LIGHTGRAY)) {
                    columnGray = false;
                }

                if (j == getRows() - 1 && columnGray) {
                    for (int x = i; x < getColumns(); x++) {
                        for (int y = 0; y < getRows(); y++) {
                            if (x == getColumns() - 1) {
                                buttons[y][x].setGraphic(new Circle(15, Color.LIGHTGRAY));
                                continue;
                            }

                            Circle newCircle = new Circle(15);
                            newCircle.setFill(((Circle) buttons[y][x + 1].getGraphic()).getFill());
                            buttons[y][x].setGraphic(newCircle);
                        }
                        System.out.println(columnEmpty(i));
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
    public boolean columnEmpty(int col) {
        for (int i = 0; i < getRows(); i++) {
            if (!((Circle)buttons[i][col].getGraphic()).getFill().equals(Color.LIGHTGRAY)) {
                return false;
            }
        }
        return true;
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
                Button butt = new Button();
                butt.setOnAction((e) -> {
                    Button b = (Button) e.getSource();
                    int x = GridPane.getRowIndex(b);
                    int y = GridPane.getColumnIndex(b);

                    ArrayList<Button> butts = checkNeighbors(x, y, new ArrayList<Button>());

                    if (butts.size() > 1) {
                        for (Button be : butts) {
                            Circle circle = new Circle(15);
                            circle.setFill(Color.LIGHTGRAY);
                            be.setGraphic(circle);
                        }
                    }

                    this.shiftDown();
                    this.shiftLeft();
                });

                buttons[i][j] = butt;
                buttons[i][j].setPrefSize(60, 60);
                Circle circle = new Circle(15);
                circle.setFill(colorArray[(int) (Math.random() * this.getNumColors())]);
                buttons[i][j].setGraphic(circle);
                gridPane.add(buttons[i][j], j, i);


            }
        }

        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    /**
     * Main method that starts a new same game instance
     * @param args The input commands of type String[]
     */
    public static void main(String[] args) {
        launch(args);
    }
 }
