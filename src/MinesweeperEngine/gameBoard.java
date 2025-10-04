package MinesweeperEngine;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class gameBoard extends JFrame {
	private int width, height,  x=20, y=40;
	private static int difficulty =1;      
	private static Cube[][] cells = new Cube[24][12];
	private Cube alpha;
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
		Color color1 = new Color(0,0,0,100);
		Font buttonFont = new Font("Times New Roman", Font.BOLD, 25);
		Border border = BorderFactory.createLineBorder(Color.darkGray);

		this.setGlassPane(new JComponent() {
			protected void paintComponent(Graphics g) {
				g.setColor(new Color(0,0,0, 220)); 		// 150 
				g.fillRect(0, 0, width, height);
			}
		});
		
		Container glassPane = (Container) this.getGlassPane();
		glassPane.setVisible(true);
		JLabel label = this.createJLabel("Welcome To Nikikosas Minesweeper",color1, Color.WHITE,buttonFont,false,false,border,null,60,40,400,40);
		
		ButtonGroup group = new ButtonGroup();   // creates a new group object
		JRadioButton easy = createJRadioButton("Easy", color1, Color.GREEN, buttonFont, false,false, 60, 520, 120, 40);
		JRadioButton medium = createJRadioButton("Medium", color1, Color.YELLOW, buttonFont, false,false, 220, 520, 120, 40);
		JRadioButton hard = createJRadioButton("Hard", color1, Color.RED, buttonFont, false,false, 380, 520, 120, 40);
		group.add(easy);   // these 3 buttons are added to the group
		group.add(medium);
		group.add(hard);

		createDifficultyActionListener(easy,1);
		createDifficultyActionListener(medium,2);
		createDifficultyActionListener(hard,3);

		JButton play = createJButton("Play", color1,Color.WHITE ,buttonFont,false,false,140,720,240,120);

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
		
		glassPane.add(easy);
		glassPane.add(medium);
		glassPane.add(hard);
		glassPane.add(play);
		glassPane.add(label);
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
		return button;
	}
	
	public void exploded() {
		
		this.setGlassPane(new JComponent() {
			
			protected void paintComponent(Graphics g) {
				g.setColor(new Color(0,0,0, 220)); 		// 150 
				g.fillRect(0, 0, width, height);
			}
		});
		
		Color color1 = new Color(0,0,0,100);
		Font buttonFont = new Font("Times New Roman", Font.BOLD, 25);
		Border border = BorderFactory.createLineBorder(Color.darkGray);

		Container glassPane = (Container) this.getGlassPane();
		glassPane.setVisible(true);
		JButton playAgain = this.createJButton("Play Again?",color1,Color.WHITE,buttonFont,false,false,140,680,240,120);
		playAgain.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == playAgain) {
					clearBoard();
				}
			}
		});
		
		JLabel label = createJLabel("You Lost!", color1, Color.RED, buttonFont, false,false,border,null,140,40,240,40);
		
		glassPane.add(label);
		glassPane.add(playAgain);
	}

	public void reset() {
		this.cubeManager.purgeCells();
		this.optionScreen();
	}

	public void checkWin() {
		this.cubeManager.checkWin(difficulty);
	}
	
	public void win() {
		Color color1 = new Color(0,0,0,100);
		Font buttonFont = new Font("Times New Roman", Font.BOLD, 25);
		Border border = BorderFactory.createLineBorder(Color.darkGray);
		this.setGlassPane(new JComponent() {
			
			protected void paintComponent(Graphics g) {
				g.setColor(new Color(0,0,0, 220)); 		// 150 
				g.fillRect(0, 0, width, height);
			}
		});
		
		Container glassPane = (Container) this.getGlassPane();
		glassPane.setVisible(true);
		JButton playAgain = this.createJButton("Play Again?",color1,Color.WHITE,buttonFont,false,false,140,680,240,120);
		playAgain.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == playAgain) {
					clearBoard();
				}
			}
		});

		JLabel label = createJLabel("You Win!", color1, Color.GREEN, buttonFont, false,false,border,null,140,40,240,40);
		glassPane.add(label);
		glassPane.add(playAgain);
	}
	
	public void clearBoard() {
		this.reset();
	}
	
	public void addSidePanels() {
		Border border = BorderFactory.createLineBorder(Color.darkGray);
		Color gray = Color.gray;
		add(createJLabel(0, 0, 20, this.height, gray, border, null, construct));
		add(createJLabel(500, 0, 20, this.height, gray, border, null, construct2));
		add(createJLabel(20, 1000, this.width, 10, gray, border, null, topPanel));
		add(createJLabel(20, 30, this.width, 10, gray, border, null, topPanel));	
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