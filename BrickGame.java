import java.awt.*;


class BrickGame{
    private final int row;
    private  final int col;
    private final int width;
    private final int height;
    private boolean visible;
    private final int offsetX;
    private final Color color;
    public BrickGame(int row, int col, int width, int height, int offsetX, Color color)
    {
        this.row=row;
        this.col=col;
        this.width=width;
        this.height=height;
        this.visible=true;
        this.offsetX=offsetX;
        this.color=color;
    }
    public void draw(Graphics g)
    {
        if(visible)
        {
            g.setColor(color);
            g.fillRect(col*(width+10)+offsetX, row*(height+10)+50, width, height);

        }
    }
    public boolean isVisible()
    {
        return visible;
    }
    public void setVisible(boolean visible)
    {
        this.visible=visible;
    }
    public Rectangle getRect()
    {
        return new Rectangle(col*(width+10)+offsetX, row*(height+10)+50, width, height);
    }

}