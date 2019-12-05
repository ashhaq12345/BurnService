package com.service.burn;

import com.rabbitmq.client.ConnectionFactory;

/**
 * Configuration class store several configuration variables and methods to tweak the project.
 * @author Ashfaqul Haque
 *
 */
public class Configuration {
	/**
	 * States the Id with which the product id will start.
	 */
	public static int productIdCounter = 0;
	
	/**
	 * Product queue name which will be used in the rabbitmq to transfer product to ovens.
	 */
	public static final String PRODUCT_QUEUE_NAME = "product_queue";
	
	/**
	 * Oven response queue name which will be used in the rabbitmq to transfer the burned product back.
	 */
	public static final String OVEN_RESPONSE_QUEUE_NAME = "oven_response";
	
	/**
	 * Date time pattern to show in the console.
	 */
	public static final String DATE_TIME_PATTERN = "yyyy/MM/dd HH:mm:ss";
	
	/**
	 * his tells RabbitMQ not to give more than one message to a worker at a time. Or, in other words, don't dispatch a new message to a worker until it has processed and acknowledged the previous one. Instead, it will dispatch it to the next worker that is not still busy.
	 */
	public static final int CONCURRENT_ASSIGNMENT_COUNT = 1;
	
	/**
	 * Default temperature to burn products if any other temperature is not provided.
	 */
	public static final int DEFAULT_TEMPERATURE_FOR_BURNING = 230;
	
	/**
	 * Maximum burning temperature of a product
	 */
	public static final int MAX_BURNING_TEMPERATURE = 500;
	
	/**
	 * Minimum burning temperature of a product
	 */
	public static final int MIN_BURNING_TEMPERATURE = 100;
	
	/**
	 * Default burning time to run the oven for a product if any other time is not provided.
	 */
	public static final int DEFAILT_BURNING_TIME = 10;
	
	/**
	 * Maximum burning time of a product
	 */
	public static final int MAX_BURNING_TIME = 20;
	
	/**
	 * Minimum burning time of a product
	 */
	public static final int MIN_BURNING_TIME = 5;
	
	/**
	 * Number of product to be prepared
	 */
	public static final int PRODUCT_COUNT = 20;
	
	/**
	 * This method is used to create a connection factory which will be used to connect with rabbitMQ messaging system.<br>
	 * By default rabbitMQ runs at localhost in port 5672 with both username and password 'guest'<br>
	 * If any of these are changed, update it accordingly here to get a successful connection.
	 * @return connection factory which will be used to communicate with PostMaster (RabbitMQ).
	 */
	public static ConnectionFactory getConnectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		factory.setPort(5672);
		factory.setUsername("guest");
		factory.setPassword("guest");
		
		return factory;
	}
}
