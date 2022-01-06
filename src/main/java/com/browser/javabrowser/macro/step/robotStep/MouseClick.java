package com.browser.javabrowser.macro.step.robotStep;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.function.Consumer;

public class MouseClick extends RobotStep {
    private String button;
    private int x;
    private int y;
    private int holdSeconds;

    // default constructor for the purposes of JSON serialization
    public MouseClick() {}
    public MouseClick(String button, int x, int y, int holdSeconds) {
        this.button = button;
        this.x = x;
        this.y = y;
        this.holdSeconds = holdSeconds;
    }

    @Override
    protected Consumer<Robot> getStep() {
        int button = this.button.equals("right") ? InputEvent.BUTTON2_DOWN_MASK : InputEvent.BUTTON1_DOWN_MASK;
        return (Robot robby) -> {
            robby.mouseMove(this.x, this.y);
            robby.mousePress(button);
            robby.delay(this.holdSeconds);
            robby.mouseRelease(button);
        };
    }

    // getters and setters for the purpose of JSON serialization
    // getters
    public String getButton() {
        return button;
    }

    public int getY() {
        return y;
    }

    public int getHoldSeconds() {
        return holdSeconds;
    }

    public int getX() {
        return x;
    }
    // -- * --

    // setters
    public void setButton(String button) {
        this.button = button;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setHoldSeconds(int holdSeconds) {
        this.holdSeconds = holdSeconds;
    }
    // -- * --
}
