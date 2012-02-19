
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

public class Buttons extends  JPanel  {

	/**
	 * @param args
	 */
	private LifeGame life;
	public JToggleButton stopStart;
	private JLabel tickCount;
	
	Buttons(final LifeGame life) {
		this.life = life;
		this.setSize(300, 50);
		stopStart = new JToggleButton("Start",false);
		final Buttons t = this;
		stopStart.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent ev) {
				// TODO Auto-generated method stub
				if(ev.getStateChange()== ItemEvent.SELECTED){
					stopStart.setText("Running");
					life.start(t);
				} else if(ev.getStateChange() == ItemEvent.DESELECTED) {
					stopStart.setText("Stopped");
					life.stop();
				}
				
			}
		});
		JButton save = new JButton("Save");
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					life.exportBoard();
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
					life.importBoard();
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
				// TODO Auto-generated method stub
				life.init();
				repaint();
			}
		});
		tickCount = new JLabel("Tick:"+life.getTicks());
		add(stopStart);
		add(imp);
		add(reset);
		add(save);
		add(imp);
		add(tickCount);
	}
	public void setTick() {
		tickCount.setText("Tick:"+life.getTicks());
	}
}
