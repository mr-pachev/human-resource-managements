package bg.softuni.human_resource_managements.service.impl;

import bg.softuni.human_resource_managements.model.dto.AddEmployeeDTO;
import bg.softuni.human_resource_managements.model.dto.EmployeeDTO;
import bg.softuni.human_resource_managements.model.entity.Employee;
import bg.softuni.human_resource_managements.model.enums.DepartmentName;
import bg.softuni.human_resource_managements.model.enums.EducationName;
import bg.softuni.human_resource_managements.model.enums.PositionName;
import bg.softuni.human_resource_managements.repository.DepartmentRepository;
import bg.softuni.human_resource_managements.repository.EducationRepository;
import bg.softuni.human_resource_managements.repository.EmployeeRepository;
import bg.softuni.human_resource_managements.repository.PositionRepository;
import bg.softuni.human_resource_managements.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;
    private final DepartmentRepository departmentRepository;
    private final EducationRepository educationRepository;
    private final RestClient employeesRestClient;

    public EmployeeServiceImpl(ModelMapper mapper, PasswordEncoder passwordEncoder, EmployeeRepository employeeRepository, PositionRepository positionRepository, DepartmentRepository departmentRepository, EducationRepository educationRepository, RestClient employeesRestClient) {
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.employeeRepository = employeeRepository;
        this.positionRepository = positionRepository;
        this.departmentRepository = departmentRepository;
        this.educationRepository = educationRepository;
        this.employeesRestClient = employeesRestClient;
    }

    @Override
    public boolean addEmployee(AddEmployeeDTO addEmployeeDTO) {
        Employee employee = mapper.map(addEmployeeDTO, Employee.class);
        employee.setPosition(positionRepository.findByPositionName(PositionName.valueOf(addEmployeeDTO.getPosition())));
        employee.setDepartment(departmentRepository.findByDepartmentName(DepartmentName.valueOf(addEmployeeDTO.getDepartment())));
        employee.setEducation(educationRepository.findByEducationName(EducationName.valueOf(addEmployeeDTO.getEducation())));

        Optional<Employee> isExistEmployee = employeeRepository.findAllByIdentificationNumber(addEmployeeDTO.getIdentificationNumber());

        if(isExistEmployee.isPresent()){
            return false;
        }

        employeeRepository.save(employee);

        return true;
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();

        List<EmployeeDTO> allEmployees = new ArrayList<>();

        for (Employee employee : employees) {
            allEmployees.add(map(employee));
        }

        return allEmployees;
    }

    @Override
    public void removeEmployee(long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public EmployeeDTO getEmployeeByID(long id) {
        Employee employee = employeeRepository.findById(id);

        return map(employee);
    }

    @Override
    public EmployeeDTO getEmployeeByIdentificationNumber(String number) {
        Employee employee = employeeRepository.findByIdentificationNumber(number);

        return map(employee);
    }

    @Override
    public void edithEmployee(EmployeeDTO employeeDTO) {
        Employee employee = reMap(employeeDTO);

//        employeeRepository.save(employee);
        employeesRestClient
                .post()
                .uri("http://localhost:8081/employees")
                .body(employee)
                .retrieve();
    }

    public EmployeeDTO map(Employee employee){
        EmployeeDTO employeeDTO = mapper.map(employee, EmployeeDTO.class);
        employeeDTO.setPosition(employee.getPosition().getPositionName().name());
        employeeDTO.setDepartment(employee.getDepartment().getDepartmentName().name());
        employeeDTO.setEducation(employee.getEducation().getEducationName().name());

       return employeeDTO;
    }

    public Employee reMap(EmployeeDTO employeeDTO){
        Employee employee = employeeRepository.findByIdentificationNumber(employeeDTO.getIdentificationNumber());

        employee.setFirstName(employeeDTO.getFirstName());
        employee.setMiddleName(employeeDTO.getMiddleName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setAge(employeeDTO.getAge());

        LocalDate startDate = mapper.map(employeeDTO.getStartDate(), LocalDate.class);
        employee.setStartDate(startDate);

        if(!employeeDTO.getEndDate().isEmpty()){
            LocalDate endDate = mapper.map(employeeDTO.getEndDate(), LocalDate.class);
            employee.setEndDate(endDate);
        }

        employee.setPosition(positionRepository.findByPositionName(PositionName.valueOf(employeeDTO.getPosition())));
        employee.setDepartment(departmentRepository.findByDepartmentName(DepartmentName.valueOf(employeeDTO.getDepartment())));
        employee.setEducation(educationRepository.findByEducationName(EducationName.valueOf(employeeDTO.getEducation())));

        return employee;
    }
}
