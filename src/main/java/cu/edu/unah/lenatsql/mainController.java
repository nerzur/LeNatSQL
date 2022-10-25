package cu.edu.unah.lenatsql;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import cu.edu.unah.lenatsql.utils.AlertMaker;
import cu.edu.unah.lenatsql.utils.Translator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

public class mainController {

    @FXML
    private StackPane rootPane;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private JFXTextArea input;

    @FXML
    protected void translateButtonClick() {
        Translator translator = new Translator();
        JFXButton btn = new JFXButton("Cerrar");
        AlertMaker.showMaterialDialog(rootPane, rootAnchorPane, Arrays.asList(btn),
                "Sentencia SQL traducida (el texto ha sido copiado):",
                        translator.translateNL2SQL(input.getText()));
    }

    @FXML
    protected void helpClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        WebView webView = new WebView();
        VBox vBox = new VBox(webView);
        Scene scene = new Scene(vBox);
        URL url = this.getClass().getResource("help.html");
        webView.getEngine().load(url.toString());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("LeNatSQL - Ayuda");
        stage.setResizable(false);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("img/Logo.png")));
        stage.showAndWait();
    }
}
