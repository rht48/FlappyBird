package graphics.gui;

public class Button {

    private final float x;
    private final float y;

    private final float lenX;
    private final float lenY;

    private final Command command;

    public Button(final float x, final float y, final float lenX, final float lenY, final Command command) {
        this.x = x;
        this.y = y;
        this.lenX = lenX;
        this.lenY = lenY;
        this.command = command;
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

    public void click(final float mouseX, final float mouseY) {
        if(mouseX >= x && mouseX <= x + lenX && mouseY >= y && mouseY <= y + lenY) {
            this.command.execute();
        }
    }
}
