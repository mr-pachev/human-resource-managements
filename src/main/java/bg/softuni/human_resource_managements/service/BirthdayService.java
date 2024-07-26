package bg.softuni.human_resource_managements.service;

import bg.softuni.human_resource_managements.model.dto.EmployeeDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BirthdayService {

    private final EmployeeService employeeService;
    private final ModelMapper modelMapper;

    public BirthdayService(EmployeeService employeeService, ModelMapper modelMapper) {
        this.employeeService = employeeService;
        this.modelMapper = modelMapper;
    }

    public List<EmployeeDTO> getBirthdayEmployees() {
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        LocalDate today = LocalDate.now();

        return employees.stream()
                .filter(employee -> {
                    String identificationNumber = employee.getIdentificationNumber();
                    LocalDate birthDate = parseDateFromIdentificationNumber(identificationNumber);
                    return birthDate != null && birthDate.getMonth() == today.getMonth() && birthDate.getDayOfMonth() == today.getDayOfMonth();
                })
                .collect(Collectors.toList());
    }

    private LocalDate parseDateFromIdentificationNumber(String identificationNumber) {
        try {
            return LocalDate.parse(identificationNumber.substring(0, 6), DateTimeFormatter.ofPattern("yyMMdd"));
        } catch (Exception e) {
            return null;
        }
    }
}
