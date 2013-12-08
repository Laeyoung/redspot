import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;



public class Main {
	private static final int MAX_LENGTH = 200 * 1000;
	private static final int MAX_INT_LENGTH = 6250;
	private static final int INTEGER_BIT_LENGTH = 32; 
	
	// 1bit에 1사람에 대한 성별 정보를 담음(따라서 사이즈는 200,000/32해서 6250). 남자는 1, 여자는 0.
	private static int[] memberArray = new int[MAX_INT_LENGTH];
	private static int[] fanArray = new int[MAX_INT_LENGTH];
	private static int[][] fanArrayTemp = new int[INTEGER_BIT_LENGTH][MAX_INT_LENGTH]; // bit shift 시켜놓은 값을 저장해놓음.
	
	private static int MALE_BIT = 0x1;
	private static int FEMALE_BIT = 0x0;
	

	public static void main(String args[]) throws FileNotFoundException {
		Scanner sc = new Scanner(new File("input.txt"));
//		Scanner sc = new Scanner(System.in);
		int cases = sc.nextInt();
		
		while (cases-- > 0) {
			// input.txt에서 필요한 parameter 값 읽기.
			String memberStr = sc.next();
			String fanStr = sc.next();
			
			int intMemberLength = (int) Math.floor(memberStr.length() / (double) INTEGER_BIT_LENGTH);
			int intFanLength = (int) Math.floor(memberStr.length() / (double) INTEGER_BIT_LENGTH);
			
			System.out.println(intMemberLength);
			System.out.println(intFanLength);
			
			// 변수를 0으로 초기화. 
			initIntBit(memberArray);
			initIntBit(fanArray);
			
			// Male, Female 변수를 integer에 bit 값으로 저장함.
			convertStringToIntBit(memberStr, memberArray);
			convertStringToIntBit(fanStr, fanArray);
			
			bitShiftedArray(fanArray, fanArrayTemp);
			
			// 결과 계산.
			calculateResult(memberArray, fanArrayTemp, memberStr.length(), fanStr.length(), intFanLength);
		}
		
		sc.close();
	}
	
	public static void calculateResult(int[] member, int[][] fan, int numMember, int numFan, int intFanLength) {
		int shiftCounter = 0;
		int indexPointer = 0; 
		
		int resultCounter = 0;
		boolean isCorrectMatching = true;
		
		for (int i=0; numFan-i<= numMember; i++) { // 남은 fan의 수가 member보다 작으면 종료.
			for (int j = 0; j < intFanLength; j++) {
				// memeber와 fan 사이에 남남이 만나는 경우가 있으면, bit 연산자 값이 0이 아닌 값이 됨.
				if ( (member[j] & fan[shiftCounter][j+indexPointer]) != 0) { 
					isCorrectMatching = false;
					break;
				}
			}
			
			if (isCorrectMatching) {
				resultCounter++;
			}
			
			shiftCounter++;
			if (shiftCounter == INTEGER_BIT_LENGTH) {
				shiftCounter = 0;
				indexPointer++;
			}
		}
		
		System.out.println(resultCounter);
	}
	
	
	public static void initIntBit(int[] target) {
		for (int i=0; i<target.length; i++) {
			target[i] = 0;
		}
	}
	
	
	// Shift 된 값을 가지고 있는 target array를 만든다.
	public static void bitShiftedArray(int[] origin, int[][] target) {
		for (int j=0; j< MAX_INT_LENGTH; j++) {
			target[0][j] = origin[j];
		}
		
		
		for (int i=1; i< INTEGER_BIT_LENGTH; i++) {
			for (int j=0; j< MAX_INT_LENGTH; j++) {
				target[i][j] = target[i-1][j] << 1;
				
				if( (target[i-1][j] & MALE_BIT) == 1) {
					target[i][j] |= MALE_BIT;
				}
			}
		}
	}
	
	public static void convertStringToIntBit(String str, int[] target) {
		int arrayCounter = 0;
		int intBitCounter = 0;
		
		for (int i=0; i<str.length(); i++) {
			target[arrayCounter] = target[arrayCounter] << 1;
			
			if (str.charAt(i) == 'M') {
				target[arrayCounter] |= MALE_BIT;
			}
					
			intBitCounter++;
			
			if (intBitCounter == 32) {
				intBitCounter = 0;
				arrayCounter++;
			}
		}
	}
}