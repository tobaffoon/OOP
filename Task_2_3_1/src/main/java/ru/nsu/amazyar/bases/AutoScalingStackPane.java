package ru.nsu.amazyar.bases;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.StackPane;

/**
 * A StackPane that <i>scales</i> its contents to fit (preserving aspect ratio),
 * or fill (scaling independently in X and Y) the available area.
 * <p>
 * Note <code>AutoScalingStackPane</code> applies to the contents a scaling
 * transformation rather than attempting to resize the contents.
 * <p>
 * If the contents is a Canvas with pixel dimension 50 by 50, after scaling the
 * Canvas still will have 50 by 50 pixels and the appearance may be pixelated
 * (this might be desired if the application is interfacing a camera and the
 * Canvas needs to match in size the camera's CCD size).
 * <p>
 * If the content contains FX Controls then these get magnified rather than
 * resized, that is all text and graphics are scaled (this might be desired for
 * Point of Sale full screen applications)
 * <p>
 * <h3>Known Limitations</h3>
 * Rescaling occurs only when the AutoScalingStackPane is resized, it does not
 * occur automatically if and when the content changes size.
 *
 * @author michaelellis
 */
public class AutoScalingStackPane extends StackPane {
    /**
     * Force scale transformation to be recomputed based on the size of this
     * <code>AutoScalingStackPane</code> and the size of the contents.
     */
    public void rescale() {
        if (!getChildren().isEmpty()) {
            getChildren().forEach(
                (c) -> Platform.runLater(() -> {  // run later to get proper boundsInLocal
                    double xScale = getWidth() / c.getBoundsInLocal().getWidth();
                    double yScale = getHeight() / c.getBoundsInLocal().getHeight();

                    switch (autoScale.get()) {
                        case FIT:
                            double scale = Math.min(xScale, yScale);
                            c.setScaleX(scale);
                            c.setScaleY(scale);
                            break;
                        case FILL:
                            c.setScaleX(xScale);
                            c.setScaleY(yScale);
                            break;
                        case NONE:
                        default:
                            c.setScaleX(1d);
                            c.setScaleY(1d);
                    }
                }));
        }
    }

    private void init() {
        widthProperty().addListener((b, o, n) -> rescale());
        heightProperty().addListener((b, o, n) -> rescale());
    }

    /**
     * No argument constructor required for Externalizable (need this to work
     * with SceneBuilder).
     */
    public AutoScalingStackPane() {
        super();
        init();
    }

    /**
     * AutoScale scaling options: {@link AutoScaleOption#NONE}, {@link AutoScaleOption#FILL},
     * {@link AutoScaleOption#FIT}
     */
    public enum AutoScaleOption {

        /**
         * No scaling - revert to behaviour of <code>StackPane</code>.
         */
        NONE,
        /**
         * Independently scaling in x and y so content fills whole region.
         */
        FILL,
        /**
         * Scale preserving content aspect ratio and center in available space.
         */
        FIT
    }

    // AutoScale Property
    private final ObjectProperty<AutoScaleOption>
        autoScale = new SimpleObjectProperty<AutoScaleOption>(this, "autoScale",
        AutoScaleOption.FIT);

    /**
     * AutoScalingStackPane scaling property
     *
     * @return AutoScalingStackPane scaling property
     * @see AutoScaleOption
     */
    public ObjectProperty<AutoScaleOption> autoScaleProperty() {
        return autoScale;
    }

    /**
     * Get AutoScale option
     *
     * @return the AutoScale option
     * @see AutoScaleOption
     */
    public AutoScaleOption getAutoScale() {
        return autoScale.getValue();
    }

    /**
     * Set the AutoScale option
     *
     * @see AutoScaleOption
     */
    public void setAutoScale(AutoScaleOption newAutoScaleOption) {
        autoScale.setValue(newAutoScaleOption);
    }
}