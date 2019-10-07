package app.view;

import lombok.Data;

@Data
public class DepartmentView {
    private Integer id;
    private String name;
    private Boolean isParent = false;
    private DepartmentView parentDepartment;
    private Integer countEmployee;
    private Integer parentDepartmentId;

}
