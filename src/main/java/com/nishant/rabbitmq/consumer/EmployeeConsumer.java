package com.nishant.rabbitmq.consumer;

import com.nishant.rabbitmq.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static com.nishant.rabbitmq.util.Constants.EMP_QUEUE;

@Service
public class EmployeeConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeConsumer.class);

    @RabbitListener(queues = EMP_QUEUE)
    public void consumeEmployee(Employee employee) throws Exception {
        LOGGER.info("Employee Message Received " + employee);
        if (employee.getSalary() < 0) {
            throw new Exception("Employee Salary Cannot Be In Negative");
        }
    }
}
