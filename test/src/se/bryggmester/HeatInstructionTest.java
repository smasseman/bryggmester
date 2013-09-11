package se.bryggmester;

import org.junit.Assert;
import org.junit.Test;

import se.bryggmester.instruction.HeatInstruction;
import se.bryggmester.instruction.InstructionType;

/**
 * @author jorgen.smas@entercash.com
 */
public class HeatInstructionTest {

	@Test
	public void testParse() {
		HeatInstruction i = (HeatInstruction) InstructionType.HEAT
				.parse("10.0");
		Assert.assertEquals(10.0f, i.getTemperature().getValue(), 0);
		Assert.assertEquals("10.0", i.getType().export(i));

		i = (HeatInstruction) InstructionType.HEAT.parse("OFF");
		Assert.assertNull(i.getTemperature());
		Assert.assertEquals("OFF", i.getType().export(i));
	}
}
