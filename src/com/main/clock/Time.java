package com.main.clock;

import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Time {
	public static void main(String[] args) {
		int h,mi,s;
		System.out.println("请设置第一个提醒点,到时后会发出提醒，以后每隔一个小时提醒一次！（不过如果提醒的时候，你点否的话就不同了）");
		System.out.print("小时（24小时制）：");
		Scanner cin = new Scanner(System.in);
		h=cin.nextInt();
		System.out.print("分钟：");
		mi=cin.nextInt();
		System.out.print("秒：");
		s=cin.nextInt();
		NowTime now = new NowTime(h,mi,s);
		ExecutorService threadExecutor = Executors.newSingleThreadExecutor();
		threadExecutor.execute(now);
		threadExecutor.shutdown();
	}
}
class NowTime implements Runnable{
	private int y,m,d,h,mi,s;
	private int myHour,myMinite,mySecond;
	private Calendar cal ;
	public NowTime(int myHour,int myMinite,int mySecond){
           this.myHour = myHour;
           this.myMinite = myMinite;
           this.mySecond = mySecond;
	}
	//如果弹出框点击的是yes，隔一个小时后再弹出
	private void setSeen(){
		
		cal = Calendar.getInstance();
		int hourNow = cal.get(Calendar.HOUR_OF_DAY);
		int minuteNow = cal.get(Calendar.MINUTE);
		int secondNow = cal.get(Calendar.SECOND);
		
		Boolean hourOk = hourNow<23;
		myHour = hourOk?myHour+1:0;
	}
	//如果弹出框点击的是yes，隔一分钟后再弹出
	private void setUnseen(){
		cal = Calendar.getInstance();
		
		int hourNow = cal.get(Calendar.HOUR_OF_DAY);
		int minuteNow = cal.get(Calendar.MINUTE);
		int secondNow = cal.get(Calendar.SECOND);
		
		Boolean minuteOk = minuteNow<59;
		if(minuteOk){
			
			myMinite++;
		}else{
			Boolean hourOk = hourNow<23;
			myHour = hourOk?myHour+1:0;
			myMinite=0;
		}
	}
	//如果弹出框点击的是右上角的叉，隔5秒后再弹出
	private void setClosed(){//秒的变化级联分和小时的变化，好麻烦啊！
		cal = Calendar.getInstance();
		
		int hourNow = cal.get(Calendar.HOUR_OF_DAY);
		int minuteNow = cal.get(Calendar.MINUTE);
		int secondNow = cal.get(Calendar.SECOND);
		
		Boolean secondOk = secondNow<59;
		if(secondOk){
			secondNow++;
		}else{
			Boolean minuteOk = minuteNow<59;
			if(minuteOk){
				myMinite++;
				mySecond=0;
			}else{
				Boolean hourOk = hourNow<23;
				myHour = hourOk?myHour+1:0;
				myMinite=0;
				mySecond=0;
			}
		}
	}
	
	public void run(){
		while(true){
			try {
				cal = Calendar.getInstance();
				y=cal.get(Calendar.YEAR);
				m=cal.get(Calendar.MONTH);
				d=cal.get(Calendar.DAY_OF_MONTH);
				h=cal.get(Calendar.HOUR_OF_DAY);
				mi=cal.get(Calendar.MINUTE);
				s=cal.get(Calendar.SECOND);
				System.out.println(toString());
				if(h==myHour&&mi==myMinite&&s==mySecond){
					
					int isSee = JOptionPane.showConfirmDialog(null, "时间到了！该休息了！许神温馨提醒！","Alarm", JOptionPane.YES_NO_OPTION);
					if(isSee==JOptionPane.YES_OPTION){
						setSeen();
					}else if(isSee==JOptionPane.NO_OPTION){
						JOptionPane.showMessageDialog(null, "二货，点什么否啊，1分钟后将再次提醒！");
						setUnseen();
					}else if(isSee==JOptionPane.CLOSED_OPTION){//为什么没有close的设置？
						setClosed();
					}
				}
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	public String toString(){//用于下面显示本地时间
		return String.format("%d year,%d month,%d day\n"+"%d :%d :%d ",y,m+1,d,h,mi,s);
	}
}
