
import javax.swing.*;


import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class WinnerFrame extends JFrame {
	String winner;
	int score;
	
	WinnerFrame(String name,int s){
		winner=name;
		score = s;
		initialize();
		this.setVisible(true);
		
		String path = "rank/"+MainFrame.H+MainFrame.W+".json";
		
		
		BufferedReader reader = null;
		String laststr = "";
		try {
//			FileInputStream fileInputStream = new FileInputStream(
//					path);
//			InputStreamReader inputStreamReader = new InputStreamReader(
//					fileInputStream, "UTF-8");
//			reader = new BufferedReader(inputStreamReader);
			InputStream is=this.getClass().getResourceAsStream(path); 
			reader=new BufferedReader(new InputStreamReader(is));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				laststr += tempString;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		laststr += "#";
		if(name==null) {
			laststr +="null";
		}else {
			laststr += name;
		}
		
		laststr += "#";
		laststr += String.valueOf(s);
		File file = new File("src\\rank\\"+ MainFrame.H+MainFrame.W + ".json");
		if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		FileWriter writer = null;
		try {
			writer = new FileWriter(file);
			for(int i = 0;i<laststr.length();i++) {
				writer.write(laststr.charAt(i));
			}
			writer.flush();
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void initialize() {
		setTitle("CS102A Project @Xiongzhuochen£¬Dengjunze");
		setSize(600, 275);
		setLocationRelativeTo(null); // Center the window.
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLayout(null);
		JPanel pnl = new JPanel();
		pnl.setBounds(45,60, 550, 75);
		pnl.setLayout(new GridLayout(1,1));
		JLabel str = new JLabel();
		str.setText("Game over!  "+winner+" is the winner!!!");
		str.setFont(new Font("ËÎÌå", Font.BOLD, 24));
		pnl.add(str);
		getContentPane().add(pnl);
	}
	
	public static void main(String[] args) {
		Runnable runnable = new Runner();
		SwingUtilities.invokeLater(runnable);
		
	}
	static class Runner implements Runnable {
		@Override
		public void run() {
			WinnerFrame WinnerFrame = new WinnerFrame("",0);
		}
	}
	@Override
	public Container getContentPane() {
		return getRootPane().getContentPane();
	}
}

