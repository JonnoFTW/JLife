import java.awt.BorderLayout;
 
import javax.swing.*;
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Start the gui
		JFrame frame = new JFrame("Game Of Life");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		// Has a play button, pause button, a grid of cells that can be flipped 1,0 on click.
		
		LifeGame lfe = new LifeGame();
		Buttons btns = new Buttons(lfe);
		frame.setLayout(new BorderLayout());
		frame.add(btns,BorderLayout.PAGE_START);
		frame.add(lfe,BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setBounds(100, 100, 660, 720);
		frame.setVisible(true);
		
	}

}
