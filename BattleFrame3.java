
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import cn.DotComponent;
import cn.EdgeComponent;
import cn.Square;
import cn.Player;

public class BattleFrame3 extends JFrame {
	protected int H;
	protected int W;
	protected Player player1;
	protected Player player2;
	Square[][] s;
	private JPanel pnl1 = new JPanel();
	private JPanel pnl2 = new JPanel();
	protected ArrayList<EdgeComponent> edges = new ArrayList<>();
	protected Color currentColor;
	protected EdgeComponent[][] click;
	Computer computer;
	protected String currentName;
	protected JLabel tips;
	protected ArrayList<Color> current = new ArrayList<>();
	protected ArrayList<Integer> player1S = new ArrayList<>();
	protected ArrayList<Integer> player2S = new ArrayList<>();
	protected ArrayList<Integer> uploadList = new ArrayList<>();
	private String squareName;

	protected class GameMouseListener extends MouseInputAdapter {

		@Override
		public void mouseClicked(MouseEvent event) {
			MainFrame.e.add(event);
			event = SwingUtilities.convertMouseEvent(BattleFrame3.this, event,
					getContentPane());

			Component component = getContentPane()
					.getComponentAt(event.getPoint());
			if (component instanceof EdgeComponent) {
				EdgeComponent edgeComponent = (EdgeComponent) component;
				if (edgeComponent.isFree()) {
					edgeComponent.setColor(currentColor);
					edgeComponent.setFree(false);
					edgeComponent.setVisible(true);
					edgeComponent.repaint();
					getCount(edgeComponent);// cnt++;
					setScore(edgeComponent);// change color. print name.
					if (isOver())
						openWin();
					if (currentColor == Color.RED) {
						currentName = player1.name;
					} else if (currentColor == Color.BLUE) {
						currentName = player2.name;
					}
					tips.setText(
							String.format("%s ,is Your turn", currentName));
					tips.repaint();
					for (EdgeComponent e : edges) {
						e.list1.add(e.isFree());
						e.list2.add(e.isVisible());
					}
					for (Square[] e : s) {
						for (Square f : e) {
							f.c.add(f.cnt);
							f.currentName.add(f.name);
						}
					}
					current.add(currentColor);
					player1S.add(player1.score);
					player2S.add(player2.score);

					new Thread(() -> {
						while (currentColor == Color.BLUE) {
							computer.run();
							if (isOver())
								break;
						}
					}).start();
				}
			}
		}

		@Override
		public void mouseMoved(MouseEvent event) {
			event = SwingUtilities.convertMouseEvent(BattleFrame3.this, event,
					getContentPane());
			Component component = getContentPane().getComponentAt(event.getPoint());
			for (EdgeComponent e : edges) {
				if (e.isFree()) {
					if (e == component) {
						e.setColor(currentColor);
						e.setVisible(true);
					} else {
						e.setVisible(false);
					}
				}
			}
		}
	}

	public BattleFrame3(int H, int W, String name1, String name2, int first) {
		this.player1 = new Player(name1);
		this.player2 = new Player(name2);
		this.H = H;
		this.W = W;
		ImageIcon background;
		JPanel myPanel;
		JLabel label;

		this.s = new Square[H - 1][W - 1];
		computer = new Computer((W - 1) * H + (H - 1) * W);
		if (first == 1) {
			currentColor = Color.RED;
		} else {
			currentColor = Color.BLUE;
		}
		if (currentColor == Color.RED) {
			currentName = player1.name;
		} else if (currentColor == Color.BLUE) {
			currentName = player2.name;
		}
		background = new ImageIcon(this.getClass().getClassLoader()
				.getResource("resource/" + MainFrame.picture + ".jpg"));
		label = new JLabel(background);
		label.setBounds(0, 0, background.getIconWidth(),
				background.getIconHeight()); // setPanel.
		myPanel = (JPanel) this.getContentPane();
		myPanel.setOpaque(false);
		myPanel.setLayout(new FlowLayout());
		initialize();
		this.setVisible(true);// set this frame visible before click.
		GameMouseListener mouseListener = new GameMouseListener();
		addMouseListener(mouseListener);
		addMouseMotionListener(mouseListener);

		this.getLayeredPane().setLayout(null);
		this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
		this.setBounds(270, 60, background.getIconWidth(),
				background.getIconHeight());
		this.setVisible(true);

		if (first == 2)
			computer.run();
		for (EdgeComponent e : edges) {
			e.list1.add(e.isFree());
			e.list2.add(e.isVisible());
		}
		for (Square[] e : s) {
			for (Square f : e) {
				f.c.add(f.cnt);
				f.currentName.add(f.name);
			}
		}
		current.add(currentColor);
		player1S.add(player1.score);
		player2S.add(player2.score);
	}
	
