package com.y_prod.vocabularymanager.data_holders;

import java.util.List;

public class TestAnswersHolder {

	private int trueAnsPosition;
	private int pressedAnsPosition;
	private List<String> variantsForButtons;
	
	public TestAnswersHolder(int truePosition, int pressedAnsP, List<String> textForButtons) {
		this.setTrueAnsPosition(truePosition);
		this.setPressedAnsPosition(pressedAnsP);
		this.setVariantsForButtons(textForButtons);
	}

	public int getTrueAnsPosition() {
		return trueAnsPosition;
	}

	public void setTrueAnsPosition(int trueAnsPosition) {
		this.trueAnsPosition = trueAnsPosition;
	}

	public int getPressedAnsPosition() {
		return pressedAnsPosition;
	}

	public void setPressedAnsPosition(int pressedAnsPosition) {
		this.pressedAnsPosition = pressedAnsPosition;
	}

	public List<String> getVariantsForButtons() {
		return variantsForButtons;
	}

	public void setVariantsForButtons(List<String> variantsForButtons) {
		this.variantsForButtons = variantsForButtons;
	}
	
}
