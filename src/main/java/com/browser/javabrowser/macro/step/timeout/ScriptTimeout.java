package com.browser.javabrowser.macro.step.timeout;

import com.browser.javabrowser.macro.step.IStep;

import java.util.List;

public class ScriptTimeout extends Timeout<StringBuilder> {
    public ScriptTimeout(Integer milliseconds, List<IStep<StringBuilder>> iSteps) {
        super(milliseconds, iSteps);
    }

    @Override
    protected void decorate(StringBuilder medium, Runnable f) {
        medium.append("setTimeout(() => {\n");
        f.run();
        medium.append("\n}, ").append(this.milliseconds.toString()).append(");");
    }
}
