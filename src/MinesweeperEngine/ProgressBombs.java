package MinesweeperEngine;

import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class ProgressBombs extends JProgressBar{
	private int bombs,max;
	
	ProgressBombs(int bombs) {
		new JProgressBar(0,bombs);
		this.bombs = bombs;
		this.max=bombs;
		setBounds(20,0,120,30);
		setStringPainted(true);
		setFont(new Font("Times New Roman", Font.BOLD, 25));
		setBackground(Color.black);
		setForeground(Color.black);
		setValue(0);
		setString(String.valueOf(bombs));
	}
	
	void removeBomb() {
		if (bombs==0) {
			
		} else {
		
		bombs--;
		setString(String.valueOf(bombs));
		}
	}

	void resetBar(int bombs) {
		this.bombs = bombs;
		this.max=bombs;
		setString(String.valueOf(bombs));
	}
	
	void addBomb() {
		if (bombs == max) {
			
		} else {
		bombs++;
		setString(String.valueOf(bombs));
		}
	}
	
	void pressed() {
		setFont(new Font("Times New Roman", Font.BOLD, 45));
	}
	
	void released() {
		setFont(new Font("Times New Roman", Font.BOLD, 25));
	}
}