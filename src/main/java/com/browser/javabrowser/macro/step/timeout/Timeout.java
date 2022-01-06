package com.browser.javabrowser.macro.step.timeout;
import com.browser.javabrowser.macro.step.IStep;
import java.util.List;

public abstract class Timeout<T> implements IStep<T> {
    protected Integer milliseconds;
    private List<IStep<T>> steps;

    // a default constructor for the purposes of JSON serialization
    public Timeout() {}

    public Timeout(Integer milliseconds, List<IStep<T>> steps) {
        this.milliseconds = milliseconds;
        this.steps = steps;
    }

    @Override
    public void append(T medium) {
        this.decorate(medium, () -> {
            for (IStep<T> step : this.steps) {
                step.append(medium);
            }
        });
    }

    protected abstract void decorate(T medium, Runnable f);

    // getters and setters for the purposes of JSON serialization
    // getters
    public Integer getMilliseconds() {
        return milliseconds;
    }

    public List<IStep<T>> getSteps() {
        return steps;
    }
    // -- * --

    // setters
    public void setMilliseconds(Integer milliseconds) {
        this.milliseconds = milliseconds;
    }

    public void setSteps(List<IStep<T>> steps) {
        this.steps = steps;
    }
    // -- * --
}
