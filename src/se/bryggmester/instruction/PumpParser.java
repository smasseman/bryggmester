package se.bryggmester.instruction;

import se.bryggmester.PumpState;

/**
 * @author jorgen.smas@entercash.com
 */
public class PumpParser extends InstructionParser<PumpInstruction> {

	@Override
	public PumpInstruction parse(String string) {
		return new PumpInstruction(PumpState.valueOf(string));
	}

	@Override
	public String myParse(PumpInstruction i) {
		return i.getState().name();
	}

	@Override
	Class<PumpInstruction> getInstructionType() {
		return PumpInstruction.class;
	}
}
