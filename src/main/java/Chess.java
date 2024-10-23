import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Chess extends Pieces implements MouseListener
{
	JPanel frame;
	JFrame FRAME;
	JPanel[][] panel;
	JLabel[] Black_Pieces;
	JLabel[] White_Pieces;
	JLabel[] empty;
	Pieces obj;
	int Empty_Index =0;
	int Piece_Index =1000;
	int[][] BlackPieces_Index;
	int[][] WhitePieces_Index;
	int[][] BlackPieces_IndexTemp;
	int[][] WhitePieces_IndexTemp;
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
		Black_Pieces = new JLabel[16];
		White_Pieces = new JLabel[16];
		empty = new JLabel[100];
		WhitePieces_Index =new int[16][2];
		BlackPieces_Index =new int[16][2];
		WhitePieces_IndexTemp =new int[16][2];
		BlackPieces_IndexTemp =new int[16][2];
		empty_pos=new int[64][2];
		
		FRAME.setLayout(new GridLayout(1,1));
		FRAME.add(frame);
		frame.setLayout(new GridLayout(8,8));
		
		for(int i=0;i<16;i++)
		{
			Black_Pieces[i]=obj.fillblack(i);
			White_Pieces[i]=obj.fillwhite(i);
			Black_Pieces[i].setOpaque(false);
			White_Pieces[i].setOpaque(false);
			Black_Pieces[i].setLayout(new GridLayout());
			White_Pieces[i].setLayout(new GridLayout());

			Black_Pieces[i].addMouseListener(this);
			White_Pieces[i].addMouseListener(this);
			
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
			panel[1][i].add(Black_Pieces[i]);
			panel[6][i].add(White_Pieces[i]);
			
			BlackPieces_Index[i][1]=2*10+(i+1);
			WhitePieces_Index[i][1]=7*10+(i+1);
		}
		panel[0][0].add(Black_Pieces[8]);
		BlackPieces_Index[8][1]=11;
		panel[0][1].add(Black_Pieces[9]);
		BlackPieces_Index[9][1]=12;
		panel[0][2].add(Black_Pieces[10]);
		BlackPieces_Index[10][1]=13;
		panel[0][3].add(Black_Pieces[12]);
		BlackPieces_Index[12][1]=14;
		panel[0][4].add(Black_Pieces[11]);
		BlackPieces_Index[11][1]=15;
		panel[0][5].add(Black_Pieces[13]);
		BlackPieces_Index[13][1]=16;
		panel[0][6].add(Black_Pieces[14]);
		BlackPieces_Index[14][1]=17;
		panel[0][7].add(Black_Pieces[15]);
		BlackPieces_Index[15][1]=18;
		
		panel[7][0].add(White_Pieces[8]);
		WhitePieces_Index[8][1]=81;
		panel[7][1].add(White_Pieces[9]);
		WhitePieces_Index[9][1]=82;
		panel[7][2].add(White_Pieces[10]);
		WhitePieces_Index[10][1]=83;
		panel[7][3].add(White_Pieces[12]);
		WhitePieces_Index[12][1]=84;
		panel[7][4].add(White_Pieces[11]);
		WhitePieces_Index[11][1]=85;
		panel[7][5].add(White_Pieces[13]);
		WhitePieces_Index[13][1]=86;
		panel[7][6].add(White_Pieces[14]);
		WhitePieces_Index[14][1]=87;
		panel[7][7].add(White_Pieces[15]);
		WhitePieces_Index[15][1]=88;
		
		for(int i=2;i<6;i++)
		{
			for(int j=0;j<8;j++)
			{
				panel[i][j].add(empty[Empty_Index]);
				empty_pos[Empty_Index][1]=(i+1)*10+(j+1);
				Empty_Index++;
				
			}
		}
		
	}

	

	@Override
	public void mouseClicked(MouseEvent e)
	{
		resetBG();
		for(int i=0;i<16;i++)
		{
			
			if(e.getSource()== Black_Pieces[i])
			{
				if(!turn)
				{
					highlight(i);
					Piece_Index = BlackPieces_Index[i][1];
					piece_pointer=i;
				}
				else 
				{
					if(isLegal(piece_pointer, Piece_Index, BlackPieces_Index[i][1]) && Piece_Index <100)
					{
						if(i==11)
						{
							System.out.println("White wins!");
							
						}
						panel[BlackPieces_Index[i][1]/10-1][BlackPieces_Index[i][1]%10-1].removeAll();
						panel[Piece_Index /10-1][Piece_Index %10-1].removeAll();
						panel[BlackPieces_Index[i][1]/10-1][BlackPieces_Index[i][1]%10-1].add(White_Pieces[piece_pointer]);
						panel[Piece_Index /10-1][Piece_Index %10-1].add(empty[Empty_Index]);
						empty[Empty_Index].revalidate();
						empty_pos[Empty_Index][1]= Piece_Index;
						Empty_Index++;
					
						panel[Piece_Index /10-1][Piece_Index %10-1].repaint();
						panel[BlackPieces_Index[i][1]/10-1][BlackPieces_Index[i][1]%10-1].repaint();
					
						WhitePieces_Index[piece_pointer][1]= BlackPieces_Index[i][1];
						BlackPieces_Index[i][1]=1000;
						
						turn=false;
						ai();
					}
					Piece_Index =1040;
				}
			}
			
			if(e.getSource()== White_Pieces[i])
			{
				if(turn)
				{
					highlight(i);
					Piece_Index = WhitePieces_Index[i][1];
					piece_pointer=i;
				}
				else
				{
					if(isLegal(piece_pointer, Piece_Index, WhitePieces_Index[i][1]) && Piece_Index <100)
					{
						if(i==11)
						{
							System.out.println("Black wins!");
							
						}
						panel[WhitePieces_Index[i][1]/10-1][WhitePieces_Index[i][1]%10-1].removeAll();
						panel[Piece_Index /10-1][Piece_Index %10-1].removeAll();
						panel[WhitePieces_Index[i][1]/10-1][WhitePieces_Index[i][1]%10-1].add(Black_Pieces[piece_pointer]);
						panel[Piece_Index /10-1][Piece_Index %10-1].add(empty[Empty_Index]);
						empty_pos[Empty_Index][1]= Piece_Index;
						empty[Empty_Index].revalidate();
						empty_pos[Empty_Index][1]= Piece_Index;
						Empty_Index++;
					
						panel[Piece_Index /10-1][Piece_Index %10-1].repaint();
						panel[WhitePieces_Index[i][1]/10-1][WhitePieces_Index[i][1]%10-1].repaint();
					
						BlackPieces_Index[piece_pointer][1]= WhitePieces_Index[i][1];
						WhitePieces_Index[i][1]=1000;
						
						turn=true;
						
					}
					Piece_Index =1030;
				}
			}
			
			
		}
		
		for(int i=0;i<64;i++)
		{
			if(e.getSource()==empty[i])
			{
				
				if(!turn)
				{
					if(isLegal(piece_pointer, Piece_Index, empty_pos[i][1]) && Piece_Index <100)
					{
						panel[empty_pos[i][1]/10-1][empty_pos[i][1]%10-1].removeAll();
						panel[Piece_Index /10-1][Piece_Index %10-1].removeAll();
						panel[empty_pos[i][1]/10-1][empty_pos[i][1]%10-1].add(Black_Pieces[piece_pointer]);
						panel[Piece_Index /10-1][Piece_Index %10-1].add(empty[i]);
					
						panel[Piece_Index /10-1][Piece_Index %10-1].repaint();
						panel[empty_pos[i][1]/10-1][empty_pos[i][1]%10-1].repaint();
					
						BlackPieces_Index[piece_pointer][1]=empty_pos[i][1];
						empty_pos[i][1]= Piece_Index;
						
						turn=true;
						
					}
					Piece_Index =1020;
				}
				else
				{
					if(isLegal(piece_pointer, Piece_Index, empty_pos[i][1]) && Piece_Index <100)
					{

						if(piece_pointer==11 && empty_pos[i][1]==83)//castling
						{
							panel[7][3].removeAll();
							panel[7][0].removeAll();
							panel[7][3].add(White_Pieces[8]);
							panel[7][0].add(empty[Empty_Index]);

							panel[7][1].repaint();
							panel[7][0].repaint();

							WhitePieces_Index[8][1]=84;
							empty_pos[Empty_Index][1]=81;
							Empty_Index++;
						}
						if(piece_pointer==11 && empty_pos[i][1]==87)//castling
						{
							panel[7][5].removeAll();
							panel[7][7].removeAll();
							panel[7][5].add(White_Pieces[15]);
							panel[7][7].add(empty[Empty_Index]);

							panel[7][7].repaint();
							panel[7][5].repaint();

							WhitePieces_Index[15][1]=86;
							empty_pos[Empty_Index][1]=88;
							Empty_Index++;
						}
						panel[empty_pos[i][1]/10-1][empty_pos[i][1]%10-1].removeAll();
						panel[Piece_Index /10-1][Piece_Index %10-1].removeAll();
						panel[empty_pos[i][1]/10-1][empty_pos[i][1]%10-1].add(White_Pieces[piece_pointer]);
						panel[Piece_Index /10-1][Piece_Index %10-1].add(empty[i]);

						panel[Piece_Index /10-1][Piece_Index %10-1].repaint();
						panel[empty_pos[i][1]/10-1][empty_pos[i][1]%10-1].repaint();

						WhitePieces_Index[piece_pointer][1]=empty_pos[i][1];
						empty_pos[i][1]= Piece_Index;
						
						turn=false;
						ai();
					}
					Piece_Index =1010;
				}
				
			}
		}
		
		for(int i=0;i<8;i++)
		{
			if(WhitePieces_Index[i][1]/10==1)
			{
				panel[0][WhitePieces_Index[i][1]%10-1].removeAll();
				panel[0][WhitePieces_Index[i][1]%10-1].add(White_Pieces[12]);
				panel[0][WhitePieces_Index[i][1]%10-1].repaint();
				WhitePieces_Index[12][1]= WhitePieces_Index[i][1];
				WhitePieces_Index[i][1]=1000;
			}
			if(BlackPieces_Index[i][1]/10==8)
			{
				panel[8][BlackPieces_Index[i][1]%10-1].removeAll();
				panel[8][BlackPieces_Index[i][1]%10-1].add(Black_Pieces[12]);
				panel[8][BlackPieces_Index[i][1]%10-1].repaint();
				BlackPieces_Index[12][1]= BlackPieces_Index[i][1];
				BlackPieces_Index[i][1]=1000;
			}
		}
		
		
	}
	
	boolean isLegal(int piece, int piece_pos, int target_pos)
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
				WhitePieces_Index[piece][1]=temp;
				turn=false;
				for(int i=0;i<16;i++)
				{
					if(BlackPieces_Index[i][1]==target_pos)
						BlackPieces_Index[i][1]=1001;
					if(isLegal(i, BlackPieces_Index[i][1], WhitePieces_Index[11][1]))
					{
						if(BlackPieces_Index[i][1]==1001)
							BlackPieces_Index[i][1]=target_pos;
						turn=true;
						WhitePieces_Index[piece][1]=piece_pos;
						temp=1005;
						return false;
					}
					if(BlackPieces_Index[i][1]==1001)
						BlackPieces_Index[i][1]=target_pos;
				}
				if(BlackPieces_Index[piece][1]==1001)
					BlackPieces_Index[piece][1]=target_pos;
				WhitePieces_Index[piece][1]=piece_pos;
				temp=1005;
				turn=true;
			}
			else
			{
				temp=target_pos;
				BlackPieces_Index[piece][1]=temp;
				turn=true;
				for(int i=0;i<16;i++)
				{
					if(WhitePieces_Index[i][1]==target_pos)
						WhitePieces_Index[i][1]=1001;
					if(isLegal(i, WhitePieces_Index[i][1], BlackPieces_Index[11][1]))
					{
						if(WhitePieces_Index[i][1]==1001)
							WhitePieces_Index[i][1]=target_pos;
						turn=false;
						BlackPieces_Index[piece][1]=piece_pos;
						temp=1005;
						return false;
					}
					if(WhitePieces_Index[i][1]==1001)
						WhitePieces_Index[i][1]=target_pos;
				}
				if(WhitePieces_Index[piece][1]==1001)
					WhitePieces_Index[piece][1]=target_pos;
				BlackPieces_Index[piece][1]=piece_pos;
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
						if(target_pos== BlackPieces_Index[i][1])
							return true;
					}
				}
				if(piece_pos/10==7)
				{
					if(target_pos/10+2==piece_pos/10 && target_pos%10==piece_pos%10)
					{
						for(int i=0;i<16;i++)
						{
							if(WhitePieces_Index[i][1]==target_pos+10 || target_pos+10== BlackPieces_Index[i][1])
								return false;
						}
						return true;
					}
				}
				if(target_pos/10+1==piece_pos/10  && target_pos%10==piece_pos%10)
				{
					for(int i=0;i<16;i++)
					{
						if(WhitePieces_Index[i][1]==target_pos || target_pos== BlackPieces_Index[i][1])
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
						if(target_pos== WhitePieces_Index[i][1])
							return true;
					}
				}
				if(piece_pos/10==2)
				{
					if(target_pos/10-2==piece_pos/10  && target_pos%10==piece_pos%10)
						{
							for(int i=0;i<16;i++)
							{
								if(WhitePieces_Index[i][1]==target_pos || target_pos== BlackPieces_Index[i][1])
									return false;
								if(WhitePieces_Index[i][1]==target_pos-10 || target_pos-10== BlackPieces_Index[i][1])
									return false;
							}
							return true;
						}
				}
				if(target_pos/10-1==piece_pos/10  && target_pos%10==piece_pos%10)
				{
					for(int i=0;i<16;i++)
					{
						if(BlackPieces_Index[i][1]==target_pos || WhitePieces_Index[i][1]==target_pos)
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
							if(WhitePieces_Index[j][1]==i*10+piece_pos%10)
								return false;
							if(BlackPieces_Index[j][1]==i*10+piece_pos%10)
							{
                                return BlackPieces_Index[j][1] == target_pos;
                            }
						}
						else
						{
							if(BlackPieces_Index[j][1]==i*10+piece_pos%10)
								return false;
							if(WhitePieces_Index[j][1]==i*10+piece_pos%10)
							{
                                return WhitePieces_Index[j][1] == target_pos;
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
							if(WhitePieces_Index[j][1]==i*10+piece_pos%10)
								return false;
							if(BlackPieces_Index[j][1]==i*10+piece_pos%10)
							{
                                return BlackPieces_Index[j][1] == target_pos;
                            }
						}
						else
						{
							if(BlackPieces_Index[j][1]==i*10+piece_pos%10)
								return false;
							if(WhitePieces_Index[j][1]==i*10+piece_pos%10)
							{
                                return WhitePieces_Index[j][1] == target_pos;
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
							if(WhitePieces_Index[j][1]==i+(piece_pos/10)*10)
								return false;
							if(BlackPieces_Index[j][1]==i+(piece_pos/10)*10)
							{
                                return BlackPieces_Index[j][1] == target_pos;
                            }
						}
						else
						{
							if(BlackPieces_Index[j][1]==i+(piece_pos/10)*10)
								return false;
							if(WhitePieces_Index[j][1]==i+(piece_pos/10)*10)
							{
                                return WhitePieces_Index[j][1] == target_pos;
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
							if(WhitePieces_Index[j][1]==i+(piece_pos/10)*10)
								return false;
							if(BlackPieces_Index[j][1]==i+(piece_pos/10)*10)
							{
                                return BlackPieces_Index[j][1] == target_pos;
                            }
						}
						else
						{
							if(BlackPieces_Index[j][1]==i+(piece_pos/10)*10)
								return false;
							if(WhitePieces_Index[j][1]==i+(piece_pos/10)*10)
							{
                                return WhitePieces_Index[j][1] == target_pos;
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
							if(WhitePieces_Index[j][1]== piece_pos+(i*10) + i)
								return false;
							if(BlackPieces_Index[j][1]== piece_pos+(i*10)+i)
							{
                                return BlackPieces_Index[j][1] == target_pos;
                            }
						}
						else
						{
							if(BlackPieces_Index[j][1]== piece_pos+(i*10) + i)
								return false;
							if(WhitePieces_Index[j][1]== piece_pos+(i*10)+i)
							{
                                return WhitePieces_Index[j][1] == target_pos;
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
							if(WhitePieces_Index[j][1]== piece_pos+(i*10) - i)
								return false;
							if(BlackPieces_Index[j][1]== piece_pos+(i*10)-i)
							{
                                return BlackPieces_Index[j][1] == target_pos;
                            }
						}
						else
						{
							if(BlackPieces_Index[j][1]== piece_pos+(i*10) - i)
								return false;
							if(WhitePieces_Index[j][1]== piece_pos+(i*10)-i)
							{
                                return WhitePieces_Index[j][1] == target_pos;
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
							if(WhitePieces_Index[j][1]== piece_pos-(i*10) + i)
								return false;
							if(BlackPieces_Index[j][1]==piece_pos-(i*10)+i)
							{
                                return BlackPieces_Index[j][1] == target_pos;
                            }
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
							if(WhitePieces_Index[j][1]== piece_pos-(i*10) - i)
								return false;
							if(BlackPieces_Index[j][1]== piece_pos-(i*10)-i)
							{
                                return BlackPieces_Index[j][1] == target_pos;
                            }
						}
						else
						{
							if(BlackPieces_Index[j][1]== piece_pos-(i*10) - i)
								return false;
							if(WhitePieces_Index[j][1]== piece_pos-(i*10)-i)
							{
                                return WhitePieces_Index[j][1] == target_pos;
                            }
						}
					}
				}
				return true;
			}
		}


		int move_XDiff = Math.abs((target_pos % 10) - (piece_pos % 10));
		int move_YDiff = Math.abs((target_pos / 10) - (piece_pos / 10));
		if(piece==9||piece==14)//for horse
		{
			if( (move_YDiff ==2 && move_XDiff ==1) || (move_YDiff ==1 && move_XDiff ==2) )
				{
					for(int i=0;i<16;i++)
					{
						if(turn)
						{
							if(WhitePieces_Index[i][1]==target_pos)
								return false;
						}
						else
						{
							if(BlackPieces_Index[i][1]==target_pos)
								return false;
						}
					}
					return true;
				}
			
		}
		
		
		if(piece==11)//for king
		{
			if( (move_YDiff + move_XDiff ==1) || (( move_YDiff == 1) && (move_XDiff ==1)) )
			{
					for(int j=0;j<16;j++)
					{
						if(turn)
						{
							if(WhitePieces_Index[j][1]==target_pos)
								return false;
						}
						else
						{
							if(BlackPieces_Index[j][1]==target_pos)
								return false;
						}
					}
				return true;
			}
			if(turn)//castling
			{
				if(target_pos==83 && WhitePieces_Index[8][1]==81 && WhitePieces_Index[11][1]==85)
				{
					for(int i=0;i<16;i++)
					{
						if(WhitePieces_Index[i][1]==82 || WhitePieces_Index[i][1]==84)
							return false;
						if(BlackPieces_Index[i][1]==82 || BlackPieces_Index[i][1]==84)
							return false;
					}
					return true;
				}
				if(target_pos==87 && WhitePieces_Index[15][1]==88 && WhitePieces_Index[11][1]==85)
				{
					for(int i=0;i<16;i++)
					{
						if(WhitePieces_Index[i][1]==86)
							return false;
						if(BlackPieces_Index[i][1]==86)
							return false;
					}
					return true;
				}
			}
			else
			{
				if(target_pos==13 && BlackPieces_Index[8][1]==11 && BlackPieces_Index[11][1]==15)
				{
					for(int i=0;i<16;i++)
					{
						if(WhitePieces_Index[i][1]==12 || WhitePieces_Index[i][1]==14)
							return false;
						if(BlackPieces_Index[i][1]==12 || BlackPieces_Index[i][1]==14)
							return false;
					}
					return true;
				}
				if(target_pos==87 && BlackPieces_Index[15][1]==18 && BlackPieces_Index[11][1]==15)
				{
					for(int i=0;i<16;i++)
					{
						if(WhitePieces_Index[i][1]==16)
							return false;
						if(BlackPieces_Index[i][1]==16)
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
							if(WhitePieces_Index[j][1]==i*10+piece_pos%10)
								return false;
							if(BlackPieces_Index[j][1]==i*10+piece_pos%10)
							{
                                return BlackPieces_Index[j][1] == target_pos;
                            }
						}
						else
						{
							if(BlackPieces_Index[j][1]==i*10+piece_pos%10)
								return false;
							if(WhitePieces_Index[j][1]==i*10+piece_pos%10)
							{
                                return WhitePieces_Index[j][1] == target_pos;
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
							if(WhitePieces_Index[j][1]==i*10+piece_pos%10)
								return false;
							if(BlackPieces_Index[j][1]==i*10+piece_pos%10)
							{
                                return BlackPieces_Index[j][1] == target_pos;
                            }
						}
						else
						{
							if(BlackPieces_Index[j][1]==i*10+piece_pos%10)
								return false;
							if(WhitePieces_Index[j][1]==i*10+piece_pos%10)
							{
                                return WhitePieces_Index[j][1] == target_pos;
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
							if(WhitePieces_Index[j][1]==i+(piece_pos/10)*10)
								return false;
							if(BlackPieces_Index[j][1]==i+(piece_pos/10)*10)
							{
                                return BlackPieces_Index[j][1] == target_pos;
                            }
						}
						else
						{
							if(BlackPieces_Index[j][1]==i+(piece_pos/10)*10)
								return false;
							if(WhitePieces_Index[j][1]==i+(piece_pos/10)*10)
							{
                                return WhitePieces_Index[j][1] == target_pos;
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
							if(WhitePieces_Index[j][1]==i+(piece_pos/10)*10)
								return false;
							if(BlackPieces_Index[j][1]==i+(piece_pos/10)*10)
							{
                                return BlackPieces_Index[j][1] == target_pos;
                            }
						}
						else
						{
							if(BlackPieces_Index[j][1]==i+(piece_pos/10)*10)
								return false;
							if(WhitePieces_Index[j][1]==i+(piece_pos/10)*10)
							{
                                return WhitePieces_Index[j][1] == target_pos;
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
						if(WhitePieces_Index[j][1]== piece_pos+(i*10) + i)
							return false;
						if(BlackPieces_Index[j][1]== piece_pos+(i*10)+i)
						{
                            return BlackPieces_Index[j][1] == target_pos;
                        }
					}
					else
					{
						if(BlackPieces_Index[j][1]== piece_pos+(i*10) + i)
							return false;
						if(WhitePieces_Index[j][1]== piece_pos+(i*10)+i)
						{
                            return WhitePieces_Index[j][1] == target_pos;
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
						if(WhitePieces_Index[j][1]== piece_pos+(i*10) - i)
							return false;
						if(BlackPieces_Index[j][1]== piece_pos+(i*10)-i)
						{
                            return BlackPieces_Index[j][1] == target_pos;
                        }
					}
					else
					{
						if(BlackPieces_Index[j][1]== piece_pos+(i*10) - i)
							return false;
						if(WhitePieces_Index[j][1]== piece_pos+(i*10)-i)
						{
                            return WhitePieces_Index[j][1] == target_pos;
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
						if(WhitePieces_Index[j][1]== piece_pos-(i*10) + i)
							return false;
						if(BlackPieces_Index[j][1]==piece_pos-(i*10)+i)
						{
                            return BlackPieces_Index[j][1] == target_pos;
                        }
					}
					else
					{
						if(BlackPieces_Index[j][1]== piece_pos-(i*10) + i)
							return false;
						if(WhitePieces_Index[j][1]== piece_pos-(i*10)+i)
						{
                            return WhitePieces_Index[j][1] == target_pos;
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
						if(WhitePieces_Index[j][1]== piece_pos-(i*10) - i)
							return false;
						if(BlackPieces_Index[j][1]== piece_pos-(i*10)-i)
						{
                            return BlackPieces_Index[j][1] == target_pos;
                        }
					}
					else
					{
						if(BlackPieces_Index[j][1]== piece_pos-(i*10) - i)
							return false;
						if(WhitePieces_Index[j][1]== piece_pos-(i*10)-i)
						{
                            return WhitePieces_Index[j][1] == target_pos;
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
					if(isLegal(piece, WhitePieces_Index[piece][1], ((i+1)*10)+j+1))
						panel[i][j].setBackground(new Color(158,240,180));
				}
				else
				{
					if(isLegal(piece, BlackPieces_Index[piece][1], ((i+1)*10)+j+1))
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
		int[] BestMove = new int[3];
		score=0;
		int max=20000;
		int temp;
		if(!turn)
		{
			for(int i=0;i<16;i++)
			{
				if(BlackPieces_Index[i][1]<100)
				{
					for(int j=11;j<=88;j++)
					{
						if(isLegal(i, BlackPieces_Index[i][1],j))
								{
									for(int k=0;k<64;k++)
									{
										if(empty_pos[k][1]==j)
										{
											first=true;
											score=0;
											temp= BlackPieces_Index[i][1];
											BlackPieces_Index[i][1]=j;
											if(i==11 && j==17)//castling
											{
												BlackPieces_Index[15][1]=16;
											}
											if(i==11 && j==13)//castling
											{
												BlackPieces_Index[9][1]=14;
											}
											score= NegaMax(-20000,20000,nodes);
											if(i==11 && j==17)//castling
											{
												BlackPieces_Index[9][1]=11;
											}
											if(i==11 && j==17)//castling
											{
												BlackPieces_Index[15][1]=18;
											}
											BlackPieces_Index[i][1]=temp;
											if(score<max)
											{
												BestMove[1]=i;
												BestMove[2]=j;
												max=score;
												break;
											}
										}
									}
									for(int k=0;k<16;k++)
									{
										if(WhitePieces_Index[k][1]==j && WhitePieces_Index[k][1]<100)
										{
											WhitePieces_Index[k][1]=1000;
											temp= BlackPieces_Index[i][1];
											BlackPieces_Index[i][1]=j;
											first=true;
											score=0;
											score= NegaMax(-20000,20000,nodes);
											if(score<max)
											{
												BestMove[1]=i;
												BestMove[2]=j;
												max=score;
												BlackPieces_Index[i][1]=temp;
												WhitePieces_Index[k][1]=j;
												break;
											}
											BlackPieces_Index[i][1]=temp;
											WhitePieces_Index[k][1]=j;
										}
									}
								}
					}
				}
			}
			move(BestMove[1],BestMove[2]);
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
					panel[0][3].add(Black_Pieces[8]);
					panel[0][0].add(empty[Empty_Index]);

					panel[0][1].repaint();
					panel[0][0].repaint();
					panel[0][7].setBackground(Color.ORANGE);

					BlackPieces_Index[8][1]=14;
					empty_pos[Empty_Index][1]=11;
					Empty_Index++;
				}
				if(piece_pointer==11 && empty_pos[i][1]==17)//castling
				{
					panel[0][5].removeAll();
					panel[0][7].removeAll();
					panel[0][5].add(Black_Pieces[15]);
					panel[0][7].add(empty[Empty_Index]);

					panel[0][7].repaint();
					panel[0][5].repaint();
					panel[0][0].setBackground(Color.ORANGE);

					BlackPieces_Index[15][1]=16;
					empty_pos[Empty_Index][1]=18;
					Empty_Index++;
				}
				panel[empty_pos[i][1]/10-1][empty_pos[i][1]%10-1].removeAll();
				panel[BlackPieces_Index[piece][1]/10-1][BlackPieces_Index[piece][1]%10-1].removeAll();
				panel[empty_pos[i][1]/10-1][empty_pos[i][1]%10-1].add(Black_Pieces[piece]);
				panel[BlackPieces_Index[piece][1]/10-1][BlackPieces_Index[piece][1]%10-1].add(empty[i]);
				
				panel[BlackPieces_Index[piece][1]/10-1][BlackPieces_Index[piece][1]%10-1].setBackground(Color.ORANGE);
				panel[empty_pos[i][1]/10-1][empty_pos[i][1]%10-1].setBackground(Color.orange);
				
				panel[BlackPieces_Index[piece][1]/10-1][BlackPieces_Index[piece][1]%10-1].repaint();
				panel[empty_pos[i][1]/10-1][empty_pos[i][1]%10-1].repaint();
			
				empty_pos[i][1]= BlackPieces_Index[piece][1];
				BlackPieces_Index[piece][1]=target;
				break;
			}
		}
		for(int i=0;i<16;i++)
		{
			if(WhitePieces_Index[i][1]==target)
			{
				if(i==11)
				{
					System.out.println("Black wins!");
					
				}
				panel[WhitePieces_Index[i][1]/10-1][WhitePieces_Index[i][1]%10-1].removeAll();
				panel[BlackPieces_Index[piece][1]/10-1][BlackPieces_Index[piece][1]%10-1].removeAll();
				panel[WhitePieces_Index[i][1]/10-1][WhitePieces_Index[i][1]%10-1].add(Black_Pieces[piece]);
				panel[BlackPieces_Index[piece][1]/10-1][BlackPieces_Index[piece][1]%10-1].add(empty[Empty_Index]);
				empty_pos[Empty_Index][1]= BlackPieces_Index[piece][1];
				empty[Empty_Index].revalidate();
				empty_pos[Empty_Index][1]= BlackPieces_Index[piece][1];
				Empty_Index++;
				
				panel[BlackPieces_Index[piece][1]/10-1][BlackPieces_Index[piece][1]%10-1].setBackground(Color.ORANGE);
				panel[WhitePieces_Index[i][1]/10-1][WhitePieces_Index[i][1]%10-1].setBackground(Color.ORANGE);
			
				panel[BlackPieces_Index[piece][1]/10-1][BlackPieces_Index[piece][1]%10-1].repaint();
				panel[WhitePieces_Index[i][1]/10-1][WhitePieces_Index[i][1]%10-1].repaint();
			
				BlackPieces_Index[piece][1]= WhitePieces_Index[i][1];
				WhitePieces_Index[i][1]=1000;
				break;
			}
		}
	}
	
	
	public int evaluate()
	{
		int score=0;
		for(int i=0;i<16;i++)
		{
			if(BlackPieces_Index[i][1]<100)
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
				if((i==9||i==14) && BlackPieces_Index[i][1]%10>2 && BlackPieces_Index[i][1]/10>2 && BlackPieces_Index[i][1]/10<7)
					score-=3;
				if(i<8 && BlackPieces_Index[i][1]%10>3 && BlackPieces_Index[i][1]/10>2 && BlackPieces_Index[i][1]/10<7)
					score-=1;
			}
			if(WhitePieces_Index[i][1]<100)
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
				if((i==9||i==14) && WhitePieces_Index[i][1]%10<7 && WhitePieces_Index[i][1]/10>2 && WhitePieces_Index[i][1]/10<7)
					score+=3;
				if(i<8 && WhitePieces_Index[i][1]%10<6 && WhitePieces_Index[i][1]/10>2 && WhitePieces_Index[i][1]/10<7)
					score+=1;
			}
		}
		return score;
	}
	
	
	int NegaMax(int alpha, int beta, int depth )
	{
		
	    if ( depth == 0 ) 
	    	return evaluate();
	    
	    
	    if(first)
	    {
	    	for(int i=0;i<16;i++)
	    	{
	    		BlackPieces_IndexTemp[i][1]= BlackPieces_Index[i][1];
	    		WhitePieces_IndexTemp[i][1]= WhitePieces_Index[i][1];
	    	}
	    	first=false;
	    }
	    
	    for(int i=0;i<16;i++)
		{
			if(BlackPieces_Index[i][1]<100 && depth%2==1)
			{
				for(int j=11;j<=88;j++)
				{
					turn=false;
					if(isLegal(i, BlackPieces_Index[i][1],j))
							{
								for(int k=0;k<64;k++)
								{
									if(empty_pos[k][1]==j)
									{
										BlackPieces_Index[i][1]=j;
										if(i==11 && j==17 && BlackPieces_Index[11][1]==15)//castling
										{
											BlackPieces_Index[15][1]=16;
										}
										if(i==11 && j==13 && BlackPieces_Index[11][1]==15)//castling
										{
											BlackPieces_Index[9][1]=14;
										}
										score = negaMin( alpha, beta, depth - 1 );
										if(i==11 && j==17 && BlackPieces_Index[11][1]==15)//castling
										{
											BlackPieces_Index[15][1]=16;
										}
										if(i==11 && j==13 && BlackPieces_Index[11][1]==15)//castling
										{
											BlackPieces_Index[9][1]=14;
										}
										BlackPieces_Index[i][1]= BlackPieces_IndexTemp[i][1];
								        if( score >= beta )
								           return beta;
								        if( score > alpha )
									           alpha=score;
									}
								}
								for(int k=0;k<16;k++)
								{
									if(WhitePieces_Index[k][1]==j)
									{
										
										WhitePieces_Index[k][1]=1000;
										BlackPieces_Index[i][1]=j;
										score = negaMin( alpha, beta, depth - 1 );
										WhitePieces_Index[k][1]= WhitePieces_IndexTemp[k][1];
										BlackPieces_Index[i][1]= BlackPieces_IndexTemp[i][1];
										if( score >= beta )
									           return beta;
								        if( score > alpha )
								           alpha=score;										
									}
								}
							}
				}
			}
			else if(WhitePieces_Index[i][1]<100 && depth%2==0)
			{
				for(int j=11;j<=88;j++)
				{
					turn=true;
					if(isLegal(i, WhitePieces_Index[i][1],j))
							{
								for(int k=0;k<64;k++)
								{
									if(empty_pos[k][1]==j)
									{
										WhitePieces_Index[i][1]=j;
										if(i==11 && j==87 && WhitePieces_Index[11][1]==85)//castling
										{
											WhitePieces_Index[15][1]=86;
										}
										if(i==11 && j==83 && WhitePieces_Index[11][1]==85)//castling
										{
											WhitePieces_Index[9][1]=84;
										}
										score = negaMin(alpha, beta,  depth - 1 );
										if(i==11 && j==87 && WhitePieces_Index[11][1]==85)//castling
										{
											BlackPieces_Index[15][1]=86;
										}
										if(i==11 && j==83 && WhitePieces_Index[11][1]==85)//castling
										{
											BlackPieces_Index[9][1]=84;
										}
										WhitePieces_Index[i][1]= WhitePieces_IndexTemp[i][1];
										 if( score >= beta )
									           return beta;
									        if( score > alpha )
										           alpha=score;
								        
									}
								}
								for(int k=0;k<16;k++)
								{
									if(BlackPieces_Index[k][1]==j)
									{
										
										WhitePieces_Index[i][1]=j;
										BlackPieces_Index[k][1]=1000;
										score = negaMin( alpha, beta, depth - 1 );
										BlackPieces_Index[k][1]= BlackPieces_IndexTemp[k][1];
										WhitePieces_Index[i][1]= WhitePieces_IndexTemp[i][1];
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
	    		BlackPieces_IndexTemp[i][1]= BlackPieces_Index[i][1];
	    		WhitePieces_IndexTemp[i][1]= WhitePieces_Index[i][1];
	    	}
	    	first=false;
	    }
	    
	    for(int i=0;i<16;i++)
		{
			if(BlackPieces_Index[i][1]<100 && depth%2==1)
			{
				
				for(int j=11;j<=88;j++)
				{
					turn=false;
					if(isLegal(i, BlackPieces_Index[i][1],j))
							{
								for(int k=0;k<64;k++)
								{
									if(empty_pos[k][1]==j)
									{
										BlackPieces_Index[i][1]=j;
										if(i==11 && j==17 && BlackPieces_Index[11][1]==15)//castling
										{
											BlackPieces_Index[15][1]=16;
										}
										if(i==11 && j==13 && BlackPieces_Index[11][1]==15)//castling
										{
											BlackPieces_Index[9][1]=14;
										}
										score = NegaMax( alpha, beta, depth - 1 );
										if(i==11 && j==17 && BlackPieces_Index[11][1]==15)//castling
										{
											BlackPieces_Index[15][1]=16;
										}
										if(i==11 && j==13  && BlackPieces_Index[11][1]==15)//castling
										{
											BlackPieces_Index[9][1]=14;
										}
										BlackPieces_Index[i][1]= BlackPieces_IndexTemp[i][1];
								        if( score <= alpha )
								           return alpha;
								        if( score < beta )
									           beta=score;
									}
								}
								for(int k=0;k<16;k++)
								{
									if(WhitePieces_Index[k][1]==j)
									{
										
										WhitePieces_Index[k][1]=1000;
										BlackPieces_Index[i][1]=j;
										score = NegaMax( alpha, beta, depth - 1 );
										WhitePieces_Index[k][1]= WhitePieces_IndexTemp[k][1];
										BlackPieces_Index[i][1]= BlackPieces_IndexTemp[i][1];
										if( score <= alpha )
									           return alpha;
									        if( score < beta )
										           beta=score;									
									}
								}
							}
				}
			}
			else if(WhitePieces_Index[i][1]<100 && depth%2==0)
			{
				
				for(int j=11;j<=88;j++)
				{
					turn=true;
					if(isLegal(i, WhitePieces_Index[i][1],j))
							{
								for(int k=0;k<64;k++)
								{
									if(empty_pos[k][1]==j)
									{
										WhitePieces_Index[i][1]=j;
										if(i==11 && j==87  && WhitePieces_Index[11][1]==85)//castling
										{
											WhitePieces_Index[15][1]=86;
										}
										if(i==11 && j==83  && WhitePieces_Index[11][1]==85)//castling
										{
											WhitePieces_Index[9][1]=84;
										}
										score = NegaMax(alpha, beta,  depth - 1 );
										if(i==11 && j==87  && WhitePieces_Index[11][1]==85)//castling
										{
											WhitePieces_Index[15][1]=86;
										}
										if(i==11 && j==83  && WhitePieces_Index[11][1]==85)//castling
										{
											WhitePieces_Index[9][1]=84;
										}
										WhitePieces_Index[i][1]= WhitePieces_IndexTemp[i][1];
										if( score <= alpha )
									           return alpha;
									        if( score < beta )
										           beta=score;		
								        
									}
								}
								for(int k=0;k<16;k++)
								{
									if(BlackPieces_Index[k][1]==j)
									{
										
										WhitePieces_Index[i][1]=j;
										BlackPieces_Index[k][1]=1000;
										score = NegaMax( alpha, beta, depth - 1 );
										BlackPieces_Index[k][1]= BlackPieces_IndexTemp[k][1];
										WhitePieces_Index[i][1]= WhitePieces_IndexTemp[i][1];
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
