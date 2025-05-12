package mainfolder.p2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class HelloApplication extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        URL fxmlUrl = HelloApplication.class.getResource("/mainfolder/p2/hello-view.fxml");
        System.out.println("FXML resource URL: " + fxmlUrl);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/mainfolder/p2/hello-view.fxml"));
        Parent root = fxmlLoader.load();
        scene = new Scene(root, 800, 600);
        stage.setTitle("Mantenimiento de Estudiantes");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {

        launch();

    }

    private static Parent loadFXML(String fxml) throws IOException {

        return new FXMLLoader(
                HelloApplication.class.getResource("/mainfolder/p2/" + fxml + ".fxml")).load();

    }

    static void setRoot(String fxml) throws IOException {

        scene.setRoot(loadFXML(fxml));

    }

}