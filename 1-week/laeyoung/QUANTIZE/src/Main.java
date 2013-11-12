import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class Main {
	private static final int MAX_LIST = 100;
	private static final int MAX_QUANTIZE = 10;
	private static int[][][] cache = new int[MAX_LIST][MAX_LIST][MAX_QUANTIZE];

	public static void main(String args[]) throws FileNotFoundException {
		Scanner sc = new Scanner(new File("input.txt"));
//		Scanner sc = new Scanner(System.in);
		int cases = sc.nextInt();
		
		while (cases-- > 0) {
			// input.txt에서 필요한 parameter 값 읽기.
			int length = sc.nextInt();
			int numDivider = sc.nextInt();
			
			List<Integer> numberList = new ArrayList<Integer>();
			for (int i = 0; i < length; i++) {
				numberList.add(sc.nextInt());
			}
			
			
			Collections.sort(numberList);
			
			
			// magic(-stick) function
			int minimumSum = calculateMinimumSum(numberList, numDivider);
			
			// calculate sum of difference^2
			System.out.println(minimumSum);
		}
	}
	
	public static int calculateMinimumSum(List<Integer> numberList, int numDivider) {
		int minimumSum = Integer.MAX_VALUE;
		int minNum = numberList.get(0);
		int maxNum = numberList.get(numberList.size() - 1);
		
		initCache(cache);
		

		// 최소값과 최대값 사이의 숫자들을 최초의 양자화 숫자로 정하고 이 중에 최소값을 찾아냄.
		for (int posDivider = minNum; posDivider <= maxNum; posDivider++) {
			int localMinimumSum = Integer.MAX_VALUE;
			
			// 해당 양자화 숫자가 정해졌을 때, 배열이 나눠지는 지점을 찾아냄.
			// 무조건 나눠져야 하므로, 2번째 구역이 시작되는 위치는 1부터 시작됨.
			int secondHalfStart = 1;
			for (int i = 1; i < numberList.size(); i++) {
				if (numberList.get(i) > posDivider) {
					secondHalfStart = i;
					break;
				}
			}
			
			
			// 남은 양자화 숫자를 fisrt half와 second half쪽에 줄 수 있는 모든 가능성의 수를 계산함.
			// first와 second 둘 다 최소 1 이상의 divider를 가져야함. 
			for (int i = 1; i < numDivider; i++) {
				int sum = divisionMinimumSum(numberList, i, 0, secondHalfStart-1)
							+ divisionMinimumSum(numberList, numDivider - i, secondHalfStart, numberList.size()-1);
				
				// 새로 구한 값이 localMinimumSum 보다 작으면 값을 update.
				if (sum < localMinimumSum) {
					localMinimumSum = sum;
				}
			}
			
			
			// 새로 localMinimumSum이 minimumSum보다 작으면 업데이트.
			if (localMinimumSum < minimumSum) {
				minimumSum = localMinimumSum;
			}
		}
		
		
		return minimumSum;
	}
		
	public static int divisionMinimumSum(List<Integer> numberList, int numDivider, int startOffset, int endOffset) {
		// 발화식 종료 조건 체크.
		if (numDivider == 0) {
			new Exception().printStackTrace();
		}
		if (numDivider == 1) {
			List<Integer> subList = numberList.subList(startOffset, endOffset+1);
			return getRoundAverage(subList);
		}
		
		
		// cache에 기존에 계산한 결과가 있을 경우, 예전 값을 그대로 return.
		if (cache[startOffset][endOffset][numDivider] != -1) {
			return cache[startOffset][endOffset][numDivider];
		}
		

		
		// 기존의 발화식대로, minimumSum을 계산. (calculateMinimumSum와 유사).
		int minimumSum = Integer.MAX_VALUE;
		int minNum = numberList.get(startOffset);
		int maxNum = numberList.get(endOffset);
		
		for (int posDivider = minNum; posDivider <= maxNum; posDivider++) {
			int localMinimumSum = Integer.MAX_VALUE;
			
			int secondHalfStart = 1;
			for (int i = 1; i < numberList.size(); i++) {
				if (numberList.get(i) > posDivider) {
					secondHalfStart = i;
					break;
				}
			}
			
			
			for (int i = 1; i < numDivider; i++) {
				int sum = divisionMinimumSum(numberList, i, 0, secondHalfStart-1)
							+ divisionMinimumSum(numberList, numDivider - i, secondHalfStart, numberList.size()-1);
				
				// 새로 구한 값이 localMinimumSum 보다 작으면 값을 update.
				if (sum < localMinimumSum) {
					localMinimumSum = sum;
				}
			}
			
			
			// 새로 localMinimumSum이 minimumSum보다 작으면 업데이트.
			if (localMinimumSum < minimumSum) {
				minimumSum = localMinimumSum;
			}
		}
		

		// cache에 결과 저장.
		cache[startOffset][endOffset][numDivider] = minimumSum;

		
		return minimumSum;
	}
	
	public static void initCache(int[][][] cache) {
		for (int i = 0; i < MAX_LIST; i++) {
			for (int j = 0; j < MAX_LIST; j++) {
				for (int k = 0; k < MAX_QUANTIZE; k++) {
					cache[i][j][k] = -1;
				}
			}
		}
	}
	
	public static int getRoundAverage(List<Integer> list) {
		int sum = 0;
		for (Integer integer : list) {
			sum += integer;
		}
		
		return Math.round( ((float)sum) / list.size() );
	}
}