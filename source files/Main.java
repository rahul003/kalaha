/*
Team Name: Code Riders
Team Members: Rachuri Anirudh 11010155
			  Rahul Huilgol 11010156
			  Harshith Reddy 11010149
Project: Game - Kalaha
*/
import java.util.*;

public class Main {
int diff;
public static void main(String[] args) throws InterruptedException {
	int index;
	int num=6; 
	int toss;
	Board gameboard =new Board();
	gameboard.board[0]=0;
	gameboard.board[7]=0;
	Random rand = new Random(); 
	int[] maxarray=new int[6];
	for(int q=0;q<6;q++)
	{	
		maxarray[q]=-1000; 
	}
	int max = -1000;
	int selection = 3;

	Scanner input=new Scanner(System.in);
	for(index=1;index<14;index++)
	{
		if(index!=7)gameboard.board[index]=num;		
	}
	gameboard.printInstructions();
	
	System.out.println("Get ready for the toss");
	System.out.println("Choose any even or odd number. I will generate a random number. If it's nature is the same as your given number, you win the toss.");
	toss=input.nextInt();
	int temp=rand.nextInt();
	//System.out.println("Random number generated is "+temp);
	
	if(toss%2!=temp%2)  //computer's first turn
		{
		System.out.println("I won the toss. I play first");
		System.out.println("The situation of the board initially is:");
	gameboard.printBoard();
		maxarray=minimax(2,gameboard);  //returns the maxarray for selection
		for(int r=0;r<6;r++) //computer selection
		{
			if(maxarray[r]>max && gameboard.board[r+8]!=0)
			{
				max=maxarray[r];selection=r+8;
			}
			
		}
		max=-1000; //re-initialize max
		Thread.sleep(500);
		System.out.println("I select "+selection);
		gameboard.extra2=gameboard.aimove(selection);  //extra2 tells us if computer gets an extra turn
		while(gameboard.extra2)  //while it keeps on getting extra turns
		{
			if(gameboard.gameOver()!=1)
			{
				break; //game ended
			}
			//else
			maxarray=minimax(2,gameboard);
			for(int r=0;r<6;r++)
			{
				if(maxarray[r]>max && gameboard.board[r+8]!=0)
				{
					max=maxarray[r];selection=r+8;
				}
			}
			max=-1000;  //re-initialize max
			Thread.sleep(500);
			System.out.println("I select "+selection);
			gameboard.extra2=gameboard.aimove(selection);

			if(!gameboard.extra2)
			{
			gameboard.printBoard();
			System.out.println("---------------------------------------------------------------------------");
			}
		}
	}

	else
	{System.out.println("You won the toss");
	System.out.println("The situation of the board initially is:");
	gameboard.printBoard();
	}
	
	
	//player starts first
	while (gameboard.gameOver()==1)
	{
		do 
		{
			if(gameboard.gameOver()==0 || gameboard.gameOver()==7)
			{
			break;
			}
			
			if(gameboard.extra)
			{
			System.out.println("You got an extra turn");
			}
			
			System.out.println("\nYour turn now. Enter the pit number(It has to be between 1 and 6)");
			gameboard.pit = input.nextInt();
			
			
			while(gameboard.pit>6 || gameboard.pit<1)
			{
			System.out.println("Choose pit between 1 and 6");
			gameboard.pit= input.nextInt();
			}
			
			
			while(gameboard.board[gameboard.pit]==0)
			{
			System.out.println("The selected pit has no seeds. Choose a different one.\n");
			gameboard.pit=input.nextInt();
				while(gameboard.pit>6 || gameboard.pit<1)
				{
				System.out.println("Choose pit between 1 and 6");
				gameboard.pit=input.nextInt();
				}
			}
			
			gameboard.extra=gameboard.humanmove(gameboard.pit);
			gameboard.printBoard();
			
			
			
			if(!gameboard.extra)
			{
			System.out.println("---------------------------------------------------------------------------");
			}
			
			else continue;
			
			
			
			if(gameboard.gameOver()!=1)
			{break;}

			maxarray=minimax(2,gameboard);
			for(int r=0;r<6;r++)
			{
				if(maxarray[r]>max && gameboard.board[r+8]!=0)
				{
					max=maxarray[r];selection=r+8;
				}
			}
			max=-1000;

			Thread.sleep(500);
			System.out.println("I select "+selection);
			gameboard.extra2=gameboard.aimove(selection);
			
			
			if(!gameboard.extra2)
			{
			gameboard.printBoard();
			System.out.println("---------------------------------------------------------------------------");
			continue;
			}
			
			while(gameboard.extra2)
			{
				if(gameboard.gameOver()!=1)
				{break;}
				
				maxarray=minimax(2,gameboard);
				for(int r=0;r<6;r++)
				{
					if(maxarray[r]>max && gameboard.board[r+8]!=0)
					{
						max=maxarray[r];selection=r+8;
					}
				}
				max=-1000;

				Thread.sleep(500);  //slowing down the display of the move
				System.out.println("I select "+selection);
				gameboard.extra2=gameboard.aimove(selection);
				
				if(!gameboard.extra2)
					{
					gameboard.printBoard(); 
					System.out.println("---------------------------------------------------------------------------");
					}
			}
			
		} while (gameboard.extra); 
	}
	//when done finish game
	gameboard.finishGame(gameboard.gameOver());
}

//---------------------------------------------Minimax algo---------------------------------------------------

public static int[] minimax(int level,Board gameboard)
{
	int i,index;
	Board[] pmoves=new Board[6];
	boolean extra=false;
	int extramin=1000,extramax=-1000;
	int[] score=new int[6];
	int[] extrascore=new int[6];
	for(i=0;i<6;i++)
	{pmoves[i]=new Board();}   //creating copies of boards
	
	if(level==1)	
	{
		for(i=0;i<6;i++)
		{
			for(index=0;index<14;index++)
			{
			pmoves[i].board[index]=gameboard.board[index]; //copy situation of game to these boards
			}
			extra=pmoves[i].thinkhumanmove(i+1); //6 possibilites for the game to proceed
			if(extra!=true)   // if human does not get extra turn in this possible move
				score[i]=pmoves[i].score();
				
			else if(extra==true)// if human gets extra turn in this possible move
			{
				extrascore=minimax(1,pmoves[i]);  //again computer thinks how human can use the extra turn. and stores the array of scores returned
				for(int j=0;j<6;j++)
				{
					if(extrascore[j]<extramin && pmoves[i].board[j+1]!=0)
					{
					extramin=extrascore[j];   //finding min of the scores of the next level
					}
				}
				score[i]=extramin; 
			}
			extramin=1000;
		}
		return(score); //returns the score array
	}
	
//-----------level where computer makes a move-----------------
	
	if(level==2)
	{
		for(i=0;i<6;i++)
		{
			for(index=0;index<14;index++)
			{
				pmoves[i].board[index]=gameboard.board[index]; //copies of the board
			}
		}
		
	
		int [] minarray=new int[6];
		for(int q=0;q<6;q++)
		{	
			minarray[q]=1000; 
		}
	
		extra=pmoves[0].thinkaimove(8);  //each move possible for the computer
		if(extra!=true)
		{
			score=minimax(level-1,pmoves[0]);   
			for(int r=0;r<6;r++)
				{
					if(minarray[0]>score[r] && gameboard.board[r+1]!=0)
					{
						minarray[0]=score[r];  //finding min of possible human moves if computer selects 8th pit
					}
				}
		}
		else if(extra==true)
		{
			extrascore=minimax(2,pmoves[0]);
			for(int j=0;j<6;j++)
			{
				if(extrascore[j]>extramax && pmoves[0].board[j+8]!=0)
				{
					extramax=extrascore[j];
				}
			}
			minarray[0]=extramax;
		}
		extramax=-1000;
		
		
		for(int z=1;z<6;z++)
		{
			extra=pmoves[z].thinkaimove(z+8);

			if(extra!=true)
			{
				score=minimax(level-1,pmoves[z]);
				for(int r=0;r<6;r++)
					{
						if(minarray[z]>score[r] && gameboard.board[r+1]!=0)
						{
							minarray[z]=score[r];  //finding min of possible human moves if computer selects (z+8)th pit
						}
					}
			}
			else if(extra==true)
			{
				extrascore=minimax(2,pmoves[z]);
				if(pmoves[z].gameOver()==7 || pmoves[z].gameOver()==0)
				{
					minarray[z]=1000;
				}
				else
				{
					for(int j=0;j<6;j++)
					{
					if(extrascore[j]>extramax && pmoves[z].board[j+8]!=0 && gameboard.gameOver()==1)
					{
					extramax=extrascore[j];
					}
					}
				minarray[z]=extramax;
				}
			}
			extramax=-1000;
		}				
		return minarray;
	}
	return score;
}
}