	public BattleFrame3() {
	}

	public void initialize() {
		setTitle("CS102A Project @Xiongzhuochen£¬Dengjunze");
		setSize(1400, 975);
		setLocationRelativeTo(null); // Center the window.
		setDefaultCloseOperation(BattleFrame3.DISPOSE_ON_CLOSE);
		setLayout(null);
		setBattle(W, H);
		setpnl(900, 75, pnl1, player1);
		getContentPane().add(pnl1);
		setpnl(900, 275, pnl2, player2);
		getContentPane().add(pnl2);
		JButton regret = new JButton("Undo");
		JButton download = new JButton("dowload");
		JButton upload = new JButton("upload");
		regret.setBounds(900, 475, 250, 75);
		download.setBounds(900, 620, 125, 55);
		upload.setBounds(1025, 620, 125, 55);
		regret.setFont(new Font("ËÎÌå", Font.BOLD, 24));
		download.setFont(new Font("ËÎÌå", Font.BOLD, 24));
		upload.setFont(new Font("ËÎÌå", Font.BOLD, 24));
		regret.addActionListener(E -> {
			new Thread(() -> {
				do {
					if (this.current.size() == 0)
						break;
					int n = this.current.size() - 1;
					for (EdgeComponent e : edges) {
						e.setFree(e.list1.get(n));
						e.setVisible(e.list2.get(n));
						if (n != 0)
							e.list1.remove(n);
						if (n != 0)
							e.list2.remove(n);
					}
					for (Square[] e : s) {
						for (Square f : e) {
							f.cnt = f.c.get(n);
							f.name = f.currentName.get(n);
							f.setName(f.name);
							if (n != 0)
								f.c.remove(n);
							if (n != 0)
								f.currentName.remove(n);
						}
					}
					currentColor = current.get(n);
					if (n != 0)
						current.remove(n);
					player1.score = player1S.get(n);
					getScore1().setText(String.valueOf(player1.score));
					player2.score = player2S.get(n);
					getScore2().setText(String.valueOf(player2.score));
					if (n != 0)
						player1S.remove(n);
					if (n != 0)
						player2S.remove(n);
					if (currentColor == Color.RED) {
						currentName = player1.name;
					} else if (currentColor == Color.BLUE) {
						currentName = player2.name;
					}
					tips.setText(
							String.format("%s ,is Your turn", currentName));
					tips.repaint();
					repaint();
				} while (currentColor == Color.BLUE);
			}).start();
		});
		
		download.addActionListener(e -> {
			todownloadList();
			try {
				download();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		upload.addActionListener(e -> {
			upload();

		});

		getContentPane().add(regret);
		getContentPane().add(download);
		getContentPane().add(upload);
		tips = new JLabel(String.format("%s ,is Your turn", currentName),
				JLabel.CENTER);
		tips.setBounds(900, 575, 400, 40);
		tips.setFont(new Font("Î¢ÈíÑÅºÚ", Font.CENTER_BASELINE, 30));
		tips.setForeground(Color.green);
		getContentPane().add(tips);
	}

	public void download() throws IOException {
		new Thread(() -> {
			try {
				Download D = new Download();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();

	}

	public ArrayList<String> todownloadList() {
		for (int i = 0; i < ((W - 1) * H + (H - 1) * W); i++) {
			if (edges.get(i).isFree()) {
				MainFrame.downloadList.add("1");
			} else {
				MainFrame.downloadList.add("0");
			}
			if (edges.get(i).isVisible()) {
				MainFrame.downloadList.add("1");
			} else {
				MainFrame.downloadList.add("0");
			}
			if (edges.get(i).getColor() == Color.RED) {
				MainFrame.downloadList.add("1");
			} else if (edges.get(i).getColor() == Color.BLUE) {
				MainFrame.downloadList.add("2");
			} else {
				MainFrame.downloadList.add("0");
			}
		}
		MainFrame.downloadList.add("#");
		MainFrame.downloadList.add(Integer.toString(player1.score));
		MainFrame.downloadList.add("#");
		MainFrame.downloadList.add(Integer.toString(player2.score));
		MainFrame.downloadList.add("# ");
		MainFrame.downloadList.add(player1.name);
		MainFrame.downloadList.add("# ");
		MainFrame.downloadList.add(player2.name);
		MainFrame.downloadList.add("#");
		for (Square[] e : s) {
			for (Square f : e) {
				if (f.name == player1.name) {
					MainFrame.downloadList.add("1");
				} else if (f.name == player2.name) {
					MainFrame.downloadList.add("2");
				} else {
					MainFrame.downloadList.add("0");
				}
			}
		}
		return MainFrame.downloadList;
	}

	private void upload() {
		new Thread(() -> {
			Upload U = new Upload();
		}).start();

		for (int i = 0; i < uploadList.size(); i++) {
			System.out.print(uploadList.get(i));
		}
	}
	public static void main(String[] args) {
		Runnable runnable = new Runner();
		SwingUtilities.invokeLater(runnable);
	}

	static class Runner implements Runnable {
		@Override
		public void run() {
			// BattleFrame2 BattleFrame = new BattleFrame2();
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
				getContentPane().add(dotComponent); // Add the component to the panel.
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
				e.setPoint(100 + 150 * i + 75, 82 + 150 * j + 8);

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
				e.setPoint(82 + 150 * j + 8, 100 + 150 * i + 75);
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
	public JLabel getName1() {
		return (JLabel) pnl1.getComponent(3);
	}
	public JLabel getName2() {
		return (JLabel) pnl2.getComponent(3);
	}

	public void getCount(EdgeComponent edgeComponent) {
		if (edgeComponent.left != null) {
			edgeComponent.left.cnt++;
		}
		if (edgeComponent.right != null) {
			edgeComponent.right.cnt++;		}
	}
	public void setScore(EdgeComponent edgeComponent) {
		if (((edgeComponent.right != null && edgeComponent.right.cnt == 4)
				&& (edgeComponent.left != null
						&& edgeComponent.left.cnt == 4))) { // get double score.
			currentColor = currentColor == Color.RED ? Color.RED : Color.BLUE;
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
			currentColor = currentColor == Color.RED ? Color.RED : Color.BLUE;
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
			currentColor = currentColor == Color.RED ? Color.RED : Color.BLUE;
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
			currentColor = currentColor == Color.RED ? Color.BLUE : Color.RED;
		}
	}
	
	public boolean isOver() {
		if (player1.score + player2.score == (W - 1) * (H - 1))
			return true;
		return false;
	}
	
	public void openWin() {
		if (player1.score > player2.score) {
			WinnerFrame win = new WinnerFrame(player1.name,player1.score);
		} else if (player2.score > player1.score) {
			WinnerFrame win = new WinnerFrame(player2.name,player2.score);
		} else {
			WinnerFrame win = new WinnerFrame("None of you ",player1.score);
		}
	}

	public class Upload extends JFrame {
		String fileName = "";
		public Upload() {
			initialize();
		}

		private void initialize() {
			JFrame jf = new JFrame("input your save ID");
			jf.setSize(300, 200);
			jf.setLocationRelativeTo(null);
			jf.setDefaultCloseOperation(Upload.DISPOSE_ON_CLOSE);
			JPanel panel = new JPanel();
			final JTextField textField = new JTextField(8);
			textField.setFont(new Font(null, Font.PLAIN, 20));
			panel.add(textField);
			JButton btn = new JButton("OK");
			btn.setFont(new Font(null, Font.PLAIN, 20));
			btn.addActionListener(E -> {
				new Thread(() -> {
					fileName = textField.getText();
					System.out.print(fileName);
					BufferedReader reader = null;
					String laststr = "";
					String Path = "src\\" + fileName + ".json";
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
					for (int i = 0; i < split[0].length(); i++) {
						uploadList.add(Integer
								.parseInt(String.valueOf(laststr.charAt(i))));
					}
					player1.score = Integer.parseInt(split[1]);
					player2.score = Integer.parseInt(split[2]);
					player1.name = split[3];
					player2.name = split[4];
					squareName = split[5];
					for (int i = 0; i < split[5].length(); i++) {
						uploadList.add(Integer
								.parseInt(String.valueOf(split[5].charAt(i))));
					}
					getScore1().setText(String.valueOf(player1.score));
					getScore2().setText(String.valueOf(player2.score));
					getName1().setText(player1.name.trim());
					getName2().setText(player2.name.trim());

					for (int i = 0; i < (W - 1) * H + (H - 1) * W; i++) {
						if (edges.get(i).left != null) {
							edges.get(i).left.cnt = 0;
						}
						if (edges.get(i).right != null) {
							edges.get(i).right.cnt = 0;
						}
					}

					for (int i = 0; i < (W - 1) * H + (H - 1) * W; i++) {
						if (uploadList.get(3 * i) == 1) {
							edges.get(i).setFree(true);
						} else {
							edges.get(i).setFree(false);
						}

						if (uploadList.get(3 * i + 1) == 1) {
							edges.get(i).setVisible(true);
						} else {
							edges.get(i).setVisible(false);
						}

						if (uploadList.get(3 * i + 2) == 1) {
							edges.get(i).setColor(Color.RED);
						} else if (uploadList.get(3 * i + 2) == 2) {
							edges.get(i).setColor(Color.BLUE);
						} else {
							edges.get(i).setColor(null);
						}
					}
					for (int i = 0; i < (W - 1) * H + (H - 1) * W; i++) {
						if (edges.get(i).isFree() == false) {
							if (edges.get(i).left != null) {
								edges.get(i).left.cnt++;
							}
							if (edges.get(i).right != null) {
								edges.get(i).right.cnt++;
							}
						}
					}

					for (int i = 0; i < squareName.length() - 1; i++) {

						if (squareName.charAt(i) == '1') {
							s[(i - i % (W - 1)) / (W - 1)][i % (W - 1)]
									.setName(player1.name.trim());
						} else if (squareName.charAt(i) == '2') {
							s[(i - i % (W - 1)) / (W - 1)][i % (W - 1)]
									.setName(player2.name.trim());
						}
					}
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
					jf.dispose();
					if (currentColor == Color.RED) {
						currentName = player1.name;
					} else if (currentColor == Color.BLUE) {
						currentName = player2.name;
					}
					tips.setText(
							String.format("%s ,is Your turn", currentName));
					tips.repaint();
				}).start();
			});
			panel.add(btn);
			jf.setContentPane(panel);
			jf.setVisible(true);
		}
	}

	public class Computer {
		int total;
		ArrayList<EdgeComponent> edges;
		Square[][] square;
		public Computer(int total) {
			this.total = total;
			edges = new ArrayList<>();
		}
		public boolean rightStep(EdgeComponent e) {
			if ((e.right != null && e.right.cnt == 3)
					|| (e.left != null && e.left.cnt == 3)) {
				return true;
			} else {
				return false;
			}
		}

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
		}
		public void edgeProcess(EdgeComponent e) {
			e.setColor(currentColor);
			e.setFree(false);
			e.setVisible(true);
			e.repaint();
			getCount(e);
			setScore(e);
		}
		// check whether the square have 'stop' edges.
		public boolean noRight(Square s) {
			for (EdgeComponent e : edges) {
				if (e.getHeight() > e.getWidth()) {
					if (e.left != null && e.left == s)
						return true;
				}
			}
			return false;
		}
		public boolean noLeft(Square s) {
			for (EdgeComponent e : edges) {
				if (e.getHeight() > e.getWidth()) {
					if (e.right != null && e.right == s)
						return true;
				}
			}
			return false;
		}
		public boolean noUp(Square s) {
			for (EdgeComponent e : edges) {
				if (e.getWidth() > e.getHeight()) {
					if (e.right != null && e.right == s)
						return true;
				}
			}
			return false;
		}
		public boolean noDown(Square s) {
			for (EdgeComponent e : edges) {
				if (e.getWidth() > e.getHeight()) {
					if (e.left != null && e.left == s)
						return true;
				}
			}
			return false;
		}
		public boolean near4(int a, int b, Square[][] e) {
			if (a - 1 < 0 || a + 1 > H - 2 || b - 1 < 0 || b + 1 > W - 2) {
				return false;
			}
			if ((e[a - 1][b] != null && e[a - 1][b].cnt == 2)
					&& (e[a + 1][b] != null && e[a + 1][b].cnt == 2)
					&& (e[a][b - 1] != null && e[a][b - 1].cnt == 2)
					&& (e[a][b + 1] != null && e[a][b + 1].cnt == 2)) {
				return true;
			} else {
				return false;
			}
		}
		public boolean near3(int a, int b, Square[][] e) {
			if (!(a - 1 < 0 || a + 1 > H - 2 || b + 1 > W - 2)
					&& (e[a - 1][b] != null && e[a - 1][b].cnt == 2)
					&& (e[a + 1][b] != null && e[a + 1][b].cnt == 2)
					&& (e[a][b + 1] != null && e[a][b + 1].cnt == 2)) {
				return true;
			} else if (!(a - 1 < 0 || a + 1 > H - 2 || b - 1 < 0)
					&& (e[a - 1][b] != null && e[a - 1][b].cnt == 2)
					&& (e[a + 1][b] != null && e[a + 1][b].cnt == 2)
					&& (e[a][b - 1] != null && e[a][b - 1].cnt == 2)) {
				return true;
			} else if (!(a + 1 > H - 2 || b - 1 < 0 || b + 1 > W - 2)
					&& (e[a + 1][b] != null && e[a + 1][b].cnt == 2)
					&& (e[a][b - 1] != null && e[a][b - 1].cnt == 2)
					&& (e[a][b + 1] != null && e[a][b + 1].cnt == 2)) {
				return true;
			} else if (!(a - 1 < 0 || b - 1 < 0 || b + 1 > W - 2)
					&& (e[a - 1][b] != null && e[a - 1][b].cnt == 2)
					&& (e[a][b - 1] != null && e[a][b - 1].cnt == 2)
					&& (e[a][b + 1] != null && e[a][b + 1].cnt == 2)) {
				return true;
			} else {
				return false;
			}
		}
		public int findOne(int a, int b, int sum) {
			if (a < 0 || a > H - 2 || b < 0 || b > W - 2) {
				return sum;
			} else if (square[a][b].cnt == 2
					|| (near4(a, b, square) && square[a][b].cnt == 1)
					|| (near3(a, b, square) && square[a][b].cnt == 0)) {
				square[a][b].cnt = 0;
				sum += 1;
				if (a - 1 >= 0 && square[a - 1][b].cnt == 2 && noUp(s[a][b])) {
					sum = findOne(a - 1, b, sum);
				}
				if (b - 1 >= 0 && square[a][b - 1].cnt == 2
						&& noLeft(s[a][b])) {
					sum = findOne(a, b - 1, sum);
				}
				if (b <= W - 3 && square[a][b + 1].cnt == 2
						&& noRight(s[a][b])) {
					sum = findOne(a, b + 1, sum);
				}
				if (a <= H - 3 && square[a + 1][b].cnt == 2
						&& noDown(s[a][b])) {
					sum = findOne(a + 1, b, sum);
				}
				return sum;
			} else {
				return sum;
			}
		}

		public void run() {
			// add free edge to edges.
			for (EdgeComponent e : BattleFrame3.this.edges) {
				if (e.isFree())
					edges.add(e);
			}
			total = edges.size();
			int[][] sum = new int[H - 1][W - 1];
			square = new Square[H - 1][W - 1];
			// get squares' count.
			for (int i = 0; i < H - 1; i++) {
				for (int j = 0; j < W - 1; j++) {
					square[i][j] = new Square();
					square[i][j].cnt = s[i][j].cnt;
				}
			}
			// find the biggest 'island' of every square.
			for (int i = 0; i < H - 1; i++) {
				for (int j = 0; j < W - 1; j++) {
					sum[i][j] = findOne(i, j, sum[i][j]);
					// initialize the square
					for (int f = 0; f < H - 1; f++) {
						for (int g = 0; g < W - 1; g++) {
							square[f][g].cnt = s[f][g].cnt;
						}
					}
				}
			}
			int m = 0;
			int n = 0;
			int min = (H - 1) * (W - 1);
			// find the best choice to avoid loss.
			for (int i = 0; i < H - 1; i++) {
				for (int j = 0; j < W - 1; j++) {
					if (sum[i][j] != 0 && min >= sum[i][j]) {
						min = sum[i][j];
						m = i;
						n = j;
					}
				}
			}
			/* this loop is only fo test.
			for (int i = 0; i < H - 1; i++) {
				for (int j = 0; j < W - 1; j++) {
					System.out.print(sum[i][j] + " ");
				}
				System.out.println("");
			}
			System.out.println("");
			*/
/*  find the biggest square. But useless now.
			int M = 0;
			int N = 0;
			int max = 1;
			// find the best choice to get more score.
			for (int i = 0; i < H - 1; i++) {
				for (int j = 0; j < W - 1; j++) {
					if (sum[i][j] != 0 && max <= sum[i][j]) {
						max = sum[i][j];
						M = i;
						N = j;
					}
				}
			}
*/
			if (edges.size() > 0) {
				int h = (int) (Math.random() * total);
				if (existRightStep(total, edges)) {
					edgeProcess(numOfRightStep(total, edges));
				} else {
					if (existWrongStep(total, edges)) {
						// all wrong step.
						if (allWrongStep(total, edges)) {
							for (EdgeComponent e : edges) {
								if (e.right == s[m][n] || e.left == s[m][n]) {
									edgeProcess(e);
									break;
								}
							}
						} else {// exist wrong edge but not all.
								// avoid the edge which is visible.
							int i = h;
							while (wrongStep(edges.get(i)) == true) {
								i = (int) (Math.random() * total);
							}
							edgeProcess(i);
						}
					} else {// all save answer
							// random
						if (edges.get(h).isFree()) {
							edgeProcess(h);
						}
					}
				}
				repaint();
			}
			
			// clear the square
			for (Square[] e : square) {
				for (Square f : e) {
					f = null;
				}
			}
			
			edges.clear();
			
			// increase time.
			for (int i = 0; i < 1999; i++) {
				for (int j = 0; j < 700; j++) {
					double x = Math.pow(j, i);
				}
			}
			
			if (isOver())
				openWin();
			if (currentColor == Color.RED) {
				currentName = player1.name;
			} else if (currentColor == Color.BLUE) {
				currentName = player2.name;
			}
			tips.setText(String.format("%s ,is Your turn", currentName));
			tips.repaint();
			for (EdgeComponent e : BattleFrame3.this.edges) {
				e.list1.add(e.isFree());
				e.list2.add(e.isVisible());
			}
			for (Square[] e : s) {
				for (Square f : e) {
					f.c.add(f.cnt);
					f.currentName.add(f.name);
				}
			}
			current.add(currentColor);
			player1S.add(player1.score);
			player2S.add(player2.score);
		}

	}
}
