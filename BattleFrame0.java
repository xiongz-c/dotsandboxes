
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import cn.DotComponent;
import cn.EdgeComponent;
import cn.Square;
import cn.Player;

public class BattleFrame0 extends JFrame {
	protected int H;
	protected int W;
	protected Player player1;
	protected Player player2;
	Square[][] s;
	private JPanel pnl1 = new JPanel();
	private JPanel pnl2 = new JPanel();
	protected int n;

	protected class Computer {
		int total = (W - 1) * H + (H - 1) * W;
		Random r = new Random();
		//  the forth edge.
		public boolean rightStep(EdgeComponent e) {
			if ((e.right != null && e.right.cnt == 3)
					|| (e.left != null && e.left.cnt == 3)) {
				return true;
			} else {
				return false;
			}
		}
		//  the third edge. 
		public boolean wrongStep(EdgeComponent e) {
			if ((e.right != null && e.right.cnt == 2)
					|| (e.left != null && e.left.cnt == 2)) {
				return true;

			} else {
				return false;
			}
		}
		
		public boolean existRightStep(int total,
				ArrayList<EdgeComponent> edges) {
			for (int i = 0; i < total; i++) {
				if (rightStep(edges.get(i)) == true) {
					return true;
				}
			}
			return false;
		}

		public int numOfRightStep(int total, ArrayList<EdgeComponent> edges) {
			int n = 0;
			for (int i = 0; i < total; i++) {
				if (rightStep(edges.get(i)) == true) {
					n = i;
				}
			}
			return n;
		}

		public boolean existWrongStep(int total,
				ArrayList<EdgeComponent> edges) {
			for (int i = 0; i < total; i++) {
				if (wrongStep(edges.get(i)) == true) {
					return true;
				}
			}
			return false;
		}

		public boolean allWrongStep(int total, ArrayList<EdgeComponent> edges) {
			for (int i = 0; i < total; i++) {
				if (wrongStep(edges.get(i)) == false) {
					return false;
				}
			}
			return true;
		}

		public int numOfWrongStep(int total, ArrayList<EdgeComponent> edges) {
			int n = 0;
			for (int i = 0; i < total; i++) {
				if (wrongStep(edges.get(i)) == true) {
					n = i;
				}
			}
			return n;
		}

		public void edgeProcess(int k) {
			edges.get(k).setColor(currentColor);
			edges.get(k).setFree(false);
			edges.get(k).setVisible(true);
			edges.get(k).repaint();
			getCount(edges.get(k));
			setScore(edges.get(k));
			edges.remove(k);
		}

		public void run() {
			while (edges.size() > 0) {
				int h = r.nextInt(total);
				if (existRightStep(total, edges)) {// click the forth edge first. 
					edgeProcess(numOfRightStep(total, edges));
					total--;
				} else {//  no the forth edge. 
					if (existWrongStep(total, edges)) {
						if (allWrongStep(total, edges)) {// all wrong.random.
							if (edges.get(h).isFree()) {
								edgeProcess(h);
								total--;
							}
						} else {// exist good edge but not all.
								// avoid the edge which is wrong.
							int i = r.nextInt(total);
							while (wrongStep(edges.get(i)) == true) {
								i = r.nextInt(total);
							}
							edgeProcess(i);
							total--;
						}

					} else {// no wrong step.
							// random
						if (edges.get(h).isFree()) {
							edgeProcess(h);
							total--;

						}
					}
				}
				// increase the time.
				for (int i = 0; i < 1999; i++) {
					for (int j = 0; j < 1999; j++) {
						double n = Math.pow(j, i);
					}
				}
				repaint();

			}
			if (isOver())
				openWin();
		}
		//  compute the counts for each square.
		public void getCount(EdgeComponent edgeComponent) {
			if (edgeComponent.left != null) {
				edgeComponent.left.cnt++;
				// System.out.println(edgeComponent.left.cnt);
			}
			if (edgeComponent.right != null) {
				edgeComponent.right.cnt++;
				// System.out.println(edgeComponent.right.cnt);
			}
		}

