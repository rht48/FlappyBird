package graphics.gui;

import events.Clickable;
import events.Command;
import graphics.color.Color;

public class Button extends Gui implements Clickable {

    private final Command command;
    protected final Color color;

    public Button(final float x, final float y,
                  final float lenX, final float lenY,
                  final String text, final Color color,
                  final int textSize, final String font,
                  final Command command) {
        super(x, y, lenX, lenY, text, textSize, font);
        this.color = color;
        this.command = command;
    }

    public Color getColor() {
        return this.color;
    }

    public String getFont() {
        return font;
    }

    @Override
    public void click(final float mouseX, final float mouseY) {
        if(mouseX >= x && mouseX <= x + lenX && mouseY >= y && mouseY <= y + lenY) {
            this.command.execute();
        }
    }
}
