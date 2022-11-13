package co.com.jorge.model;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * Clase Sheet, representa cada cuadrícula dentro del tablero, en la cual va almacenada la imagen y responde al evento
 * del clic para mostrar u ocultar las imágenes
 */

public class Sheet {
    /**
     * Identificador único de cada objeto de la clase Sheet
     */
    private final int id;

    /**
     * Contenedor de la clase ImageView de javaFX para almacenar las imágenes y mostrarlas en la interfaz gráfica
     */
    private ImageView image;

    /**
     * Representación booleana para identificar la imagen está cubierta
     */
    private boolean cover;

    /**
     * Representación booleana para identificar que la imagen está descubierta al igual que su semejante
     */
    private boolean pair;

    /**
     * Generador de ID único para cada objeto de la clase Sheet
     */
    public static int generateId = 0;

    /**
     * Constructor con parámetro image de  la clase ImageView
     * @param image Imagen a cargar en el objeto de la clase Sheet
     */
    public Sheet(int type) {
        this.id = generateId++;
        this.image = new ImageView(new Image(getClass().getResourceAsStream("/img/img" + type + ".jpg")));
        this.image.setId("img"+ type);
        this.image.setFitHeight(125);
        this.image.setFitWidth(125);
        coverImage();
        this.image.addEventHandler(MouseEvent.MOUSE_CLICKED, setHandler());
    }

    /**
     * Cubre la imagen del objeto de la clase Sheet si este no está identificado como par, lo cual indica que no se ha
     * encontrado su semejante
     */
    public void coverImage(){
        if(!isPair()){
            image.setImage(new Image (getClass().getResourceAsStream("/img/img0.jpg")));
            this.cover = true;
        }
    }

    /**
     * Muestra o descubre la imagen del objeto de la clase Sheet
     */
    public void uncoverImage(){
        image.setImage(new Image (getClass().getResourceAsStream("/img/" + image.getId() + ".jpg")));
        this.cover = false;
    }

    /**
     * Evento del mouse para detectar click
     * @return Retorna un evento para el clic del mouse que invoca a los métodos de cubrir o descubrir imagen según
     * el estado de la imagen
     */
    private EventHandler<MouseEvent> setHandler() {
        return  (MouseEvent event) -> {
            if (isCover()) {
                uncoverImage();
            } else {
                coverImage();
            }
        };
    }

    /**
     * Método que retorna el objeto de la clase ImageView contenido en el objeto de la clase Sheet el cual contiene
     * @return Retorna el objeto de la clase ImageView contenido en el objeto de la clase Sheet el cual contiene
     * la imagen
     */
    public ImageView getImage() {
        return image;
    }

    /**
     * Modifica el atributo image cargando un objeto de la clase ImageView
     * @param image Carga un objeto de la clase ImageView en el atributo image del objeto de la clase Sheet
     */
    public void setImage(ImageView image) {
        this.image = image;
    }

    /**
     * Método que retorna el valor del ID único de cada objeto de la clase Sheet
     * @return Retorna el ID único del objeto de la clase Sheet
     */
    public int getId() {
        return id;
    }

    /**
     * Método para determinar si una imagen esta cubierta
     * @return retorna true si la imagen se encuentra cubierta y false si está se encuentra descubierta
     */
    public boolean isCover() {
        return cover;
    }

    /**
     * Modifica el estado del objeto de la clase Sheet que representa si la imagen está cubierta o descubierta
     * @param cover indica el estado que se le dara al objeto de la clase Sheet para representar si la imagen está
     *              cubierta o descubierta
     */
    public void setCover(boolean cover) {
        image.setImage(new Image (getClass().getResourceAsStream("/img/img0.jpg")));
        this.cover = cover;
    }

    /**
     * Método que determina si una dos objetos con la igual imagen han sido descubiertos
     * @return Retorna el estado del objeto de la clase Sheet, que representa si la imagen semejante ha sido descubierta
     */
    public boolean isPair() {
        return pair;
    }

    /**
     * Modifica el estado del objeto de la clase Sheet, que representa si la imagen semejante ha sido descubierta
     * @param pair Representa el estado del objeto de la clase Sheet, que representa si la imagen semejante ha
     *             sido descubierta
     */
    public void setPair(boolean pair) {
        this.pair = pair;
    }

    /**
     * Método para comparar con otro objeto de la clase Sheet y determinar si poseen imágenes semejantes
     * @param o Objeto de la clase SSheet con el cual se desea comparar
     * @return retorna true si ambos objetos contiene la misma imagen y false de no ser asi
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sheet sheet = (Sheet) o;
        return cover == sheet.cover && image.getId().equals(sheet.image.getId());
    }

    @Override
    public String toString() {
        return "Sheet{" +
                "id=" + id +
                ", image=" + image.getId() +
                ", cover=" + cover +
                '}';
    }
}
