package com.test.snake;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

@SuppressLint({ "DrawAllocation", "HandlerLeak" }) public class SnakeView extends View implements Runnable{
    final int UP = 1 , DOWN = 2 , LEFT = 3 , RIGHT = 4;  
	final int DEFAULT_SNAKE_LENGTH = 20;//默认蛇的长度
    final int BLOCK_LENGTH = 18;//一次前进多少像素==node长度
    final Handler handler;
    boolean isLeft  = false ;
    boolean isRight = false ;
    boolean isGaming = true ;
    int dir = RIGHT;
    int scores = 0 ;
    int viewWidth , viewHeight ;
    private double T = 150 ;// 前进周期(ms)
    Thread thread;
    Food food = new Food() ;
    
    ArrayList<Node> nodes = new ArrayList<Node>();

    public SnakeView(Context context,AttributeSet attrs){    		
	    super(context,attrs);
	    for(int i = 0 ; i < DEFAULT_SNAKE_LENGTH ; i++){
	    	Node node = new Node(BLOCK_LENGTH/2-BLOCK_LENGTH*i, 5*BLOCK_LENGTH+BLOCK_LENGTH/2);
		    nodes.add(node);
	    }
	    
		handler = new Handler(){
			public void handleMessage(Message msg){
				if(msg.what== 0x123)
					invalidate();
			}
	    };
	
	    thread = new Thread(SnakeView.this);
	    thread.start();
	    
    }
    public void restart(){
    	isLeft  = false ;
    	isRight = false ;
    	isGaming = true ;
    	scores = 0 ;
    	dir = RIGHT ;
    	T = 150 ;
    	nodes = new ArrayList<Node>() ;
	    for(int i = 0 ; i < DEFAULT_SNAKE_LENGTH ; i++){
	    	Node node = new Node(BLOCK_LENGTH/2-BLOCK_LENGTH*i, 5*BLOCK_LENGTH+BLOCK_LENGTH/2);
		    nodes.add(node);
	    }
	    thread = new Thread(SnakeView.this);
	    thread.start();
	    
    }

	public void setPeriod(long a){
	    T = a;
    }

	public void onDraw(Canvas canvas){
    	Paint snakePaint = new Paint();
    	snakePaint.setStyle(Paint.Style.FILL);
	    snakePaint.setAntiAlias(true);
	    snakePaint.setColor(Color.WHITE);
	    
	    Paint foodPaint = new Paint();
	    foodPaint.setStyle(Paint.Style.FILL);
	    foodPaint.setAntiAlias(true);
	    foodPaint.setColor(Color.RED);
	    
	    canvas.drawCircle(food.x, food.y, BLOCK_LENGTH/2, foodPaint);
	    
    	for(int i = 0 ; i < nodes.size() ; i++)
    		canvas.drawRect( nodes.get(i).x-BLOCK_LENGTH/2 , nodes.get(i).y-BLOCK_LENGTH/2 , nodes.get(i).x+BLOCK_LENGTH/2 , nodes.get(i).y+BLOCK_LENGTH/2 , snakePaint);   	    	
    	if(isGaming == false){
    		canvas.drawARGB(122, 0, 0, 0);
    		}
	} 
	
