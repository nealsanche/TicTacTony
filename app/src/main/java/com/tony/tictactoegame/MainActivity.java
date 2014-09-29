package com.tony.tictactoegame;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity
{

    private TicTacToeGame mGame;

    private Button mBoardButtons[];

    private TextView mInfoTextView;
    private TextView mPlayerCount;
    private TextView mTiesCount;
    private TextView mAndroidCount;

    private int mPlayerCounter = 0;
    private int mTiesCounter = 0;
    private int mAndroidCounter = 0;

    private boolean mPlayerFirst = true;
    private boolean mGameOver = false;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mGame = new TicTacToeGame();

        mBoardButtons = new Button[mGame.GetBoardSize()];
        mBoardButtons[0] = (Button) findViewById(R.id.one);
        mBoardButtons[1] = (Button) findViewById(R.id.two);
        mBoardButtons[2] = (Button) findViewById(R.id.three);
        mBoardButtons[3] = (Button) findViewById(R.id.four);
        mBoardButtons[4] = (Button) findViewById(R.id.five);
        mBoardButtons[5] = (Button) findViewById(R.id.six);
        mBoardButtons[6] = (Button) findViewById(R.id.seven);
        mBoardButtons[7] = (Button) findViewById(R.id.eight);
        mBoardButtons[8] = (Button) findViewById(R.id.nine);


        mInfoTextView = (TextView) findViewById(R.id.Information);
        mPlayerCount = (TextView) findViewById(R.id.count);
        mTiesCount = (TextView) findViewById(R.id.ties_counter);
        mAndroidCount = (TextView) findViewById(R.id.Comp_Counter);

        mPlayerCount.setText(Integer.toString(mPlayerCounter));
        mTiesCount.setText(Integer.toString(mTiesCounter));
        mAndroidCount.setText(Integer.toString(mAndroidCounter));

        StartNewGame();
    }

    private void StartNewGame()
    {
        mGame.ClearBoard();

        for(int i = 0; i < mBoardButtons.length;++i )
        {
            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
        }

        if(mPlayerFirst)
        {
            mInfoTextView.setText(R.string.first_human);
            mPlayerFirst = false;
        }
        else
        {
            mInfoTextView.setText(R.string.turn_computer);
            int move = mGame.GetComputerMove();

            SetMove(mGame.GetComputerPlayer(), move);
            mPlayerFirst = true;

        }

    }


    private class ButtonClickListener implements View.OnClickListener
    {
        int location;

        public ButtonClickListener(int location)
        {
            this.location = location;
        }

        public void onClick(View view)
        {
            if(!mGameOver)
            {
                if(mBoardButtons[location].isEnabled())
                {
                    SetMove(mGame.GetHumanPlayer(), location);

                    TicTacToeGame.TypeOfWinner winner = mGame.CheckForWinner();

                    if(winner == TicTacToeGame.TypeOfWinner.GameNotOver)
                    {
                        mInfoTextView.setText(R.string.turn_computer);
                        int move = mGame.GetComputerMove();
                        SetMove(mGame.GetComputerPlayer(), move);

                        winner = mGame.CheckForWinner();
                    }

                    if(winner == TicTacToeGame.TypeOfWinner.GameNotOver)
                    {
                        mInfoTextView.setText(R.string.turn_human);
                    }
                    else if(winner == TicTacToeGame.TypeOfWinner.Tie)
                    {
                        mInfoTextView.setText(R.string.result_tie);
                        mTiesCounter++;
                        mTiesCount.setText(Integer.toString(mTiesCounter));
                        mGameOver = true;
                    }
                    else if(winner == TicTacToeGame.TypeOfWinner.PlayerWins)
                    {
                        mInfoTextView.setText(R.string.result_human_wins);
                        mPlayerCounter++;
                        mPlayerCount.setText(Integer.toString(mPlayerCounter));
                        mGameOver = true;
                    }
                    else
                    {
                        mInfoTextView.setText(R.string.result_android_wins);
                        mAndroidCounter++;
                        mAndroidCount.setText(Integer.toString(mAndroidCounter));
                        mGameOver = true;
                    }

                }
            }
        }
    }


    private void SetMove(char player, int location)
    {
        mGame.SetMove(player, location);
        mBoardButtons[location].setEnabled(false);
        mBoardButtons[location].setText(String.valueOf(player));

        if(player == mGame.GetHumanPlayer())
            mBoardButtons[location].setTextColor(Color.GREEN);
        else
            mBoardButtons[location].setTextColor(Color.RED);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
