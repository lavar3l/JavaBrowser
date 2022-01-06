package com.browser.javabrowser.macro.step.busyWaiting;

import java.awt.Robot;
import java.util.List;
import java.util.function.Consumer;

public class RobotBusyWaiting extends BusyWaiting<List<Consumer<Robot>>> {
    public RobotBusyWaiting() { super(); }

    public RobotBusyWaiting(Integer tickCount) {
        super(tickCount);
    }

    @Override
    public void append(List<Consumer<Robot>> medium) {
        medium.add((Robot robby) -> {
            // busy waiting
           for (int i = 0; i < tickCount; i++) { }
        });
    }
}
