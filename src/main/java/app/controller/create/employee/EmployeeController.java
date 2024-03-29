package app.controller.create.employee;

import app.controller.Controller;
import app.rest.RestClientDepartments;
import app.rest.RestClientEmployee;
import app.view.DepartmentView;
import app.view.EmployeeView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;

public class EmployeeController {

    private Controller mainController;

    private RestClientDepartments restClientDepartments = new RestClientDepartments();
    private RestClientEmployee restClientEmployee = new RestClientEmployee();

    @FXML
    TextField nameTextField;
    @FXML
    ComboBox<DepartmentView> departmentComboBox;
    @FXML
    Button okButton;
    @FXML
    Button discardButton;
    @FXML
    CheckBox isArchiveCheckBox;

    private Boolean updateForm = false;
    private EmployeeView employeeView;

    private void init(Controller mainController, boolean updateForm, EmployeeView employeeView) {
        this.mainController = mainController;
        this.updateForm = updateForm;
        this.employeeView = employeeView;
        if (updateForm && employeeView != null) {
            nameTextField.setText(employeeView.getName());
            departmentComboBox.getSelectionModel().select(employeeView.getDepartment());
            isArchiveCheckBox.setSelected(employeeView.getIsArchive());
        }
    }

    @FXML
    private void initialize() {
        Callback<ListView<DepartmentView>, ListCell<DepartmentView>> factory = lv -> new ListCell<DepartmentView>() {
            @Override
            protected void updateItem(DepartmentView item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getId() + " " + item.getName());
            }
        };

        departmentComboBox.setCellFactory(factory);
        departmentComboBox.setButtonCell(factory.call(null));
        departmentComboBox.getItems().addAll(restClientDepartments.getDepartmentList());

        okButton.setOnAction(action -> {
            if(updateForm.equals(false)) {
                EmployeeView employeeView = new EmployeeView();
                employeeView.setName(nameTextField.getText());
                employeeView.setIsArchive(isArchiveCheckBox.isSelected());
                employeeView.setDepartment(departmentComboBox.getSelectionModel().getSelectedItem());
                restClientEmployee.addEmployee(employeeView);
            }else {
                this.employeeView.setName(nameTextField.getText());
                this.employeeView.setIsArchive(isArchiveCheckBox.isSelected());
                this.employeeView.setDepartment(departmentComboBox.getSelectionModel().getSelectedItem());
                restClientEmployee.updateEmployee(this.employeeView);
            }
            if (mainController != null) {
                mainController.externalReload();
            }
            Stage stage = (Stage) okButton.getScene().getWindow();
            stage.close();
        });
        discardButton.setOnAction(action -> {
            Stage stage = (Stage) okButton.getScene().getWindow();
            stage.close();
        });
    }

    public static void openCreateEmployeeWindow(Controller mainController, boolean updateForm, EmployeeView employeeView) {
        try {
            FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource("fxml/createEmployee.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            Scene scene = new Scene(loader.load());
            EmployeeController controller = loader.getController();
            controller.init(mainController, updateForm, employeeView);
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
