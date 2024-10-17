import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Pieces 
{
	JLabel Black[];
	JLabel White[];
	
	Image blackpawn;
	Image whitepawn;
	Image blackking;
	Image whiteking;
	Image blackqueen;
	Image whitequeen;
	Image blackbishop;
	Image whitebishop;
	Image blackrook;
	Image whiterook;
	Image blackhorse;
	Image whitehorse;
	
	public Pieces() 
	{
		Black = new JLabel[16];
		White = new JLabel[16];
		   Image blackpawn = new ImageIcon("blackpawn.png").getImage().getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH ) ;
		   Image whitepawn = new ImageIcon("whitepawn.png").getImage().getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH ) ; 
		   Image blackking = new ImageIcon("blackking.png").getImage().getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH ) ; 
		   Image whiteking = new ImageIcon("whiteking.png").getImage().getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH ) ; 
		   Image blackqueen = new ImageIcon("blackqueen.png").getImage().getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH ) ; 
		   Image whitequeen = new ImageIcon("whitequeen.png").getImage().getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH ) ; 
		   Image blackbishop = new ImageIcon("blackbishop.png").getImage().getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH ) ; 
		   Image whitebishop = new ImageIcon("whitebishop.png").getImage().getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH ) ; 
		   Image blackrook = new ImageIcon("blackrook.png").getImage().getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH ) ; 
		   Image whiterook = new ImageIcon("whiterook.png").getImage().getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH ) ; 
		   Image blackhorse = new ImageIcon("blackhorse.png").getImage().getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH ) ; 
		   Image whitehorse = new ImageIcon("whitehorse.png").getImage().getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH ) ; 
		  
		
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
