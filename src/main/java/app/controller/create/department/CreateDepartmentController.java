package app.controller.create.department;

import app.controller.Controller;
import app.rest.RestClientDepartments;
import app.view.DepartmentView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;

public class CreateDepartmentController {

    private Controller mainController;

    private RestClientDepartments restClientDepartments = new RestClientDepartments();

    @FXML
    TextField nameTextField;
    @FXML
    ComboBox<DepartmentView> headDepartmentComboBox;
    @FXML
    Button okButton;
    @FXML
    Button discardButton;

    @FXML
    private void initialize(){
        Callback<ListView<DepartmentView>, ListCell<DepartmentView>> factory = lv -> new ListCell<DepartmentView>() {
            @Override
            protected void updateItem(DepartmentView item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getId() + " " +item.getName());
            }
        };

        headDepartmentComboBox.setButtonCell(factory.call(null));
        headDepartmentComboBox.setCellFactory(factory);
        headDepartmentComboBox.getItems().addAll(restClientDepartments.getDepartmentList());


        okButton.setOnAction(action->{
            DepartmentView departmentView = new DepartmentView();
            departmentView.setName(nameTextField.getText());
            departmentView.setParentDepartment(headDepartmentComboBox.getSelectionModel().getSelectedItem());
            restClientDepartments.addDepartment(departmentView);
            if(mainController!=null){
                mainController.externalReload();
            }
            Stage stage  = (Stage)okButton.getScene().getWindow();
            stage.close();

        });

        discardButton.setOnAction(action->{
            Stage stage  = (Stage)okButton.getScene().getWindow();
            stage.close();
        });
    }

    private void init(Controller mainController){
        this.mainController = mainController;
    }


    public static void openCreateDepartmentWindow(Controller mainController) {
        try {
            FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource("fxml/createDepartment.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            Scene scene = new Scene(loader.load());
            CreateDepartmentController controller = loader.getController();
            controller.init(mainController);
            stage.setTitle("Создание отдела");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(null);
            stage.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
