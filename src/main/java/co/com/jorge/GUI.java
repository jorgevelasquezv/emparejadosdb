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
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class GUI extends Application {

    private Stage stage;

    private Table table;

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        this.stage.setTitle("Emparejados DB");
        this.stage.setResizable(false);
        this.stage.getIcons().add(new Image(getClass().getResourceAsStream("/img/icon.png")));

        // Primera vista
        firstWindow();
    }

    @Override
    public void stop() throws Exception {
        executorService.shutdown();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    // Primera ventana
    private void firstWindow(){
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

        play.addEventHandler(MouseEvent.MOUSE_CLICKED, handleOnClickPlay(selectLevel));

        Scene scene = new Scene(paneOne);
        stage.setScene(scene);

        stage.show();
    }

    // Segunda ventana
    private void secondWindow(){
        TilePane paneTwo = new TilePane();
        paneTwo.setAlignment(Pos.CENTER);
        paneTwo.setPadding(new Insets(12, 12, 12, 12));
        paneTwo.setHgap(5);
        paneTwo.setVgap(5);
        if(table.getColumns() == table.getRows()){
            paneTwo.setMaxWidth(table.getColumns() * 160); // Nivel 2
        }else {
            paneTwo.setMaxWidth(table.getColumns() * 200); // Nivel 1 y 3
        }

        table.getSheetList().forEach(sheet -> paneTwo.getChildren().add(sheet.getImage()));

        paneTwo.addEventHandler(MouseEvent.MOUSE_CLICKED, handlerOnClickPane(table, stage));

        Scene scene = new Scene(paneTwo);
        stage.setScene(scene);
        stage.show();

    }


    // Ventana Emergente
    private void modalFinalPlay(){
        FlowPane pane = new FlowPane(Orientation.VERTICAL);
        pane.setPadding(new Insets(50, 10, 13, 70));
        pane.setHgap(15);
        pane.setVgap(15);

        Label label = new Label("¿Desea Continuar Jugando?");
        label.setStyle("-fx-font: 15px \"Britannic Bold\";");

        HBox hBox = new HBox();
        Button resume = new Button("Continuar");
        resume.setPrefSize(80, 15);
        resume.addEventHandler(MouseEvent.MOUSE_CLICKED, handleOnClickButtonWindowModal(stage));
        Button leave = new Button("Salir");
        leave.setPrefSize(80, 15);
        leave.addEventHandler(MouseEvent.MOUSE_CLICKED, handleOnClickButtonWindowModal(stage));
        hBox.getChildren().addAll(resume, leave);
        hBox.setPadding(new Insets(40, 10, 0, 0));
        hBox.setSpacing(25);

        pane.getChildren().addAll(label, hBox);
        Scene scene = new Scene(pane,300, 200);
        stage.setScene(scene);
        stage.show();
    }

    // Evento del botón Jugar
    private EventHandler<MouseEvent> handleOnClickPlay(ComboBox<String> selectLevel) {
        return (MouseEvent event) -> {
            switch (selectLevel.getValue()) {
                case "Nivel 1":
                    table = new Table(Level.LEVEL1);
                    break;
                case "Nivel 2":
                    table = new Table(Level.LEVEL2);
                    break;
                case "Nivel 3":
                    table = new Table(Level.LEVEL3);
                    break;
            }
            stage.close();
            secondWindow();
        };
    }


    // Evento de click del mouse para objeto pane de la clase TilePane
    private EventHandler<MouseEvent> handlerOnClickPane(Table table, Stage stage) {

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
                stage.close();
                modalFinalPlay();
            }
        };
    }

    // Evento del click para venta modal
    private EventHandler<MouseEvent> handleOnClickButtonWindowModal(Stage stage){
        return (MouseEvent event) -> {
            String buttonPressed = event.getSource().toString();
            if (buttonPressed.contains("Continuar")){
                stage.close();
                firstWindow();
            } else if (buttonPressed.contains("Salir")) {
                stage.close();
            }
        };
    }
}
