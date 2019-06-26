package cn;


import javax.swing.*;


import java.awt.*;
import java.util.ArrayList;

public class EdgeComponent extends JComponent implements Cloneable{

	public Square right;
	public Square left;
    private Color color = Color.WHITE;
    private boolean free = true;
    public Point point;
    public ArrayList<Boolean> list1 =  new ArrayList<>();
    public ArrayList<Boolean> list2 =  new ArrayList<>();
   

    public EdgeComponent(int x, int y, int width, int height) {
        super();
        setLocation(x, y);
        setSize(width, height);
    }
    
    public void setPoint(int x,int y) {
    	this.point = new Point(x, y);
    }
    
    public Point getPoint() {
    	return this.point;
    }

    
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        boolean horizontal = getWidth() > getHeight();
        int midValue = (horizontal ? getHeight() : getWidth()) / 2;
        int alphaStep = free ? 255 / midValue : 0;
        for (int i = 0; i < midValue; i++) {
            g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 255 - alphaStep * i));
            if (horizontal) {
                g.drawLine(0, midValue + i, getWidth(), midValue + i);
                g.drawLine(0, midValue - i, getWidth(), midValue - i);
            } else {
                g.drawLine(midValue + i, 0, midValue + i, getHeight());
                g.drawLine(midValue - i, 0, midValue - i, getHeight());
            }
        }
    }
    @Override
	public Object clone() throws CloneNotSupportedException {
		EdgeComponent newEdge =  (EdgeComponent) super.clone();
		if(newEdge.right !=null) newEdge.right = (Square) right.clone();
		if(newEdge.left !=null)	newEdge.left = (Square) left.clone();
		return newEdge;
    }

}
