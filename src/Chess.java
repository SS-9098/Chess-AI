import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 *   Each square should be a separate panel
 *   Each square also must have two states: Empty and Occupied
 *   To  move, first highlight legal possible spaces(also check panel state)
 *   After a move, delete the piece from that panel and redeploy it in the target panel(Change panel state to occupied)
 *   Add "empty" button to the previous box
 *   
 *   Each piece is a panel containing a button(and a label if necessary)
 *   
 *   IDEA: TRY USING MOUSE LISTENER TO REPLACE BUTTONS
 *   
 *   black and white pieces indexes are increased by 1
 *   
 *   Each square must have an invisible button
 *   on buttons with no piece, check if background is green, if yes then move previously clicked piece there and add
 *   a new empty square in the original position
 */
public class Chess extends Pieces implements MouseListener
{
	JPanel frame;
	JFrame FRAME;
	JPanel[][] panel;
	JLabel[] blackpieces;
	JLabel[] whitepieces;
	JLabel[] empty;
	Pieces obj;
	int empty_index=0;
	int piece_index=1000;
	int[][] blackpieces_index;
	int[][] whitepieces_index;
	int[][] blackpieces_indextemp;
	int[][] whitepieces_indextemp;
	int[][] empty_pos;
	int piece_pointer;
	int temp=1005;
	int score;
	int nodes=2;//for minimax algorithm
	boolean first=true;//same
	boolean turn=true;
	
