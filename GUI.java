import java.awt.BorderLayout;

 
import javax.swing.*;
public class GUI {

    public LifeGame life;
    public Buttons btns;
    /**
     * @param args
     */
    
    GUI() {
        // Start the gui
        JFrame frame = new JFrame("Game Of Life");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        life = new LifeGame(this);
        btns = new Buttons(this);
        frame.setLayout(new BorderLayout());
        frame.add(btns,BorderLayout.PAGE_START);
        frame.add(life,BorderLayout.CENTER);
        life.init();
        frame.pack();
    //  frame.setResizable(false);
        frame.setBounds(100, 100, 660, 720);
        frame.setVisible(true);
        
    }

}
