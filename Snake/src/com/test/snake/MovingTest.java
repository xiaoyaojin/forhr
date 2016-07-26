package com.test.snake;

import com.test.snake.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


@SuppressLint("HandlerLeak") public class MovingTest extends Activity implements Runnable{
	
	TextView replay;
	TextView score;
	TextView backScore;
	TextView exit;
	SnakeView snake;
	Handler handler;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
       
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	
    	//snake.move();   	
    	//score.setText("123");
    	setContentView(R.layout.frame_layout);
    	replay = (TextView)  findViewById(R.id.replay);
    	score = (TextView) findViewById(R.id.score);
    	backScore = (TextView) findViewById(R.id.backScore);
    	snake = (SnakeView) findViewById(R.id.snakeView);
    	exit = (TextView) findViewById(R.id.exit);
    	replay.setVisibility(View.INVISIBLE);
    	score.setVisibility(View.INVISIBLE);
    	exit.setVisibility(View.INVISIBLE);
    	handler = new Handler(){
    		public void handleMessage(Message msg){
    			if (msg.what == 0x111 ){
    				score.setText(""+snake.scores);
    				replay.setVisibility(View.VISIBLE);
    				score.setVisibility(View.VISIBLE);
    				exit.setVisibility(View.VISIBLE);
    				}
    			if (msg.what == 0x222 ){
    				replay.setVisibility(View.INVISIBLE);
    				score.setVisibility(View.INVISIBLE);
    				exit.setVisibility(View.INVISIBLE);
    				}
    			if ( msg.what == 0x333){
    				backScore.setText(""+snake.scores);
    			}
    		}
    	};
    	Thread thread = new Thread(this);
    	thread.start();
    }
	@Override
	public void run() {
		boolean hasSent1 = false;
		boolean hasSent2 = false;
		int s = snake.scores;
		// TODO Auto-generated method stub
		while(true){
			if(!snake.isGaming && hasSent1 == false ){				
				handler.sendEmptyMessage(0x111);
				hasSent1 = true;
				hasSent2 = false;
			}
			else if(snake.isGaming && hasSent2 == false){
				handler.sendEmptyMessage(0x222);
				hasSent2 = true;
				hasSent1 = false;
				}
			if(s!=snake.scores){
				handler.sendEmptyMessage(0x333);
				s = snake.scores ;
			}
		}
	}

    public void replay(View view){
    	
    	snake.restart();   	   	   	
    	
    }
    public void exit(View view){
    	Intent startMain = new Intent(Intent.ACTION_MAIN);
    	startMain.addCategory(Intent.CATEGORY_HOME);
    	startMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	startActivity(startMain);
    	System.exit(0);
    }
	
    
}

