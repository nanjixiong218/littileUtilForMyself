package com.test.clock;

public class Test {
	 public static void main(String[] args) {
	        hello h1=new hello("A");
	        hello h2=new hello("B");
	        h1.run();
	        h2.run();
	    }
}
class Father {
	protected String name;
	public Father(){
		this.name = "xu";
	}
}
class Child extends Father{
	public Child(){
		this.name="hui";
	}
}
class hello extends Thread {
	private String name;
    public hello() {
 
    }
 
    public hello(String name) {
        this.name = name;
    }
 
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(name + "ÔËÐÐ     " + i);
        }
    }
 
   
 
   
}
