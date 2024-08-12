import java.awt.Color;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MyFrame extends JFrame
{

	public MyFrame() 
	{
		this.setVisible(true);
		this.setSize(800,800);
		this.getContentPane().setBackground(Color.gray);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Chess");
		this.setLocationRelativeTo(null);
	}

}
