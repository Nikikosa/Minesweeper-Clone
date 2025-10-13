/**
 * Author: Nikikosa
 */
package MinesweeperEngine;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

public class CubeManager {
    private gameBoard board;
    private int numCubes;
    private Cube[][] cells;
    private LinkedList<Cube> cellList;
    private LinkedList<Cube> bombList;
    private ProgressBombs bar;
    private int numOfBombsRemaining;
    private int difficulty;

    public CubeManager(gameBoard board,int numCubes) {
        this.board = board;
        this.numCubes = numCubes;
        this.cells = new Cube[24][12];
        this.bar = new ProgressBombs(0);
        initCubeMatrix();
    }

    private void initCubeMatrix() {
        this.cellList = new LinkedList<>();
        Cube cell;
        int x = 20;
        int y = 40;
        for (int i =0; i<24; i++) {
			for (int j=0; j<12; j++) {
				cell = new Cube(this,x,y);
				this.cells[i][j]=cell;
                cellList.add(cell);
				x+=40;
			}
			x=20;
			y+=40;	
		}
		x=0;
		y=0;
    }

    public void populateCells(int difficulty) {
        int numOfBombs = 0;
        switch (difficulty) {
            case 1: 
                numOfBombs = 25;
                break;
            case 2: 
                numOfBombs = 50;
                break;
            case 3: 
                numOfBombs = 90;
                break;
            default:
                return;
        }
        this.bombList = new LinkedList<>();
        this.generateBombs(numOfBombs);
        this.numOfBombsRemaining = numOfBombs;
        this.bar.resetBar(numOfBombs);
        this.populateTypes();
        this.difficulty = difficulty;
    }

    public ProgressBombs getProgressBar() {
        return this.bar;
    }

    private int getNumOfBombs() {
        switch (difficulty) {
            case 1: 
                return 25;
            case 2: 
                return 50;
            case 3: 
                return 90;
            default:
                return 0;
        }
    }

    private void populateTypes() {
        for (int i =0; i<24; i++) {
			for (int j=0; j<12; j++) {
				
				
			
					if (!(i-1==-1 || j-1==-1)) {
					this.cells[i][j].addNeighbor(this.cells[i-1][j-1]);
					}
				
					if (!(i-1==-1)) {
					this.cells[i][j].addNeighbor(this.cells[i-1][j]);
					}
				
					if(!(i-1==-1 || j+1==12)) {
					this.cells[i][j].addNeighbor(this.cells[i-1][j+1]);
					}
					
					if (!(j-1==-1)) {
					this.cells[i][j].addNeighbor(this.cells[i][j-1]);
					}
					
					if (!(j+1==12)) {
					this.cells[i][j].addNeighbor(this.cells[i][j+1]);
					}
					
					if (!(i+1==24 || j-1==-1)) {
					this.cells[i][j].addNeighbor(this.cells[i+1][j-1]);
					}
				 
					if (!(i+1==24)) {
					this.cells[i][j].addNeighbor(this.cells[i+1][j]);
					}
		 
					if (!(i+1==24 || j+1==12)) {
					this.cells[i][j].addNeighbor(this.cells[i+1][j+1]);
					}
				
					this.cells[i][j].setType();
			}	
		}
    }

    private void generateBombs(int numOfBombs) {
        HashSet<Integer> bombCellIds = new HashSet<>();
        Random rand = new Random();
        
        while (bombCellIds.size() < numOfBombs) {
            bombCellIds.add(rand.nextInt(this.numCubes)+1);
        }

        this.populateBombs(bombCellIds);
    }

    private void populateBombs(HashSet<Integer> bombIds) {
        for (Cube cell : this.cellList) {
            if (bombIds.contains(cell.getID())) {
                cell.changtoBomb();
                this.bombList.add(cell);
            }
        }
    }

    public void checkWin(int difficulty) {
        int numOfBombs = getNumOfBombs();
        int counter = 0;
        for (Cube cell : this.bombList) {
            if (cell.isFlagged) {
                counter++;
            }
        }

        if (counter == numOfBombs) {
            this.board.win();
        }
    }


    public void purgeCells() {
        for (Cube cell : this.cellList) {
            cell.clearCube();
            cell.setVisible(false);
        }
    }

    public void showCells() {
        for (Cube cell : this.cellList) {
            this.board.add(cell).setVisible(true);;
        }
    }

    private void uncoverAllCells() {
        for (Cube cell : this.cellList) {
            cell.uncover();
        }
    }

    public void explosionTriggered() {
        uncoverAllCells();
        this.board.exploded();
    }

    public void setFlag(int id) {
        if (this.numOfBombsRemaining > 0) {
            this.cellList.get(id-1).flagButton();
            this.bar.removeBomb();
            this.numOfBombsRemaining--;
        }
        if (this.numOfBombsRemaining == 0) {
            this.checkWin(this.difficulty);
        }
    }

    public void removeFlag(int id) {
        this.cellList.get(id-1).removeFlag();
        this.bar.addBomb();
        this.numOfBombsRemaining++;
    }
}
