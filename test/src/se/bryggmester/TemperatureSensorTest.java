package se.bryggmester;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author jorgen.smas@entercash.com
 */
public class TemperatureSensorTest {

	private File file;

	@Before
	public void init() throws IOException {
		file = File.createTempFile(getClass().getName(), ".tmp");
	}

	@After
	public void destroy() {
		file.delete();
	}

	@Test
	public void testNo() throws IOException {
		try (FileWriter w = new FileWriter(file)) {
			w.write("00 00 00 00 00 00 00 00 00 : crc=00 NO\n"
					+ "50 05 4b 46 7f ff 0c 10 1c t=0\n");
			w.close();
			TemperatureSensor s = new TemperatureSensor();
			s.setFile(file);
			try {
				s.getTemperature();
			} catch (IOException expected) {
			}
		}
	}

	@Test
	public void testOk() throws IOException {
		checkParsedValue("10000", 10.0f);
		checkParsedValue("24875", 24.9f);
		checkParsedValue("24811", 24.8f);
	}

	private void checkParsedValue(String temp, float expected)
			throws IOException {
		try (FileWriter w = new FileWriter(file)) {
			w.write("8e 01 4b 46 7f ff 02 10 02 : crc=02 YES\n"
					+ "8e 01 4b 46 7f ff 02 10 02 t=" + temp + "\n");
			w.close();
			TemperatureSensor s = new TemperatureSensor();
			s.setFile(file);
			Assert.assertEquals(expected, s.getTemperature().getValue());
		}
	}
}
