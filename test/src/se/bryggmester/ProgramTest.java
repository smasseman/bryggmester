package se.bryggmester;

import java.io.File;
import java.io.FileWriter;

import org.junit.Assert;
import org.junit.Test;

import se.bryggmester.instruction.InstructionType;

/**
 * @author jorgen.smas@entercash.com
 */
public class ProgramTest {

	@Test
	public void testParse() throws Exception {
		File f = File.createTempFile(getClass().getSimpleName(), ".brw");
		FileWriter w = new FileWriter(f);
		w.write("name=testcase\n");
		w.write("id=7\n");
		w.write("-\n");
		w.write("WAIT 10m");
		w.close();
		Program x = Program.parse(f);
		Assert.assertEquals("testcase", x.getName());
		Assert.assertEquals(new Long(7L), x.getId());
		Assert.assertEquals(1, x.getInstructions().size());
		Assert.assertEquals(InstructionType.WAIT, x.getInstructions().get(0)
				.getType());
	}
}
