package com.service.burn.model;

import com.service.burn.Configuration;

/**
 * <h2>OvenParameter class represents oven settings.</h2>
 * 
 * <h2>Product class has 3 properties-</h2>
 * <b>temperature:</b> Temperature for the oven.</br>
 * <b>burningTime:</b> Run time for the oven in seconds.<br><br>
 * 
 * @author Ashfaqul Haque
 *
 */
public class OvenParameter{
	/**
	 * Temperature for the oven.
	 */
	private int temperature;
	
	/**
	 * Run time for the oven in seconds.
	 */
	private int burningTime;
	
	/**
	 * <p>Constructor to initialize an oven parameter with given temperature and burning time.</p>
	 * 
	 * @param temperature Temperature for the oven.
	 * @param burningTime Run time for the oven in seconds.
	 */
	public OvenParameter(int temperature, int burningTime) {
		this.setBurningTime(burningTime);
		this.setTemperature(temperature);
	}
	
	/**
	 * <p>Constructor to initialize an oven parameter with default temperature and burning time.</p>
	 */
	public OvenParameter() {
		this.setDefaultBurningTime();
		this.setDefaultTemperature();
	}
	
	/**
	 * <p>Get temperature of the current oven parameter.</p>
	 * @return temperature.
	 */
	public int getTemparature() {
		return this.temperature;
	}
	
	/**
	 * <p>Get burning time of the current oven parameter</p>
	 * @return burning time
	 */
	public int getBurningTime() {
		return this.burningTime;
	}
	
	@Override
	public String toString() {
		return "Oven Parameter: Temparature: " + this.temperature + " | Burning Time: " + this.burningTime;
	}
	
	/**
	 * <p>Set temperature of the current oven settings.</p>
	 * If given temperature is below Zero, default temperature is set<br><br>
	 * @param temperature temperature
	 */
	private void setTemperature(int temperature) {
		if (temperature < 0)
			this.setDefaultTemperature();
		
		this.temperature = temperature;
	}
	
	/**
	 * <p>Set burning time of the current oven settings.</p>
	 * If provided time is below 1 second, default burning time is set.<br><br>
	 * 
	 * @param time burning time.
	 */
	private void setBurningTime(int time) {
		if (time < 1)
			this.setDefaultBurningTime();
		
		this.burningTime = time;
	}
	
	/**
	 * <p>Set default temperature provided in the configuration</p>
	 * Default temperature can be updated by editing DEFAULT_TEMPERATURE_FOR_BURNING variable in Configuration Class. 
	 */
	private void setDefaultTemperature() {
		this.temperature = Configuration.DEFAULT_TEMPERATURE_FOR_BURNING;
	}

	/**
	 * <p>Set default burning time provided in the configuration</p>
	 * Default burning time can be updated by editing DEFAULT_BURNING_TIME variable in Configuration Class. 
	 */
	private void setDefaultBurningTime() {
		this.burningTime = Configuration.DEFAILT_BURNING_TIME;
	}
}
