package se.bryggmester;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import se.bryggmester.Temperature.Scale;
import se.bryggmester.instruction.PumpInstruction;

import com.pi4j.io.gpio.GpioPinDigitalOutput;

/**
 * @author jorgen.smas@entercash.com
 */
public class PumpControllerTest {

	private Pump p;
	private PumpController c;
	private TemperatureSensorMock tempSensor;

	@Before
	public void init() throws IllegalArgumentException, IllegalAccessException,
			NoSuchFieldException, SecurityException {
		tempSensor = new TemperatureSensorMock();
		c = new PumpController();
		c.setTempSensor(tempSensor);
		p = new Pump();

		GpioPinDigitalOutput gpioPin = GpioTestUtil.getInstance().createPin(1);
		p.setPin(gpioPin);
		c.setPump(p);
		c.init();
	}

	@After
	public void destroy() throws InterruptedException {
		c.stop();
	}

	@Test
	public void test() throws InterruptedException {
		PumpInstruction i = new PumpInstruction(PumpState.ON, 10 * 1000,
				3 * 1000);
		c.execute(i);
		Thread.sleep(1000);
		assertPumpOn();
		Thread.sleep(10 * 1000);
		assertPumpOff();
		Thread.sleep(3 * 1000);
		assertPumpOn();
		c.stop();
		Thread.sleep(1000);
		assertPumpOff();
	}

	@Test
	public void test2() throws InterruptedException {
		PumpInstruction i = new PumpInstruction(PumpState.ON, 10 * 1000,
				3 * 1000);
		c.execute(i);
		Thread.sleep(1000);
		assertPumpOn();
		c.execute(new PumpInstruction(PumpState.ON, 60 * 1000, 6 * 1000));
		Thread.sleep(3 * 1000);
		assertPumpOn();
		c.stop();
		Thread.sleep(1000);
		assertPumpOff();
	}

	@Test
	public void testHeatSafety() throws InterruptedException {
		PumpInstruction i = new PumpInstruction(PumpState.ON, 0, 0);
		c.execute(i);
		Thread.sleep(1000);
		assertPumpOn();
		tempSensor.setCurrentTemp(new Temperature(80, Scale.CELCIUS));
		assertPumpOn();
		tempSensor.setCurrentTemp(new Temperature(99, Scale.CELCIUS));
		assertPumpOff();
		tempSensor.setCurrentTemp(new Temperature(80, Scale.CELCIUS));
		assertPumpOn();
	}

	private void assertPumpOff() {
		Assert.assertEquals(PumpState.OFF, p.getCurrentState());
	}

	private void assertPumpOn() {
		Assert.assertEquals(PumpState.ON, p.getCurrentState());
	}
}
