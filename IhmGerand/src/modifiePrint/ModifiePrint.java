package modifiePrint;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ModifiePrint extends HBox {
    private Button modifie;
    private Button delete;

    public enum ButtonType{
        BUTTON_MOD,
        BUTTON_DEL
    }

    private ActionListener mouseEntered;
    private ActionListener mouseExited;

    public ModifiePrint(Node n, List<ButtonType> buttons) {
        getStylesheets().add("modifiePrint.css");
        getChildren().add(n);

        Button b = new Button();
        b.getStyleClass().add("icon-button");
        b.setPickOnBounds(true);

        Region iconModifie = new Region();
        iconModifie.getStyleClass().add("icon-modifie");
        b.setGraphic(iconModifie);

        Button b2 = new Button();
        b2.getStyleClass().add("icon-button");
        b2.setPickOnBounds(true);

        Region iconDelete = new Region();
        iconDelete.getStyleClass().add("icon-delete");
        b2.setGraphic(iconDelete);

        b.setVisible(false);
        b2.setVisible(false);
        b.setManaged(false);
        b2.setManaged(false);
        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(1,1,1,1));
        setOnMouseEntered(event -> {
            if(buttons.contains(ButtonType.BUTTON_MOD)){
                b.setVisible(true);
                b.setManaged(true);
            }
            if(buttons.contains(ButtonType.BUTTON_DEL)){
                b2.setVisible(true);
                b2.setManaged(true);
            }
            if(mouseEntered != null){
                mouseEntered.actionPerformed(null);
            }
            setStyle("-fx-background-color: beige");
        });

        setOnMouseExited(e ->{
            b.setVisible(false);
            b2.setVisible(false);
            b.setManaged(false);
            b2.setManaged(false);
            if(mouseExited != null){
                mouseExited.actionPerformed(null);
            }
            setStyle("");
        });


        modifie = b;
        delete = b2;
        Region r = new Region();
        HBox.setHgrow(r, Priority.ALWAYS);
        getChildren().addAll(r, b, b2);
    }

    public ModifiePrint(Node n) {
        this(n, Arrays.asList(ButtonType.BUTTON_DEL, ButtonType.BUTTON_MOD));
    }

    public Button getModifie() {
        return modifie;
    }

    public Button getDelete() {
        return delete;
    }

    public void setMouseEntered(ActionListener mouseEntered) {
        this.mouseEntered = mouseEntered;
    }

    public void setMouseExited(ActionListener mouseExited) {
        this.mouseExited = mouseExited;
    }
}
