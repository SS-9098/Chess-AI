import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Pieces 
{
	JLabel Black[];
	JLabel White[];

	
	public Pieces() 
	{
		Black = new JLabel[16];
		White = new JLabel[16];
		   Image blackpawn = new ImageIcon("Chess pieces/blackpawn.png").getImage().getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH ) ;
		   Image whitepawn = new ImageIcon("Chess pieces/whitepawn.png").getImage().getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH ) ;
		   Image blackking = new ImageIcon("Chess pieces/blackking.png").getImage().getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH ) ;
		   Image whiteking = new ImageIcon("Chess pieces/whiteking.png").getImage().getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH ) ;
		   Image blackqueen = new ImageIcon("Chess pieces/blackqueen.png").getImage().getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH ) ;
		   Image whitequeen = new ImageIcon("Chess pieces/whitequeen.png").getImage().getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH ) ;
		   Image blackbishop = new ImageIcon("Chess pieces/blackbishop.png").getImage().getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH ) ;
		   Image whitebishop = new ImageIcon("Chess pieces/whitebishop.png").getImage().getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH ) ;
		   Image blackrook = new ImageIcon("Chess pieces/blackrook.png").getImage().getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH ) ;
		   Image whiterook = new ImageIcon("Chess pieces/whiterook.png").getImage().getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH ) ;
		   Image blackhorse = new ImageIcon("Chess pieces/blackhorse.png").getImage().getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH ) ;
		   Image whitehorse = new ImageIcon("Chess pieces/whitehorse.png").getImage().getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH ) ;
		  
		
		for(int i=0;i<16;i++)
		{
			Black[i]=new JLabel();
			White[i]=new JLabel();
			
			Black[i].setOpaque(false);
			//Black[i].setContentAreaFilled(false);
			//Black[i].setBorderPainted(false);
			White[i].setOpaque(false);
			//White[i].setContentAreaFilled(false);
			//White[i].setBorderPainted(false);
			
			if(i<8)
			{
				Black[i].setIcon(new ImageIcon(blackpawn));
				White[i].setIcon(new ImageIcon(whitepawn));
			}
			
			if(i==8||i==15)
			{
				Black[i].setIcon(new ImageIcon(blackrook));
				White[i].setIcon(new ImageIcon(whiterook));
			}
			
			if(i==9||i==14)
			{
				Black[i].setIcon(new ImageIcon(blackhorse));
				White[i].setIcon(new ImageIcon(whitehorse));
			}
			
			if(i==10||i==13)
			{
				Black[i].setIcon(new ImageIcon(blackbishop));
				White[i].setIcon(new ImageIcon(whitebishop));
			}
			
			if(i==11)
			{
				Black[i].setIcon(new ImageIcon(blackking));
				White[i].setIcon(new ImageIcon(whiteking));
			}
			
			if(i==12)
			{
				Black[i].setIcon(new ImageIcon(blackqueen));
				White[i].setIcon(new ImageIcon(whitequeen));
			}
		}
	}
	
	public JLabel fillblack(int i)
	{
		return Black[i];
	}
	public JLabel fillwhite(int i)
	{
		return White[i];
	}

}