		public void setScore(EdgeComponent edgeComponent) {
			if (((edgeComponent.right != null && edgeComponent.right.cnt == 4)
					&& (edgeComponent.left != null
							&& edgeComponent.left.cnt == 4))) { // get double
																// score.
				currentColor = currentColor == Color.RED
						? Color.RED
						: Color.BLUE;
				if (currentColor == Color.RED) {
					player1.score += 2;
					getScore1().setText(String.valueOf(player1.score));
					edgeComponent.right.setName(player1.name);
					edgeComponent.left.setName(player1.name);
				} else if (currentColor == Color.BLUE) {
					player2.score += 2;
					getScore2().setText(String.valueOf(player2.score));
					edgeComponent.right.setName(player2.name);
					edgeComponent.left.setName(player2.name);
				}
				edgeComponent.right.setVisible(true);
				edgeComponent.left.setVisible(true);
				edgeComponent.right.repaint();
				edgeComponent.left.repaint();
			} else if ((edgeComponent.right != null // right get score.
					&& edgeComponent.right.cnt == 4)) {
				currentColor = currentColor == Color.RED
						? Color.RED
						: Color.BLUE;
				if (currentColor == Color.RED) {
					player1.score++;
					getScore1().setText(String.valueOf(player1.score));
					edgeComponent.right.setName(player1.name);
				} else if (currentColor == Color.BLUE) {
					player2.score++;
					getScore2().setText(String.valueOf(player2.score));
					edgeComponent.right.setName(player2.name);
				}
				edgeComponent.right.setVisible(true);
				edgeComponent.right.repaint();
			} else if (edgeComponent.left != null // left get score.
					&& edgeComponent.left.cnt == 4) {
				currentColor = currentColor == Color.RED
						? Color.RED
						: Color.BLUE;

				if (currentColor == Color.RED) {
					player1.score++;
					getScore1().setText(String.valueOf(player1.score));
					edgeComponent.left.setName(player1.name);
					edgeComponent.left.setVisible(true);
				} else if (currentColor == Color.BLUE) {
					player2.score++;
					getScore2().setText(String.valueOf(player2.score));
					edgeComponent.left.setName(player2.name);
				}
				edgeComponent.left.setVisible(true);
				edgeComponent.left.repaint();
			} else {
				currentColor = currentColor == Color.RED
						? Color.BLUE
						: Color.RED;
			}
		}
	}
	public boolean isOver() {
		if (player1.score + player2.score == (W - 1) * (H - 1))
			return true;
		return false;
	}
	public void openWin() {
		if (player1.score > player2.score) {
			WinnerFrame win = new WinnerFrame(player1.name, player1.score);
		} else if (player2.score > player1.score) {
			WinnerFrame win = new WinnerFrame(player2.name, player2.score);
		} else {
			WinnerFrame win = new WinnerFrame("None of you ", player1.score);
		}
	}

	// private List<EdgeComponent> edges = new ArrayList<>();
	protected ArrayList<EdgeComponent> edges = new ArrayList<>();
	// protected ArrayList<EdgeComponent> edges1 = edges;
	protected Color currentColor;

	public BattleFrame0(int H, int W, String name1, String name2, int first) {
		// add a picture in battleFrame.
		ImageIcon background;
		JPanel myPanel;
		JLabel label;
		background = new ImageIcon(this.getClass().getClassLoader()
				.getResource("resource/" + MainFrame.picture + ".jpg"));
		label = new JLabel(background);
		label.setBounds(0, 0, background.getIconWidth(),
				background.getIconHeight()); // setPanel.
		myPanel = (JPanel) this.getContentPane();
		myPanel.setOpaque(false);
		myPanel.setLayout(new FlowLayout());

		Player player1 = new Player(name1);
		this.player1 = player1;
		Player player2 = new Player(name2);
		this.player2 = player2;
		this.H = H;
		this.W = W;
		this.s = new Square[H - 1][W - 1];
		initialize();
		currentColor = Color.RED;

		this.getLayeredPane().setLayout(null);
		this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
		this.setBounds(270, 60, background.getIconWidth(),
				background.getIconHeight());
		this.setVisible(true);
		if (first == 1) {
			currentColor = Color.RED;
		} else {
			currentColor = Color.BLUE;
		}
		Computer c = new Computer();
		new Thread(() -> c.run()).start();
	}

