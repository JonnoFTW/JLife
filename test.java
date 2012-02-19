import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class test6 extends Canvas implements Runnable {
	static test6 t6;

	public test6() {
		setPreferredSize(new Dimension(400, 300));
		setIgnoreRepaint(true);
	}

	public void run() {
		int x = 20;
		boolean upDownFlag = true;
		t6.createBufferStrategy(2);
		BufferStrategy bs = t6.getBufferStrategy();
		while (true) {
			if (upDownFlag)
				++x;
			else
				--x;
			if (x > 200 || x < 20)
				upDownFlag = !upDownFlag;
			Graphics g = bs.getDrawGraphics();
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, t6.getWidth(), t6.getHeight());
			g.setColor(Color.BLACK);
			g.drawString("Hello World!", x, x);
			g.dispose();
			bs.show();
			try {
				Thread.sleep(20);
			} catch (InterruptedException ie) {
			}
		}
	}

	public static void main(String[] args) {
		Frame f = new Frame();
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}

			public void windowOpened(WindowEvent we) {
				new Thread(t6).start();
			}
		});
		t6 = new test6();
		f.add(t6);
		f.pack();
		f.setVisible(true);
	}
}