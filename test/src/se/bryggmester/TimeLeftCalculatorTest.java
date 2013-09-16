package se.bryggmester;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import se.bryggmester.instruction.AlarmInstruction;
import se.bryggmester.instruction.AlarmInstruction.Type;
import se.bryggmester.instruction.HeatInstruction;
import se.bryggmester.instruction.Instruction;
import se.bryggmester.instruction.PumpInstruction;
import se.bryggmester.instruction.WaitInstruction;
import se.bryggmester.util.TimeUnit;

/**
 * @author jorgen.smas@entercash.com
 */
public class TimeLeftCalculatorTest {

	private TimeLeftCalculator c;
	private Temperature t;
	private List<Instruction> instructions;

	@Before
	public void init() {
		this.c = new TimeLeftCalculator();
		this.t = Temperature.createCelcius(30);
		this.instructions = new LinkedList<Instruction>();
	}

	@Test
	public void testWaitInstruction() {
		instructions.add(new WaitInstruction(20));
		check(20);
	}

	@Test
	public void testAlarmInstruction() {
		instructions
				.add(new AlarmInstruction("The name", Type.WAIT, "The msg"));
		check(0);
	}

	@Test
	public void testPumpInstruction() {
		instructions.add(new PumpInstruction(PumpState.ON, 100, 10));
		check(0);
	}

	@Test
	public void testHeatInstruction() {
		this.t = Temperature.createCelcius(20);
		instructions.add(new HeatInstruction(Temperature.createCelcius(50)));
		check(TimeUnit.MINUTE.asMillis(50 - 20));
	}

	@Test
	public void testHeatWaitHeat() {
		this.t = Temperature.createCelcius(20);
		long expected = 0;

		instructions.add(new HeatInstruction(Temperature.createCelcius(50)));
		expected += TimeUnit.MINUTE.asMillis(50 - 20);

		instructions.add(new WaitInstruction(100));
		expected += 100;

		instructions.add(new HeatInstruction(Temperature.createCelcius(90)));
		expected += TimeUnit.MINUTE.asMillis(90 - 50);

		check(expected);
	}

	private void check(long expectedTime) {
		Assert.assertEquals(expectedTime, c.calculateTimeLeft(instructions, t));
	}
}
