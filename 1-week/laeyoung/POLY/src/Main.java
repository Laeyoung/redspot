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
	
	private static int resultCounter = 0;
	
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
		
		while (cases-- > 0) {
			// input.txt에서 필요한 parameter 값 읽기.
			int numSquare = sc.nextInt();
			
			resultCounter = 0;
			
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
	
	public static void findPoly(int numPoly, int[] previousBlockPos) {
		
	}
	
}