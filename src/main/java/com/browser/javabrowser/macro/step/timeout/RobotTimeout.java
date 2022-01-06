package com.browser.javabrowser.macro.step.timeout;

import com.browser.javabrowser.macro.step.IStep;
import java.awt.Robot;
import java.util.List;
import java.util.function.Consumer;

public class RobotTimeout extends Timeout<List<Consumer<Robot>>> {
    public RobotTimeout() { super(); }

    public RobotTimeout(Integer milliseconds, List<IStep<List<Consumer<Robot>>>> steps) {
        super(milliseconds, steps);
    }

    @Override
    protected void decorate(List<Consumer<Robot>> medium, Runnable f) {
        medium.add((Robot robby) -> {
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //robby.delay(milliseconds);
        });
    }
}
