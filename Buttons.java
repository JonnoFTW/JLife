
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/**
 * @author Jonathan
 *
 */
public class Buttons extends  JPanel  {


	private GUI g;
	public JToggleButton stopStart;
	private JLabel tickCount;
	
	/**
	 * The buttons that go at the top of the gui
	 * @param g
	 */
	Buttons(final GUI g) {
	    this.g = g;
		this.setSize(300, 50);
		stopStart = new JToggleButton("Start",false);
		stopStart.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent ev) {
				// TODO Auto-generated method stub
				if(ev.getStateChange()== ItemEvent.SELECTED){
					stopStart.setText("Running");
					g.life.start();
				} else if(ev.getStateChange() == ItemEvent.DESELECTED) {
					stopStart.setText("Stopped");
					g.life.stop();
				}
				
			}
		});
		JButton save = new JButton("Save");
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					g.life.exportBoard();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}); 
	//	save.addItemListener();
		JButton imp = new JButton("Import");
		imp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					g.life.importBoard();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				g.life.init();
				repaint();
			}
		});
		tickCount = new JLabel("Tick:"+g.life.getTicks());
		add(stopStart);
		add(imp);
		add(reset);
		add(save);
		add(imp);
		add(tickCount);
	}
	/**
	 * Sets the tick count on the ticket label
	 */
	public void setTick() {
		tickCount.setText("Tick:"+g.life.getTicks());
	}
}
