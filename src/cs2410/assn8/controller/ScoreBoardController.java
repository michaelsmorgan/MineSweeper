package cs2410.assn8.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Mike on 4/29/2017.
 * @version 1.0
 */
public class ScoreBoardController {
    @FXML
    public Button newGame = new Button();
    private Timer timer;
    private boolean isRunning = false;
    private int seconds = 0;

    private int bombs = 100;

    @FXML
    HBox hBox;
    @FXML
    Label bombsLeft = new Label();
    @FXML
    Label time = new Label();

    /**
     * this initializes the above information in the associated FXML
     */
    @FXML
    public void initialize() {
        bombsLeft.setText("" + 100);
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if(isRunning) {
                            updateTime();
                        }
                    }
                });

            }
        }, 0, 1000);

    }

    /**
     * updates the time the game has run for
     */
    public void updateTime() {
        seconds++;
        time.setText("" + seconds);
    }

    /**
     * stops the clock
     */
    public void stop() {
        isRunning = false;
    }

    /**
     * closes the timer
     */
    public void close() {
        timer.cancel();
    }

    /**
     * adds a bomb to the Bombs left tally
     */
    public void addBomb() {
        bombs++;
        bombsLeft.setText("" + bombs);
    }

    /**
     * removes a bomb from the Bombs left tally
     */
    public void removeBomb() {
        bombs--;
        bombsLeft.setText("" + bombs);
    }

    /**
     * sets the timer to run
     */
    public void setRunning() {
        isRunning = true;
    }

    /**
     * returns the seconds the game has run for
     * @return
     */
    public int getSeconds() {
        return seconds;
    }

    /**
     * resets the game
     *
     */
    public void reset() {
        isRunning = false;
        seconds = 0;
        time.setText("" + seconds);
        time.setDisable(false);
        bombs = 100;
        bombsLeft.setText("100");
    }

}
