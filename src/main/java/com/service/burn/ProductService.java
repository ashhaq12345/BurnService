package com.service.burn;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.MessageProperties;
import com.service.burn.model.OvenParameter;
import com.service.burn.model.Product;

/**
 * <h2>ProductService class is used to create products which will be delivered to ovens.</h2><br>
 * Run the main method of the class to start product service.<br>
 * Create product by console input.<br>
 * This service will also get the burned product back from the ovens<br>
 * Run OvenService n times to create n oven so that the products created can be consumed by the ovens.
 * 
 * @author Ashfaqul Haque
 *
 */
public class ProductService 
{
    public static void main( String[] args ) throws IOException, TimeoutException
    {
        System.out.println("Product Service Started");
        openConnectionToGetBurnedProduct();
        ConnectionFactory factory = Configuration.getConnectionFactory(); //Get the connection settings to connect to rabbitMQ
        
        try (Connection connection = factory.newConnection();  // Get a new connection
        	Channel channel = connection.createChannel()) { // Create a new channel to pass message
        	channel.queueDeclare(Configuration.PRODUCT_QUEUE_NAME, true, false, false, null); // Declare a queue for message passing
            for(int j = 1; j <= Configuration.PRODUCT_COUNT; j++) {
            	try {
            		Product product = getProduct(j);
            		String json = Utility.getJson(product);
                    
                    channel.basicPublish("", Configuration.PRODUCT_QUEUE_NAME,  //Send the product to scheduler
                            MessageProperties.PERSISTENT_TEXT_PLAIN,
                            json.getBytes("UTF-8"));
                    
                    System.out.println("Sent: '" + product.toString() + "' | At " + Utility.getCurrentDateTime());	
                
            	} catch (Exception e) {
            		System.out.println(e.getMessage());
            	}
            }
        }
    }
    
    /**
     * Get a product randomly generated
     * @return created product.
     */
    private static Product getProduct(int id) {
    	
    	int temerature = Utility.getRandom(Configuration.MAX_BURNING_TEMPERATURE, Configuration.MIN_BURNING_TEMPERATURE);
    	int time = Utility.getRandom(Configuration.MAX_BURNING_TIME, Configuration.MIN_BURNING_TIME);
    	OvenParameter parameter = new OvenParameter(temerature, time);
    	Product product = new Product("Product " + id, parameter);
    	
    	return product;
    }
    
    /**
     * Open a connection to get burned product back from the oven.<br>
     * Gets the product back from a queue set in the configuration file.
     */
    private static void openConnectionToGetBurnedProduct() {
    	ConnectionFactory factory = Configuration.getConnectionFactory();
	    
    	try {
    		final Connection connection = factory.newConnection();
    	    final Channel channel = connection.createChannel();
    	    
    	    DeliverCallback deliverCallback = getDeliveryCallBack(channel);
    	    channel.queueDeclare(Configuration.OVEN_RESPONSE_QUEUE_NAME, true, false, false, null);

    	    channel.basicQos(Configuration.CONCURRENT_ASSIGNMENT_COUNT); // Determines how many product can be assigned to an oven without knowing whether the oven already has heavy work or not.

    	    channel.basicConsume(Configuration.OVEN_RESPONSE_QUEUE_NAME, false, deliverCallback, consumerTag -> { }); //Consume a product from oven and call deliverCallBack
    	    System.out.println("Connection Opened to Get Burned Product at " + Utility.getCurrentDateTime());
    	} catch (Exception ex) {
    		System.out.println(ex.getMessage());
    	}
    }
    
    /**
     * Get the function which will be called after a burned product is returned.
     * @param channel in which the burned product will get back.
     * @return DeliverCallBack function.
     */
    private static DeliverCallback getDeliveryCallBack(Channel channel) {
		  DeliverCallback deliverCallback = (consumerTag, delivery) -> {
		        String productJson = new String(delivery.getBody(), "UTF-8"); //Get message in given char set.
		        Product burnedProduct = Utility.getProduct(productJson);
		        System.out.println("Received Burned Product: '" + burnedProduct.toString() + "'. At " + Utility.getCurrentDateTime());
		        System.out.println("Received product was burned by oven: " + burnedProduct.getBurnedBy() );
		        channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false); //Let Postmaster (RabbitMQ) know that the delivery has been acknowledged
		    };
		    return deliverCallback;
	  }
}
