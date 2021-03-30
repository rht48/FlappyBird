package graphics.gui;

import graphics.color.Color;
import model.game.score.Score;

import java.util.List;

public class Panel {
    private final float x;
    private final float y;

    private final float lenX;
    private final float lenY;

    private final List<Button> buttons;
    private final Score score;
    private final Color color;

    public Panel(final float x, final float y, final float lenX, final float lenY, final List<Button> buttons, final Color color, final Score score) {
        this.x = x;
        this.y = y;
        this.lenX = lenX;
        this.lenY = lenY;
        this.buttons = buttons;
        this.color = color;
        this.score = score;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getLenX() {
        return lenX;
    }

    public float getLenY() {
        return lenY;
    }

    public List<Button> getButtons() {
        return buttons;
    }

    public Score getScore() {
        return score;
    }

    public Color getColor() {
        return color;
    }
}
