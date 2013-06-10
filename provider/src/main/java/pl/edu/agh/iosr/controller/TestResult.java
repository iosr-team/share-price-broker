package pl.edu.agh.iosr.controller;

public class TestResult {
	private int num;
	private long time;
	
	public TestResult(int num, long time) {
		this.num = num;
		this.time = time;
	}
	
	public int getNum() {
		return num;
	}
	
	public long getTime() {
		return time;
	}
}
