package cs2410.assn8.view;

import cs2410.assn8.controller.ScoreBoardController;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Created by Mike on 4/26/2017.
 * @version 1.0
 */
public class CustomButton extends Button {
    private ScoreBoardController scoreBoard;
    private ImageView icon;
    private int pos = 0;
    private Image image;
    private int locX;
    private int locY;
    private boolean isBomb;
    private boolean marked = false;
    private int surroundBombs = 0;

    /**
     *
     * @param setAsBomb this sets whether the cell is a bomb
     * @param sb this allows the program to update the scoreboard when something changes in this class
     */
    public CustomButton(boolean setAsBomb, ScoreBoardController sb) {
                isBomb = setAsBomb;
                scoreBoard = sb;

        String cssFile = "cs2410/assn8/resource/CustomButtonStyle.css";

        getStylesheets().add(cssFile);

        setPrefWidth(30);
        setPrefHeight(30);

        setOnMouseClicked(this::mouseClicked);
    }

    /**
     *
     * @param e this method determines whether the right mouse clicked the cell, and responds accordingly.
     *          It will either set an image of a flag, a question mark, or clear the image from the cell
     */
    private void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseButton.SECONDARY) {
            scoreBoard.newGame.setDisable(true);
            scoreBoard.setRunning();
            if (pos == 1) {
                image = new Image("cs2410/assn8/Img/question.png");
                icon = new ImageView(image);
                icon.setFitHeight(11);
                icon.setFitWidth(11);
                setGraphic(icon);
                marked = true;
                pos++;
            }
            else if (pos == 2) {
                scoreBoard.addBomb();
                setGraphic(null);
                pos = 0;
                marked = false;
            }
            else {
                scoreBoard.removeBomb();
                image = new Image("cs2410/assn8/Img/flag.png");
                icon = new ImageView(image);
                icon.setFitHeight(11);
                icon.setFitWidth(11);
                setGraphic(icon);
                marked = true;
                pos++;
            }
        }
    }

    /**
     *
     * @return returns the column the cell is located in
     */
    public int getX() {
        return locX;
    }

    /**
     *
     * @param x the column the cell is in
     * @param y the row the cell is in
     */
    public void setXAndY(int x, int y) {
        locX = x;
        locY = y;
    }

    /**
     *
     * @return returns the cell's row
     */
    public int getY() {
        return locY;
    }

    /**
     *
     * @param numBombs the number of bombs surrounding the cell
     */
    public void setSurroundBombs(int numBombs) {
        surroundBombs = numBombs;
    }

    /**
     *
     * @return returns the number stored in surroundBombs
     */
    public int getSurroundBombs() {
        return surroundBombs;
    }

    /**
     *
     * @return returns whether or not the cell is a bomb
     */
    public boolean getIsBomb() {
        return isBomb;
    }

    /**
     *
     * @return returns whether or not the cell has been marked with a flag or questions mark
     */
    public boolean getIsMarked() {
        return marked;
    }

    /**
     * shows the bomb image on the cell. This happens when a bomb is clicked on the board and all bombs are revealed.
     */
    public void showBomb() {
        setText("");
        image = new Image("cs2410/assn8/Img/bomb.png");
        icon = new ImageView(image);
        icon.setFitHeight(8);
        icon.setFitWidth(8);
        setGraphic(icon);
    }
}
