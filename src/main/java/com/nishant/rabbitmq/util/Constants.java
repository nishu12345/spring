package com.nishant.rabbitmq.util;

public interface Constants {

    String TEST_EXCHANGE = "test-exchange";
    String TEST_DEAD_LETTER_EXCHANGE = "dead-letter-exchange";
    String EMP_DEAD_LETTER_QUEUE = "employee-dead-letter-queue";
    String EMP_DEAD_LETTER_ROUTING_KEY = "employee-dead-letter-routing";
    String EMP_QUEUE = "employee-queue";
    String EMP_QUEUE_ROUTING_KEY = "employee-queue-routing";

    interface UrlMapping{
        String BASE_URL = "/rabbit/test/";
        String EMP_PRODUCE = "employee-to-queue";
    }
}
