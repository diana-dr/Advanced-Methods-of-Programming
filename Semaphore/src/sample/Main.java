package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader1 = new FXMLLoader();
        loader1.setLocation(getClass().getResource("runController.fxml"));
        Parent mainWindow = loader1.load();

        runController mainWindowController = loader1.getController();

        FXMLLoader loader2 = new FXMLLoader();
        loader2.setLocation(getClass().getResource("selectController.fxml"));
        Parent secondaryWindow = loader2.load();

        selectController selectWindowController = loader2.getController();
        selectWindowController.setMainController(mainWindowController);

        primaryStage.setTitle("Main Window");
        primaryStage.setScene(new Scene(mainWindow, 920, 450));
        primaryStage.show();

        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Select Program Window");
        secondaryStage.setScene(new Scene(secondaryWindow, 750, 300));
        secondaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
