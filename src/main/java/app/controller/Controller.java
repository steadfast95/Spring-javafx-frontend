package app.controller;

import app.rest.RestClientDepartments;
import app.rest.RestClientEmployee;
import app.rest.SpringRestTemplate;
import app.view.DepartmentView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller {

    RestClientDepartments restClientDepartments = new RestClientDepartments();
    RestClientEmployee restClientEmployee = new RestClientEmployee();

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


    @FXML
    private void initialize() {
        ObservableList<DepartmentView> data =FXCollections.observableArrayList();
        for(DepartmentView departmentView : restClientDepartments.getDepartmentList()){
            if(departmentView!=null && departmentView.getParentDepartment()!=null) {
                departmentView.setParentDepartmentId(departmentView.getParentDepartment().getId());
                data.add(departmentView);
            }else {
                data.add(departmentView);
            }
        }

        departmentTable.getItems().addAll(data);

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        isParent.setCellValueFactory(new PropertyValueFactory<>("isParent"));
        isParent.setCellFactory(param -> new BooleanCell());
        parent_departmentId.setCellValueFactory(new PropertyValueFactory<>("parentDepartmentId"));
        countEmployee.setCellValueFactory(new PropertyValueFactory<>("countEmployee"));


        restClientDepartments.getDepartment(15);



    }
}