	public void run(){
		while(isGaming){
	    		//左转
	    		if( isLeft == true ){
	    			switch(dir){
	    			  case UP   : nodes.get(0).x -=BLOCK_LENGTH ; dir = LEFT ;
	    			  break ;
	    			  case DOWN : nodes.get(0).x +=BLOCK_LENGTH ; dir = RIGHT;
	    			  break ;
	    			  case LEFT : nodes.get(0).y +=BLOCK_LENGTH ; dir = DOWN ;
	    			  break ;
	    			  case RIGHT: nodes.get(0).y -=BLOCK_LENGTH ; dir = UP   ;
	    			  break ;
	    			}
	    			isLeft = false ;
	    		}
	    		//右转
	    		else if( isRight == true ){
	    			switch(dir){
	    			  case UP   :nodes.get(0).x +=BLOCK_LENGTH ; dir = RIGHT ; break ;
	    			  case DOWN :nodes.get(0).x -=BLOCK_LENGTH ; dir = LEFT  ; break ;
	    			  case LEFT :nodes.get(0).y -=BLOCK_LENGTH ; dir = UP    ; break ;
	    			  case RIGHT:nodes.get(0).y +=BLOCK_LENGTH ; dir = DOWN  ; break ;
	    			}
	    			isRight = false ;
	    		}
	    		//继续前行
	    		else{
	    			switch(dir){
	    			  case UP   :nodes.get(0).y -=BLOCK_LENGTH ; break ;
	    			  case DOWN :nodes.get(0).y +=BLOCK_LENGTH ; break ;
	    			  case LEFT :nodes.get(0).x -=BLOCK_LENGTH ; break ;
	    			  case RIGHT:nodes.get(0).x +=BLOCK_LENGTH ; break ;
	    		    }
	    		}
	    		//是否撞到自己
	    		for(int j = 1 ; j < nodes.size() ; j++ ){
	    			if( nodes.get(0).x == nodes.get(j).x && nodes.get(0).y == nodes.get(j).y ){
	    				gameover();
	    				break;
	    				}
	    		}
	    		//越界穿越
	    		if(nodes.get(0).x > viewWidth )  nodes.get(0).x = BLOCK_LENGTH/2 ;
	    		if(nodes.get(0).x < 0 )          nodes.get(0).x = BLOCK_LENGTH*(  (int) (viewWidth/BLOCK_LENGTH) ) + BLOCK_LENGTH/2 ;
	    		if(nodes.get(0).y > viewHeight ) nodes.get(0).y = BLOCK_LENGTH/2 ;
	    		if(nodes.get(0).y < 0 )          nodes.get(0).y = BLOCK_LENGTH*(  (int) (viewHeight/BLOCK_LENGTH)) + BLOCK_LENGTH/2 ;
	    		//吃到食物
	    		if( nodes.get(0).x == food.x && nodes.get(0).y == food.y){ 
	    			nodes.add(new Node(nodes.get(nodes.size()-1).x, nodes.get(nodes.size() -1).y ) );
	    			for(int i = SnakeView.this.nodes.size() - 2 ; i > 0 ; i-- ){	
      	    			/*SnakeView.this.*/nodes.get(i).x = /*SnakeView.this.*/nodes.get(i-1).x ;
      	    			/*SnakeView.this.*/nodes.get(i).y = /*SnakeView.this.*/nodes.get(i-1).y ;
      	    		}
	    			scores+=50;    			
	    			food.setFood();
	    		}
	    		else for(int i = SnakeView.this.nodes.size() - 1 ; i > 0 ; i-- ){	
	    			/*SnakeView.this.*/nodes.get(i).x = /*SnakeView.this.*/nodes.get(i-1).x ;
	    			/*SnakeView.this.*/nodes.get(i).y = /*SnakeView.this.*/nodes.get(i-1).y ;
	    		}
	    		 
	    		handler.sendEmptyMessage(0x123);
	    	    try {
	    	    	if(T>40)
	    	    		Thread.sleep((long) (T-= 0.1));
	    	    	else 
						Thread.sleep(40);
						    	    	
				} catch (Exception e) {
					// TODO: handle exception
					//Toast.makeText(getContext(), ""+e, Toast.LENGTH_LONG).show();
				}
		}
	}
	
	public void gameover(){
		isGaming = false;
		handler.sendEmptyMessage(0x123);
		
	}

	class Node{ 	
		private int x , y ;

		public Node(){
    		x = BLOCK_LENGTH/2; y = 5*BLOCK_LENGTH+BLOCK_LENGTH/2;
    	}
    	public Node(int a,int b){
    		x=a ; y=b ;
    	}
    	public void setNode(int a, int b){
    		x=a ; y=b ;
    	}
	}
	
	class Food{
		private int x , y ; //必须全屏不然会看不到food
		public Food(){
			DisplayMetrics metric = getResources().getDisplayMetrics();
    		//方法二：（必须在Activity下使用）
			//DisplayMetrics metric = new DisplayMetrics();
			//getWindowManager().getDefaultDisplay().getMetrics(metric);
    		viewWidth = metric.widthPixels;
    		viewHeight = metric.heightPixels;
    		
			int a = viewWidth /BLOCK_LENGTH;
			int b = viewHeight /BLOCK_LENGTH;
			int c =(int) (Math.random()*(a-1));//a-1是为了防止加了BLOCK_LENGTH/2后越界
			int d =(int) (Math.random()*(b-1));
			x = c * BLOCK_LENGTH +BLOCK_LENGTH/2;
			y = d * BLOCK_LENGTH +BLOCK_LENGTH/2;
			//Toast.makeText(MovingTest.this,"(" +x+"."+y+")", Toast.LENGTH_LONG).show();
		}
		public void setFood(){
			int a = viewWidth /BLOCK_LENGTH;
			int b = viewHeight /BLOCK_LENGTH;
			int c =(int) (Math.random()*(a-1));
			int d =(int) (Math.random()*(b-1));
			x = c * BLOCK_LENGTH +BLOCK_LENGTH/2;
			y = d * BLOCK_LENGTH +BLOCK_LENGTH/2;
			//Toast.makeText(MovingTest.this,"(" +x+"."+y+")", Toast.LENGTH_LONG).show();
		}
	}
	@Override
	public boolean onTouchEvent( MotionEvent event) {
		// TODO Auto-generated method stub
		
		if( event.getX() < viewWidth/2 )
			this.isLeft = true ;
		else 
			this.isRight = true ;
		return false;
    }
	

}
