package co.com.jorge;

import co.com.jorge.model.Level;
import co.com.jorge.model.Sheet;
import co.com.jorge.model.Table;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class GUI extends Application {
    private int rows = 4, columns = 3;

    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Primera vista
        firstWindow(primaryStage);
    }

    @Override
    public void stop() throws Exception {
        executorService.shutdown();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    // Primera ventana
    private void firstWindow(Stage primaryStage){
        VBox paneOne = new VBox();
        paneOne.setPadding(new Insets(10));

        ImageView logo = new ImageView(new Image(getClass().getResourceAsStream("/img/LogoDB.png")));

        Label label = new Label("Seleccione un Nivel");
        label.setFont(Font.font("Britannic Bold" ,25));
        label.setTextFill(Paint.valueOf("BLACK"));

        ComboBox<String> selectLevel = new ComboBox<>();

        selectLevel.setValue("Nivel 1");
        ObservableList<String> listLevel = FXCollections.observableArrayList("Nivel 1", "Nivel 2", "Nivel 3");
        selectLevel.setItems(listLevel);

        selectLevel.setStyle("-fx-font: 20px \"Britannic Bold\";");

        Button play = new Button("Jugar");
        play.setMinSize(100, 40);
        play.setFont(Font.font("Britannic Bold", 20));
        play.setStyle("-fx-background-color: #0610B2;-fx-text-fill: #FFFFFF; -fx-background-radius: 5em;" +
                "-fx-effect: dropshadow( gaussian, rgba(1,20,98,0.85), 10, 0.5, 0.0, 0.0 )");

        StackPane paneTwo = new StackPane();
        paneTwo.setPadding(new Insets(0, 10,10,10));
        paneTwo.setMinSize(400, 150);

        paneTwo.setAlignment(label, Pos.TOP_CENTER);
        paneTwo.setAlignment(play, Pos.BOTTOM_CENTER);
        paneTwo.getChildren().addAll(label,selectLevel,play);

        paneOne.getChildren().addAll(logo, paneTwo);

        play.addEventHandler(MouseEvent.MOUSE_CLICKED, handleOnClickPlay(selectLevel, primaryStage));

        Scene scene = new Scene(paneOne);
        primaryStage.setTitle("Emparejados DB");
        primaryStage.setScene(scene);

        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/icon.png")));

        primaryStage.show();
    }

    // Segunda ventana
    private void secondWindow(Stage primaryStage){
        TilePane paneTwo = new TilePane();
        paneTwo.setAlignment(Pos.CENTER);
        paneTwo.setPadding(new Insets(12, 12, 12, 12));
        paneTwo.setHgap(5);
        paneTwo.setVgap(5);
        if(columns == rows){
            paneTwo.setMaxWidth(columns * 160); // Nivel 2
        }else {
            paneTwo.setMaxWidth(columns * 200); // Nivel 1 y 3
        }

        Table table = new Table(rows, columns);

        table.getSheetList().forEach(sheet -> paneTwo.getChildren().add(sheet.getImage()));

        paneTwo.addEventHandler(MouseEvent.MOUSE_CLICKED, handlerOnClickPane(table, primaryStage));

        Scene scene = new Scene(paneTwo);
        primaryStage.setTitle("Emparejados DB");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    // Ventana Emergente
    private void modalFinalPlay(){
        Stage stage = new Stage();
        FlowPane pane = new FlowPane(Orientation.VERTICAL);
        pane.setPadding(new Insets(11, 12, 13, 14));
        pane.setHgap(5);
        pane.setVgap(5);
        pane.getChildren().addAll(new Label("Desea Continuar Jugando"));
        pane.getChildren().addAll(new Button("Continuar"));
        pane.getChildren().addAll(new Button("Salir"));
        Scene scene = new Scene(pane, 200, 200);
        stage.setTitle("FlowPane");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    // Evento del botón Jugar
    private EventHandler<MouseEvent> handleOnClickPlay(ComboBox<String> selectLevel, Stage primaryStage) {
        return (MouseEvent event) -> {
            switch (selectLevel.getValue()) {
                case "Nivel 1":
                    rows = Level.LEVEL1.getRows();
                    columns = Level.LEVEL1.getColumns();
                    break;
                case "Nivel 2":
                    rows = Level.LEVEL2.getRows();
                    columns = Level.LEVEL2.getColumns();
                    break;
                case "Nivel 3":
                    rows = Level.LEVEL3.getRows();
                    columns = Level.LEVEL3.getColumns();
                    break;
            }
            secondWindow(primaryStage);
        };
    }


    // Evento de click del mouse para objeto pane de la clase TilePane
    private EventHandler<MouseEvent> handlerOnClickPane(Table table, Stage primaryStage) {

        return (MouseEvent event) -> {
            // Obtiene lista de fichas que están descubiertas y que no han sido emparejadas
            List<Sheet> sheetsUncover = table.getSheetList().stream().filter(sheet -> !sheet.isCover() && !sheet.isPair()).collect(Collectors.toList());
            // Si el contador de clicks de la clase table es 0
            if (table.getCounterClick() == 0) {
                // pone en uno contador de clics de la clase table
                table.setCounterClick(1);
                // Si el número de elementos de la lista de imágenes descubiertas es mayor a uno
            } else if (sheetsUncover.size() > 1) {
                // Obtiene el primer elemento de lista de imágenes descubiertas para comparar
                Sheet compareSheet = sheetsUncover.get(0);
                // Si los dos elementos dela lista de imágenes son iguales
                if (sheetsUncover.get(1).equals(compareSheet)) {
                    // Se asigna valor de pares a ambas imágenes
                    sheetsUncover.forEach(sheet -> sheet.setPair(true));
                    // Si los dos elementos de la lista imágenes descubiertas no son iguales
                } else {
                    // Se crea un lapso de tiempo para que el usuario vea las imágenes antes de cubrirlas cubrir ambas images
                    executorService.schedule(Table::coverAllImageNoPair, 500, TimeUnit.MILLISECONDS);
                }
                // pone contador de clics de la clase table en 0
                table.setCounterClick(0);
            }
            // Verifica si todas las images han sido descubiertas
            if (table.isAllSheetsPairs()) {
                executorService.schedule(Table::coverAllImage, 500, TimeUnit.MILLISECONDS);
                modalFinalPlay();
            }
        };
    }
}
