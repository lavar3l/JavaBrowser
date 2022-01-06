package com.browser.javabrowser.macro.step.busyWaiting;

import com.browser.javabrowser.macro.step.IStep;

public abstract class BusyWaiting<T> implements IStep<T> {
    protected Integer tickCount;

    // a default constructor for the purposes of JSON serialization
    public BusyWaiting() {}

    public BusyWaiting(Integer tickCount) {
        this.tickCount = tickCount;
    }

    // getters and setters for the purposes of JSON serialization
    public Integer getTickCount() {
        return tickCount;
    }

    public void setTickCount(Integer tickCount) {
        this.tickCount = tickCount;
    }
}
