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
		Assert.assertEquals("ON", i.getType().export(i));

		i = (PumpInstruction) InstructionType.PUMP.parse("OFF");
		Assert.assertEquals(PumpState.OFF, i.getState());
		Assert.assertEquals("OFF", i.getType().export(i));
	}
}
