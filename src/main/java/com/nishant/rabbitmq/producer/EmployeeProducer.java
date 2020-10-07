package com.nishant.rabbitmq.producer;

import com.nishant.rabbitmq.model.Employee;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import static com.nishant.rabbitmq.util.Constants.EMP_QUEUE_ROUTING_KEY;
import static com.nishant.rabbitmq.util.Constants.TEST_EXCHANGE;

@Service
public class EmployeeProducer {

    private AmqpTemplate amqpTemplate;

    public EmployeeProducer(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public String produceEmployee(String empName, String empId, Integer empSalary) {
        Employee employee = new Employee();
        employee.setId(empId);
        employee.setName(empName);
        employee.setSalary(empSalary);
        amqpTemplate.convertAndSend(TEST_EXCHANGE, EMP_QUEUE_ROUTING_KEY, employee);
        return "Message sent to the RabbitMQ Successfully";
    }
}
