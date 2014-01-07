import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Main {
	private static final int MAX_MATERIAL = 200;
	private static final int MAX_MATERIAL_AMOUNT = 1000+1;
	private static final int MAX_PRE_INPUT_AMOUNT = 1000+1;
	
	// 이전 블럭의 수와 현재 블록의 수에 따라서 결과가 정해지므로, 결과를 저장하는 cache는 아래와 같이 정의됨.
	public static int numMaterial;
	public static int[] materials = new int[MAX_MATERIAL];
	public static int[] preMaterials = new int[MAX_MATERIAL];
	public static int[] postMaterials = new int[MAX_MATERIAL];
	
	public static int[][] gcdCache = new int[MAX_MATERIAL_AMOUNT][MAX_MATERIAL_AMOUNT];
	public static long[][] lcmCache = new long[MAX_MATERIAL_AMOUNT][MAX_MATERIAL_AMOUNT];
	
	public static void main(String args[]) throws FileNotFoundException {
		Scanner sc = new Scanner(new File("input.txt"));
//		Scanner sc = new Scanner(System.in);
		int cases = sc.nextInt();
		
		initCache();
		while (cases-- > 0) {
			initArray();
			
			// 현재 재료의 숫자.
			numMaterial = sc.nextInt();
			// 약에 들어가야 하는 각 재료의 양
			for (int i=0; i < numMaterial; i++) {
				materials[i] = sc.nextInt();
			}
			// 현재 이미 들어간 재료의 양. 
			for (int i=0; i < numMaterial; i++) {
				preMaterials[i] = sc.nextInt();
			}
			
			
			System.out.println("LCM: " + materialsLcm());

//			// 만들어야 하는 최소의 병수를 구한다. (ex. 한 재료를 1.5병 분량을 넣었다면 최소 2병은 만들어 져야함.)
//			int minBottle = getMinBottle();
//			// 해당 병수를 만들기 위해 필요한 재료의 양을 materials array에 저장한다.
//			multiplyMaterial(minBottle);
//			// 추가로 넣어야 할 재료를 계산해서 postMaterials array에 저장한다.
//			fillPostMaterial();
//			
//			// 결과 출력!! 킹왕짱.
//			for (int i = 0; i < numMaterial; i++) {
//				System.out.print(postMaterials[i] + " ");
//			}
//			System.out.println("");
		}
		
		sc.close();
	}

	public static int getMinBottle() {
		int minBottle = 1; // 최소값.
		for (int i = 0; i < numMaterial; i++) {
			int n = (int) Math.ceil((double) preMaterials[i] / (double) materials[i]);
			
			if (n > minBottle) {
				minBottle = n;
			}
		}
		
		return minBottle;
	}
	
	public static void multiplyMaterial(int minBottle) {
		for (int i = 0; i < numMaterial; i++) {
			materials[i] = materials[i] * minBottle;
		}
	}
	
	public static void fillPostMaterial() {
		for (int i = 0; i < numMaterial; i++) {
			postMaterials[i] = materials[i] - preMaterials[i];
		}
	}
	
	public static void initArray() {
		for (int i = 0; i < MAX_MATERIAL; i++) {
			materials[i] = -1;
			preMaterials[i] = -1;
			postMaterials[i] = -1;
		}
	}
	
	public static long materialsLcm() {
		long lcm = materials[0];
		
		for (int i=0; i<numMaterial; i++) {
			lcm = lcm(materials[i], lcm);
		}
		
		return lcm;
	}
	
	public static long lcm(long a, long b) {
		int gcd_value = gcd((int) a, (int) b);

		if (gcd_value == 0) {
			return 0; // 인수가 둘다 0일 때의 에러 처리
		}

		return Math.abs((a * b) / gcd_value);
	}
	
	public static int gcd(int p, int q) {
		if (gcdCache[p][q] != -1) {
			return gcdCache[p][q];
		}
		
		int gcd = -1;
		if (q==0) {
			gcd = p;
		} else {
			gcd = gcd(q, p % q);
		}
		
		if (gcdCache[p][q] == -1) {
			gcdCache[p][q] = gcd;
		}
		return gcd;
	}
	
	public static void initCache() {
		for (int i = 0; i < MAX_MATERIAL_AMOUNT; i++) {
			for (int j = 0; j < MAX_MATERIAL_AMOUNT; j++) {
				gcdCache[i][j] = -1;
				lcmCache[i][j] = -1L;
			}
		}
	}
}