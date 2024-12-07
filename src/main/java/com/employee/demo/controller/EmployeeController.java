package com.employee.demo.controller;

import com.employee.demo.entity.Employee;
import com.employee.demo.response.EmployeeResponse;
import com.employee.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employee/{id}")
    ResponseEntity<EmployeeResponse> getEmployee(@PathVariable("id") int id) {

        return employeeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/employees")
    ResponseEntity<List<EmployeeResponse>> getEmployees() {

        List<EmployeeResponse> employeeResponses = employeeService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(employeeResponses);
    }

    @PostMapping("/employee/save")
    ResponseEntity<EmployeeResponse> save(@RequestBody Employee employee) {

        try {
            EmployeeResponse employeeResponse = employeeService.save(employee);
            return ResponseEntity.status(HttpStatus.OK).body(employeeResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/employee/delete/{id}")
    ResponseEntity delete(@PathVariable("id") int id) {
        employeeService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
