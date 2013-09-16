package se.bryggmester;

import java.util.HashMap;
import java.util.Map;

import se.bryggmester.pi4j.GpioProviderSimulator;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

/**
 * @author jorgen.smas@entercash.com
 */
public class GpioTestUtil {

	private static GpioTestUtil instance;

	public static synchronized GpioTestUtil getInstance() {
		if (instance == null) {
			instance = new GpioTestUtil();
		}
		return instance;
	}

	private GpioController gpio;
	private Map<String, GpioPinDigitalOutput> pinCache = new HashMap<>();

	private GpioTestUtil() {
		GpioFactory.setDefaultProvider(new GpioProviderSimulator());
		this.gpio = GpioFactory.getInstance();
	}

	public GpioPinDigitalOutput createPin(int i) {
		try {
			String nr = String.valueOf(i);
			if (nr.length() < 2)
				nr = "0" + nr;
			String pinName = "GPIO_" + nr;
			if (pinCache.containsKey(pinName)) {
				return pinCache.get(pinName);
			} else {
				Pin pn = (Pin) RaspiPin.class.getField(pinName).get(null);
				GpioPinDigitalOutput gpioPin = gpio.provisionDigitalOutputPin(
						pn, null, PinState.LOW);
				pinCache.put(pinName, gpioPin);
				return gpioPin;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