	public Chess() 
	{
		obj=new Pieces();
		FRAME=new MyFrame();
		frame = new JPanel();
		panel = new JPanel[8][8];
		blackpieces = new JLabel[16];
		whitepieces = new JLabel[16];
		empty = new JLabel[100];
		whitepieces_index=new int[16][2];
		blackpieces_index=new int[16][2];
		whitepieces_indextemp=new int[16][2];
		blackpieces_indextemp=new int[16][2];
		empty_pos=new int[64][2];
		
		FRAME.setLayout(new GridLayout(1,1));
		FRAME.add(frame);
		frame.setLayout(new GridLayout(8,8));
		
		for(int i=0;i<16;i++)
		{
			blackpieces[i]=obj.fillblack(i);
			whitepieces[i]=obj.fillwhite(i);
			blackpieces[i].setOpaque(false);
			whitepieces[i].setOpaque(false);
			blackpieces[i].setLayout(new GridLayout());
			whitepieces[i].setLayout(new GridLayout());
			
			//blackpieces[i].add(obj.fillblack(i));
			//whitepieces[i].add(obj.fillwhite(i));
			blackpieces[i].addMouseListener(this);
			whitepieces[i].addMouseListener(this);
			
		}
		
		
		
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				panel[i][j]=new JPanel();
				panel[i][j].setLayout(new GridLayout(1,1));
								
				if((i+j)%2==0)
					panel[i][j].setBackground(Color.lightGray);
				else
					panel[i][j].setBackground(Color.gray);
				
				frame.add(panel[i][j]);
				
			}
		}
		
		for(int i=0;i<100;i++)
		{
			empty[i]=new JLabel();
			empty[i].addMouseListener(this);
			
			//panel[4][1].add(empty[i]);
			
			
		}
		
		StartingBoard();
		frame.setVisible(true);
	}
	
	public void StartingBoard()
	{
		
			
		for(int i=0;i<8;i++)
		{
			panel[1][i].add(blackpieces[i]);
			panel[6][i].add(whitepieces[i]);
			
			blackpieces_index[i][1]=2*10+(i+1);
			whitepieces_index[i][1]=7*10+(i+1);
		}
		panel[0][0].add(blackpieces[8]);
		blackpieces_index[8][1]=11;
		panel[0][1].add(blackpieces[9]);
		blackpieces_index[9][1]=12;
		panel[0][2].add(blackpieces[10]);
		blackpieces_index[10][1]=13;
		panel[0][3].add(blackpieces[12]);
		blackpieces_index[12][1]=14;
		panel[0][4].add(blackpieces[11]);
		blackpieces_index[11][1]=15;
		panel[0][5].add(blackpieces[13]);
		blackpieces_index[13][1]=16;
		panel[0][6].add(blackpieces[14]);
		blackpieces_index[14][1]=17;
		panel[0][7].add(blackpieces[15]);
		blackpieces_index[15][1]=18;
		
		panel[7][0].add(whitepieces[8]);
		whitepieces_index[8][1]=81;
		panel[7][1].add(whitepieces[9]);
		whitepieces_index[9][1]=82;
		panel[7][2].add(whitepieces[10]);
		whitepieces_index[10][1]=83;
		panel[7][3].add(whitepieces[12]);
		whitepieces_index[12][1]=84;
		panel[7][4].add(whitepieces[11]);
		whitepieces_index[11][1]=85;
		panel[7][5].add(whitepieces[13]);
		whitepieces_index[13][1]=86;
		panel[7][6].add(whitepieces[14]);
		whitepieces_index[14][1]=87;
		panel[7][7].add(whitepieces[15]);
		whitepieces_index[15][1]=88;
		
		for(int i=2;i<6;i++)
		{
			for(int j=0;j<8;j++)
			{
				panel[i][j].add(empty[empty_index]);
				empty_pos[empty_index][1]=(i+1)*10+(j+1);
				empty_index++;
				
			}
		}
		
	}

	

	@Override
	public void mouseClicked(MouseEvent e)
	{
		resetBG();
		for(int i=0;i<16;i++)
		{
			
			if(e.getSource()==blackpieces[i])
			{
				if(turn==false)
				{
					highlight(i);
					piece_index=blackpieces_index[i][1];
					piece_pointer=i;
					System.out.println("click "+blackpieces_index[i][1]%10+" "+blackpieces_index[i][1]/10);
					
				}
				else 
				{
					if(islegal(piece_pointer, piece_index, blackpieces_index[i][1]) && piece_index<100)
					{
						if(i==11)
						{
							System.out.println("White wins!");
							
						}
						panel[blackpieces_index[i][1]/10-1][blackpieces_index[i][1]%10-1].removeAll();
						panel[piece_index/10-1][piece_index%10-1].removeAll();
						panel[blackpieces_index[i][1]/10-1][blackpieces_index[i][1]%10-1].add(whitepieces[piece_pointer]);
						panel[piece_index/10-1][piece_index%10-1].add(empty[empty_index]);
						empty[empty_index].revalidate();
						empty_pos[empty_index][1]=piece_index;
						empty_index++;
					
						panel[piece_index/10-1][piece_index%10-1].repaint();
						panel[blackpieces_index[i][1]/10-1][blackpieces_index[i][1]%10-1].repaint();
					
						whitepieces_index[piece_pointer][1]=blackpieces_index[i][1];
						blackpieces_index[i][1]=1000;
						
						turn=false;
						ai();
					}
					piece_index=1040;
				}
			}
			
			if(e.getSource()==whitepieces[i])
			{
				if(turn)
				{
					highlight(i);
					piece_index=whitepieces_index[i][1];
					piece_pointer=i;
					System.out.println("click "+whitepieces_index[i][1]%10+" "+whitepieces_index[i][1]/10);
					
				}
				else
				{
					if(islegal(piece_pointer, piece_index, whitepieces_index[i][1]) && piece_index<100)
					{
						if(i==11)
						{
							System.out.println("Black wins!");
							
						}
						panel[whitepieces_index[i][1]/10-1][whitepieces_index[i][1]%10-1].removeAll();
						panel[piece_index/10-1][piece_index%10-1].removeAll();
						panel[whitepieces_index[i][1]/10-1][whitepieces_index[i][1]%10-1].add(blackpieces[piece_pointer]);
						panel[piece_index/10-1][piece_index%10-1].add(empty[empty_index]);
						empty_pos[empty_index][1]=piece_index;
						empty[empty_index].revalidate();
						empty_pos[empty_index][1]=piece_index;
						empty_index++;
					
						panel[piece_index/10-1][piece_index%10-1].repaint();
						panel[whitepieces_index[i][1]/10-1][whitepieces_index[i][1]%10-1].repaint();
					
						blackpieces_index[piece_pointer][1]=whitepieces_index[i][1];
						whitepieces_index[i][1]=1000;
						
						turn=true;
						
					}
					piece_index=1030;
				}
			}
			
			
		}
		
		for(int i=0;i<64;i++)
		{
			if(e.getSource()==empty[i])
			{
				
				if(turn==false)
				{
					if(islegal(piece_pointer, piece_index, empty_pos[i][1]) && piece_index<100)
					{
						System.out.println("move");
						panel[empty_pos[i][1]/10-1][empty_pos[i][1]%10-1].removeAll();
						panel[piece_index/10-1][piece_index%10-1].removeAll();
						panel[empty_pos[i][1]/10-1][empty_pos[i][1]%10-1].add(blackpieces[piece_pointer]);
						panel[piece_index/10-1][piece_index%10-1].add(empty[i]);
					
						panel[piece_index/10-1][piece_index%10-1].repaint();
						panel[empty_pos[i][1]/10-1][empty_pos[i][1]%10-1].repaint();
					
						blackpieces_index[piece_pointer][1]=empty_pos[i][1];
						empty_pos[i][1]=piece_index;
						
						turn=true;
						
					}
					piece_index=1020;
				}
				else
				{
					if(islegal(piece_pointer, piece_index, empty_pos[i][1]) && piece_index<100)
					{

						if(piece_pointer==11 && empty_pos[i][1]==83)//castling
						{
							panel[7][3].removeAll();
							panel[7][0].removeAll();
							panel[7][3].add(whitepieces[8]);
							panel[7][0].add(empty[empty_index]);

							panel[7][1].repaint();
							panel[7][0].repaint();

							whitepieces_index[8][1]=84;
							empty_pos[empty_index][1]=81;
							empty_index++;
						}
						if(piece_pointer==11 && empty_pos[i][1]==87)//castling
						{
							panel[7][5].removeAll();
							panel[7][7].removeAll();
							panel[7][5].add(whitepieces[15]);
							panel[7][7].add(empty[empty_index]);

							panel[7][7].repaint();
							panel[7][5].repaint();

							whitepieces_index[15][1]=86;
							empty_pos[empty_index][1]=88;
							empty_index++;
						}
						panel[empty_pos[i][1]/10-1][empty_pos[i][1]%10-1].removeAll();
						panel[piece_index/10-1][piece_index%10-1].removeAll();
						panel[empty_pos[i][1]/10-1][empty_pos[i][1]%10-1].add(whitepieces[piece_pointer]);
						panel[piece_index/10-1][piece_index%10-1].add(empty[i]);

						panel[piece_index/10-1][piece_index%10-1].repaint();
						panel[empty_pos[i][1]/10-1][empty_pos[i][1]%10-1].repaint();

						whitepieces_index[piece_pointer][1]=empty_pos[i][1];
						empty_pos[i][1]=piece_index;
						
						turn=false;
						ai();
					}
					piece_index=1010;
				}
				
			}
		}
		
		for(int i=0;i<8;i++)
		{
			if(whitepieces_index[i][1]/10==1)
			{
				panel[0][whitepieces_index[i][1]%10-1].removeAll();
				panel[0][whitepieces_index[i][1]%10-1].add(whitepieces[12]);
				panel[0][whitepieces_index[i][1]%10-1].repaint();
				whitepieces_index[12][1]=whitepieces_index[i][1];
				whitepieces_index[i][1]=1000;
			}
			if(blackpieces_index[i][1]/10==8)
			{
				panel[8][blackpieces_index[i][1]%10-1].removeAll();
				panel[8][blackpieces_index[i][1]%10-1].add(blackpieces[12]);
				panel[8][blackpieces_index[i][1]%10-1].repaint();
				blackpieces_index[12][1]=blackpieces_index[i][1];
				blackpieces_index[i][1]=1000;
			}
		}
		
		
	}
	
	boolean islegal(int piece, int piece_pos, int target_pos)
	{
		if(piece_pos>100)
		{
			return false;
		}
		
		
		
		if(temp==1005)
		{
			if(turn)
			{
				temp=target_pos;
				whitepieces_index[piece][1]=temp;
				turn=false;
				for(int i=0;i<16;i++)
				{
					if(blackpieces_index[i][1]==target_pos)
						blackpieces_index[i][1]=1001;
					if(islegal(i, blackpieces_index[i][1], whitepieces_index[11][1]))
					{
						if(blackpieces_index[i][1]==1001)
							blackpieces_index[i][1]=target_pos;
						turn=true;
						whitepieces_index[piece][1]=piece_pos;
						temp=1005;
						return false;
					}
					if(blackpieces_index[i][1]==1001)
						blackpieces_index[i][1]=target_pos;
				}
				if(blackpieces_index[piece][1]==1001)
					blackpieces_index[piece][1]=target_pos;
				whitepieces_index[piece][1]=piece_pos;
				temp=1005;
				turn=true;
			}
			else
			{
				temp=target_pos;
				blackpieces_index[piece][1]=temp;
				turn=true;
				for(int i=0;i<16;i++)
				{
					if(whitepieces_index[i][1]==target_pos)
						whitepieces_index[i][1]=1001;
					if(islegal(i, whitepieces_index[i][1], blackpieces_index[11][1]))
					{
						if(whitepieces_index[i][1]==1001)
							whitepieces_index[i][1]=target_pos;
						turn=false;
						blackpieces_index[piece][1]=piece_pos;
						temp=1005;
						return false;
					}
					if(whitepieces_index[i][1]==1001)
						whitepieces_index[i][1]=target_pos;
				}
				if(whitepieces_index[piece][1]==1001)
					whitepieces_index[piece][1]=target_pos;
				blackpieces_index[piece][1]=piece_pos;
				temp=1005;
				turn=false;
			}
		}
		
		if(piece<8)//for pawn
		{
			if(turn)
			{
				if((piece_pos/10-1==target_pos/10&&piece_pos%10-1==target_pos%10) || (piece_pos/10-1==target_pos/10 && piece_pos%10+1==target_pos%10))
				{
					for(int i=0;i<16;i++)
					{
						if(target_pos==blackpieces_index[i][1])
							return true;
					}
				}
				if(piece_pos/10==7)
				{
					if(target_pos/10+2==piece_pos/10 && target_pos%10==piece_pos%10)
					{
						for(int i=0;i<16;i++)
						{
							if(whitepieces_index[i][1]==target_pos+10 || target_pos+10==blackpieces_index[i][1])
								return false;
						}
						return true;
					}
				}
				if(target_pos/10+1==piece_pos/10  && target_pos%10==piece_pos%10)
				{
					for(int i=0;i<16;i++)
					{
						if(whitepieces_index[i][1]==target_pos || target_pos==blackpieces_index[i][1])
							return false;
					}
					return true;
				}
			}
			else
			{
				if(piece_pos+11==target_pos || (piece_pos/10+1==target_pos/10 && piece_pos%10-1==target_pos%10))
				{
					for(int i=0;i<16;i++)
					{
						if(target_pos==whitepieces_index[i][1])
							return true;
					}
				}
				if(piece_pos/10==2)
				{
					if(target_pos/10-2==piece_pos/10  && target_pos%10==piece_pos%10)
						{
							for(int i=0;i<16;i++)
							{
								if(whitepieces_index[i][1]==target_pos || target_pos==blackpieces_index[i][1])
									return false;
								if(whitepieces_index[i][1]==target_pos-10 || target_pos-10==blackpieces_index[i][1])
									return false;
							}
							return true;
						}
				}
				if(target_pos/10-1==piece_pos/10  && target_pos%10==piece_pos%10)
				{
					for(int i=0;i<16;i++)
					{
						if(blackpieces_index[i][1]==target_pos || whitepieces_index[i][1]==target_pos)
							return false;
					}
					return true;
				}
			}
			
		}
		
		else if(piece==8||piece==15)//for rook
		{
			if(target_pos/10>piece_pos/10 && target_pos%10==piece_pos%10)
			{
				for(int i=piece_pos/10+1;i<=target_pos/10;i++)
				{
					for(int j=0;j<16;j++)
					{
						if(turn)
						{
							if(whitepieces_index[j][1]==i*10+piece_pos%10)
								return false;
							if(blackpieces_index[j][1]==i*10+piece_pos%10)
							{
								if(blackpieces_index[j][1]==target_pos)
									return true;
								return false;
							}
						}
						else
						{
							if(blackpieces_index[j][1]==i*10+piece_pos%10)
								return false;
							if(whitepieces_index[j][1]==i*10+piece_pos%10)
							{
								if(whitepieces_index[j][1]==target_pos)
									return true;
								return false;
							}
						}
						
					}
				}
				return true;
			}
			if(target_pos/10<piece_pos/10  && target_pos%10==piece_pos%10)
			{
				for(int i=piece_pos/10-1;i>=target_pos/10;i--)
				{
					for(int j=0;j<16;j++)
					{
						if(turn)
						{
							if(whitepieces_index[j][1]==i*10+piece_pos%10)
								return false;
							if(blackpieces_index[j][1]==i*10+piece_pos%10)
							{
								if(blackpieces_index[j][1]==target_pos)
									return true;
								return false;
							}
						}
						else
						{
							if(blackpieces_index[j][1]==i*10+piece_pos%10)
								return false;
							if(whitepieces_index[j][1]==i*10+piece_pos%10)
							{
								if(whitepieces_index[j][1]==target_pos)
									return true;
								return false;
							}
						}
					}
				}
				return true;
			}
			if(target_pos%10<piece_pos%10  && target_pos/10==piece_pos/10)
			{
				for(int i=piece_pos%10-1;i>=target_pos%10;i--)
				{
					for(int j=0;j<16;j++)
					{
						if(turn)
						{
							if(whitepieces_index[j][1]==i+(piece_pos/10)*10)
								return false;
							if(blackpieces_index[j][1]==i+(piece_pos/10)*10)
							{
								if(blackpieces_index[j][1]==target_pos)
									return true;
								return false;
							}
						}
						else
						{
							if(blackpieces_index[j][1]==i+(piece_pos/10)*10)
								return false;
							if(whitepieces_index[j][1]==i+(piece_pos/10)*10)
							{
								if(whitepieces_index[j][1]==target_pos)
									return true;
								return false;
							}
						}
					}
				}
				return true;
			}
			if(target_pos%10>piece_pos%10  && target_pos/10==piece_pos/10)
			{
				for(int i=piece_pos%10+1;i<=target_pos%10;i++)
				{
					for(int j=0;j<16;j++)
					{
						if(turn)
						{
							if(whitepieces_index[j][1]==i+(piece_pos/10)*10)
								return false;
							if(blackpieces_index[j][1]==i+(piece_pos/10)*10)
							{
								if(blackpieces_index[j][1]==target_pos)
									return true;
								return false;
							}
						}
						else
						{
							if(blackpieces_index[j][1]==i+(piece_pos/10)*10)
								return false;
							if(whitepieces_index[j][1]==i+(piece_pos/10)*10)
							{
								if(whitepieces_index[j][1]==target_pos)
									return true;
								return false;
							}
						}
						
					}
				}
				return true;
			}
		}
		
		
		else if(piece==10||piece==13)//for bishop
		{
			if( ((target_pos/10)-(piece_pos/10) == (target_pos%10)-(piece_pos%10)) && ((target_pos%10)-(piece_pos%10))>0)
			{
				for(int i=1;i<=target_pos/10-piece_pos/10;i++)
				{
					for(int j=0;j<16;j++)
					{
						if(turn)
						{
							if(whitepieces_index[j][1]== piece_pos+(i*10) + i)
								return false;
							if(blackpieces_index[j][1]== piece_pos+(i*10)+i)
							{
								if(blackpieces_index[j][1]==target_pos)
									return true;
								return false;
							}
						}
						else
						{
							if(blackpieces_index[j][1]== piece_pos+(i*10) + i)
								return false;
							if(whitepieces_index[j][1]== piece_pos+(i*10)+i)
							{
								if(whitepieces_index[j][1]==target_pos)
									return true;
								return false;
							}
						}
					}
				}
				return true;
			}
			
			if( (target_pos/10)-(piece_pos/10) == -((target_pos%10)-(piece_pos%10)) && (target_pos/10)-(piece_pos/10)>0)
			{
				for(int i=1;i<=target_pos/10-piece_pos/10;i++)
				{
					for(int j=0;j<16;j++)
					{
						if(turn)
						{
							if(whitepieces_index[j][1]== piece_pos+(i*10) - i)
								return false;
							if(blackpieces_index[j][1]== piece_pos+(i*10)-i)
							{
								if(blackpieces_index[j][1]==target_pos)
									return true;
								return false;
							}
						}
						else
						{
							if(blackpieces_index[j][1]== piece_pos+(i*10) - i)
								return false;
							if(whitepieces_index[j][1]== piece_pos+(i*10)-i)
							{
								if(whitepieces_index[j][1]==target_pos)
									return true;
								return false;
							}
						}
					}
				}
				return true;
			}
			
			if( -((target_pos/10)-(piece_pos/10)) == (target_pos%10)-(piece_pos%10) && (target_pos/10)-(piece_pos/10)<0)
			{
				for(int i=1;i<=target_pos%10-piece_pos%10;i++)
				{
					for(int j=0;j<16;j++)
					{
						if(turn)
						{
							if(whitepieces_index[j][1]== piece_pos-(i*10) + i)
								return false;
							if(blackpieces_index[j][1]==piece_pos-(i*10)+i)
							{
								if(blackpieces_index[j][1]==target_pos)
									return true;
								return false;
							}
						}
						else
						{
							}
					}
				}
				return true;
			}
			
			if( ((target_pos/10)-(piece_pos/10) == (target_pos%10)-(piece_pos%10)) && (target_pos%10)-(piece_pos%10)<0)
			{
				for(int i=1;i<=-(target_pos/10-piece_pos/10);i++)
				{
					for(int j=0;j<16;j++)
					{
						if(turn)
						{
							if(whitepieces_index[j][1]== piece_pos-(i*10) - i)
								return false;
							if(blackpieces_index[j][1]== piece_pos-(i*10)-i)
							{
								if(blackpieces_index[j][1]==target_pos)
									return true;
								return false;
							}
						}
						else
						{
							if(blackpieces_index[j][1]== piece_pos-(i*10) - i)
								return false;
							if(whitepieces_index[j][1]== piece_pos-(i*10)-i)
							{
								if(whitepieces_index[j][1]==target_pos)
									return true;
								return false;
							}
						}
					}
				}
				return true;
			}
		}
		
		
		if(piece==9||piece==14)//for horse
		{
			if( (Math.abs((target_pos/10)-(piece_pos/10))==2 && Math.abs((target_pos%10)-(piece_pos%10))==1) || (Math.abs((target_pos/10)-(piece_pos/10))==1 && Math.abs((target_pos%10)-(piece_pos%10))==2) )
				{
					for(int i=0;i<16;i++)
					{
						if(turn)
						{
							if(whitepieces_index[i][1]==target_pos)
								return false;
						}
						else
						{
							if(blackpieces_index[i][1]==target_pos)
								return false;
						}
					}
					return true;
				}
			
		}
		
		
		if(piece==11)//for king
		{
			if( (Math.abs((target_pos/10)-(piece_pos/10)) + Math.abs((target_pos%10)-(piece_pos%10))==1) || (( Math.abs((target_pos/10)-(piece_pos/10)) == 1) && (Math.abs((target_pos%10)-(piece_pos%10))==1)) )
			{
					for(int j=0;j<16;j++)
					{
						if(turn)
						{
							if(whitepieces_index[j][1]==target_pos)
								return false;
						}
						else
						{
							if(blackpieces_index[j][1]==target_pos)
								return false;
						}
					}
				return true;
			}
			if(turn)//castling
			{
				if(target_pos==83 && whitepieces_index[8][1]==81 && whitepieces_index[11][1]==85)
				{
					for(int i=0;i<16;i++)
					{
						if(whitepieces_index[i][1]==82 || whitepieces_index[i][1]==84)
							return false;
						if(blackpieces_index[i][1]==82 || blackpieces_index[i][1]==84)
							return false;
					}
					return true;
				}
				if(target_pos==87 && whitepieces_index[15][1]==88 && whitepieces_index[11][1]==85)
				{
					for(int i=0;i<16;i++)
					{
						if(whitepieces_index[i][1]==86)
							return false;
						if(blackpieces_index[i][1]==86)
							return false;
					}
					return true;
				}
			}
			else
			{
				if(target_pos==13 && blackpieces_index[8][1]==11 && blackpieces_index[11][1]==15)
				{
					for(int i=0;i<16;i++)
					{
						if(whitepieces_index[i][1]==12 || whitepieces_index[i][1]==14)
							return false;
						if(blackpieces_index[i][1]==12 || blackpieces_index[i][1]==14)
							return false;
					}
					return true;
				}
				if(target_pos==87 && blackpieces_index[15][1]==18 && blackpieces_index[11][1]==15)
				{
					for(int i=0;i<16;i++)
					{
						if(whitepieces_index[i][1]==16)
							return false;
						if(blackpieces_index[i][1]==16)
							return false;
					}
					return true;

				}
			}

		}
		
		
		if (piece==12)//for queen
		{
			if(target_pos/10>piece_pos/10 && target_pos%10==piece_pos%10)
			{
				for(int i=piece_pos/10+1;i<=target_pos/10;i++)
				{
					for(int j=0;j<16;j++)
					{
						if(turn)
						{
							if(whitepieces_index[j][1]==i*10+piece_pos%10)
								return false;
							if(blackpieces_index[j][1]==i*10+piece_pos%10)
							{
								if(blackpieces_index[j][1]==target_pos)
									return true;
								return false;
							}
						}
						else
						{
							if(blackpieces_index[j][1]==i*10+piece_pos%10)
								return false;
							if(whitepieces_index[j][1]==i*10+piece_pos%10)
							{
								if(whitepieces_index[j][1]==target_pos)
									return true;
								return false;
							}
						}
						
					}
				}
				return true;
			}
			if(target_pos/10<piece_pos/10  && target_pos%10==piece_pos%10)
			{
				for(int i=piece_pos/10-1;i>=target_pos/10;i--)
				{
					for(int j=0;j<16;j++)
					{
						if(turn)
						{
							if(whitepieces_index[j][1]==i*10+piece_pos%10)
								return false;
							if(blackpieces_index[j][1]==i*10+piece_pos%10)
							{
								if(blackpieces_index[j][1]==target_pos)
									return true;
								return false;
							}
						}
						else
						{
							if(blackpieces_index[j][1]==i*10+piece_pos%10)
								return false;
							if(whitepieces_index[j][1]==i*10+piece_pos%10)
							{
								if(whitepieces_index[j][1]==target_pos)
									return true;
								return false;
							}
						}
					}
				}
				return true;
			}
			if(target_pos%10<piece_pos%10  && target_pos/10==piece_pos/10)
			{
				for(int i=piece_pos%10-1;i>=target_pos%10;i--)
				{
					for(int j=0;j<16;j++)
					{
						if(turn)
						{
							if(whitepieces_index[j][1]==i+(piece_pos/10)*10)
								return false;
							if(blackpieces_index[j][1]==i+(piece_pos/10)*10)
							{
								if(blackpieces_index[j][1]==target_pos)
									return true;
								return false;
							}
						}
						else
						{
							if(blackpieces_index[j][1]==i+(piece_pos/10)*10)
								return false;
							if(whitepieces_index[j][1]==i+(piece_pos/10)*10)
							{
								if(whitepieces_index[j][1]==target_pos)
									return true;
								return false;
							}
						}
					}
				}
				return true;
			}
			if(target_pos%10>piece_pos%10  && target_pos/10==piece_pos/10)
			{
				for(int i=piece_pos%10+1;i<=target_pos%10;i++)
				{
					for(int j=0;j<16;j++)
					{
						if(turn)
						{
							if(whitepieces_index[j][1]==i+(piece_pos/10)*10)
								return false;
							if(blackpieces_index[j][1]==i+(piece_pos/10)*10)
							{
								if(blackpieces_index[j][1]==target_pos)
									return true;
								return false;
							}
						}
						else
						{
							if(blackpieces_index[j][1]==i+(piece_pos/10)*10)
								return false;
							if(whitepieces_index[j][1]==i+(piece_pos/10)*10)
							{
								if(whitepieces_index[j][1]==target_pos)
									return true;
								return false;
							}
						}
						
					}
				}
				return true;
			}
		
			
		if( (target_pos/10)-(piece_pos/10) == (target_pos%10)-(piece_pos%10) && ((target_pos%10)-(piece_pos%10))>0)
		{
			for(int i=1;i<=target_pos/10-piece_pos/10;i++)
			{
				for(int j=0;j<16;j++)
				{
					if(turn)
					{
						if(whitepieces_index[j][1]== piece_pos+(i*10) + i)
							return false;
						if(blackpieces_index[j][1]== piece_pos+(i*10)+i)
						{
							if(blackpieces_index[j][1]==target_pos)
								return true;
							return false;
						}
					}
					else
					{
						if(blackpieces_index[j][1]== piece_pos+(i*10) + i)
							return false;
						if(whitepieces_index[j][1]== piece_pos+(i*10)+i)
						{
							if(whitepieces_index[j][1]==target_pos)
								return true;
							return false;
						}
					}
				}
			}
			return true;
		}
		
		if( (target_pos/10)-(piece_pos/10) == -((target_pos%10)-(piece_pos%10)) && (target_pos/10)-(piece_pos/10)>0)
		{
			for(int i=1;i<=target_pos/10-piece_pos/10;i++)
			{
				for(int j=0;j<16;j++)
				{
					if(turn)
					{
						if(whitepieces_index[j][1]== piece_pos+(i*10) - i)
							return false;
						if(blackpieces_index[j][1]== piece_pos+(i*10)-i)
						{
							if(blackpieces_index[j][1]==target_pos)
								return true;
							return false;
						}
					}
					else
					{
						if(blackpieces_index[j][1]== piece_pos+(i*10) - i)
							return false;
						if(whitepieces_index[j][1]== piece_pos+(i*10)-i)
						{
							if(whitepieces_index[j][1]==target_pos)
								return true;
							return false;
						}
					}
				}
			}
			return true;
		}
		
		if( -((target_pos/10)-(piece_pos/10)) == (target_pos%10)-(piece_pos%10) && (target_pos/10)-(piece_pos/10)<0)
		{
			for(int i=1;i<=target_pos%10-piece_pos%10;i++)
			{
				for(int j=0;j<16;j++)
				{
					if(turn)
					{
						if(whitepieces_index[j][1]== piece_pos-(i*10) + i)
							return false;
						if(blackpieces_index[j][1]==piece_pos-(i*10)+i)
						{
							if(blackpieces_index[j][1]==target_pos)
								return true;
							return false;
						}
					}
					else
					{
						if(blackpieces_index[j][1]== piece_pos-(i*10) + i)
							return false;
						if(whitepieces_index[j][1]== piece_pos-(i*10)+i)
						{
							if(whitepieces_index[j][1]==target_pos)
								return true;
							return false;
						}
					}
				}
			}
			return true;
		}
		
		if( (target_pos/10)-(piece_pos/10) == (target_pos%10)-(piece_pos%10) && (target_pos%10)-(piece_pos%10)<0)
		{
			for(int i=1;i<=-(target_pos/10-piece_pos/10);i++)
			{
				for(int j=0;j<16;j++)
				{
					if(turn)
					{
						if(whitepieces_index[j][1]== piece_pos-(i*10) - i)
							return false;
						if(blackpieces_index[j][1]== piece_pos-(i*10)-i)
						{
							if(blackpieces_index[j][1]==target_pos)
								return true;
							return false;
						}
					}
					else
					{
						if(blackpieces_index[j][1]== piece_pos-(i*10) - i)
							return false;
						if(whitepieces_index[j][1]== piece_pos-(i*10)-i)
						{
							if(whitepieces_index[j][1]==target_pos)
								return true;
							return false;
						}
					}
				}
			}
			return true;
		}
	}	
		
		return false;
	}
	
	void highlight(int piece)
	{
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				if(turn)
				{
					if(islegal(piece, whitepieces_index[piece][1], ((i+1)*10)+j+1))
						panel[i][j].setBackground(new Color(158,240,180));
				}
				else
				{
					if(islegal(piece, blackpieces_index[piece][1], ((i+1)*10)+j+1))
						panel[i][j].setBackground(new Color(158,240,180));
				}
			}
		}
	}
	
	void resetBG()

	{
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
								
				if((i+j)%2==0)
					panel[i][j].setBackground(Color.lightGray);
				else
					panel[i][j].setBackground(Color.gray);
			}
		}
	}

	public void ai()
	{
		int bestmove[]=new int[3];
		score=0;
		int max=20000;
		int temp;
		if(turn==false)
		{
			for(int i=0;i<16;i++)
			{
				if(blackpieces_index[i][1]<100)
				{
					for(int j=11;j<=88;j++)
					{
						if(islegal(i,blackpieces_index[i][1],j))
								{
									for(int k=0;k<64;k++)
									{
										if(empty_pos[k][1]==j)
										{
											first=true;
											score=0;
											temp=blackpieces_index[i][1];
											blackpieces_index[i][1]=j;
											if(i==11 && j==17)//castling
											{
												blackpieces_index[15][1]=16;
											}
											if(i==11 && j==13)//castling
											{
												blackpieces_index[9][1]=14;
											}
											score=negaMax(-20000,20000,nodes);
											if(i==11 && j==17)//castling
											{
												blackpieces_index[9][1]=11;
											}
											if(i==11 && j==17)//castling
											{
												blackpieces_index[15][1]=18;
											}
											blackpieces_index[i][1]=temp;
											System.out.println(score);
											if(score<max)
											{
												bestmove[1]=i;
												bestmove[2]=j;
												max=score;
												break;
											}
										}
									}
									for(int k=0;k<16;k++)
									{
										if(whitepieces_index[k][1]==j && whitepieces_index[k][1]<100)
										{
											whitepieces_index[k][1]=1000;
											temp=blackpieces_index[i][1];
											blackpieces_index[i][1]=j;
											first=true;
											score=0;
											score=negaMax(-20000,20000,nodes);
											if(score<max)
											{
												bestmove[1]=i;
												bestmove[2]=j;
												max=score;
												blackpieces_index[i][1]=temp;
												whitepieces_index[k][1]=j;
												break;
											}
											blackpieces_index[i][1]=temp;
											whitepieces_index[k][1]=j;
										}
									}
								}
					}
				}
			}
			move(bestmove[1],bestmove[2]);
			turn=true;
			
		}
	}
	
	public void move(int piece, int target)
	{
		for(int i=0;i<64;i++)
		{
			if(empty_pos[i][1]==target) 
			{
				if(piece_pointer==11 && empty_pos[i][1]==13)//castling
				{
					panel[0][3].removeAll();
					panel[0][0].removeAll();
					panel[0][3].add(blackpieces[8]);
					panel[0][0].add(empty[empty_index]);

					panel[0][1].repaint();
					panel[0][0].repaint();
					panel[0][7].setBackground(Color.ORANGE);

					blackpieces_index[8][1]=14;
					empty_pos[empty_index][1]=11;
					empty_index++;
				}
				if(piece_pointer==11 && empty_pos[i][1]==17)//castling
				{
					panel[0][5].removeAll();
					panel[0][7].removeAll();
					panel[0][5].add(blackpieces[15]);
					panel[0][7].add(empty[empty_index]);

					panel[0][7].repaint();
					panel[0][5].repaint();
					panel[0][0].setBackground(Color.ORANGE);

					blackpieces_index[15][1]=16;
					empty_pos[empty_index][1]=18;
					empty_index++;
				}
				panel[empty_pos[i][1]/10-1][empty_pos[i][1]%10-1].removeAll();
				panel[blackpieces_index[piece][1]/10-1][blackpieces_index[piece][1]%10-1].removeAll();
				panel[empty_pos[i][1]/10-1][empty_pos[i][1]%10-1].add(blackpieces[piece]);
				panel[blackpieces_index[piece][1]/10-1][blackpieces_index[piece][1]%10-1].add(empty[i]);
				
				panel[blackpieces_index[piece][1]/10-1][blackpieces_index[piece][1]%10-1].setBackground(Color.ORANGE);
				panel[empty_pos[i][1]/10-1][empty_pos[i][1]%10-1].setBackground(Color.orange);
				
				panel[blackpieces_index[piece][1]/10-1][blackpieces_index[piece][1]%10-1].repaint();
				panel[empty_pos[i][1]/10-1][empty_pos[i][1]%10-1].repaint();
			
				empty_pos[i][1]=blackpieces_index[piece][1];
				blackpieces_index[piece][1]=target;
				break;
			}
		}
		for(int i=0;i<16;i++)
		{
			if(whitepieces_index[i][1]==target)
			{
				if(i==11)
				{
					System.out.println("Black wins!");
					
				}
				panel[whitepieces_index[i][1]/10-1][whitepieces_index[i][1]%10-1].removeAll();
				panel[blackpieces_index[piece][1]/10-1][blackpieces_index[piece][1]%10-1].removeAll();
				panel[whitepieces_index[i][1]/10-1][whitepieces_index[i][1]%10-1].add(blackpieces[piece]);
				panel[blackpieces_index[piece][1]/10-1][blackpieces_index[piece][1]%10-1].add(empty[empty_index]);
				empty_pos[empty_index][1]=blackpieces_index[piece][1];
				empty[empty_index].revalidate();
				empty_pos[empty_index][1]=blackpieces_index[piece][1];
				empty_index++;
				
				panel[blackpieces_index[piece][1]/10-1][blackpieces_index[piece][1]%10-1].setBackground(Color.ORANGE);
				panel[whitepieces_index[i][1]/10-1][whitepieces_index[i][1]%10-1].setBackground(Color.ORANGE);
			
				panel[blackpieces_index[piece][1]/10-1][blackpieces_index[piece][1]%10-1].repaint();
				panel[whitepieces_index[i][1]/10-1][whitepieces_index[i][1]%10-1].repaint();
			
				blackpieces_index[piece][1]=whitepieces_index[i][1];
				whitepieces_index[i][1]=1000;
				break;
			}
		}
	}
	
	
	public int evaluate()
	{
		int score=0;
		for(int i=0;i<16;i++)
		{
			if(blackpieces_index[i][1]<100)
			{
				if(i<8)
					score-=10;
				if(i==8||i==15)
					score-=50;
				if(i==9||i==14)
					score-=30;
				if(i==10||i==13)
					score-=30;
				if(i==11)
					score-=90;
				if(i==12)
					score-=900;
				if((i==9||i==14) && blackpieces_index[i][1]%10>2 && blackpieces_index[i][1]/10>2 && blackpieces_index[i][1]/10<7)
					score-=3;
				if(i<8 && blackpieces_index[i][1]%10>3 && blackpieces_index[i][1]/10>2 && blackpieces_index[i][1]/10<7)
					score-=1;
			}
			if(whitepieces_index[i][1]<100)
			{
				if(i<8)
					score+=10;
				if(i==8||i==15)
					score+=50;
				if(i==9||i==14)
					score+=30;
				if(i==10||i==13)
					score+=30;
				if(i==11)
					score+=90;
				if(i==12)
					score+=900;
				if((i==9||i==14) && whitepieces_index[i][1]%10<7 && whitepieces_index[i][1]/10>2 && whitepieces_index[i][1]/10<7)
					score+=3;
				if(i<8 && whitepieces_index[i][1]%10<6 && whitepieces_index[i][1]/10>2 && whitepieces_index[i][1]/10<7)
					score+=1;
			}
		}
		return score;
	}
	
	
	int negaMax( int alpha, int beta, int depth ) 
	{
		
	    if ( depth == 0 ) 
	    	return evaluate();
	    
	    
	    if(first)
	    {

	    	for(int i=0;i<16;i++)
	    	{
	    		blackpieces_indextemp[i][1]=blackpieces_index[i][1];
	    		whitepieces_indextemp[i][1]=whitepieces_index[i][1];
	    	}
	    	first=false;
	    }
	    
	    for(int i=0;i<16;i++)
		{
			if(blackpieces_index[i][1]<100 && depth%2==1)
			{
				
				for(int j=11;j<=88;j++)
				{
					turn=false;
					if(islegal(i,blackpieces_index[i][1],j))
							{
								for(int k=0;k<64;k++)
								{
									if(empty_pos[k][1]==j)
									{
										blackpieces_index[i][1]=j;
										if(i==11 && j==17 && blackpieces_index[11][1]==15)//castling
										{
											blackpieces_index[15][1]=16;
										}
										if(i==11 && j==13 && blackpieces_index[11][1]==15)//castling
										{
											blackpieces_index[9][1]=14;
										}
										score = negaMin( alpha, beta, depth - 1 );
										if(i==11 && j==17 && blackpieces_index[11][1]==15)//castling
										{
											blackpieces_index[15][1]=16;
										}
										if(i==11 && j==13 && blackpieces_index[11][1]==15)//castling
										{
											blackpieces_index[9][1]=14;
										}
										blackpieces_index[i][1]=blackpieces_indextemp[i][1];
								        if( score >= beta )
								           return beta;
								        if( score > alpha )
									           alpha=score;
									}
								}
								for(int k=0;k<16;k++)
								{
									if(whitepieces_index[k][1]==j)
									{
										
										whitepieces_index[k][1]=1000;
										blackpieces_index[i][1]=j;
										score = negaMin( alpha, beta, depth - 1 );
										whitepieces_index[k][1]=whitepieces_indextemp[k][1];
										blackpieces_index[i][1]=blackpieces_indextemp[i][1];
										if( score >= beta )
									           return beta;
								        if( score > alpha )
								           alpha=score;										
									}
								}
							}
				}
			}
			else if(whitepieces_index[i][1]<100 && depth%2==0)
			{
				
				for(int j=11;j<=88;j++)
				{
					turn=true;
					if(islegal(i,whitepieces_index[i][1],j))
							{
								for(int k=0;k<64;k++)
								{
									if(empty_pos[k][1]==j)
									{
										whitepieces_index[i][1]=j;
										if(i==11 && j==87 && whitepieces_index[11][1]==85)//castling
										{
											whitepieces_index[15][1]=86;
										}
										if(i==11 && j==83 && whitepieces_index[11][1]==85)//castling
										{
											whitepieces_index[9][1]=84;
										}
										score = negaMin(alpha, beta,  depth - 1 );
										if(i==11 && j==87 && whitepieces_index[11][1]==85)//castling
										{
											blackpieces_index[15][1]=86;
										}
										if(i==11 && j==83 && whitepieces_index[11][1]==85)//castling
										{
											blackpieces_index[9][1]=84;
										}
										whitepieces_index[i][1]=whitepieces_indextemp[i][1];
										 if( score >= beta )
									           return beta;
									        if( score > alpha )
										           alpha=score;
								        
									}
								}
								for(int k=0;k<16;k++)
								{
									if(blackpieces_index[k][1]==j)
									{
										
										whitepieces_index[i][1]=j;
										blackpieces_index[k][1]=1000;
										score = negaMin( alpha, beta, depth - 1 );
										blackpieces_index[k][1]=blackpieces_indextemp[k][1];
										whitepieces_index[i][1]=whitepieces_indextemp[i][1];
										 if( score >= beta )
									           return beta;
									        if( score > alpha )
										           alpha=score;
								        
									}
								}
							}
				}
			}
		}    
	    turn=false;
	    return alpha;
	}
	int negaMin( int alpha, int beta, int depth ) 
	{
		
	    if ( depth == 0 ) 
	    	return -evaluate();
	    
	    
	    if(first)
	    {

	    	for(int i=0;i<16;i++)
	    	{
	    		blackpieces_indextemp[i][1]=blackpieces_index[i][1];
	    		whitepieces_indextemp[i][1]=whitepieces_index[i][1];
	    	}
	    	first=false;
	    }
	    
	    for(int i=0;i<16;i++)
		{
			if(blackpieces_index[i][1]<100 && depth%2==1)
			{
				
				for(int j=11;j<=88;j++)
				{
					turn=false;
					if(islegal(i,blackpieces_index[i][1],j))
							{
								for(int k=0;k<64;k++)
								{
									if(empty_pos[k][1]==j)
									{
										blackpieces_index[i][1]=j;
										if(i==11 && j==17 && blackpieces_index[11][1]==15)//castling
										{
											blackpieces_index[15][1]=16;
										}
										if(i==11 && j==13 && blackpieces_index[11][1]==15)//castling
										{
											blackpieces_index[9][1]=14;
										}
										score = negaMax( alpha, beta, depth - 1 );
										if(i==11 && j==17 && blackpieces_index[11][1]==15)//castling
										{
											blackpieces_index[15][1]=16;
										}
										if(i==11 && j==13  && blackpieces_index[11][1]==15)//castling
										{
											blackpieces_index[9][1]=14;
										}
										blackpieces_index[i][1]=blackpieces_indextemp[i][1];
								        if( score <= alpha )
								           return alpha;
								        if( score < beta )
									           beta=score;
									}
								}
								for(int k=0;k<16;k++)
								{
									if(whitepieces_index[k][1]==j)
									{
										
										whitepieces_index[k][1]=1000;
										blackpieces_index[i][1]=j;
										score = negaMax( alpha, beta, depth - 1 );
										whitepieces_index[k][1]=whitepieces_indextemp[k][1];
										blackpieces_index[i][1]=blackpieces_indextemp[i][1];
										if( score <= alpha )
									           return alpha;
									        if( score < beta )
										           beta=score;									
									}
								}
							}
				}
			}
			else if(whitepieces_index[i][1]<100 && depth%2==0)
			{
				
				for(int j=11;j<=88;j++)
				{
					turn=true;
					if(islegal(i,whitepieces_index[i][1],j))
							{
								for(int k=0;k<64;k++)
								{
									if(empty_pos[k][1]==j)
									{
										whitepieces_index[i][1]=j;
										if(i==11 && j==87  && whitepieces_index[11][1]==85)//castling
										{
											whitepieces_index[15][1]=86;
										}
										if(i==11 && j==83  && whitepieces_index[11][1]==85)//castling
										{
											whitepieces_index[9][1]=84;
										}
										score = negaMax(alpha, beta,  depth - 1 );
										if(i==11 && j==87  && whitepieces_index[11][1]==85)//castling
										{
											whitepieces_index[15][1]=86;
										}
										if(i==11 && j==83  && whitepieces_index[11][1]==85)//castling
										{
											whitepieces_index[9][1]=84;
										}
										whitepieces_index[i][1]=whitepieces_indextemp[i][1];
										if( score <= alpha )
									           return alpha;
									        if( score < beta )
										           beta=score;		
								        
									}
								}
								for(int k=0;k<16;k++)
								{
									if(blackpieces_index[k][1]==j)
									{
										
										whitepieces_index[i][1]=j;
										blackpieces_index[k][1]=1000;
										score = negaMax( alpha, beta, depth - 1 );
										blackpieces_index[k][1]=blackpieces_indextemp[k][1];
										whitepieces_index[i][1]=whitepieces_indextemp[i][1];
										if( score <= alpha )
									           return alpha;
									        if( score < beta )
										           beta=score;		
								        
									}
								}
							}
				}
			}
		}    
	    turn=false;
	    return beta;
	}
	
	
	int negaMax(  int depth ) 
	{
		int max=-2000;
		
	    if ( depth == 0 ) 
	    	return evaluate();
	    
	    
	    if(first)
	    {

	    	for(int i=0;i<16;i++)
	    	{
	    		blackpieces_indextemp[i][1]=blackpieces_index[i][1];
	    		whitepieces_indextemp[i][1]=whitepieces_index[i][1];
	    	}
	    	first=false;
	    }
	    
	    for(int i=0;i<16;i++)
		{
			if(blackpieces_index[i][1]<100 && depth%2==1)
			{
				
				for(int j=11;j<=88;j++)
				{
					turn=false;
					if(islegal(i,blackpieces_index[i][1],j))
							{
								for(int k=0;k<64;k++)
								{
									if(empty_pos[k][1]==j)
									{
										blackpieces_index[i][1]=j;
										if(i==11 && j==17  && blackpieces_index[11][1]==15)//castling
										{
											blackpieces_index[15][1]=16;
										}
										if(i==11 && j==13  && blackpieces_index[11][1]==15)//castling
										{
											blackpieces_index[9][1]=14;
										}
										score = -negaMax( depth - 1 );
										if(i==11 && j==17  && blackpieces_index[11][1]==15)//castling
										{
											blackpieces_index[15][1]=16;
										}
										if(i==11 && j==13  && blackpieces_index[11][1]==15)//castling
										{
											blackpieces_index[9][1]=14;
										}
										blackpieces_index[i][1]=blackpieces_indextemp[i][1];
								        if( score > max )
								        {
								        	max=score;
								        	break;
								        }
									}
								}
								for(int k=0;k<16;k++)
								{
									if(whitepieces_index[k][1]==j)
									{
										
										whitepieces_index[k][1]=1000;
										blackpieces_index[i][1]=j;
										score = -negaMax( depth - 1 );
										whitepieces_index[k][1]=whitepieces_indextemp[k][1];
										blackpieces_index[i][1]=blackpieces_indextemp[i][1];
								        if( score > max )
								        {
								           max=score;		
								           break;
								        }
									}
								}
							}
				}
			}
			else if(whitepieces_index[i][1]<100 && depth%2==0)
			{
				
				for(int j=11;j<=88;j++)
				{
					turn=true;
					if(islegal(i,whitepieces_index[i][1],j))
							{
								for(int k=0;k<64;k++)
								{
									if(empty_pos[k][1]==j)
									{
										whitepieces_index[i][1]=j;
										if(i==11 && j==87  && whitepieces_index[11][1]==85)//castling
										{
											whitepieces_index[15][1]=86;
										}
										if(i==11 && j==83  && whitepieces_index[11][1]==85)//castling
										{
											whitepieces_index[9][1]=84;
										}
										score = -negaMax(depth - 1 );
										if(i==11 && j==87  && whitepieces_index[11][1]==85)//castling
										{
											whitepieces_index[15][1]=86;
										}
										if(i==11 && j==83  && whitepieces_index[11][1]==85)//castling
										{
											whitepieces_index[9][1]=84;
										}
										whitepieces_index[i][1]=whitepieces_indextemp[i][1];
									        if( score > max )
									        {
										           max=score;
										           break;
									        }
								        
									}
								}
								for(int k=0;k<16;k++)
								{
									if(blackpieces_index[k][1]==j)
									{
										
										whitepieces_index[i][1]=j;
										blackpieces_index[k][1]=1000;
										score = -negaMax(depth - 1 );
										blackpieces_index[k][1]=blackpieces_indextemp[k][1];
										whitepieces_index[i][1]=whitepieces_indextemp[i][1];
									        if( score > max )
									        {
										           max=score;
										           break;
									        }
								        
									}
								}
							}
				}
			}
		}    
	    turn=false;
	    return max;
	}

	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
