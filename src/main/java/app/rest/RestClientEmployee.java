package app.rest;

import app.view.DepartmentView;
import app.view.EmployeeView;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.util.List;

public class RestClientEmployee extends SpringRestTemplate {

    public List<EmployeeView> getEmployeeList() {
        return restTemplate.exchange("http://localhost:8080/employee/list/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<EmployeeView>>() {
                }).getBody();
    }

    public EmployeeView getEmployee(Integer id) {
        return restTemplate.getForEntity("http://localhost:8080/employee/get/" + id, EmployeeView.class).getBody();
    }

    public void addEmployee(EmployeeView employeeView) {
        restTemplate.put("http://localhost:8080/employee/add/", employeeView);
    }

    public void deleteEmployee(Integer id) {
        restTemplate.delete("http://localhost:8080/employee/delete/" + id);
    }
}
