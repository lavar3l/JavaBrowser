package com.browser.javabrowser.macro.step;

public class Script implements IStep<StringBuilder> {
    private String contents;

    // a default constructor for the purposes of JSON serialization
    public Script() {}

    public Script(String contents) {
        this.contents = contents;
    }

    @Override
    public void append(StringBuilder medium) {
        medium.append(this.contents);
    }

    // getters and setters for the purposes of JSON serialization
    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
