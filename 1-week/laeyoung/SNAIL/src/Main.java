import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;



public class Main {
	private static final int MAX_WELL_DEPTH = 1000+1;
	private static final int MAX_DAYS = 1000+1;
	
	public static double[][] cache = new double[MAX_WELL_DEPTH][MAX_DAYS];
	public static double[] factorialCache = new double[MAX_WELL_DEPTH];

	
	public static void main(String args[]) throws FileNotFoundException {
//		Scanner sc = new Scanner(new File("input.txt"));
		Scanner sc = new Scanner(System.in);
		int cases = sc.nextInt();
		
		initCache(cache);
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
		
				
		return calculateProb(wellDepth, days);
	}
	
	public static double calculateProb(int depth, int days) {
		if (cache[depth][days] != -1) {
			return cache[depth][days];
		}
		
		// 종료 조건 체크.
		if (depth == 0) { // 탈출 성공!!
			return 1.0d;
		} else if (depth > days) { // 탈출 불가, 희망 고문...
			return 0.0d;
		}
			
		
		double caseRainy = 0.75d * calculateProb(depth - 1, days - 1);
		double caseNotRainy = 0.25d * calculateProb(depth, days - 1);;
		
		return cache[depth][days] = (caseRainy + caseNotRainy);
	}
	
	public static void initCache(double[][] cache) {
		for (int i = 0; i < MAX_WELL_DEPTH; i++) {
			for (int j = 0; j < MAX_DAYS; j++) {
				cache[i][j] = -1;
			}
		}
	}
}