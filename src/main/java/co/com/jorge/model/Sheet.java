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
     * Constructor vacío de la clase Sheet
     */
    public Sheet() {
        this.id = generateId++;
    }

    /**
     * Constructor con parámetro image de  la clase ImageView
     * @param image Imagen a cargar en el objeto de la clase Sheet
     */
    public Sheet(ImageView image) {
        this.id = generateId++;
        this.image = image;
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
     * @return Retorna el objeto de la clase ImageView contenido en el objeto de la clase Sheet el cual contiene
     * la imagen
     */
    public ImageView getImage() {
        return image;
    }

    /**
     * @param image Carga un objeto de la clase ImageView en el atributo image del objeto de la clase Sheet
     */
    public void setImage(ImageView image) {
        this.image = image;
    }

    /**
     * @return Retorna el ID unico del objeto de la clase Sheet
     */
    public int getId() {
        return id;
    }

    /**
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
        this.cover = true;
    }

    /**
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
     * @param o Compara el objeto de la clase Sheet con otro objeto de la misma clase para determinar si poseen
     *          imágenes semejantes
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