	public BattleFrame0() {

	}

	public void initialize() {

		setTitle("CS102A Project @Xiongzhuochen£¬Dengjunze");
		setSize(1400, 975);
		setLocationRelativeTo(null); // Center the window.
		setDefaultCloseOperation(BattleFrame0.DISPOSE_ON_CLOSE);
		setLayout(null);
		setBattle(W, H);
		setpnl(900, 75, pnl1, player1);
		getContentPane().add(pnl1);
		setpnl(900, 275, pnl2, player2);
		getContentPane().add(pnl2);
	}

	public static void main(String[] args) {
		Runnable runnable = new Runner();
		SwingUtilities.invokeLater(runnable);
	}

	static class Runner implements Runnable {
		@Override
		public void run() {
			BattleFrame0 BattleFrame = new BattleFrame0();

		}
	}
	@Override
	public Container getContentPane() {
		return getRootPane().getContentPane();
	}
	public void setBattle(int width, int height) {

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				DotComponent dotComponent = new DotComponent(75 + 150 * i,
						75 + 150 * j, 30);
				getContentPane().add(dotComponent); // Add the component to thepanel.
			}
		}

		for (int i = 0; i < H - 1; i++) {
			for (int j = 0; j < W - 1; j++) {
				s[i][j] = new Square("", JLabel.CENTER);
				s[i][j].setLocation(95 + 150 * j, 95 + 150 * i, 120);
				getContentPane().add(s[i][j]);
			}
		}
		for (int i = 0; i < width - 1; i++) {
			for (int j = 0; j < height; j++) {
				EdgeComponent e = new EdgeComponent(100 + 150 * i, 82 + 150 * j,
						150, 16);
				if (j > 0 && j < height - 1) {
					e.left = s[j - 1][i];
					e.right = s[j][i];
				} else if (j == 0) {
					e.right = s[j][i];
				} else if (j == height - 1) {
					e.left = s[j - 1][i];
				}
				edges.add(e);
			}
		}
		for (int i = 0; i < height - 1; i++) {
			for (int j = 0; j < width; j++) {
				EdgeComponent e = new EdgeComponent(82 + 150 * j, 100 + 150 * i,
						16, 150);// left==up,right==down
				if (j > 0 && j < width - 1) {
					e.left = s[i][j - 1];
					e.right = s[i][j];
				} else if (j == 0) {
					e.right = s[i][j];
				} else if (j == width - 1) {
					e.left = s[i][j - 1];
				}
				edges.add(e);
			}
		}

		for (EdgeComponent e : edges) {
			e.setVisible(false);
			getContentPane().add(e);
		}
	}
	public Square[][] getSquare() {
		return s;
	}
	public void setpnl(int x, int y, JPanel pnl, Player player) {

		pnl.setBounds(x, y, 250, 75);
		pnl.setOpaque(false);
		JLabel score = new JLabel("Score:");
		score.setFont(new Font("ËÎÌå", Font.BOLD, 24));

		JLabel Num = new JLabel();
		Num.setText(String.valueOf(player.score));
		Num.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 24));

		JLabel play = new JLabel("Player:");
		play.setFont(new Font("ËÎÌå", Font.BOLD, 24));
		JLabel Name = new JLabel(player.name);
		Name.setFont(new Font("ËÎÌå", Font.BOLD, 24));

		pnl.setLayout(new GridLayout(2, 2));
		pnl.add(score);
		pnl.add(Num);
		pnl.add(play);
		pnl.add(Name);
	}
	public JLabel getScore1() {
		return (JLabel) pnl1.getComponent(1);
	}
	public JLabel getScore2() {
		return (JLabel) pnl2.getComponent(1);
	}

}
