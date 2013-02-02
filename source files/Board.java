/*
Team Name: Code Riders
Team Members: Rachuri Anirudh 11010155
			  Rahul Huilgol 11010156
			  Harshith Reddy 11010149
Project: Game - Kalaha
*/

import java.util.*;

public class Board {
	int pit,player;
	boolean extra,extra2;
	Scanner input=new Scanner(System.in);
	int[] board=new int[14];
	
	
	void printBoard()
	{	int index;
		System.out.println("");
		for(index=13;index>7;index--)
		{
			System.out.print("\t"+board[index]);		
		}
		System.out.print("\n\n"+board[0]);		
		System.out.println("\t\t\t\t\t\t\t"+board[7]+"\n");
		for(index=1;index<7;index++)
		{
			System.out.print("\t"+board[index]);		
		}
		System.out.println("\n");
		
	}
	
	
	void printInstructions()
	{	int index;
	System.out.println("\n\t\t\t\t\tWelcome to Kalaha\n");
	System.out.println("I will take you through the rules first");
	System.out.println("\nObjective of the game:\n\n\tEach player attempts to collect as many seeds as possible \n\t before one of the players clears his or her side of the seeds.");
	System.out.println("\nThe Board:\n\n\tEach player has a side of the board(top and bottom).\n\tThe six pits nearest to each player belong to him or her\n\tand each player's Kalaha is to the right.");
	System.out.println("\nHow to play:\n\n\tPlayers alternate turns.\n\tIn his or her turn each player selects a pit of seeds from one\n\tbox on his or her side of the board.\n\tEach seed is placed one by one in the pits around the board,\n\tincluding his Kalaha but not the opponent's Kalaha.\n\tIf the last seed lands into the player's own Kalaha,\n\tthat player gets an extra turn.\n\tIf the last seed lands in an empty pit on the player's own side,\n\the captures all the\n\tseeds from the opponent's pit directly opposite that pit.");
	System.out.println("\nHow to win:\n\n\tThe game is over when a player has no more pits in play on the board.\n\tThe winner is the player with the greatest total of seeds in his or her Kalaha\n\tand any remaining seeds on the opponents side of the board.");
		System.out.println("\n\tThese are the numbers for the various pits. We will be using these in the game\n");
		for(index=13;index>7;index--)
		{
			System.out.print("\t"+index);		
		}
		System.out.print("\n\n 0");		
		System.out.println("\t\t\t\t\t\t\t7\n");
		for(index=1;index<7;index++)
		{
			System.out.print("\t"+index);		
		}
		System.out.println("\n");
		System.out.println("Pit 0 is my Kalaha and Pit 7 is yours.");
	System.out.println("---------------------------------------------------------------------------");
	}
	
	
	void finishGame(int player)
	{	int i;
		int opp=player;//opp=0
		for(i=(player+1);i<=(player+6);i++)//1 to 6
			{board[opp]=board[(opp)]+board[(i)];//going to 7
			board[(i)]=0;}		
		System.out.println("Game Ended!");
		printBoard();
		if(board[0]>board[7]) System.out.println("\nI rule!\n");
		else if(board[0]<board[7])  System.out.println("\nYou managed to sneak a win. Lets play again, you will lose\n");
		else System.out.println("\nLets play again and decide whos greatest.\n");
	}
	
	void thinkfinishGame(int player)
	{	int i;
		int opp=player;//opp=0
		for(i=(player+1);i<=(player+6);i++)//1 to 6
			{board[opp]=board[(opp)]+board[(i)];//going to 7
			board[(i)]=0;}		
	}

	int gameOver()
	{		
		int j;
		for(j=1;j<7;j++)//human pits r empty
			{
			if(board[j]!=0) break;
			if(j==6) return 7;
			}
		
		for(j=8;j<14;j++)//8 to 14 r empty
			{
			if(board[j]!=0) break;
			if(j==13) return 0;
			}
		return 1;
	}

	
	
	boolean humanmove(int pit)		//move for human
	{	
		int i;
		int seeds=board[pit]+pit;
		board[pit]=0;
		for(i=pit+1;i<=seeds;i++)
		{	
			if((i%14)!=0)board[i%14]++;
			else seeds++;
		}
		if(seeds%14 == 7){return true;}  //playr got extra turn
		if(seeds%14<=6 && seeds%14>=1 && board[seeds%14]==1 && board[14-seeds%14]!=0)
		{
			System.out.println("You captured " + board[14-(seeds%14)] +" pieces");
			board[7]=board[7]+1+board[14-(seeds%14)];
			board[14-(seeds%14)]=0;
			board[seeds%14]=0;
		}
		return false;
	}

	
	
	boolean thinkhumanmove(int pit)		//pseudo move for human
	{	
		int i;
		int seeds=board[pit]+pit;
		board[pit]=0;
		for(i=pit+1;i<=seeds;i++)
		{	
			if((i%14)!=0)board[i%14]++;
			else seeds++;
		}
		if(seeds%14 == 7){return true;}  //playr got extra turn
		if(seeds%14<=6 && seeds%14>=1 && board[seeds%14]==1 && board[14-seeds%14]!=0)
		{
			board[7]=board[7]+1+board[14-(seeds%14)];
			board[14-(seeds%14)]=0;
			board[seeds%14]=0;
		}
		if(this.gameOver()==0 || this.gameOver()==7)
			this.thinkfinishGame(this.gameOver());
		return false;
	}
	
	
	
	boolean aimove(int pit)		//move for comp
	{	
		
		int i;
		int seeds=board[pit]+pit;
		board[pit]=0;
		for(i=pit+1;i<=seeds;i++)
		{	
			if((i%14)!=7){board[i%14]++;}
			else seeds++;
		}
		
		if(seeds%14 == 0){
			this.printBoard();
			if(this.gameOver()==1){System.out.println("I got an extra turn"); }
		return true;}  //comp got extra turn
		if(seeds%14<=13 && seeds%14>=8 && board[seeds%14]==1 && board[14-seeds%14]!=0)
		{
			System.out.println("I captured " + board[14-(seeds%14)] +" of your pieces");
			board[0]=board[0]+1+board[14-(seeds%14)];
			board[14-(seeds%14)]=0;
			board[seeds%14]=0;
		}
		return false;
	}
	
	
	
	boolean thinkaimove(int pit)		//pseudo move for comp
	{	
		
		int i;
		int seeds=board[pit]+pit;
		board[pit]=0;
		for(i=pit+1;i<=seeds;i++)
		{	
			if((i%14)!=7){board[i%14]++;}
			else seeds++;
		}
		
		if(seeds%14 == 0){return true;}  //comp got extra turn
		if(seeds%14<=13 && seeds%14>=8 && board[seeds%14]==1 && board[14-seeds%14]!=0)
		{
			board[0]=board[0]+1+board[14-(seeds%14)];
			board[14-(seeds%14)]=0;
			board[seeds%14]=0;
		}
		if(this.gameOver()==0 || this.gameOver()==7)
			this.thinkfinishGame(this.gameOver());
		return false;
	}
	
	
	
	int score()
	{
		return (board[0]-board[7]);
	}
}
