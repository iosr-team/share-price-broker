package pl.edu.agh.iosr.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.util.StopWatch;

public class AmqpPerformanceTest {
	private static final String PERFORMANCE_TEST_QUEUE_NAME = "test";
	private static final String PERFORMANCE_TEST_ROUTING_KEY = "test";
	
	AmqpTemplate template;
	public AmqpPerformanceTest(AmqpTemplate template) {
		this.template = template;
	}
	
	private long single(int n) {
		StopWatch sw = new StopWatch();
    	sw.start();
    	
    	for(int i=0; i<n; ++i) {
    		template.convertAndSend(PERFORMANCE_TEST_QUEUE_NAME, "message");
    		template.receiveAndConvert(PERFORMANCE_TEST_ROUTING_KEY);
    	}
    	
    	sw.stop();
    	return sw.getTotalTimeMillis();
	}
	
    public TestResult testNum(int n) {
    	long time = Math.min(single(n), Math.min(single(n), single(n)));
    	return new TestResult(n, time);
    }
}
