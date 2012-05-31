import java.io.*;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;

import javax.swing.Timer;
/**
 * @author Jonathan
 *
 */
public class LifeGame extends Canvas {
	private byte[][] cells, newCells; 
	
	private long tick;
	private int size = 100;
	private Timer t;
	private BufferStrategy bf;
	private GUI g;
	/**
	 * A new game of life, 
	 * @param g the GUI object that holds this frame and the buttons
	 */
	LifeGame(final GUI g) {
	    this.g = g;
		bf = getBufferStrategy();
		setSize(1000+size,1000+size);
		setBackground(Color.WHITE);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				int x = e.getX()/(getWidth()/size);
				int y = e.getY()/(getHeight()/size);
				
				cells[x][y] = (byte) (~cells[x][y] & 1);
				System.out.println("Marking cell at: x="+x+" y="+y+" as "+cells[x][y]);
				repaint();
			}
		});
		t = new Timer(500,new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent arg0) {
                tick();
                g.btns.setTick();
            }
        });
		
	}
	/* (non-Javadoc)
	 * @see java.awt.Canvas#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {
		createBufferStrategy(1);
		
		bf = getBufferStrategy();
		g = null;
		try{
			g = bf.getDrawGraphics();
			render(g);
		} finally {
			g.dispose();
		}
		bf.show();
		Toolkit.getDefaultToolkit().sync();
		
	}
	
	/**
	 * Render the cells in the frame with a neat border around each cell
	 * @param g
	 */
	private void render(Graphics g) {
		for(int x = 0; x < cells.length; x++ ) {
			for (int y = 0; y < cells[x].length; y++) {
				if(cells[x][y] ==1){
					g.setColor(Color.BLACK);
					g.fillRect(x*(getWidth()/size), y*(getHeight()/size),getWidth()/size ,getWidth()/size);
				}
				g.setColor(Color.GRAY);
				g.drawRect(x*(getWidth()/size), y*(getHeight()/size),getWidth()/size ,getWidth()/size);
			}
		}
	}
	/**
	 * Resets the game board
	 */
	public void init() {
		cells = new byte[size][size];
		// Set all cells to 0 (dead)
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				cells[i][j] = 0;
			}
		}
		tick = 0;
		g.btns.setTick();
		repaint();
	}
	/**
	 * Takes in the board.dat file and uses it to fill the board
	 * @throws FileNotFoundException
	 */
	public void importBoard() throws FileNotFoundException {
		init();
		Scanner in = new Scanner(new File("board.dat"));
		while(in.hasNext()) {
			cells[in.nextByte()][in.nextByte()] = 1;
		}
		in.close();
	}
	/**
	 * Returns the number of ticks that have passed in the simulation
	 * @return
	 */
	public long getTicks() {
		return tick;
	}
	/**
	 * Exports the current board to the file board.dat for later use.
	 * @throws IOException
	 */
	public void exportBoard() throws IOException {
		// Save the board to a file as x y
		BufferedWriter out = new BufferedWriter(new FileWriter("board.dat"));
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				if(cells[i][j] == 1) {
					out.write(i+" "+j+" ");
				}
			}
		}
		out.close();
	}
	/**
	 * Starts the simulation
	 */
	public void start() {
		t.start();
		
	}
	/**
	 * Stops the simulation
	 */
	public void stop() {
		t.stop();
		
	}
	/**
	 * Progresses the simulation one step forward. Uses the 8 neighbours around each cell 
	 * to calculate if a given cell lives, dies or is born.
	 */
	private void tick() {
		tick++;
		newCells = new byte[size][size];
		int totalSum = 0;
		// The array should wrap around so that cells[1000][1001] refers to the top right cell
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				
				int ip = (((i+1)%cells.length)+cells.length)%cells.length;
				int im = (((i-1)%cells.length)+cells.length)%cells.length;
				int jp = (((j+1)%cells[i].length)+cells[i].length)%cells[i].length;
				int jm = (((j-1)%cells[i].length)+cells[i].length)%cells[i].length;
				
				byte[] neighbours = {
					cells[im][j],
					cells[ip][j],
					cells[i][jp],
					cells[i][jm],
					cells[ip][jp],
					cells[ip][jm],
					cells[im][jp],
					cells[im][jm]
				};
				//System.out.println(Arrays.toString(neighbours));
				byte sum = 0;
				for(byte b: neighbours) {
					sum += b;
				}
				totalSum += sum;
				if(cells[i][j] == 1){
					// Dealing with live cells
					if(sum < 2) {
						newCells[i][j] = 0;
					} else if(sum > 3) {
						newCells[i][j] = 0;
					} else {
						newCells[i][j] = 1;
					}
				} else if(sum == 3) {
					// Dealing with dead cell
					newCells[i][j] = 1;
				} else {
					newCells[i][j] = 0;
				}
			}
		}
		if(totalSum == 0) {
			// Should probably stop now, since everyone is dead
			System.out.println("All cells are dead, stopping!");
			stop();
		}
		cells = newCells;
		repaint();
	}
}
