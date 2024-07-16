package bg.softuni.human_resource_managements.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AddDepartmentDTO {
    @NotBlank
    @Size(min = 3, max = 50)
    private String departmentName;

    @NotBlank
    @Size(min = 9, max = 50)
    private String manager;

    @NotBlank
    @Size(min = 10, max = 255)
    private String descriptions;

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

}
