package com.browser.javabrowser.macro.step.robotStep;

import com.browser.javabrowser.macro.step.IStep;

import java.awt.Robot;
import java.util.List;
import java.util.function.Consumer;

public abstract class RobotStep implements IStep<List<Consumer<Robot>>> {
    @Override
    public void append(List<Consumer<Robot>> medium) {
        medium.add(this.getStep());
    }

    protected abstract Consumer<Robot> getStep();
}
