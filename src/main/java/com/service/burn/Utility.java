package com.service.burn;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.service.burn.model.Product;

/**
 * Utility class contains several utility functions which are used throughout the project.
 * @author Ashfaqul Haque.
 *
 */
public class Utility {
	
	/**
	 * Converts given product into JSON String.
	 * @param product which will be converted into JSON String
	 * @return Converted JSON String
	 */
	public static String getJson(Product product) {
		Gson gson = getGson();
        String json = gson.toJson(product); // Convert product into JSON String.
        return json;
	}
	
	/**
	 * Converts given JSON string into product object.
	 * @param json which will be converted
	 * @return product
	 * @throws JsonSyntaxException
	 */
	public static Product getProduct(String json) throws JsonSyntaxException {
        Gson gson = getGson();
        try {
        	Product product = gson.fromJson(json, Product.class); // Converts JSON into product.
            return product;
        } catch (JsonSyntaxException ex) {
        	throw ex;
        }
	}
	
	/**
	 * <p>Get current date time.</p>
	 * Date time format is set in the Configuration class.<br>
	 * Date time format can be updated by editing DATE_TIME_PATTERN variable in the Configuration class.
	 * <p>
     * This method will create a formatter based on a simple
     * <a href="#patterns">pattern of letters and symbols</a>
     * as described in the class documentation.
     * For example, {@code d MMM uuuu} will format 2011-12-03 as '3 Dec 2011'.
     * <p>
	 * @return current formatted DateTime string.
	 */
	public static String getCurrentDateTime() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Configuration.DATE_TIME_PATTERN);  
		LocalDateTime now = LocalDateTime.now();  
		return formatter.format(now);  
	}
	
	/**
	 * Generate a random number between given range
	 * @param max maximum number which can be generated
	 * @param min minimum number which can be generated
	 * @return random number
	 */
	public static int getRandom(int max, int min) {
		return (int)((Math.random() * ((max - min) + 1)) + min);
	}
	
	/**
	 * Get a Gson object from GsonBuilder.
	 * @return Gson object.
	 */
	private static Gson getGson() {
		GsonBuilder builder = new GsonBuilder();
        return builder.create();
	}
}
