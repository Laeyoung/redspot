import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;



public class Main {
	private static final int MAX_LENGTH = 200 * 1000;
	private static final int MAX_INT_LENGTH = 6250;
	
	// 1bit에 1사람에 대한 성별 정보를 담음(따라서 사이즈는 200,000/32해서 6250). 남자는 1, 여자는 0.
	private static int[] memberArray = new int[MAX_INT_LENGTH];
	private static int[] fanArray = new int[MAX_INT_LENGTH];
	
	private static int MALE_BIT = 0x0;
	private static int FEMALE_BIT = 0x1;
	

	public static void main(String args[]) throws FileNotFoundException {
		Scanner sc = new Scanner(new File("input.txt"));
//		Scanner sc = new Scanner(System.in);
		int cases = sc.nextInt();
		
		while (cases-- > 0) {
			// input.txt에서 필요한 parameter 값 읽기.
			String memberStr = sc.nextLine();
			String fanStr = sc.nextLine();
			
			
			
			numberList = new ArrayList<Integer>();
			for (int i = 0; i < length; i++) {
				numberList.add(sc.nextInt());
			}
			
			
			Collections.sort(numberList);
			
			
			// magic(-stick) function
			int minimumSum = calculateMinimumSum(numDivider);
			
			// calculate sum of difference^2
			System.out.println(minimumSum);
		}
		
		sc.close();
	}
	
	public static int calculateMinimumSum(int numDivider) {
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
				int sum = divisionMinimumSum(i, 0, secondHalfStart-1, NO_BEGIN_DIVIER, posDivider)
							+ divisionMinimumSum(numDivider - i, secondHalfStart, numberList.size()-1, posDivider, NO_LAST_DIVIER);
				
				// 새로 구한 값이 localMinimumSum 보다 작으면 값을 update.
				if (sum < localMinimumSum && sum >= 0) {
					localMinimumSum = sum;
					System.out.println("localMinimumSum: " + localMinimumSum);
				}
			}
			
			
			// 새로 localMinimumSum이 minimumSum보다 작으면 업데이트.
			if (localMinimumSum < minimumSum) {
				minimumSum = localMinimumSum;
				System.out.println("minimumSum: " + minimumSum);
			}
		}
		
		
		return minimumSum;
	}
	
	/**
	 * 분할 되었을 때의 최소값을 구하는 method
	 * 
	 * @param numberList
	 * @param numDivider
	 * @param startOffset
	 * @param endOffset
	 * @param beginDivider 제일 앞에 Dividier의 위치 값. 없었으면 NO_BEGIN_DIVIDER. 
	 * @param lastDividier 제일 뒤에 있는 Dividier의 위치 값. 없었으면 NO_LAST_DIVIDER.
	 * @return
	 */
	public static int divisionMinimumSum(int numDivider, int startOffset, int endOffset, int beginDivider, int lastDividier) {
		// 발화식 종료 조건 체크.
		if (numDivider == 0) {
			new Exception().printStackTrace();
		}
		if (numDivider == 1) {
			List<Integer> subList = numberList.subList(startOffset, endOffset+1);
			return getRoundAverage(subList, beginDivider, lastDividier);
		}
		
		
		// cache에 기존에 계산한 결과가 있을 경우, 예전 값을 그대로 return.
		if (cache[beginDivider][lastDividier][numDivider] != -1) {
			return cache[beginDivider][lastDividier][numDivider];
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
				int sum = divisionMinimumSum(i, 0, secondHalfStart-1, beginDivider, posDivider)
							+ divisionMinimumSum(numDivider - i, secondHalfStart, numberList.size()-1, posDivider, lastDividier);
				
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
		cache[beginDivider][lastDividier][numDivider] = minimumSum;

		
		return minimumSum;
	}
	
	public static void initCache(int[][][] cache) {
		for (int i = 0; i < MAX_NUM; i++) {
			for (int j = 0; j < MAX_NUM; j++) {
				for (int k = 0; k < MAX_QUANTIZE; k++) {
					cache[i][j][k] = -1;
				}
			}
		}
	}
	
	public static int getRoundAverage(List<Integer> list, int beginDivider, int lastDividier) {
		int resultPosition = 0;
		int minSum = Integer.MAX_VALUE;
		int start;
		int end;
		
		if (beginDivider == NO_BEGIN_DIVIER) {
			start = 0;
			beginDivider = Integer.MAX_VALUE;
		} else {
			start = beginDivider;
		}
		
		if (lastDividier == NO_LAST_DIVIER) {
			end = 1000;
			lastDividier = Integer.MAX_VALUE;
		} else {
			end = lastDividier;
		}
		
		
		for (int i = start; i <= end; i++) {
			int sum = 0;
			for (Integer integer : list) {
				sum += getMin(integer, start, i, end);
			}
			
			
			if (sum < minSum) {
				minSum = sum;
				resultPosition = i;
			}
		}
		
		return minSum;
	}
	
	public static int getMin(int integer, int start, int i, int end) {
		int a = Math.abs(start - integer);
		int b = Math.abs(i - integer);
		int c = Math.abs(end - integer);
		
		if (a <= b && a <= c) {
			return a;
		} else if (b <= a && b <= c) {
			return b;
		} else {
			return c;
		}
	}
	
	public static void convertStringToIntBit(String str, int[] target) {
		int strIndex = str.length();
		int targetReverseIndex = 1;
		int intBitCounter = 0;
		
		while (strIndex-- > 0) {
			if (str.charAt(strIndex) == 'M') {
				target[MAX_INT_LENGTH - j] |= MALE_BIT;
				target[MAX_INT_LENGTH - j] = target[MAX_INT_LENGTH - j] << 1;
			} else {
				target[MAX_INT_LENGTH - j] |= FEMALE_BIT;
				target[MAX_INT_LENGTH - j] = target[MAX_INT_LENGTH - j] << 1;
			}
					
			targetReverseIndex++;
			intBitCounter++;
			
			if (intBitCounter == 32) {
				intBitCounter = 0;
			}
		}
		
	}
}