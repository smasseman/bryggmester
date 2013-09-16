package se.bryggmester;

import org.junit.Assert;
import org.junit.Test;

import se.bryggmester.instruction.InstructionType;
import se.bryggmester.instruction.PumpInstruction;

/**
 * @author jorgen.smas@entercash.com
 */
public class PumpInstructionTest {

	@Test
	public void testParse() {
		PumpInstruction i = (PumpInstruction) InstructionType.PUMP.parse("ON");
		Assert.assertEquals(PumpState.ON, i.getState());
		Assert.assertEquals("ON|0|0", i.getType().export(i));

		i = (PumpInstruction) InstructionType.PUMP.parse("OFF");
		Assert.assertEquals(PumpState.OFF, i.getState());
		Assert.assertEquals("OFF|0|0", i.getType().export(i));
	}

	@Test
	public void testParseWithIntervals() {
		PumpInstruction i = (PumpInstruction) InstructionType.PUMP
				.parse("ON|3m|30s");
		Assert.assertEquals(PumpState.ON, i.getState());
		Assert.assertEquals(3 * 60 * 1000, i.getRunInterval());
		Assert.assertEquals(30 * 1000, i.getPausInterval());

		i = (PumpInstruction) InstructionType.PUMP.parse(InstructionType.PUMP
				.export(i));

		Assert.assertEquals(PumpState.ON, i.getState());
		Assert.assertEquals(3 * 60 * 1000, i.getRunInterval());
		Assert.assertEquals(30 * 1000, i.getPausInterval());

	}
}
