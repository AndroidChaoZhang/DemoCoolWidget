package com.zc.democoolwidget.casetotal.myview;

import android.content.Context;
import android.os.SystemClock;

/**
 * 用于计算位移的工程师
 * @author leo
 *
 */
public class MyScroller {
	/**
	 * 起始点的X坐标
	 */
	private int startX;
	/**
	 * 起始点的Y坐标
	 */
	private int startY;
	/**
	 * X方向 移动的距离
	 */
	private int distanceX;
	/**
	 * Y方向 移动的距离
	 */
	private int distanceY;

	/**
	 * 开始时的时间
	 */
	private long startTime;

	public MyScroller(Context ctx){

	}

	/**
	 *
	 * @param startX
	 * @param startY
	 * @param distanceX
	 * @param distanceY
	 */
	public void startScroll(int startX,int startY,int distanceX,int distanceY){
		this.startX = startX;
		this.startY = startY;

		this.distanceX = distanceX;
		this.distanceY = distanceY;

		this.startTime = SystemClock.uptimeMillis();

		isFinish = false;
	}

	private boolean isFinish = false;

	/**
	 * 默认运行的总的时间
	 */
	private long TOTLE_TIME = 1000;

	private long currX;
	private long currY;

	/**
	 * 计算运行的偏移量
	 * @return
	 * true 表示，还在运行
	 * false 表示，运行已经结束
	 */
	public boolean computeScrollOffset(){
		if(isFinish){
			return false;
		}

		long passTime = SystemClock.uptimeMillis() - startTime;

		if(passTime<TOTLE_TIME){//说明还在做运动

			// 以前的X坐标 = 开始的X坐标 + 这段时的位移          位移 = 时间 * 速度
			currX = startX+ passTime * distanceX/TOTLE_TIME;

			currY = startY+ passTime * distanceY/TOTLE_TIME;

		}else{// 运动，已经结束了

			currX = startX+distanceX;

			currY = startY+distanceY;

			isFinish = true;
		}


		return true;
	}

	public long getCurrX() {
		return currX;
	}


	public long getCurrY() {
		return currY;
	}

}
