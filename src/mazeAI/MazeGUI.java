package mazeAI;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MazeGUI {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		
		MazeGenerator maze = new MazeGenerator(20,20);
				
		Cell[] cells = maze.getCells();
		Cell start = cells[0];
		Cell goal = cells[cells.length - 1];
	    Solver sol=new Solver(start, goal, cells,maze.width,maze.height);
	    
		MazePanel mazePanel = new MazePanel(maze, sol);
		JScrollPane scroll = new JScrollPane(mazePanel);

		setFrame(frame, scroll);

	}

	private static void setFrame(JFrame frame, JScrollPane scrollpane) {
		frame.setTitle("Maze");
		frame.setSize(600,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(scrollpane);
		frame.setVisible(true);
	}
}

class MazePanel extends JPanel
{	
	MazeGenerator maze;
	Solver sol;

	
	public MazePanel(MazeGenerator m, Solver s) {
		this.maze = m;
		this.sol=s;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setPreferredSize(getPreferredSize());
		this.setBackground(Color.WHITE);
		maze.drawMaze(g);
		sol.drawSolution(g);
		
		
	}
}
