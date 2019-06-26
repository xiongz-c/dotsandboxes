
import javax.swing.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class RankFrame extends JFrame {
	public ArrayList<String> arr = new ArrayList<>();
	String[] name = new String[3];
	int[] Score = new int[3];
	public RankFrame() {
		upload();
		initialize();
		this.setVisible(true);
	}
	public void upload() {
		String fileName = ""+MainFrame.H+MainFrame.W;
		BufferedReader reader = null;
		String laststr = "";
		String Path = "src/rank/" + fileName + ".json";
		try {
			FileInputStream fileInputStream = new FileInputStream(
					Path);
			InputStreamReader inputStreamReader = new InputStreamReader(
					fileInputStream, "UTF-8");
			reader = new BufferedReader(inputStreamReader);
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
		String[] split = laststr.split("#");
		int[] Num = new int[split.length/2]; 
		for(int i=1;i<split.length;i+=2) {
			Num[i/2]=Integer.parseInt(split[i]);
		}
		sort(Num);
		for(int i=0;i<3;i++) {
			Score[i] = Num[i];
		}
		for(int i=0;i<3;i++) {
			for(int j=1;j<split.length;j+=2) {
				if(Integer.parseInt(split[j])==Score[i]) {
					name[i] = split[j-1];
					if(i>0&&name[i].equals(name[i-1])) {
						continue;
					}else {
						break;
					}
				}
			}
		}
		
		
	}
	public void sort(int[] args) {
		  
  
        int time1 = 0, time2 = 0;
        for (int i = 0; i < args.length - 1; i++) {
            ++time1;
            for (int j = i + 1; j < args.length; j++) {
                ++time2;
                int temp;
                if (args[i] < args[j]) {
                    temp = args[j];
                    args[j] = args[i];
                    args[i] = temp;
                }
            }
        }
    }
	public void initialize() {
		setTitle("CS102A Project @Xiongzhuochen£¬Dengjunze");
		setSize(800, 900);
		ImageIcon background;
		JPanel myPanel;
		JLabel label;
		
		background = new ImageIcon(this.getClass().getClassLoader()
				.getResource("resource/rank.png"));
		label = new JLabel(background);		
		label.setBounds(0, 0, background.getIconWidth(), background.getIconHeight()); 
		myPanel = (JPanel)this.getContentPane();		
		myPanel.setOpaque(false);					
		myPanel.setLayout(new FlowLayout());
		setDefaultCloseOperation(RankFrame.DISPOSE_ON_CLOSE);
		setLayout(null);
		this.setBounds(700, 150, background.getIconWidth(), background.getIconHeight());
		setLocationRelativeTo(null); // Center the window.
		JPanel pnl1 = new JPanel();
		setpnl1(pnl1);
		getContentPane().add(pnl1);
		JPanel pnl2 = new JPanel();
		setpnl2(pnl2);
		getContentPane().add(pnl2);
		
		
		this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
	
		this.setVisible(true);
		
	}
	private void setpnl1(JPanel pnl) {
		pnl.setBounds(135, 220, 300, 100);
		pnl.setOpaque(false);
		pnl.setLayout(new GridLayout(1, 2));
		JLabel mode = new JLabel("MODE",JLabel.CENTER);
		mode.setFont(new Font("Î¢ÈíÑÅºÚ", Font.CENTER_BASELINE, 36));
		JLabel m = new JLabel(MainFrame.H+"X"+MainFrame.W,JLabel.CENTER);
		m.setFont(new Font("Î¢ÈíÑÅºÚ", Font.CENTER_BASELINE, 36));
		pnl.add(mode);
		pnl.add(m);

	}
	private void setpnl2(JPanel pnl) {
		pnl.setBounds(75, 350, 425, 350);
		pnl.setOpaque(false);
		JLabel rank = new JLabel("Rank",JLabel.CENTER);
		rank.setFont(new Font("Î¢ÈíÑÅºÚ", Font.CENTER_BASELINE, 24));
		JLabel id = new JLabel("Player",JLabel.CENTER);
		id.setFont(new Font("Î¢ÈíÑÅºÚ", Font.CENTER_BASELINE, 24));
		JLabel score = new JLabel("Score",JLabel.CENTER);
		score.setFont(new Font("Î¢ÈíÑÅºÚ", Font.CENTER_BASELINE, 24));
		JLabel rank1 = new JLabel("No.1",JLabel.CENTER);
		rank1.setFont(new Font("Î¢ÈíÑÅºÚ", Font.CENTER_BASELINE, 24));
		JLabel rank2 = new JLabel("No.2",JLabel.CENTER);
		rank2.setFont(new Font("Î¢ÈíÑÅºÚ", Font.CENTER_BASELINE, 24));
		JLabel rank3 = new JLabel("No.3",JLabel.CENTER);
		rank3.setFont(new Font("Î¢ÈíÑÅºÚ", Font.CENTER_BASELINE, 24));
		/*
		JLabel rank4 = new JLabel("No.4",JLabel.CENTER);
		rank4.setFont(new Font("Î¢ÈíÑÅºÚ", Font.CENTER_BASELINE, 36));
		JLabel rank5 = new JLabel("No.5",JLabel.CENTER);
		rank5.setFont(new Font("Î¢ÈíÑÅºÚ", Font.CENTER_BASELINE, 36));
		JLabel rank6 = new JLabel("No.6",JLabel.CENTER);
		rank6.setFont(new Font("Î¢ÈíÑÅºÚ", Font.CENTER_BASELINE, 36));
		JLabel rank7 = new JLabel("No.7",JLabel.CENTER);
		rank7.setFont(new Font("Î¢ÈíÑÅºÚ", Font.CENTER_BASELINE, 36));
		JLabel rank8 = new JLabel("No.8",JLabel.CENTER);
		rank8.setFont(new Font("Î¢ÈíÑÅºÚ", Font.CENTER_BASELINE, 36));
		JLabel rank9 = new JLabel("No.9",JLabel.CENTER);
		rank9.setFont(new Font("Î¢ÈíÑÅºÚ", Font.CENTER_BASELINE, 36));
		JLabel rank10 = new JLabel("No.10",JLabel.CENTER);
		rank10.setFont(new Font("Î¢ÈíÑÅºÚ", Font.CENTER_BASELINE, 36));
		*/
		JLabel id1 = new JLabel(name[0],JLabel.CENTER);
		id1.setFont(new Font("Î¢ÈíÑÅºÚ", Font.CENTER_BASELINE, 18));
		JLabel id2 = new JLabel(name[1],JLabel.CENTER);
		id2.setFont(new Font("Î¢ÈíÑÅºÚ", Font.CENTER_BASELINE, 18));
		JLabel id3 = new JLabel(name[2],JLabel.CENTER);
		id3.setFont(new Font("Î¢ÈíÑÅºÚ", Font.CENTER_BASELINE, 18));
		/*
		JLabel id4 = new JLabel("MODE",JLabel.CENTER);
		id4.setFont(new Font("Î¢ÈíÑÅºÚ", Font.CENTER_BASELINE, 36));
		JLabel id5 = new JLabel("MODE",JLabel.CENTER);
		id5.setFont(new Font("Î¢ÈíÑÅºÚ", Font.CENTER_BASELINE, 36));
		JLabel id6 = new JLabel("MODE",JLabel.CENTER);
		id6.setFont(new Font("Î¢ÈíÑÅºÚ", Font.CENTER_BASELINE, 36));
		JLabel id7 = new JLabel("MODE",JLabel.CENTER);
		id7.setFont(new Font("Î¢ÈíÑÅºÚ", Font.CENTER_BASELINE, 36));
		JLabel id8 = new JLabel("MODE",JLabel.CENTER);
		id8.setFont(new Font("Î¢ÈíÑÅºÚ", Font.CENTER_BASELINE, 36));
		JLabel id9 = new JLabel("MODE",JLabel.CENTER);
		id9.setFont(new Font("Î¢ÈíÑÅºÚ", Font.CENTER_BASELINE, 36));
		JLabel id10 = new JLabel("MODE",JLabel.CENTER);
		id10.setFont(new Font("Î¢ÈíÑÅºÚ", Font.CENTER_BASELINE, 36));
		*/
		JLabel score1 = new JLabel(""+Score[0],JLabel.CENTER);
		score1.setFont(new Font("Î¢ÈíÑÅºÚ", Font.CENTER_BASELINE, 24));
		JLabel score2 = new JLabel(""+Score[1],JLabel.CENTER);
		score2.setFont(new Font("Î¢ÈíÑÅºÚ", Font.CENTER_BASELINE, 24));
		JLabel score3 = new JLabel(""+Score[2],JLabel.CENTER);
		score3.setFont(new Font("Î¢ÈíÑÅºÚ", Font.CENTER_BASELINE, 24));
		/*
		JLabel score4 = new JLabel("MODE",JLabel.CENTER);
		score4.setFont(new Font("Î¢ÈíÑÅºÚ", Font.CENTER_BASELINE, 36));
		JLabel score5 = new JLabel("MODE",JLabel.CENTER);
		score5.setFont(new Font("Î¢ÈíÑÅºÚ", Font.CENTER_BASELINE, 36));
		JLabel score6 = new JLabel("MODE",JLabel.CENTER);
		score6.setFont(new Font("Î¢ÈíÑÅºÚ", Font.CENTER_BASELINE, 36));
		JLabel score7 = new JLabel("MODE",JLabel.CENTER);
		score7.setFont(new Font("Î¢ÈíÑÅºÚ", Font.CENTER_BASELINE, 36));
		JLabel score8 = new JLabel("MODE",JLabel.CENTER);
		score8.setFont(new Font("Î¢ÈíÑÅºÚ", Font.CENTER_BASELINE, 36));
		JLabel score9 = new JLabel("MODE",JLabel.CENTER);
		score9.setFont(new Font("Î¢ÈíÑÅºÚ", Font.CENTER_BASELINE, 36));
		JLabel score10 = new JLabel("MODE",JLabel.CENTER);
		score10.setFont(new Font("Î¢ÈíÑÅºÚ", Font.CENTER_BASELINE, 36));
		*/
		pnl.setLayout(new GridLayout(4, 3));
		pnl.add(rank);
		pnl.add(id);
		pnl.add(score);
		pnl.add(rank1);
		pnl.add(id1);
		pnl.add(score1);
		pnl.add(rank2);
		pnl.add(id2);
		pnl.add(score2);
		pnl.add(rank3);
		pnl.add(id3);
		pnl.add(score3);
		/*
		pnl.add(rank4);
		pnl.add(id4);
		pnl.add(score4);
		pnl.add(rank5);
		pnl.add(id5);
		pnl.add(score5);
		pnl.add(rank6);
		pnl.add(id6);
		pnl.add(score6);
		pnl.add(rank7);
		pnl.add(id7);
		pnl.add(score7);
		pnl.add(rank8);
		pnl.add(id8);
		pnl.add(score8);
		pnl.add(rank9);
		pnl.add(id9);
		pnl.add(score9);
		pnl.add(rank10);
		pnl.add(id10);
		pnl.add(score10);
		*/
	}
	public static void main(String[] args) {
		Runnable runnable = new Runner();
		SwingUtilities.invokeLater(runnable);

	}

	static class Runner implements Runnable {
		@Override
		public void run() {
			RankFrame MainFrame = new RankFrame();
		}
	}
	@Override
	public Container getContentPane() {
		return getRootPane().getContentPane();
	}
	

}
