package app.controller;

import app.components.BooleanCell;
import app.controller.create.department.CreateDepartmentController;
import app.controller.create.employee.CreateEmployeeController;
import app.rest.RestClientDepartments;
import app.rest.RestClientEmployee;
import app.view.DepartmentView;
import app.view.EmployeeView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller {

    private RestClientDepartments restClientDepartments = new RestClientDepartments();
    private RestClientEmployee restClientEmployee = new RestClientEmployee();

    //region departments table view control
    @FXML
    TableView<DepartmentView> departmentTable;
    @FXML
    TableColumn<DepartmentView, Integer> id;
    @FXML
    TableColumn<DepartmentView, String> name;
    @FXML
    TableColumn<DepartmentView, Boolean> isParent;
    @FXML
    TableColumn<DepartmentView, Integer> parent_departmentId;
    @FXML
    TableColumn<DepartmentView, Integer> countEmployee;
    //endregion

    //region employee table view control
    @FXML
    TableView<EmployeeView> employeeTable;
    @FXML
    TableColumn<EmployeeView, Integer> employeeId;
    @FXML
    TableColumn<EmployeeView, String> employeeName;
    @FXML
    TableColumn<EmployeeView, Integer> departmentId;
    @FXML
    TableColumn<EmployeeView, Boolean> isArchive;
    //endregion

    //region buttons
    @FXML
    Button createDepartmentButton;
    @FXML
    Button deleteDepartmentButton;
    @FXML
    Button refreshDepartmentButton;
    @FXML
    Button createEmployeeButton;
    @FXML
    Button deleteEmployeeButton;
    @FXML
    Button refreshEmployeeButton;
    //endregion

    @FXML
    private void initialize() {
        initializeDepartmentsTableView();
        initializeEmployeeView();
        initializeButtons();
        reloadDepartment();
        reloadEmployee();
    }

    private void initializeButtons() {
        refreshDepartmentButton.setOnAction(action -> reloadDepartment());
        refreshEmployeeButton.setOnAction(action -> reloadEmployee());
        deleteDepartmentButton.setOnAction(action -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтверждение");
            alert.setHeaderText("Удалить отдел?");
             alert.showAndWait().filter(response -> response == ButtonType.OK).ifPresent(response -> deleteDepartment());
        });
        deleteEmployeeButton.setOnAction(action -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтверждение");
            alert.setHeaderText("Уволить сотрудника?");
            alert.showAndWait().filter(response -> response == ButtonType.OK).ifPresent(response -> deleteEmployee());
        });

        createDepartmentButton.setOnAction(action -> CreateDepartmentController.openCreateDepartmentWindow(this));

        createEmployeeButton.setOnAction(action -> CreateEmployeeController.openCreateEmployeeWindow(this));

    }

    private void deleteEmployee() {
        EmployeeView employeeView = employeeTable.getSelectionModel().getSelectedItem();
        if (employeeView != null) {
            restClientEmployee.deleteEmployee(employeeView.getId());
        }
        reloadEmployee();
    }

    private void deleteDepartment() {
        DepartmentView departmentView = departmentTable.getSelectionModel().getSelectedItem();
        if (departmentView != null) {
            restClientDepartments.deleteDepartment(departmentView.getId());
        }
        reloadDepartment();
    }

    private void initializeDepartmentsTableView() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        isParent.setCellValueFactory(new PropertyValueFactory<>("isParent"));
        isParent.setCellFactory(param -> new BooleanCell());
        parent_departmentId.setCellValueFactory(new PropertyValueFactory<>("parentDepartmentId"));
        countEmployee.setCellValueFactory(new PropertyValueFactory<>("countEmployee"));
    }

    private void initializeEmployeeView() {
        employeeId.setCellValueFactory(new PropertyValueFactory<>("id"));
        employeeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        departmentId.setCellValueFactory(new PropertyValueFactory<>("departmentId"));
        isArchive.setCellValueFactory(new PropertyValueFactory<>("isArchive"));
        isArchive.setCellFactory(param -> new BooleanCell());
    }

    private void reloadDepartment() {
        ObservableList<DepartmentView> departmentsData = FXCollections.observableArrayList();
        for (DepartmentView departmentView : restClientDepartments.getDepartmentList()) {
            if (departmentView != null && departmentView.getParentDepartment() != null) {
                departmentView.setParentDepartmentId(departmentView.getParentDepartment().getId());
                departmentsData.add(departmentView);
            } else {
                departmentsData.add(departmentView);
            }
        }
        departmentTable.setItems(departmentsData);
    }

    private void reloadEmployee() {
        ObservableList<EmployeeView> employeeData = FXCollections.observableArrayList();
        for (EmployeeView employeeView : restClientEmployee.getEmployeeList()) {
            if (employeeView != null && employeeView.getDepartment() != null) {
                employeeView.setDepartmentId(employeeView.getDepartment().getId());
                employeeData.add(employeeView);
            } else {
                employeeData.add(employeeView);
            }
        }
        employeeTable.setItems(employeeData);
    }

    public void externalReload() {
        this.reloadDepartment();
        this.reloadEmployee();
    }
}
