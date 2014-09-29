package com.tony.tictactoegame; /**
 * Created by Tony on 2014-09-14.
 */
import java.util.Random;

/**
 * Created by Tony on 2014-09-13.
 */
public class TicTacToeGame
{

    enum TypeOfWinner
    {
        PlayerWins,
        CompWins,
        Tie,
        GameNotOver
    }

    //variables
    private final int mBoardDim = 9;
    private char[] mBoard;
    private final char mPlayer = 'X';
    private final char mComp = 'O';
    private final char mEmptySpace = ' ';
    private Random mRand;

    //methods

    //constructor
    TicTacToeGame()
    {
        mBoard = new char[9];

        for(int i = 0; i < mBoardDim; i++)
        {
            mBoard[i] = mEmptySpace;
        }

        mRand = new Random();
    }

    public final char GetHumanPlayer()
    {
        return mPlayer;
    }

    public final char GetComputerPlayer()
    {
        return mComp;
    }



    public int GetBoardSize()
    {
        return mBoardDim;
    }

    public void ClearBoard()
    {
        for(int i = 0; i < mBoardDim; ++i)
        {
            mBoard[i] = mEmptySpace;
        }
    }

    public void SetMove(char player,int location)
    {
        mBoard[location] = player;
    }

    public int GetComputerMove()
    {
        int move;

        //computer checks to see if there are any locations that will allow it to win
        for(int i = 0; i < mBoardDim; ++i)
        {
            //if space is empty
            if(mBoard[i] != mPlayer && mBoard[i] != mComp)
            {
                char curr = mBoard[i];
                mBoard[i] = mComp;

                //if CheckForWinner == 3 the computer has won and sets it move to that position
                if(CheckForWinner() == TypeOfWinner.CompWins)
                {
                    SetMove(mComp, i);
                    return i;
                }
                //set back to a empty position
                else
                {
                    mBoard[i] = curr;
                }
            }

        }


        for(int i = 0; i < mBoardDim; ++i)
        {
            //if space is empty
            if(mBoard[i] != mPlayer && mBoard[i] != mComp)
            {
                char curr = mBoard[i];
                mBoard[i] = mPlayer;
                //if CheckForWinner == 2 the human player will win and
                // the computer sets it move to that position to stop it
                if(CheckForWinner() == TypeOfWinner.PlayerWins)
                {
                    SetMove(mComp, i);
                    return i;
                }
                //set back to a empty position
                else
                {
                    mBoard[i] = curr;
                }
            }

        }

        do
        {

            move = mRand.nextInt(mBoardDim);
        }while(mBoard[move] == mPlayer || mBoard[move] == mComp);

        SetMove(mComp, move);

        return move;
    }



    public TypeOfWinner CheckForWinner()
    {

        //check winner horizontally
        for(int i = 0;i < mBoardDim; i+= 3)
        {
            if(mBoard[i] == mPlayer &&  mBoard[i+1] == mPlayer && mBoard[i+2] == mPlayer)
                return TypeOfWinner.PlayerWins;

            if(mBoard[i] == mComp && mBoard[i+1] == mComp && mBoard[i+2] == mComp)
                return TypeOfWinner.CompWins;
        }

        //check winner vertically
        for(int i = 0; i <= 2; ++i)
        {
            if(mBoard[i] == mPlayer && mBoard[i+3] == mPlayer && mBoard[i+6] == mPlayer)
                return TypeOfWinner.PlayerWins;
            if(mBoard[i] == mComp && mBoard[i+3] == mComp && mBoard[i+6] == mComp)
                return TypeOfWinner.CompWins;
        }

        //check winner diagonally
        if((mBoard[0] == mPlayer && mBoard[4] == mPlayer && mBoard[8] == mPlayer)
         || (mBoard[2] == mPlayer && mBoard[4] == mPlayer && mBoard[6] == mPlayer))
            return TypeOfWinner.PlayerWins;

        if((mBoard[0] == mComp && mBoard[4] == mComp && mBoard[8] == mComp)
          ||( mBoard[2] == mComp && mBoard[4] == mComp && mBoard[6] == mComp))
            return TypeOfWinner.CompWins;

        //return 0 if there is still a space
        for(int i = 0; i < mBoardDim; ++i)
        {
            if(mBoard[i] != mPlayer && mBoard[i] != mComp)
                return TypeOfWinner.GameNotOver;
        }

        //return 1 for a tie
        return TypeOfWinner.Tie;
    }
}
