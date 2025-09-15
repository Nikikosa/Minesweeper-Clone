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
	protected int width, height,  x=20, y=40;
	protected static int difficulty =1;      
	protected static Cube[][] cells = new Cube[24][12];
	protected Cube alpha;
	protected ProgressBombs bar;
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
	
	
	
	public gameBoard() {
		this.width = 536;
		this.height = 1049;
		//Border border = BorderFactory.createLineBorder(Color.white);
		setTitle("Nikikosas Minesweeper");	
		setIconImage(bombImage2.getImage());
		setSize(this.width, this.height);
		setLayout(null);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		addSidePanels();
		optionScreen();		
		setVisible(true);
		
	}
	public void optionScreen() {
		this.setGlassPane(new JComponent() {
			protected void paintComponent(Graphics g) {
				g.setColor(new Color(0,0,0, 220)); 		// 150 
				g.fillRect(0, 0, width, height);
			}
		});
		
		Container glassPane = (Container) this.getGlassPane();
		glassPane.setVisible(true);
		
		JLabel label = new JLabel("Welcome To Nikikosas Minesweeper", SwingConstants.CENTER);
		label.setBounds(60, 40,400, 40 );
		label.setFont(new Font("Times New Roman", Font.BOLD, 25));
		Border border = BorderFactory.createLineBorder(Color.darkGray);
		label.setBackground(new Color(0,0,0,100));
		label.setForeground(Color.white);
		label.setBorder(border);
		label.setLayout(null);
		
		JRadioButton easy = new JRadioButton("Easy");
		JRadioButton medium = new JRadioButton("Medium");
		JRadioButton hard = new JRadioButton("Hard");
		ButtonGroup group = new ButtonGroup();   // creates a new group object
		group.add(easy);   // these 3 buttons are added to the group
		group.add(medium);
		group.add(hard);
		
		easy.setBounds(60, 520, 120, 40);
		easy.setBackground(new Color(0,0,0,100));
		easy.setOpaque(false);
		easy.setForeground(Color.GREEN);
		easy.setFont(new Font("Times New Roman", Font.BOLD, 25));
		
		medium.setBounds(220, 520, 120, 40);
		medium.setBackground(new Color(0,0,0,100));
		medium.setForeground(Color.YELLOW);
		medium.setFont(new Font("Times New Roman", Font.BOLD, 25));
		medium.setOpaque(false);
		
		hard.setBounds(380, 520, 120, 40);
		hard.setBackground(new Color(0,0,0,100));
		hard.setOpaque(false);
		hard.setForeground(Color.RED);
		hard.setFont(new Font("Times New Roman", Font.BOLD, 25));
		
		easy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource()== easy) {
					difficulty = 1;
				}
				
			}
			
		});
		medium.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource()== medium) {
					difficulty = 2;
				}
				
			}
			
		});
		hard.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource()== hard) {
					difficulty = 3;
				}
				
			}
			
		});
		
		
		
		JButton play = new JButton();
		play.setFocusable(false);
		play.setText("Play");
		play.setForeground(Color.WHITE);
		play.setFont(new Font("Times New Roman", Font.BOLD, 25));
		play.setBackground(new Color(0,0,0,100));
		play.setOpaque(false);
		play.setBounds(140, 720, 240, 120);
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