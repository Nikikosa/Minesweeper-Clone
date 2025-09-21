package MinesweeperEngine;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.Border;

public class Cube extends JPanel implements ActionListener{
	public static int cubeID =1;
	public static int numOfBombs;
	public static int flags;
	private  Cube[] neighbors = new Cube[8];
	private CubeManager manager;
	protected int iD;
	protected JButton button;
	protected JLabel label;
	protected static ImageIcon cubeImage = new ImageIcon("MineImages/Cell_Image.png");
	protected static ImageIcon one = new ImageIcon("MineImages/1.png");
	protected static ImageIcon two = new ImageIcon("MineImages/2.png");
	protected static ImageIcon three = new ImageIcon("MineImages/3.png");
	protected static ImageIcon four = new ImageIcon("MineImages/4.png");
	protected static ImageIcon five = new ImageIcon("MineImages/5.png");
	protected static ImageIcon six = new ImageIcon("MineImages/6.png");
	protected static ImageIcon seven = new ImageIcon("MineImages/7.png");
	protected static ImageIcon eight = new ImageIcon("MineImages/8.png");
	protected static ImageIcon blank = new ImageIcon("MineImages/blank.png");
	protected static ImageIcon flag = new ImageIcon("MineImages/flag.png");
	protected static ImageIcon bombImage = new ImageIcon("MineImages/Bomb.png");
	protected static ImageIcon pressedBombImage = new ImageIcon("MineImages/PressedBomb.png");
	protected int px,py,omega, neighCounter=0,counter, pressedBomb=0;
	protected boolean remove =false;
	protected boolean isBomb = false;
	protected boolean isFlagged = false;
	protected boolean clicked = false;
	protected boolean uncovered = false;
	protected static ProgressBombs bar;
	protected static gameBoard board;
	protected static int uncoveredCells=0;

	public Cube(CubeManager manager ,int x, int y) {	
		this.manager=manager;	
		iD=cubeID;
		cubeID++;
		px=x;
		py=y;
		
		addButton();
	
		
		Border border = BorderFactory.createLineBorder(Color.darkGray);
		setBackground(Color.gray);
		setBorder(border);
		setBounds(x,y,40,40);
		setLayout(null);
		
		add(button);
	}
	public void clearCube() {
		isBomb = false;
		uncovered = false;
		isFlagged = false;
		clicked = false;
		board = null;
		
		remove(button);
		button = null;
		clearNeighbors();
		addButton();
		add(button);
	}
	public void clearNeighbors() {
		for (int i = 0; i< neighbors.length; i++) {
			neighbors[i] = null;
		}
		neighCounter=0;
	}
	public void clearID() {
		cubeID=1;
	}
	public int getID() {
		return this.iD;
	}
	public void setBar(ProgressBombs bar) {
		this.bar=bar;
	}
	public void setBoard(gameBoard board) {
		this.board=board;
	}
	public void addButton() {
		button = new JButton();
		button.setFocusable(false);
		button.setText("");
		button.setIcon(cubeImage);
		button.setBackground(Color.LIGHT_GRAY);
		button.setBounds(0, 0, 40, 40);;
		button.addActionListener(this);
		button.addMouseListener(new MouseListener() { // Bug: doesnt allow a flag to be replaced (fixed)
			@Override
			public void mouseClicked(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) {	
				if (uncovered == false) {
				if (SwingUtilities.isRightMouseButton(e)|| e.isControlDown()) {
					if (uncovered == true) {
						
					} else {
					if (isFlagged == true) {
						removeFlag();
					} else {
							setFlag();
							if (flags == 0 || uncoveredCells==288) {
								//gameBoard.checkWin();
							}
							
					}
					}
				}
				}
				
			}
			@Override
			public void mouseReleased(MouseEvent e) {	
			}
			@Override
			public void mouseEntered(MouseEvent e) {	
			}

			@Override
			public void mouseExited(MouseEvent e) {	
			}
			
		});
			
		
	}
	public void setFlag() {
		// System.out.println(flags + " " + numOfBombs);
		// if (flags == numOfBombs*2) {
		// 	flags*=0.5;
		// }
		
		// if (!(flags < 1)) {
		// 	uncoveredCells++;
		// if (isFlagged == false) {
		// 	flags--;
		// }
		// isFlagged = true;
		// this.button.setIcon(flag);
		// bar.removeBomb();
		// if (flags == 0) {
		// }
		
		// }
		this.manager.setFlag(this.iD);
	}

	public void flagButton() {
		this.button.setIcon(flag);
	}
	
	public void resetCounter() {
		uncoveredCells=0;
	}
	public void addNeighbor(Cube alpha) {
			
			
			neighbors[neighCounter]=alpha;
			neighCounter++;
			
			
	}
	public void removeFlag() {
		uncoveredCells--;
		this.button.setIcon(cubeImage);
		bar.addBomb();
		isFlagged=false;
		flags++;
	}
	public void changtoBomb() {
		
		
		
		this.isBomb=true;
		//button.setIcon(bombImage);
	}
	public void setFlags() {
		flags=numOfBombs;
	}
	public void setNumOfBombs(int bombs) {
		numOfBombs=bombs;
		setFlags();
	}
	public boolean bombStatus() {
		return this.isBomb;
	}
	public void setType() {
		counter=0;
		for (int i =0; i < 8 ; i++) {
			
			if (neighbors[i]!=null)  {
				
			if (neighbors[i].bombStatus() == true) {
				counter++;
					
			}
			}
		}
		
	}
	public void uncover() {
		
		if (this.isBomb==true) {
			if (pressedBomb==1) {
				this.button.setIcon(pressedBombImage);
				uncovered=true;
			} else {
			this.button.setIcon(bombImage);
			uncovered=true;
			}
		} else {
		
		switch (counter) {
		case 1:
			this.button.setIcon(one);
			uncovered=true;
			break;
		case 2:
			this.button.setIcon(two);
			uncovered=true;
			break;
		case 3:
			this.button.setIcon(three);
			uncovered=true;
			break;
		case 4:
			this.button.setIcon(four);
			uncovered=true;
			break;
		case 5:
			this.button.setIcon(five);
			uncovered=true;
			break;
		case 6:
			this.button.setIcon(six);
			uncovered=true;
			break;
		case 7:
			this.button.setIcon(seven);
			uncovered=true;
			break;
		case 8:
			this.button.setIcon(eight);
			uncovered=true;
			break;
		default:
			uncovered=true;
			removeBlanks(this);
			break;
		}
		}
		clicked=true;
		uncoveredCells++;
		//System.out.println(uncoveredCells);
	}
	public void removeBlanks(Cube alpha) { // Causes stack overflow (fixed)
		
		
		alpha.button.setIcon(blank);
		
		for (int i =0; i < 8 ; i++) {
			
			if (neighbors[i]!=null && !neighbors[i].bombStatus())  {
				if (!alpha.neighbors[i].uncovered) {
				
				alpha.neighbors[i].uncover();
				}
			}
		}
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==button) {
			if (!clicked) {
			button.setVisible(false);
			
			if (isFlagged == true) {
				flags++;
			}
			//System.out.println(flags); // Bug with flags, When unchecked, number of flags increases for no reason  (Fixed)
			
			if (isBomb) {
				this.pressedBomb=1;
				button.setIcon(pressedBombImage);
				uncovered = true;
				
				manager.explosionTriggered();
				
			} else {
				uncover();
			}
			button.setVisible(true);
			clicked=true;
			} else {
				
			}
			
			
			if (uncoveredCells == 288) {
				//gameBoard.checkWin();
			}
		}
	}
	
}
