package MinesweeperEngine;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class gameBoard extends JFrame {
	private int width, height;
	private static final int CELL_SIZE = 40;
	private static final int BOARD_ROWS = 24;
	private static final int BOARD_COLUMNS = 12;
	private static final int BOARD_X = 20;
	private static final int BOARD_Y = 40;
	private static final int SIDE_PANEL_WIDTH = 20;
	private static final int PANEL_HEIGHT = 10;
	private static final int TOP_PANEL_Y = BOARD_Y - PANEL_HEIGHT;
	private static final int BOARD_WIDTH = BOARD_COLUMNS * CELL_SIZE;
	private static final int BOARD_HEIGHT = BOARD_ROWS * CELL_SIZE;
	private static final int RIGHT_PANEL_X = BOARD_X + BOARD_WIDTH;
	private static final int BOTTOM_PANEL_Y = BOARD_Y + BOARD_HEIGHT;
	private static final int SIDE_PANEL_HEIGHT = BOTTOM_PANEL_Y + PANEL_HEIGHT;
	private static final Color START_BACKGROUND = new Color(24, 24, 24);
	private static final Color OVERLAY_BACKGROUND = new Color(0, 0, 0, 220);
	private static final Color PANEL_BACKGROUND = new Color(48, 48, 48);
	private static final Color CONTROL_BACKGROUND = Color.gray;
	private static final Color CONTROL_HOVER_BACKGROUND = new Color(112, 112, 112);
	private static final Color PANEL_BORDER = Color.darkGray;
	private static final Color CONTROL_HOVER_BORDER = Color.LIGHT_GRAY;
	private static final Color COPY_COLOR = Color.WHITE;
	private static final Color SUPPORT_COPY_COLOR = Color.LIGHT_GRAY;
	private static final Color WIN_COPY_COLOR = new Color(80, 220, 90);
	private static final Color LOSS_COPY_COLOR = new Color(230, 70, 70);
	private static final Font TITLE_FONT = new Font("Times New Roman", Font.BOLD, 28);
	private static final Font BODY_FONT = new Font("Times New Roman", Font.BOLD, 20);
	private static final Font BUTTON_FONT = new Font("Times New Roman", Font.BOLD, 24);
	private static int difficulty =1;
	private static Cube[][] cells = new Cube[24][12];
	private ProgressBombs bar;
	protected ImageIcon icon = new ImageIcon("MineImages/Bomb");
	protected ImageIcon construct = new ImageIcon("MineImages/Constructed.png");
	protected ImageIcon construct2 = new ImageIcon("MineImages/Constructed2.png");
	protected ImageIcon topPanel = new ImageIcon("MineImages/TopPanel.png");
	protected ImageIcon bottomPanel = new ImageIcon("MineImages/BottomPanel.png");
	protected static ImageIcon topFlag = new ImageIcon("MineImages/flagTop.png");
	protected static ImageIcon playAgainPic = new ImageIcon("MineImages/PlayAgainIcon.png");
	protected static ImageIcon bombImage = new ImageIcon("MineImages/Bomb.png");
	protected static ImageIcon bombImage2 = new ImageIcon("MineImages/BombIcon.png");
	protected JButton topFlagButton;
	private CubeManager cubeManager;
	
	
	
	public gameBoard() {
		this.width = 536;
		this.height = 1049;
		setTitle("Nikikosas Minesweeper");	
		setIconImage(bombImage2.getImage());
		setSize(this.width, this.height);
		setLayout(null);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		this.cubeManager = new CubeManager(this,288);
		addSidePanels();
		optionScreen();		
		setVisible(true);
		
	}

	public void optionScreen() {
		Container glassPane = createOverlayGlassPane(START_BACKGROUND);
		JPanel menuPanel = createOverlayPanel(60, 230, 400, 430);

		JLabel label = this.createJLabel("Nikikosas Minesweeper", PANEL_BACKGROUND, COPY_COLOR, TITLE_FONT, true, false, null, null, 20, 24, 360, 42);
		JLabel subLabel = this.createJLabel("Select a difficulty to start", PANEL_BACKGROUND, SUPPORT_COPY_COLOR, BODY_FONT, true, false, null, null, 20, 74, 360, 30);

		ButtonGroup group = new ButtonGroup();   // creates a new group object
		JRadioButton easy = createJRadioButton("Easy", PANEL_BACKGROUND, Color.GREEN, BODY_FONT, true, false, 30, 144, 104, 44);
		JRadioButton medium = createJRadioButton("Medium", PANEL_BACKGROUND, Color.YELLOW, BODY_FONT, true, false, 140, 144, 120, 44);
		JRadioButton hard = createJRadioButton("Hard", PANEL_BACKGROUND, Color.RED, BODY_FONT, true, false, 266, 144, 104, 44);
		group.add(easy);   // these 3 buttons are added to the group
		group.add(medium);
		group.add(hard);
		easy.setSelected(difficulty == 1);
		medium.setSelected(difficulty == 2);
		hard.setSelected(difficulty == 3);

		createDifficultyActionListener(easy,1);
		createDifficultyActionListener(medium,2);
		createDifficultyActionListener(hard,3);

		JButton play = createJButton("Play", CONTROL_BACKGROUND, COPY_COLOR, BUTTON_FONT, true, false, 80, 266, 240, 88);

		play.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == play) {
					cubeManager.populateCells(difficulty);
					cubeManager.showCells();
					bar = cubeManager.getProgressBar();
					add(bar);
					bar.setVisible(true);
					glassPane.setVisible(false);
				}
				
			}

		});

		menuPanel.add(easy);
		menuPanel.add(medium);
		menuPanel.add(hard);
		menuPanel.add(play);
		menuPanel.add(label);
		menuPanel.add(subLabel);
		glassPane.add(menuPanel);
	}

	private Container createOverlayGlassPane() {
		return createOverlayGlassPane(OVERLAY_BACKGROUND);
	}

	private Container createOverlayGlassPane(Color background) {
		this.setGlassPane(new JComponent() {
			@Override
			protected void paintComponent(Graphics g) {
				g.setColor(background);
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		});

		Container glassPane = (Container) this.getGlassPane();
		glassPane.setLayout(null);
		glassPane.setVisible(true);
		return glassPane;
	}

	private JPanel createOverlayPanel(int x, int y, int panelWidth, int panelHeight) {
		JPanel panel = new JPanel(null);
		Border outerBorder = BorderFactory.createLineBorder(PANEL_BORDER, 3);
		Border innerPadding = new EmptyBorder(12, 12, 12, 12);
		panel.setBorder(new CompoundBorder(outerBorder, innerPadding));
		panel.setBackground(PANEL_BACKGROUND);
		panel.setBounds(x, y, panelWidth, panelHeight);
		panel.setOpaque(true);
		return panel;
	}

	private void createDifficultyActionListener(JRadioButton button, int level) {
		button.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource()== button) {
				difficulty = level;
			}
		}
		});
	}

	private JLabel createJLabel(String name, Color colorBackGround, Color colorForeGround, Font font, boolean Opaque,boolean focusable, Border border, LayoutManager layout, int x, int y, int width, int height) {
		JLabel label = new JLabel(name, SwingConstants.CENTER);
		label.setBounds(x, y,width, height);
		label.setFont(font);
		label.setBackground(colorBackGround);
		label.setForeground(colorForeGround);
		label.setBorder(border);
		label.setLayout(layout);
		label.setOpaque(Opaque);
		label.setFocusable(focusable);
		return label;
	}

	private JButton createJButton(String name, Color colorBackGround, Color colorForeGround, Font buttonFont, boolean Opaque,boolean focusable, int x, int y, int width, int height) {
		JButton button = new JButton(name);
		button.setFocusable(focusable);
		button.setBounds(x, y, width, height);
		button.setBackground(colorBackGround);
		button.setOpaque(Opaque);
		button.setForeground(colorForeGround);
		button.setFont(buttonFont);
		button.setBorder(BorderFactory.createLineBorder(PANEL_BORDER, 2));
		button.setFocusPainted(false);
		button.setMargin(new Insets(0, 0, 0, 0));
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				button.setBackground(CONTROL_HOVER_BACKGROUND);
				button.setBorder(BorderFactory.createLineBorder(CONTROL_HOVER_BORDER, 2));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				button.setBackground(colorBackGround);
				button.setBorder(BorderFactory.createLineBorder(PANEL_BORDER, 2));
			}
		});
		return button;
	}

	private JRadioButton createJRadioButton(String name, Color colorBackGround, Color colorForeGround, Font buttonFont, boolean Opaque,boolean focusable, int x, int y, int width, int height) {
		JRadioButton button = new JRadioButton(name);
		button.setFocusable(focusable);
		button.setBounds(x, y, width, height);
		button.setBackground(colorBackGround);
		button.setOpaque(Opaque);
		button.setForeground(colorForeGround);
		button.setFont(buttonFont);
		button.setBorder(BorderFactory.createLineBorder(PANEL_BORDER));
		button.setFocusPainted(false);
		button.setHorizontalAlignment(SwingConstants.CENTER);
		return button;
	}

	public void exploded() {
		showResultScreen("You Lost!", "The field is cleared for another run.", LOSS_COPY_COLOR);
	}

	public void reset() {
		this.cubeManager.purgeCells();
		this.optionScreen();
	}

	public void checkWin() {
		this.cubeManager.checkWin();
	}
	
	public void win() {
		showResultScreen("You Win!", "Nice sweep. Ready for another board?", WIN_COPY_COLOR);
	}

	private void showResultScreen(String title, String message, Color titleColor) {
		Container glassPane = createOverlayGlassPane();
		JPanel resultPanel = createOverlayPanel(60, 290, 400, 320);
		JButton playAgain = this.createJButton("Play Again?", CONTROL_BACKGROUND, COPY_COLOR, BUTTON_FONT, true, false, 80, 190, 240, 82);
		playAgain.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == playAgain) {
					clearBoard();
				}
			}
		});

		JLabel label = createJLabel(title, PANEL_BACKGROUND, titleColor, TITLE_FONT, true, false, null, null, 20, 48, 360, 42);
		JLabel subLabel = createJLabel(message, PANEL_BACKGROUND, SUPPORT_COPY_COLOR, BODY_FONT, true, false, null, null, 20, 104, 360, 34);
		resultPanel.add(label);
		resultPanel.add(subLabel);
		resultPanel.add(playAgain);
		glassPane.add(resultPanel);
	}
	
	public void clearBoard() {
		this.reset();
	}
	
	public void addSidePanels() {
		Border border = BorderFactory.createLineBorder(Color.darkGray);
		Color gray = Color.gray;
		add(createJLabel(0, 0, SIDE_PANEL_WIDTH, SIDE_PANEL_HEIGHT, gray, border, null, construct));
		add(createJLabel(RIGHT_PANEL_X, 0, SIDE_PANEL_WIDTH, SIDE_PANEL_HEIGHT, gray, border, null, construct2));
		add(createJLabel(BOARD_X, BOTTOM_PANEL_Y, BOARD_WIDTH, PANEL_HEIGHT, gray, border, null, bottomPanel));
		add(createJLabel(BOARD_X, TOP_PANEL_Y, BOARD_WIDTH, PANEL_HEIGHT, gray, border, null, topPanel));	
	}

	private JLabel createJLabel(int x, int y, int width, int height, Color background, Border border, LayoutManager layout, ImageIcon icon) {
		JLabel label = new JLabel();
		label.setBackground(background);
		label.setBorder(border);
		label.setBounds(x,y,width,height);
		label.setLayout(layout);
		label.setIcon(icon);

		return label;
	}
	
	public void addNeigh(int i,int j) {
		if (!(i < 0 || j < 0) && !(i >=24 || j>=12)) {
			cells[i][j].addNeighbor(cells[i][j]);
		} 
		
	}
}
