package cs2410.assn8.view;

import cs2410.assn8.controller.ScoreBoardController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * the main class, where everything is run from
 */
public class Main extends Application {
    /**
     * each of the following 6 items is an instance used to access the other classes
     */
    FXMLLoader loader;
    Parent buttonBar;
    ScoreBoardController sbController;
    Board board;
    Scene scene;
    BorderPane borderPane;

    @Override
    public void start(Stage primaryStage) throws Exception{
        /**
         * here is where the program loads in the FXML and the controller class for the ScoreBoard
         */
        loader = new FXMLLoader(getClass().getResource("/cs2410/assn8/controller/ScoreBoard.fxml"));
        buttonBar = loader.load();
        sbController = (ScoreBoardController) loader.getController();

        /**
         * below the program creates a new board that holds the cells, and adds it to a borderpane to be displayed in
         */
        board = new Board(sbController);
        borderPane = new BorderPane();
        borderPane.setTop(buttonBar);
        borderPane.setCenter(board);

        scene = new Scene(borderPane);

        primaryStage.setWidth(500);
        primaryStage.setHeight(600);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(scene);


        primaryStage.sizeToScene();

        primaryStage.show();

        /**
         * the following method is used to start a new game once a player has won or lost
         */
        sbController.newGame.setOnAction(e -> {
            sbController.reset();

            board = new Board(sbController);
            borderPane.setCenter(board);
        });

        /**
         * below stops everything that was running when the program is closed out of
         */
        primaryStage.setOnCloseRequest( event -> {
            sbController.close();
        });

    }

    public static void main(String[] args) {
        launch(args);
    }
}


