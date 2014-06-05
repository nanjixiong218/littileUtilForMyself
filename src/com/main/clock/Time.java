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
		System.out.println("�����õ�һ�����ѵ�,��ʱ��ᷢ�����ѣ��Ժ�ÿ��һ��Сʱ����һ�Σ�������������ѵ�ʱ������Ļ��Ͳ�ͬ�ˣ�");
		System.out.print("Сʱ��24Сʱ�ƣ���");
		Scanner cin = new Scanner(System.in);
		h=cin.nextInt();
		System.out.print("���ӣ�");
		mi=cin.nextInt();
		System.out.print("�룺");
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
	//���������������yes����һ��Сʱ���ٵ���
	private void setSeen(){
		
		cal = Calendar.getInstance();
		int hourNow = cal.get(Calendar.HOUR_OF_DAY);
		int minuteNow = cal.get(Calendar.MINUTE);
		int secondNow = cal.get(Calendar.SECOND);
		
		Boolean hourOk = hourNow<23;
		myHour = hourOk?myHour+1:0;
	}
	//���������������yes����һ���Ӻ��ٵ���
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
	//������������������ϽǵĲ棬��5����ٵ���
	private void setClosed(){//��ı仯�����ֺ�Сʱ�ı仯�����鷳����
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
					
					int isSee = JOptionPane.showConfirmDialog(null, "ʱ�䵽�ˣ�����Ϣ�ˣ�������ܰ���ѣ�","Alarm", JOptionPane.YES_NO_OPTION);
					if(isSee==JOptionPane.YES_OPTION){
						setSeen();
					}else if(isSee==JOptionPane.NO_OPTION){
						JOptionPane.showMessageDialog(null, "��������ʲô�񰡣�1���Ӻ��ٴ����ѣ�");
						setUnseen();
					}else if(isSee==JOptionPane.CLOSED_OPTION){//Ϊʲôû��close�����ã�
						setClosed();
					}
				}
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	public String toString(){//����������ʾ����ʱ��
		return String.format("%d year,%d month,%d day\n"+"%d :%d :%d ",y,m+1,d,h,mi,s);
	}
}
