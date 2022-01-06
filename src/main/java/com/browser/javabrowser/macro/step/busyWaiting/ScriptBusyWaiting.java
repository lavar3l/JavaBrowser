package com.browser.javabrowser.macro.step.busyWaiting;

public class ScriptBusyWaiting extends BusyWaiting<StringBuilder> {
    public ScriptBusyWaiting() { super(); }

    public ScriptBusyWaiting(Integer tickCount) {
        super(tickCount);
    }

    @Override
    public void append(StringBuilder medium) {
        medium
                .append("let i = 0;\n")
                .append("while (i < ")
                .append(this.tickCount)
                .append(") { i = i + 1; }");
    }
}
