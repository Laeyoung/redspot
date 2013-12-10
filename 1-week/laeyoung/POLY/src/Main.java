import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;



public class Main {
	private static final int MAX_SQUARE = 100;
	private static final int INTEGER_MAX_BOUND = 10000000;
	
	// 이전 블럭의 수와 현재 블록의 수에 따라서 결과가 정해지므로, 결과를 저장하는 cache는 아래와 같이 정의됨.
	public static double[][] cache = new double[MAX_SQUARE][MAX_SQUARE];  

	
	/*
	 * BitSet
	 * http://www.java-samples.com/showtutorial.php?tutorialid=378
	 * 
	 * Sparse Array
	 * 
	 */

	public static void main(String args[]) throws FileNotFoundException {
		Scanner sc = new Scanner(new File("input.txt"));
//		Scanner sc = new Scanner(System.in);
		int cases = sc.nextInt();
		
		initCache(cache);
		while (cases-- > 0) {
			// input.txt에서 필요한 parameter 값 읽기.
			int numSquare = sc.nextInt();
			
			double result = findPoly(numSquare, 0);
			
			int intResult = 0;
			if (result < INTEGER_MAX_BOUND) {
				intResult = (int) Math.round(result);
			} else {
				intResult = (int) Math.round(result / INTEGER_MAX_BOUND);
			}
			
			System.out.println(intResult);
		}
		
		sc.close();
	}
	
	
	/**
	 * 1. 이전 줄에 있는 블럭은 무조건 문제의 조건을 통과했기 때문에, 무조건 한줄로 배치 될 수 밖에 없음.
	 * 2. 이번 줄에 놓일 블럭의 갯수를 알면, 거기서 가능한 경우의 수가 나옴.
	 *    경우의 수는 (이전 블럭의 갯수 + 놓일 블럭 수 -1)
	 * 3. 매 처음 시작 일 때는 numPrevBlock이 0 값이 들어온 채로 시작.
	 *    해당 경우, 경우의 수(casePosibility)는 항상 1임.
	 */
	public static double findPoly(int numRemainSquare, int numPrevBlock) {
		// 예전에 계산되어서 cache에 저장되어 있는 값이 있는지 확인.
		if (cache[numRemainSquare][numPrevBlock] >= 0) {
			return cache[numRemainSquare][numPrevBlock];
		}
		
		
		
		// 종료 조건 체크.
		// 남은 블럭이 없으므로, 경우의 수가 하나라 1을 return함.
		if (numRemainSquare == 0) {
			return cache[numRemainSquare][numPrevBlock] = 1;
		}
		
		double resultSum = 0;
		// 무조건 한개의 블럭의 놓아야 하므로 1부터 시작함.
		for(int i = 1; i <= numRemainSquare; i++) {
			// i개이 블록의 놓기로 정해져 있을 때, 놓을 수 있는 경우의 수.
			int casePosibility;
			if (numPrevBlock > 0) {
				casePosibility = numPrevBlock + i - 1;
			} else {
				casePosibility = 1;
			}
			
			// 현재 경우의 수 * 하위 경우의 수를 하면, 현재 상황에서의 총 경우의 수가 된다.
			resultSum += casePosibility * findPoly(numRemainSquare-i, i);
		}
		
		
		return cache[numRemainSquare][numPrevBlock] = resultSum; 
	}
	
	
	public static void initCache(double[][] cache) {
		for (int i = 0; i < MAX_SQUARE; i++) {
			for (int j = 0; j < MAX_SQUARE; j++) {
				cache[i][j] = -1;
			}
		}
	}
}