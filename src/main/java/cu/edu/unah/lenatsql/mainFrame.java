package cu.edu.unah.lenatsql;

import cu.edu.unah.lenatsql.utils.Translator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.InputStream;

public class mainFrame extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        InputStream questionsStream = getClass().getResourceAsStream("datasets/questions.dat");
        InputStream answersStream = getClass().getResourceAsStream("datasets/answers.dat");
        if(!Translator.loadData(questionsStream, answersStream)) {
            JOptionPane.showMessageDialog(null, "Los dataset no tienen una estructura correcta.\nLa aplicación se cerrará de forma automática", "Error Crítico", JOptionPane.ERROR_MESSAGE);
////            System.exit(1);
        }
        questionsStream.close();
        answersStream.close();

        FXMLLoader fxmlLoader = new FXMLLoader(mainFrame.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("LeNatSQL");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("img/Logo.png")));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
