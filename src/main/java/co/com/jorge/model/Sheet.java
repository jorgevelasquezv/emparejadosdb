package co.com.jorge.model;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

public class Sheet {
    private int id;

    private ImageView image;

    private boolean cover;

    private boolean pair;

    public static int generateId = 0;

    public Sheet() {
        this.id = generateId++;
    }

    public Sheet(ImageView image) {
        this.id = generateId++;
        this.image = image;
        coverImage();
        this.image.addEventHandler(MouseEvent.MOUSE_CLICKED, setHandler());
    }

    public void coverImage(){
        if(!isPair()){
            image.setImage(new Image (getClass().getResourceAsStream("/img/img0.jpg")));
            this.cover = true;
        }
    }

    public void uncoverImage(){
        image.setImage(new Image (getClass().getResourceAsStream("/img/" + image.getId() + ".jpg")));
        this.cover = false;
    }

    private EventHandler<MouseEvent> setHandler() {
        return  (MouseEvent event) -> {
            if (isCover()) {
                uncoverImage();
            } else {
                coverImage();
            }
        };
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public boolean isCover() {
        return cover;
    }

    public void setCover(boolean cover) {
        image.setImage(new Image (getClass().getResourceAsStream("/img/img0.jpg")));
        this.cover = cover;
    }

    public boolean isPair() {
        return pair;
    }

    public void setPair(boolean pair) {
        this.pair = pair;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sheet sheet = (Sheet) o;
        return cover == sheet.cover && image.getId().equals(sheet.image.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(image, cover);
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
