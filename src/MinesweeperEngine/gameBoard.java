package MinesweeperEngine;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

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
		this.cubeManager = new CubeManager(288);
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
					makeBoard();
					playGame();
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
	
	public void playGame() {
		addBombs();
		cells[0][0].setBoard(this);
		cells[0][0].resetCounter();		
		setTypes();
	}
	
	@SuppressWarnings("serial")
	public void exploded() {
		
		uncoverAll();
		
		this.setGlassPane(new JComponent() {
			
			protected void paintComponent(Graphics g) {
				g.setColor(new Color(0,0,0, 220)); 		// 150 
				g.fillRect(0, 0, width, height);
			}
		});
		
		Container glassPane = (Container) this.getGlassPane();
		glassPane.setVisible(true);
		
		JButton playAgain = new JButton();
		playAgain.setFocusable(false);
		playAgain.setText("Play Again?");
		playAgain.setForeground(Color.WHITE);
		playAgain.setFont(new Font("Times New Roman", Font.BOLD, 25));
		playAgain.setBackground(new Color(0,0,0,100));
		playAgain.setOpaque(false);
		playAgain.setBounds(140, 680, 240, 120);
		playAgain.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == playAgain) {
					clearBoard();
				}
				
			}
			
		});
		
		JLabel label = new JLabel("You Lost!", SwingConstants.CENTER);
		label.setBounds(140, 40,240, 40 );
		label.setFont(new Font("Times New Roman", Font.BOLD, 25));
		Border border = BorderFactory.createLineBorder(Color.darkGray);
		label.setBackground(new Color(0,0,0,100));
		label.setForeground(Color.RED);
		label.setBorder(border);
		label.setLayout(null);
		
		glassPane.add(label);
		glassPane.add(playAgain);
		
		
	}
	public static void checkWin() {
		
		int bombs, counter = 0;

		switch(difficulty) {
		case 1:
			bombs = 25;
			break;
		case 2: 
			bombs = 50;
			break;
		case 3: 
			bombs = 90;
			break;
		default: 
			bombs = 0;
			break;
		}
		
		
		for (int i =0; i<24; i++) {
			for (int j=0; j<12; j++) {
				if (cells[i][j].isFlagged && cells[i][j].isBomb) {
					counter++;
				}
			}
		}
		
		if (counter == bombs) {
			game.win();
		}
	}
	
	public void win() {
		
		this.setGlassPane(new JComponent() {
			
			protected void paintComponent(Graphics g) {
				g.setColor(new Color(0,0,0, 220)); 		// 150 
				g.fillRect(0, 0, width, height);
			}
		});
		
		Container glassPane = (Container) this.getGlassPane();
		glassPane.setVisible(true);
		
		JButton playAgain = new JButton();
		playAgain.setFocusable(false);
		playAgain.setText("Play Again?");
		playAgain.setForeground(Color.WHITE);
		playAgain.setFont(new Font("Times New Roman", Font.BOLD, 25));
		//playAgain.setIcon(playAgainPic);
		playAgain.setBackground(new Color(0,0,0,100));
		playAgain.setOpaque(false);
		playAgain.setBounds(140, 680, 240, 120);
		playAgain.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == playAgain) {
					clearBoard();
				}
				
			}
			
		});
		
		JLabel label = new JLabel("You Win!", SwingConstants.CENTER);
		label.setBounds(140, 40,240, 40 );
		label.setFont(new Font("Times New Roman", Font.BOLD, 25));
		Border border = BorderFactory.createLineBorder(Color.darkGray);
		label.setBackground(new Color(0,0,0,100));
		label.setForeground(Color.GREEN);
		label.setBorder(border);
		label.setLayout(null);
		
		glassPane.add(label);
		glassPane.add(playAgain);
	}
	
	public void uncoverAll() {
		for (int i =0; i<24; i++) {
			for (int j=0; j<12; j++) {
				cells[i][j].uncover();
			}
		}
	}
	
	public void makeBoard() {
		for (int i =0; i<24; i++) {
			for (int j=0; j<12; j++) {
				alpha = new Cube(x,y);
				cells[i][j]=alpha;
				add(alpha);
				x+=40;
			}
			x=20;
			y+=40;	
		}
		
		x=0;
		y=0;
	}
	
	public void clearBoard() {
		
		cells[0][0].clearID();
		this.dispose();
		game.reset();
		
	}
	
	public void setTypes() {
		
		for (int i =0; i<24; i++) {
			for (int j=0; j<12; j++) {
				
				
			
					if (!(i-1==-1 || j-1==-1)) {
					cells[i][j].addNeighbor(cells[i-1][j-1]);
					}
				
					if (!(i-1==-1)) {
					cells[i][j].addNeighbor(cells[i-1][j]);
					}
				
					if(!(i-1==-1 || j+1==12)) {
					cells[i][j].addNeighbor(cells[i-1][j+1]);
					}
					
					if (!(j-1==-1)) {
					cells[i][j].addNeighbor(cells[i][j-1]);
					}
					
					if (!(j+1==12)) {
					cells[i][j].addNeighbor(cells[i][j+1]);
					}
					
					if (!(i+1==24 || j-1==-1)) {
					cells[i][j].addNeighbor(cells[i+1][j-1]);
					}
				 
					if (!(i+1==24)) {
					cells[i][j].addNeighbor(cells[i+1][j]);
					}
		 
					if (!(i+1==24 || j+1==12)) {
					cells[i][j].addNeighbor(cells[i+1][j+1]);
					}
		
				
					cells[i][j].setType();
			}	
		}
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
	
	/**
	 * The addBombs method adds bombs to the game board based on the difficulty choosen by the user.
	 * Easy difficultly is 25 bombs, medium is 50, and hard is 90. Within the add bombs method, the switch
	 * statement should never reach the default case. After the amount of bombs is choosen, this method creates
	 * a arraylist of type integer, and stores random cube ID's within it. The size of the array will be based on 
	 * the difficulty of the program. Once the list is filled, a nested for loop runs through all of the 
	 * cubes within the gameboard, and sets the cubes whose ID's match the ID's within the array list. Also, the 
	 * top panel is added within this method, it contains the number of bombs in the current game. Runtime for this method is O(1).
	 */
	void addBombs() {
		int remaining;
		
		switch(difficulty) {	// Difficulty is choosen based on a users choice within the startint screen.
		case 1:
			remaining = 25;
			break;
		case 2: 
			remaining = 50;
			break;
		case 3: 
			remaining = 90;
			break;
		default: 
			remaining = 0;
			break;
		}
		
		
		if (remaining == 0) {	// This if statment is here just in case of a error in the program.
			return;
		}
		
		ArrayList<Integer> nums = new ArrayList<>();	// This array list will hold random cube ID's to change to bombs.
		Random rand = new Random();
		int bomb;
		bar = new ProgressBombs(remaining);		// This bar will contain the number of bombs in the beginning of the game. Later is can be used to track how many are left.
		cells[0][0].setNumOfBombs(remaining);	// The number of bombs is set within the cube class. This variable is the same throughout all of the cubes.
		
		while (remaining != 0) {	// This while loop determines the random cube ID's and adds them to the list
			bomb = rand.nextInt(288)+1;
			if (!nums.contains(bomb)) {
				nums.add(bomb);
				remaining--;
			}
			
			
		}
			
		for (int i =0; i<24; i++) {		// Using the array list, the cubes that are choosen to be bombs and changed to be bombs.
			for (int j=0; j<12; j++) {
				if (nums.contains(cells[i][j].getID())) {
					cells[i][j].changtoBomb();				
				}
			}
			
		}
		
		cells[0][0].setBar(bar);	// The bar is set within the cube classes. This varibale is the same troughout all of the objects.
		add(bar);	// bar is added to the gameboard
		
	}
	
}