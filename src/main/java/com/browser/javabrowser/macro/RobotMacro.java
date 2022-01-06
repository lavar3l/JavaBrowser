package com.browser.javabrowser.macro;

import com.browser.javabrowser.BrowserController;
import com.browser.javabrowser.json.adapters.macro.step.RobotStepAdapter;
import com.browser.javabrowser.json.serializer.Serializer;
import com.browser.javabrowser.macro.step.IStep;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class RobotMacro extends Macro<List<Consumer<Robot>>> {
    private double recordedWidth;
    private double recordedHeight;
    private double realWidth;
    private double realHeight;
    private double realX;
    private double realY;

    private boolean macroCompleted = false;


    public RobotMacro() { super(); }
    public RobotMacro(String name, Integer initialDelay, String url, LinkedList<IStep<List<Consumer<Robot>>>> list,
                      double recordedHeight, double recordedWidth) {
        super(name, initialDelay, url, list);

        this.recordedHeight = recordedHeight;
        this.recordedWidth = recordedWidth;
    }

    @Override
    public void execute(BrowserController controller) {
        try {
            Robot robby = new Robot();
            this.executeRobotCommands(robby, controller);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private void executeRobotCommands(Robot robby, BrowserController controller) {
        // set the dimensions of the window to match the macro
        boolean dimensionsChanged = this.changeDimensions(controller, this.recordedWidth, this.recordedHeight, -10, 0);

        LinkedList<Consumer<Robot>> medium = new LinkedList<>();

        for (IStep<List<Consumer<Robot>>> step : steps)
            step.append(medium);

        WebEngine engine = controller.navigateActiveTab(url);

        // start robot when the url loads
        engine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                if (newState != Worker.State.SUCCEEDED) return;

                // execute robot commands using robby on a proper thread
                Thread robot = new Thread(() -> {
                    // initial delay parameter indicates how much to wait until the page fully loads
                    try {
                        Thread.sleep(initialDelay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    for (Consumer<Robot> f : medium) {
                        f.accept(robby);
                    }

                    // resize the window back
                    if (dimensionsChanged)
                        changeDimensions(controller, realWidth, realHeight, realX, realY);
                });

                if (!macroCompleted) {
                    macroCompleted = true;
                    robot.start();
                }
            }
        });
    }

    private boolean changeDimensions(BrowserController controller, double width, double height, double x, double y) {
        try {
            this.realHeight = controller.getHeight();
            this.realWidth = controller.getWidth();
            this.realX = controller.getX();
            this.realY = controller.getY();

            controller.setHeight(height);
            controller.setWidth(width);
            controller.setX(x);
            controller.setY(y);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static RobotMacro deserialize(String path) {
        Serializer serializer = new Serializer();
        return serializer.deserialize(RobotMacro.class, path, serializer.getCustomGson(IStep.class, new RobotStepAdapter()));
    }

    // getters and setters for the purpose of JSON serialization
    // getters
    public double getRecordedWidth() {
        return recordedWidth;
    }

    public double getRecordedHeight() {
        return recordedHeight;
    }
    // -- * --

    // setters
    public void setRecordedWidth(double recordedWidth) {
        this.recordedWidth = recordedWidth;
    }

    public void setRecordedHeight(double recordedHeight) {
        this.recordedHeight = recordedHeight;
    }
    // -- * --
}
