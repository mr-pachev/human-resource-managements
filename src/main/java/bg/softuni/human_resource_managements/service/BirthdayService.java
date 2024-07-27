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
    private final ModelMapper mapper;

    public BirthdayService(EmployeeService employeeService, ModelMapper mapper) {
        this.employeeService = employeeService;
        this.mapper = mapper;
    }

    public List<EmployeeDTO> getBirthdayEmployees() {
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        LocalDate today = LocalDate.now();

        return employees.stream()
                .filter(employee -> {
                    String identificationNumber = employee.getIdentificationNumber().substring(0, 6);
                    LocalDate birthDate = parseDateFromIdentificationNumber(identificationNumber);

                    return birthDate != null &&
                            birthDate.getMonth() == today.getMonth() &&
                            birthDate.getDayOfMonth() == today.getDayOfMonth();
                })
                .collect(Collectors.toList());
    }

    private LocalDate parseDateFromIdentificationNumber(String identificationNumber) {
        try {
            return mapper.map(identificationNumber, LocalDate.class);
        } catch (Exception e) {
            return null;
        }
    }
}
