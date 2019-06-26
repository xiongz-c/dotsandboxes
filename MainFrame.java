



import javax.swing.*;



import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.MouseEvent;

import java.io.File;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class MainFrame extends JFrame {
	public static ArrayList<MouseEvent> e = new ArrayList<MouseEvent>();
	public static int W;
	public static int H;
	public static String name1;
	public static String name2;
	public static String picture;
	public static int first;
	protected static ArrayList<String> downloadList = new ArrayList<>();
	private URL url;
	private AudioClip ac;

	public MainFrame() {
		new Thread(() -> {
			try {
				playMusic();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
		initialize();
		this.setVisible(true);
	}

	public void initialize() {
		setTitle("CS102A Project @Xiongzhuochen，Dengjunze");
		setSize(900, 675);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		// add background.
		ImageIcon background;
		JPanel myPanel;
		JLabel label;
		background = new ImageIcon(this.getClass().getClassLoader()
				.getResource("resource\\main.jpg"));
		label = new JLabel(background);
		label.setBounds(0, 0, background.getIconWidth(),
				background.getIconHeight());
		myPanel = (JPanel) this.getContentPane();
		myPanel.setOpaque(false);
		myPanel.setLayout(new FlowLayout());
		setLayout(null);
		this.setBounds(700, 150, background.getIconWidth(),
				background.getIconHeight());
		setLocationRelativeTo(null); // Center the window.
		this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
		// add some component.
		JComboBox<String> modelBox = new JComboBox<String>();
		JComboBox<String> gradeBox = new JComboBox<String>();
		JComboBox<String> firstHand = new JComboBox<String>();
		JComboBox<String> pictureBox = new JComboBox<String>();
		JTextField Name1 = new JTextField(15);
		JTextField Name2 = new JTextField(15);
		JPanel pnl1 = new JPanel();
		setpnl1(pnl1, modelBox, gradeBox, firstHand, pictureBox, Name1, Name2);
		getContentPane().add(pnl1);
		JPanel pnl2 = new JPanel();
		getContentPane().add(pnl2);
		setpnl2(pnl2, modelBox, gradeBox, firstHand, pictureBox, Name1, Name2);
		
		JPanel pnl3 = new JPanel();
		getContentPane().add(pnl3);
		pnl3.setBounds(115, 580, 600, 30);
		pnl3.setOpaque(false);
		JLabel tips = new JLabel(
				"tips: if you play with computer, player2 will be the computer.",
				JLabel.CENTER);
		tips.setFont(new Font("微软雅黑", Font.CENTER_BASELINE, 18));
		tips.setForeground(Color.MAGENTA);
		pnl3.setLayout(new GridLayout(1, 1));
		pnl3.add(tips);

	}

	public void playMusic() throws MalformedURLException {
		URL fileURL=this.getClass().getResource("resource/music.wav"); 
					url = fileURL;
					ac = Applet.newAudioClip(url);
					ac.play();
					ac.loop();
	}
	public static void main(String[] args) {
		Runnable runnable = new Runner();
		SwingUtilities.invokeLater(runnable);

	}

	static class Runner implements Runnable {
		@Override
		public void run() {
			MainFrame MainFrame = new MainFrame();
		}
	}
	@Override
	public Container getContentPane() {
		return getRootPane().getContentPane();
	}
	public void setpnl1(JPanel pnl, JComboBox<String> modelBox,
			JComboBox<String> gradeBox, JComboBox<String> firstHand,
			JComboBox<String> pictureBox, JTextField Name1, JTextField Name2) {
		pnl.setBounds(5, 15, 850, 200);
		pnl.setOpaque(false);
		JLabel model = new JLabel("MODEL:", JLabel.CENTER);
		model.setFont(new Font("宋体", Font.BOLD, 24));
		JLabel grade = new JLabel("DEGREE:", JLabel.CENTER);
		grade.setFont(new Font("宋体", Font.BOLD, 24));
		JLabel player1 = new JLabel("Player1:", JLabel.CENTER);
		player1.setFont(new Font("宋体", Font.BOLD, 24));
		JLabel player2 = new JLabel("Player2:", JLabel.CENTER);
		player2.setFont(new Font("宋体", Font.BOLD, 24));
		JLabel first = new JLabel("First hand:", JLabel.CENTER);
		first.setFont(new Font("宋体", Font.BOLD, 24));
		JLabel picture = new JLabel("Background:", JLabel.CENTER);
		picture.setFont(new Font("宋体", Font.BOLD, 24));

		modelBox.setFont(new Font("宋体", Font.BOLD, 20));
		modelBox.addItem("computer battle");
		modelBox.addItem("human vs computer");
		modelBox.addItem("human battle");
		modelBox.addItem("human vs computer(H)");

		gradeBox.setFont(new Font("宋体", Font.BOLD, 24));
		gradeBox.addItem("3x3");
		gradeBox.addItem("3x4");
		gradeBox.addItem("3x5");
		gradeBox.addItem("3x6");
		gradeBox.addItem("4x3");
		gradeBox.addItem("4x4");
		gradeBox.addItem("4x5");
		gradeBox.addItem("4x6");
		gradeBox.addItem("5x3");
		gradeBox.addItem("5x4");
		gradeBox.addItem("5x5");
		gradeBox.addItem("5x6");
		gradeBox.addItem("6x3");
		gradeBox.addItem("6x4");
		gradeBox.addItem("6x5");
		gradeBox.addItem("6x6");

		firstHand.setFont(new Font("宋体", Font.BOLD, 24));
		firstHand.addItem("Player1");
		firstHand.addItem("Player2");

		pictureBox.setFont(new Font("宋体", Font.BOLD, 24));
		pictureBox.addItem("Forest");
		pictureBox.addItem("River");
		pictureBox.addItem("Grassland");
		pictureBox.addItem("TomandJerry");

		pnl.setLayout(new GridLayout(3, 4));
		pnl.add(model);
		pnl.add(modelBox);
		pnl.add(player1);
		pnl.add(Name1);
		pnl.add(grade);
		pnl.add(gradeBox);
		pnl.add(player2);
		pnl.add(Name2);
		pnl.add(first);
		pnl.add(firstHand);
		pnl.add(picture);
		pnl.add(pictureBox);
	}
	public void setpnl2(JPanel pnl, JComboBox<String> modelBox,
			JComboBox<String> gradeBox, JComboBox<String> firstHand,
			JComboBox<String> pictureBox, JTextField name1, JTextField name2) {
		pnl.setBounds(125, 475, 600, 100);
		pnl.setOpaque(false);
		JButton exit = new JButton("HELP ME");
		exit.setFont(new Font("宋体", Font.BOLD, 24));
		exit.addActionListener(e -> {
			try {
				openHELP();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		JButton enter = new JButton("ENTER");
		enter.setFont(new Font("宋体", Font.BOLD, 24));
		enter.addActionListener(e -> {
			picture = pictureBox.getSelectedItem().toString();
			String str = modelBox.getSelectedItem().toString();
			String str1 = firstHand.getSelectedItem().toString();
			MainFrame.H = Integer.parseInt(
					gradeBox.getSelectedItem().toString().substring(0, 1));
			MainFrame.W = Integer.parseInt(
					gradeBox.getSelectedItem().toString().substring(2, 3));
			MainFrame.name1 = name1.getText();
			MainFrame.name2 = name2.getText();
			MainFrame.first = str1.equals("Player1") ? 1 : 2;
			if (str.equals("computer battle")) {
				BattleFrame0 BattleFrame = new BattleFrame0(H, W,
						MainFrame.name1, MainFrame.name2, MainFrame.first);
			} else if (str.equals("human vs computer")) {
				BattleFrame1 BattleFrame = new BattleFrame1(H, W,
						MainFrame.name1, MainFrame.name2, MainFrame.first);
			} else if (str.equals("human battle")) {
				BattleFrame2 BattleFrame = new BattleFrame2(H, W,
						MainFrame.name1, MainFrame.name2, MainFrame.first);
			} else if (str.equals("human vs computer(H)")) {
				BattleFrame3 BattleFrame = new BattleFrame3(H, W,
						MainFrame.name1, MainFrame.name2, MainFrame.first);
			}
		});
		JButton rank = new JButton("RANK");
		rank.setFont(new Font("宋体", Font.BOLD, 24));
		rank.addActionListener(e -> {
			MainFrame.H = Integer.parseInt(
					gradeBox.getSelectedItem().toString().substring(0, 1));
			MainFrame.W = Integer.parseInt(
					gradeBox.getSelectedItem().toString().substring(2, 3));
			RankFrame R = new RankFrame();
		});
		pnl.setLayout(new GridLayout(1, 3));
		pnl.add(exit);
		pnl.add(enter);
		pnl.add(rank);
	}
	private void openHELP() throws IOException {
		URL fileURL=this.getClass().getResource("resource/HELP.png"); 
		File help = null;
		try {
			help = new File(fileURL.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		Desktop.getDesktop().open(help);

	}

}
