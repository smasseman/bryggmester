package se.bryggmester;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import se.bryggmester.Temperature.Scale;

/**
 * @author jorgen.smas@entercash.com
 */
public class HistoryLoggerTest {

	private File dir;

	@Before
	public void init() {
		dir = new File("/tmp/" + System.currentTimeMillis());
		dir.mkdir();
	}

	@After
	public void destroy() {
		File[] files = dir.listFiles();
		for (File f : files) {
			f.delete();
		}
		dir.delete();
	}

	@Test
	public void test() {
		HistoryLogger hl = new HistoryLogger();
		hl.setDirectory(dir);

		ProgramExecutor programExecutor = Mockito.mock(ProgramExecutor.class);
		Program program = new Program();
		program.setName("The program name");
		Mockito.when(programExecutor.getCurrentProgram()).thenReturn(program);
		hl.setProgramExecutor(programExecutor);

		HeatController heatController = Mockito.mock(HeatController.class);
		Mockito.when(heatController.getWantedTemp()).thenReturn(
				new Temperature(40f, Scale.CELCIUS));
		hl.setHeatController(heatController);

		TemperatureSensor tempSensor = Mockito.mock(TemperatureSensor.class);
		Mockito.when(tempSensor.getCurrentTemperature()).thenReturn(
				new Temperature(40f, Scale.CELCIUS));
		hl.setTempSensor(tempSensor);

		hl.notifyProgramChanged(0);
		hl.notifyTemperatureChanged(new Temperature(30f, Scale.CELCIUS));
		hl.notifyWantedTempChanged(new Temperature(35f, Scale.CELCIUS));
		hl.pumpStataNotify(PumpState.ON);
		hl.heatStateNotify(HeatState.OFF);
		hl.notifyProgramChanged(-1);
		List<HistoryEntry> entries = hl.getHistoryEntries();
		Assert.assertEquals(1, entries.size());

		HistoryEntry e = entries.get(0);
		Assert.assertEquals("The program name", e.getProgramName());
		long dateDiff = System.currentTimeMillis() - e.getDate().getTime();
		Assert.assertTrue(dateDiff < 10 * 1000 && dateDiff > 0);
		long idDiff = System.currentTimeMillis() - e.getId();
		Assert.assertTrue(idDiff < 10 * 1000 && idDiff > 0);

		for (HistoryData d : e.getData()) {
			System.out.println(d);
		}
	}
}
