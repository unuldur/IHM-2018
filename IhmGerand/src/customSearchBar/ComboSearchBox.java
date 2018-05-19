package customSearchBar;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;


public class ComboSearchBox<T> extends ComboBox<Integer> {
    private ObservableList<T> initialList;
    private ObservableList<T> bufferList = FXCollections.observableArrayList();
    private String previousValue = "";

    public String getPreviousValue() {
        return previousValue;
    }

    private ChangeListener<T> change;

    public ComboSearchBox() {
        super(FXCollections.observableArrayList());
        super.setEditable(true);
        initialList = FXCollections.observableArrayList();
        initialList.addListener((ListChangeListener<T>) c -> {
            setItems(getIndexes(initialList));
        });
        StringConverter<Integer> sc = new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                if(object == null || object < 0 || object >= initialList.size()) return "";
                return initialList.get(object).toString();
            }

            @Override
            public Integer fromString(String string) {
                int i = 0;
                for (T obj :
                        initialList) {
                    if (string.equals(obj.toString()))
                        return i;
                    i++;
                }
                return -1;
            }
        };
        setCellFactory(lv -> {
            ListCell<Integer> cell = new ListCell<Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : sc.toString(item));
                }
            };
            cell.setOnMousePressed(e -> {
                if (! cell.isEmpty()) {
                    change.changed(null, null, initialList.get(cell.getItem()));
                }
            });
            return cell ;
        });
        this.setOnKeyReleased(event -> {
            if(event.getCode() == KeyCode.ENTER){
                change.changed(null, null, initialList.get(getSelectionModel().getSelectedItem()));
            }
        });
        setConverter(sc);
        configAutoFilterListener();
    }

    private void configAutoFilterListener() {
        this.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            previousValue = oldValue;
            final TextField editor = this.getEditor();
            final Integer selected = this.getSelectionModel().getSelectedItem();
            if (selected == null || !initialList.get(selected).toString().equals(editor.getText())) {
                filterItems(newValue, this);

                this.show();
            }
        });
    }

    private void filterItems(String filter, ComboBox<Integer> comboBox) {
        if (filter.startsWith(previousValue) && !previousValue.isEmpty()) {
            ObservableList<T> filteredList = this.readFromList(filter, bufferList);
            bufferList.clear();
            bufferList = filteredList;
        } else {
            bufferList = this.readFromList(filter, initialList);
        }
        comboBox.hide();
        comboBox.setItems(getIndexes(bufferList));
        comboBox.show();
    }

    private ObservableList<Integer> getIndexes(ObservableList<T> items){
        ObservableList<Integer> i = FXCollections.observableArrayList();
        for (T item :
                items) {
            i.add(initialList.indexOf(item));
        }
        return i;
    }

    private ObservableList<T> readFromList(String filter, ObservableList<T> originalList) {
        ObservableList<T> filteredList = FXCollections.observableArrayList();
        for (T item : originalList) {
            if (item.toString().toLowerCase().startsWith(filter.toLowerCase())) {
                filteredList.add(item);
            }
        }

        return filteredList;
    }



    public ObservableList<T> getAllItems() {
        return initialList;
    }

    public void setChange(ChangeListener<T> change) {
        this.change = change;
    }

    public void clearSelection(){
        ObservableList<T> tmp = FXCollections.observableArrayList(initialList);
        initialList = FXCollections.observableArrayList();
        setItems(getIndexes(initialList));
        getSelectionModel().selectFirst();
        initialList.addAll(tmp);
        setItems(getIndexes(initialList));
        bufferList = FXCollections.observableArrayList();
        previousValue = "";
        show();
    }
}
