package app.view;

import lombok.Data;

@Data
public class EmployeeView {
    private Integer id;
    private String name;
    private DepartmentView department;
    private Boolean isArchive;
}
