package com.service.burn;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.MessageProperties;
import com.service.burn.model.OvenParameter;
import com.service.burn.model.Product;

/**
 * OvenService class is used to create an oven which can be used to burn products.<br>
 * Run the main method in the class to create an oven.<br>
 * Run this class <b>N</b> times to get N number of ovens.<br>
 * Product will be delivered to an oven which is not busy by the scheduler (RabbitMQ).<br>
 * If an oven is destroyed before completely burning a product, the product will be sent to another oven which is free and running.
 * @author Ashfaqul Haque
 *
 */
public class OvenService {
	
	public static void main(String[] argv) throws Exception {
	    ConnectionFactory factory = Configuration.getConnectionFactory(); //Get the connection settings to connect to rabbitMQ
	    
	    final Connection connection = factory.newConnection(); //Get a new connection with given settings
	    final Channel channel = connection.createChannel(); //Create a channel with RabbitMQ to communicate
	    
	    DeliverCallback deliverCallback = getDeliveryCallBack(channel);
	    channel.queueDeclare(Configuration.PRODUCT_QUEUE_NAME, true, false, false, null); // This will declare a queue if not exists, otherwise it will listen the change in the queue
	    System.out.println(" [*] Waiting for products to burn. To exit press CTRL+C");
	
	    channel.basicQos(Configuration.CONCURRENT_ASSIGNMENT_COUNT);
	
	    channel.basicConsume(Configuration.PRODUCT_QUEUE_NAME, false, deliverCallback, consumerTag -> { });
	}
	
	/**
	 * Start the oven to burn the product.
	 * @param parameter oven parameter which has to be set to start burning.
	 */
	private static void startBurning(OvenParameter parameter) {
		try {
			Thread.sleep(parameter.getBurningTime() * 1000); //Converting into millisecond
		} catch (InterruptedException _ignored) {
			Thread.currentThread().interrupt();
		}
	}
	
	/**
	 * Create a deliver call back class which will be called after a product is delivered to this oven.<br>
	 * After a product is delivered to this oven, this will start burning the product considering burning requirement of the product<br>
	 * This method will also send the product back after completing the burn.
	 * @param channel the channel with which the product will be delivered to the oven
	 * 
	 * @return call back function.
	 */
	private static DeliverCallback getDeliveryCallBack(Channel channel) {
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String productJson = new String(delivery.getBody(), "UTF-8");
	        Product productToBurn = Utility.getProduct(productJson);
	        System.out.println("Received: '" + productToBurn.toString() + "' By Oven: " + consumerTag );
	        OvenParameter parameter = productToBurn.getOvenParameter();
	        System.out.println("Over Parameter Set: '" + parameter.toString() + "' To Oven: " + consumerTag);
	        try {
	        	System.out.println("Burning Started At '" + Utility.getCurrentDateTime() + "' By Oven: " + consumerTag);
	        	startBurning(parameter);
	        	System.out.println("Burning Ended At '" + Utility.getCurrentDateTime() + "' By Oven: " + consumerTag);
	        } finally {
	            System.out.println("Done" + " By Oven: " + consumerTag);
	            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false); //Let Postmaster (RabbitMQ) know that the delivery has been acknowledged
	            returnProductAfterBurning(productToBurn, consumerTag);
	        }
	    };
	    return deliverCallback;
	}
	
	/**
	 * This method returns the given product to product service.
	 * 
	 * @param product the product which will be returned
	 */
	private static void returnProductAfterBurning(Product product, String consumerTag) {
		ConnectionFactory factory = Configuration.getConnectionFactory();
	        
	    try (Connection connection = factory.newConnection();
        	Channel channel = connection.createChannel()) {
        	channel.queueDeclare(Configuration.OVEN_RESPONSE_QUEUE_NAME, true, false, false, null);
        	product.setBurnedBy(consumerTag);
            String json = Utility.getJson(product);
            
            channel.basicPublish("", Configuration.OVEN_RESPONSE_QUEUE_NAME,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    json.getBytes("UTF-8"));
            
            System.out.println("Product Sent back After Burning: '" + product.toString() + "' By Oven: " + consumerTag);
        } catch (Exception e) {
			System.out.println("Exception Occurred: '" + e.getMessage() + "'");
		}
	}
}
