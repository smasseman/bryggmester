package se.bryggmester;

import org.junit.Assert;
import org.junit.Test;

import se.bryggmester.Temperature.Scale;

/**
 * @author jorgen.smas@entercash.com
 */
public class TemperatureTest {

	@Test
	public void testParse() {
		Temperature t = Temperature.parse("15 C");
		Assert.assertEquals(15f, t.getValue(), 0);
		Assert.assertEquals(Scale.CELCIUS, t.getScale());
	}
}
