package app.rest;

import app.view.DepartmentView;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import java.util.List;

public class RestClientDepartments extends SpringRestTemplate {

    public List<DepartmentView> getDepartmentList() {
        return restTemplate.exchange("http://localhost:8080/department/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<DepartmentView>>() {
                }).getBody();
    }

    public DepartmentView getDepartment(Integer id) {
        return restTemplate.getForEntity("http://localhost:8080/department/" + id, DepartmentView.class).getBody();
    }

    public void addDepartment(DepartmentView departmentView) {
        restTemplate.postForObject("http://localhost:8080/department/",departmentView,DepartmentView.class);
    }

    public void deleteDepartment(Integer id) {
        restTemplate.delete("http://localhost:8080/department/" + id);
    }
}
