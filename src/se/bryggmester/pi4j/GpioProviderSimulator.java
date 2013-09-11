package se.bryggmester.pi4j;

import com.pi4j.io.gpio.GpioProviderBase;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;

/**
 * @author jorgen.smas@entercash.com
 */
public class GpioProviderSimulator extends GpioProviderBase {

	public GpioProviderSimulator() {
	}

	@Override
	public String getName() {
		return "RaspberryPi GPIO Provider";
	}

	@Override
	public void setState(Pin pin, PinState state) {
		super.setState(pin, state);
		System.out.println(pin.getName() + " is " + state);
	}
}
