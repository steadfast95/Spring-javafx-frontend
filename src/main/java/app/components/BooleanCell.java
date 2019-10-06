package app.components;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;

public class BooleanCell<T> extends TableCell<T,Boolean> {
    @Override
    protected void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) {
            CheckBox checkbox = new CheckBox();
            checkbox.selectedProperty().set(item);
            checkbox.setDisable(true);
            checkbox.setOpacity(1);
            setGraphic(checkbox);
            setStyle("-fx-alignment: CENTER;");
        }else {
            setGraphic(null);
        }

    }

}
