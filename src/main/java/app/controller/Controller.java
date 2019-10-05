package app.controller;

import app.rest.RestClientDepartments;
import app.rest.RestClientEmployee;
import app.rest.SpringRestTemplate;
import app.view.DepartmentView;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class Controller {

    RestClientDepartments restClientDepartments = new RestClientDepartments();
    RestClientEmployee restClientEmployee = new RestClientEmployee();

    @FXML
    TableView departmentTable;

    @FXML
    TableColumn id;
    @FXML
    TableColumn name;
    @FXML
    TableColumn isParent;
    @FXML
    TableColumn parent_departmentId;
    @FXML
    TableColumn countEmployee;



    @FXML
    private void initialize() {
//        DepartmentView departmentView = new DepartmentView();
//        departmentView.setName("name");
//        restClientDepartments.addDepartment(departmentView);
//        restClientDepartments.deleteDepartment(9);

        restClientDepartments.getDepartment(15);



    }
}
