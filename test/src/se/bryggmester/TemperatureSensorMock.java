package se.bryggmester;

import se.bryggmester.Temperature.Scale;

/**
 * @author jorgen.smas@entercash.com
 */
public class TemperatureSensorMock extends TemperatureSensor {

	public TemperatureSensorMock() {
		setCurrentTemp(new Temperature(20, Scale.CELCIUS));
	}

	@Override
	public void setCurrentTemp(Temperature temp) {
		super.setCurrentTemp(temp);
	}
}
