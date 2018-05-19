package customSearchBar;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.beans.EventHandler;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddSearchBar<T> extends HBox {
    public ComboSearchBox<T> combo;
    public Button button;

    public ObservableList<T> getItems(){
        return combo.getAllItems();
    }

    public AddSearchBar() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource( "addSearchBar.fxml" ) );

        loader.setRoot( this );
        loader.setController( this );

        try {
            loader.load();
        } catch ( IOException e ) {
            throw new RuntimeException( e );
        }
    }

    public String getText() {
        return combo.getPromptText();
    }

    public void setText(String value) {
        combo.promptTextProperty().set(value);
    }

    public StringProperty textProperty() {
        return combo.promptTextProperty();
    }

    public void setOnItemSelected(javafx.beans.value.ChangeListener<T> e){
        combo.setChange(e);
    }

    public void resetSelection(){
        combo.clearSelection();
    }



}
