package com.browser.javabrowser.macro.step.robotStep;

import java.awt.*;
import java.util.function.Consumer;

public class MouseWheel extends RobotStep {
    private int notches;

    // default constructor for the purposes of JSON serialization
    public MouseWheel() {}
    public MouseWheel(int notches) {
        this.notches = notches;
    }

    @Override
    protected Consumer<Robot> getStep() {
        return (Robot robby) -> {
            robby.mouseWheel(this.notches);
        };
    }

    // getters and setters for the purposes of JSON serialization
    public int getNotches() {
        return notches;
    }

    public void setNotches(int notches) {
        this.notches = notches;
    }

}
