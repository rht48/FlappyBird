package graphics.gui;

import events.StringCommand;

public class Label extends Gui {

    private StringCommand command = null;

    public Label(final float x, final float y, final String text, final int textSize, final String font) {
        super(x, y, 0, 0, text, textSize, font);
    }

    public Label(final float x, final float y, final StringCommand command, final int textSize, final String font) {
        super(x, y, 0, 0, "", textSize, font);
        this.command = command;
    }

    @Override
    public String getText() {
        if(command != null) {
            return command.execute();
        }
        return this.text;
    }
}
