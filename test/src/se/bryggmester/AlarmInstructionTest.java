package se.bryggmester;

import org.junit.Assert;
import org.junit.Test;

import se.bryggmester.instruction.AlarmInstruction;
import se.bryggmester.instruction.InstructionType;

/**
 * @author jorgen.smas@entercash.com
 */
public class AlarmInstructionTest {

	@Test
	public void testParse() {
		AlarmInstruction i = (AlarmInstruction) InstructionType.ALARM
				.parse("sound.wav|WAIT|Hello World.");
		Assert.assertEquals("sound.wav", i.getAlarmName());
		Assert.assertEquals(AlarmInstruction.Type.WAIT, i.getAlarmType());
		Assert.assertEquals("Hello World.", i.getMessage());

		Assert.assertEquals("sound.wav|WAIT|Hello World.", i.getType()
				.export(i));
	}
}
