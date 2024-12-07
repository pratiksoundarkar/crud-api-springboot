package com.employee.demo.service;

import com.employee.demo.entity.Employee;
import com.employee.demo.repo.EmployeeRepository;
import com.employee.demo.response.AddressResponse;
import com.employee.demo.response.EmployeeResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final RestTemplate restTemplate;
    private final ModelMapper modelMapper;
    @Value("${addressService.base.url}")
    private String addressServiceUri;

    @Autowired
    public EmployeeService(@Value("${addressService.base.url}") String addressServiceUri,
                           EmployeeRepository employeeRepository, RestTemplateBuilder builder, ModelMapper modelMapper) {
        System.out.println("uri :" + addressServiceUri);
        this.employeeRepository = employeeRepository;
        this.restTemplate = builder.rootUri(addressServiceUri).build();
        this.modelMapper = modelMapper;
    }

    public Optional<EmployeeResponse> findById(int id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        Optional<EmployeeResponse> employeeResponse = employee
                .map(emp -> modelMapper.map(emp, EmployeeResponse.class));
//        AddressResponse addressResponse = restTemplate.getForObject("/address/{id}", AddressResponse.class, id);
//        employeeResponse.setAddressResponse(addressResponse);

        return employeeResponse;
    }

    public List<EmployeeResponse> findAll() {
        List<Employee> employeeList = employeeRepository.findAll();

        // using stream api mappinng the object
        /*List<EmployeeResponse> employeeResponseList = employeeList.stream()
                .map(employee -> modelMapper.map(employee, EmployeeResponse.class))
                .collect(Collectors.toList());*/

        Type listType = new TypeToken<List<EmployeeResponse>>() {}.getType();
        List<EmployeeResponse> employeeResponseList = modelMapper.map(employeeList, listType);

        return employeeResponseList;
    }

    public EmployeeResponse save(Employee employee) {
        Employee saveEmployee;
        if (employee.getId() != null) {
            // Update existing employee
            Employee existingEmployee = employeeRepository.findById(employee.getId())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));

            existingEmployee.setName(employee.getName());
            existingEmployee.setEmail(employee.getEmail());
            existingEmployee.setPhoneNumber(employee.getPhoneNumber());

            saveEmployee = employeeRepository.save(existingEmployee);
        } else {
            // Create new employee
            saveEmployee = employeeRepository.save(employee);
        }

        EmployeeResponse employeeResponse = modelMapper.map(saveEmployee, EmployeeResponse.class);

        return employeeResponse;
    }

    public void delete(int id) {
        employeeRepository.deleteById(id);
    }

}
