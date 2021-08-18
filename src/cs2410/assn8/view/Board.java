package cs2410.assn8.view;

import cs2410.assn8.controller.ScoreBoardController;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Mike on 4/26/2017.
 * @version 1.0
 */
public class Board extends GridPane {
    ScoreBoardController scoreBoard;
    CustomButton custom;
    ArrayList<CustomButton> list = new ArrayList<>();
    private int blanksLeft; // keeps track of how many blank spaces are left on the board.

    /**
     * constructor for the class. It sets up 100 bombs and 300 blank cells, and adds them to itself, as it
     * extends Gridpane
     */
    public Board(ScoreBoardController sb) {
        scoreBoard = sb;
        for (int i = 0; i < 100; i++) {
            list.add(new CustomButton(true, sb));
        }

        for (int i = 0; i < 300; i++) {
            list.add(new CustomButton(false, sb));
        }

        Collections.shuffle(list);
        blanksLeft = 300;

        int pos = 0;
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                custom = list.get(pos);
                wasPressed();
                custom.setXAndY(i, j);
                add(custom, i, j);
                pos++;
            }
        }

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                int totalBombs = 0;
                CustomButton temp = (CustomButton)getNodeAt(i, j);
                for (int k = (i - 1); k < (i + 2); k++) {
                    for (int l = (j - 1); l < (j + 2); l++) {
                        CustomButton surround = (CustomButton) getNodeAt(k, l);
                        if (!(surround instanceof CustomButton)) {

                        }
                        else if (surround.getIsBomb()) {
                            totalBombs++;
                        }
                    }
                }
                temp.setSurroundBombs(totalBombs);
            }
        }
    }

    /**
     *
     * @param x column of the wanted node
     * @param y row of the wanted node
     * @return returns a node at a certain x and y position
     */
    private Node getNodeAt(int x, int y) {
        for (Node node : getChildren()) {
            if (GridPane.getColumnIndex(node) == x && GridPane.getRowIndex(node) == y) {
                return node;
            }
        }
        return null;
    }

    /**
     * this method handles what happens when the left mouse button is pressed, which is to uncover a cell, a
     * cell and its neighbors, or the entire board, depending on what the cell holds.
     */
    public void wasPressed() {
        custom.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                scoreBoard.newGame.setDisable(true);
                scoreBoard.setRunning();
                CustomButton temp = (CustomButton)e.getSource();
                int x = temp.getX();
                int y = temp.getY();

                if (temp.getIsMarked()) {

                }

                else if (temp.getIsBomb()) {
                    loseGame();
                }

                else {
                    emptySpace(x, y, temp);
                }
                if (blanksLeft < 1) {
                    win();
                }
            }
        });
    }

    /**
     *
     * @param x the cell's column
     * @param y the cell's row
     * @param temp the cell itself
     *             this method determines what happens when a blank cell is clicked
     */
    public void emptySpace(int x, int y, CustomButton temp) {
        if (!(temp instanceof CustomButton)) {

        }
        else if (x > 19 || y > 19) {

        }
        else if (x < 0 || y < 0) {

        }
        else if (temp.getSurroundBombs() > 0) {
            temp.setText("" + temp.getSurroundBombs());
            blanksLeft--;
            temp.setDisable(true);
        }
        else {
            if (!temp.isDisable()) {
                temp.setText("");
                blanksLeft--;
                temp.setDisable(true);
                for (int i = (x - 1); i < (x + 2); i++) {
                    for (int j = (y - 1); j < (y + 2); j++) {
                        try {
                            emptySpace(i, j, ((CustomButton) getChildren().get((i * 20) + j)));
                        } catch (ArrayIndexOutOfBoundsException ex) {
                            continue;
                        }
                        catch (IndexOutOfBoundsException ex) {
                            continue;
                        }
                    }
                }
            }

        }
    }

    /**
     * this is a method that is called when the player successfully uncovers all blank cells without clicking
     * on a mine
     */
    public void win() {
        scoreBoard.stop();
        scoreBoard.newGame.setDisable(false);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("You won in " + scoreBoard.getSeconds() + " seconds!");
        alert.setHeaderText("");
        alert.show();
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                CustomButton temp = (CustomButton) getChildren().get((i * 20) + j);
                if (temp.getIsBomb()) {
                    temp.setStyle("-fx-background-color: green; -fx-border-color: black; -fx-background-radius: 0; -fx-opacity: 1");
                }
            }
        }
    }

    /**
     * this is the method called if a player clicks on a mine
     */
    public void loseGame() {
        scoreBoard.stop();
        scoreBoard.newGame.setDisable(false);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("You lost!");
        alert.setHeaderText("");
        alert.show();
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                CustomButton temp = (CustomButton)getChildren().get((i * 20) + j);
                if (temp.getIsBomb()) {
                    if (temp.getIsMarked()) {
                        temp.setStyle("-fx-background-color: green; -fx-border-color: black; -fx-background-radius: 0; -fx-opacity: 1");
                    }
                    else {
                        temp.setStyle("-fx-background-color: red; -fx-border-color: black; -fx-background-radius: 0; -fx-opacity: 1");
                    }
                    temp.showBomb();
                }
                else {
                    if (temp.getIsMarked()) {
                        if (temp.getSurroundBombs() == 0) {
                            temp.setText("");
                        }
                        else {
                            temp.setText("" + temp.getSurroundBombs());
                        }
                        temp.setDisable(true);
                        temp.setGraphic(null);
                        temp.setStyle("-fx-background-color: yellow; -fx-border-color: black; -fx-background-radius: 0; -fx-opacity: 1");
                    }
                    else {
                        if (temp.getSurroundBombs() == 0) {
                            temp.setText("");
                        }
                        else {
                            temp.setText("" + temp.getSurroundBombs());
                        }
                        temp.setDisable(true);
                    }
                }
            }
        }
    }
    }