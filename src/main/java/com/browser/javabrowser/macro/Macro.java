package com.browser.javabrowser.macro;

import com.browser.javabrowser.macro.step.IStep;
import java.util.LinkedList;
import java.util.List;

public abstract class Macro<T> implements IMacro{
    protected String name;
    protected Integer initialDelay;
    protected String url;
    protected LinkedList<IStep<T>> steps;

    public Macro() {}

    public Macro(String name, Integer initialDelay, String url, LinkedList<IStep<T>> steps) {
        this.initialDelay = initialDelay;
        this.name = name;
        this.url = url;
        this.steps = steps;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    // getters and setters for the purposes of JSON serialization
    // getters
    public String getName() {
        return this.name;
    }

    public Integer getInitialDelay() {
        return initialDelay;
    }

    public String getUrl() {
        return url;
    }

    public List<IStep<T>> getSteps() {
        return steps;
    }
    // -- * --

    // setters
    public void setName(String name) {
        this.name = name;
    }

    public void setInitialDelay(Integer initialDelay) {
        this.initialDelay = initialDelay;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setSteps(LinkedList<IStep<T>> steps) {
        this.steps = steps;
    }
    // -- * --
}