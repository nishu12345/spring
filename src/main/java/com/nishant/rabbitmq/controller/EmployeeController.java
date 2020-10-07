package com.nishant.rabbitmq.controller;

import com.nishant.rabbitmq.producer.EmployeeProducer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.nishant.rabbitmq.util.Constants.UrlMapping.BASE_URL;
import static com.nishant.rabbitmq.util.Constants.UrlMapping.EMP_PRODUCE;

@RestController
@EnableSwagger2
@RequestMapping(BASE_URL)
public class EmployeeController {

    private EmployeeProducer employeeProducer;

    public EmployeeController(EmployeeProducer employeeProducer) {
        this.employeeProducer = employeeProducer;
    }

    @GetMapping(EMP_PRODUCE)
    public String sendEmpToQueue(@RequestParam("empName") String empName, @RequestParam("empId") String empId, @RequestParam("empSalary") Integer empSalary) {
        return employeeProducer.produceEmployee(empName, empId, empSalary);
    }
}
