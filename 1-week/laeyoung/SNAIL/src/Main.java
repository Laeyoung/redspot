import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;



public class Main {
	private static final int MAX_WELL_DEPTH = 1000+1;
	private static final int MAX_DAYS = 1000+1;
	
	public static long[][] cache = new long[MAX_WELL_DEPTH][MAX_DAYS];
	public static double[] factorialCache = new double[MAX_WELL_DEPTH];

	
	public static void main(String args[]) throws FileNotFoundException {
		Scanner sc = new Scanner(new File("input.txt"));
//		Scanner sc = new Scanner(System.in);
		int cases = sc.nextInt();
		
//		for (int i=1000; i>0; i--) {
//			double prob = Math.pow(3d/4d, i) / Math.pow(4d, (1000 - i));
//			System.out.println(i + ": " + prob);
//		}
		
		initCache(cache);
		initFactorialCache(factorialCache);
		
		
		for (int i = 1000; i > 501; i--) {
			double prob = numNRainyDayCase(500, i);;
			System.out.println(i + ": " + prob);
		}
		
		
		while (cases-- > 0) {
			int wellDepth = sc.nextInt(); // 우물 깊이
			int days = sc.nextInt(); // 장마철 기간
			
			double result = findProb(wellDepth, days);
			
			System.out.printf("%.10f\n", result);
		}
		
		sc.close();
	}
	
	
	/**
	 * 문제를 간단하게 바꾼다.
	 * 1. 매일 하루씩은 무조건 1m씩 올라가므로, 실제 올라가야 할 우물의 깊이는 (depth - days)
	 * 2. 그렇게 깊이를 바꾼 후, 비가 오면 1m 올라가고 안오면 제자리에 있는 걸로 문제를 변환함.
	 */
	public static double findProb(int wellDepth, int days) {
		// 예전에 계산되어서 cache에 저장되어 있는 값이 있는지 확인.
		if (cache[wellDepth][days] != -1) {
			return cache[wellDepth][days];
		}
		
		// 비가 오면 1m, 안오면 0m로 변환하고 우물의 높이는 해당 경우에 맞게 재조정.
		wellDepth -= days;
		
		// 계산을 안해도 답을 알 수 있는 경우 처리.
		if (wellDepth <= 0) { // 올라가지 않아도 될 경우, 탈출 확률은 100%
			return 1.0D;
		} else if (wellDepth > days) {
			return 0.0D;
		}
		
				
		return numSuccessCase(wellDepth, days);
	}
	
	public static double numSuccessCase(int depth, int days) {
		double posibleCases = 0d; // 통과 할 때의 경우의 수.
		
		
		// 비가 depth일 만큼 올 때부터, 모든날 비가 오는 경우까지의 경우의 수를 더함.
		// 경우의 수에 해당 경우가 나올 확률을 곱함.
		for (int i = depth; i <= days; i++) {
			/*
			 * days가 512가 초과할 경우, Math.pow(4, days)가 Double.INFINITY를 초과해서,
			 * 나중에 allCase로 나눠줘서 계산하지 않고 확률값을 더하는 부분에서 바로바로 계산함.
			 * 
			 * Math.pow(3, i) / Math.pow(4, days) => Math.pow(3/4, i) / Math.pow(4, (days-i)
			 */
			double probability = Math.pow(3d/4d, i) / Math.pow(4d, (days - i));
			
			
			posibleCases += numNRainyDayCase(i, days) * probability;
			
			System.out.println("probability : " + probability);
			System.out.println(">> numNRainyDayCase(i, days) : " + numNRainyDayCase(i, days));
		}
		
		return posibleCases;
	}
	
	
	/**
	 * 해당 days 기간동안 N-rainyDay 일 경우의 수를 반환함. (day Combination rainyDay)
	 */
	public static double numNRainyDayCase(int rainyDay, int days) {
		double a = 1.0d;
		for (int i = days; i > rainyDay; i--) {
			a *= i;
		}
		
		double b = factorial (days-rainyDay);
		
		return a / b;
	}
	
	
	/**
	 * integer 범위가 넘어갈 수 있으므로, double 타입으로 return.
	 */
	public static double factorial(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("n must be >= 0");
		}
		
		// cache에 있는지 확인.
		if (factorialCache[n] != -1) {
			return factorialCache[n];
		}
		
		double factorial = 1;
		for (int i = 2; i <= n; i++) {
			factorial *= i;
		}
		
		return factorialCache[n] = factorial;
	}
	
	
	public static void initCache(long[][] cache) {
		for (int i = 0; i < MAX_WELL_DEPTH; i++) {
			for (int j = 0; j < MAX_DAYS; j++) {
				cache[i][j] = -1;
			}
		}
	}
	
	public static void initFactorialCache(double[] cache) {
		for (int i = 0; i < MAX_WELL_DEPTH; i++) {
			cache[i] = -1;
		}
	}
}