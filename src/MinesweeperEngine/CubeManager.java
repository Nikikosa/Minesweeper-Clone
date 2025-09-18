package MinesweeperEngine;

import java.util.LinkedList;

public class CubeManager {
    private int numCubes;
    private int width = 40;
    private int height = 40;
    private Cube[][] cells;
    private LinkedList<Cube> cellList;

    public CubeManager(int numCubes) {
        this.numCubes = numCubes;
        this.cells = new Cube[24][12];
        this.cellList = new LinkedList<>();
        initCubeMatrix();
    }

    private void initCubeMatrix() {
        Cube cell;
        int x = 20;
        int y = 40;
        for (int i =0; i<24; i++) {
			for (int j=0; j<12; j++) {
				cell = new Cube(x,y);
				this.cells[i][j]=cell;
                cellList.add(cell);
				//add(alpha);
				x+=40;
			}
			x=20;
			y+=40;	
		}
		
		x=0;
		y=0;
    }
}
