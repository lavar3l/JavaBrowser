package com.browser.javabrowser.macro.step.robotStep;

import java.awt.*;
import java.util.function.Consumer;

public class Type extends RobotStep {
    private String text;

    // default constructor for the purpose of JSON serialization
    public Type() {}
    public Type(String text) {
        this.text = text;
    }

    @Override
    protected Consumer<Robot> getStep() {
        return (Robot robby) -> {
            for (char letter : this.text.toCharArray()) {
                int key = java.awt.event.KeyEvent.getExtendedKeyCodeForChar(letter);
                robby.keyPress(key);
                robby.keyRelease(key);
            }
        };
    }

    // getters and setters for the purpose of JSON serialization
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
