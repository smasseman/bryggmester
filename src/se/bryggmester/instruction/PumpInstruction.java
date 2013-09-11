package se.bryggmester.instruction;

import se.bryggmester.PumpState;

/**
 * @author jorgen.smas@entercash.com
 */
public class PumpInstruction extends Instruction {

	private PumpState state;

	public PumpInstruction(PumpState s) {
		super(InstructionType.PUMP);
		this.state = s;
	}

	public PumpState getState() {
		return state;
	}

	public void setState(PumpState state) {
		this.state = state;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass().getSimpleName());
		builder.append("[state=");
		builder.append(state);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public String displayString() {
		return "Pump " + (state == PumpState.ON ? "på" : "av");
	}
}
