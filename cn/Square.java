package cn;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Square extends JLabel implements Cloneable{
	public int cnt=0;
	public int x;
	public int y;
	public String name;
	public ArrayList<Integer> c = new ArrayList<>();
	public ArrayList<String> currentName = new ArrayList<>();
	public void setName(String name) {
		this.name=name;
		setText(name);
		setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 24));
	}
	public Square(String text, int horizontalAlignment) {
		super(text,horizontalAlignment);
	}
	public Square() {
		
	}

		
	
	public void setLocation(int x, int y, int size) {
		this.x=x;
		this.y=y;
		setBounds(x,y,size,size);
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		return (Square)super.clone();
	}
}
