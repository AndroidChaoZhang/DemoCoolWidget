package com.zc.democoolwidget.casetotal.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
/**
 * һ��view�Ӵ�����������ʾ����Ļ�ϣ�֮�����Ҫ���裺
 * 1�����ù��캯��
 * 
 * 2��������С
 * 		onMeasure(width,height);
 * 3��ȷ��λ��
 * 		onLayout(b,l,t,r,b);
 * 4������View������
 * 		onDraw(canvas);
 */
public class MyView extends View{
	/**
	 * �ڴ����У��ùؼ��� new ���������ʱ�򣬵���
	 */
	public MyView(Context context) {
		super(context);
	}
	/**
	 * ��ϵͳ�����÷���ķ�ʽ���ã�
	 * ʹ�ó�����view������xml �����ļ�����������ʹ��findViewById()��������ø�view�����ʱ����ϵͳ�Զ�����
	 */
	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	/**�����ò���
	 */
	public MyView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	/**
	 * ϵͳ�����ؼ���С��ʱ�򣬵���
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);//������С ����������ϵͳ��Ĭ�Ϲ��������С  Ĭ�� fill_parent
		 // ���ǵ����񣬾��Ǹ���ϵͳ����ǰview��Ҫ���ռ�   //��͸�
		setMeasuredDimension(200, 200);//�̳�View��ʵ���Լ���Ҫ�����,������������˵�ǰView�Ĵ�С
	}
	/**
	 * ��view��λ��ȷ�����Ժ󣬵��ô˷���
	 */
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
//		super.onLayout(changed, left, top, right, bottom);
		//��view��λ�ã��ո� view���� 
		System.out.println("left:"+left);
		System.out.println("top:"+top);
		System.out.println("right:"+right);
		System.out.println("bottom:"+bottom);
	}
	/**
	 * ����view������
	 */
	@Override
	protected void onDraw(Canvas canvas) {
//		super.onDraw(canvas);
		canvas.drawColor(Color.RED);
	}
}
