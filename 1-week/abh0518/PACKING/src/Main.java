import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
	
	private String[] luggage;
	private int size[];
	private int need[];
	private Map<String, boolean[]> cache = new HashMap<String, boolean[]>();
	
	public Main(String[] luggage, int size[], int need[]){
		this.luggage = luggage;
		this.size = size;
		this.need = need;
	}
	
	public boolean[] pack(int backSize){
		boolean[] selectList = new boolean[luggage.length];
		Arrays.fill(selectList, false);
		return pack(selectList, backSize);
	}
	
	public String getKey(boolean[] luggageList, int backSize){
		return Arrays.toString(luggageList)+backSize;
	}
	
	public boolean[] pack(boolean[] selectList, int backSize){
		String key = getKey(selectList, backSize);
		if(cache.get(key) != null) return cache.get(key);
		
		boolean[] result = Arrays.copyOf(selectList, selectList.length);
		
		for(int i = 0 ; i < luggage.length ; i++){
			if( backSize-size[i] >= 0 && selectList[i] == false){
				boolean[] nSelectList = Arrays.copyOf(selectList, selectList.length);
				nSelectList[i] = true;
				nSelectList = pack(nSelectList, backSize-size[i]);
				result = getMaxSelect(result, nSelectList);
			}
		}
		
		cache.put(key, result);
		return result;
	}
	
	private boolean[] getMaxSelect(boolean[] aSelect, boolean[] bSelect){
		
		int sumA = 0;
		for(int i = 0 ; i < aSelect.length ; i++){
			if(aSelect[i] == true) sumA += need[i];
		}
		
		int sumB = 0;
		for(int i = 0 ; i < bSelect.length ; i++){
			if(bSelect[i] == true) sumB += need[i];
		}
		
		return sumA > sumB ? aSelect : bSelect; 
	}
	
	
	
	public static void main(String args[]) throws FileNotFoundException{
		
		Scanner in = new Scanner(new File("test.txt"));
		//Scanner in = new Scanner(System.in);
		
		int testCount = in.nextInt();
		
		for(int i = 0 ; i < testCount ; i++){
			int luggageCount = in.nextInt();
			int backSize = in.nextInt();
			
			String[] luggageName = new String[luggageCount];
			int[] size = new int[luggageCount];
			int[] need = new int[luggageCount];
			in.nextLine();
			for(int j = 0 ; j < luggageCount ; j++){
				String str = in.nextLine();
				StringTokenizer tk = new StringTokenizer(str," ");
				luggageName[j] = tk.nextToken();
				size[j] = Integer.parseInt(tk.nextToken());
				need[j] = Integer.parseInt(tk.nextToken());
				
			}
			
			Main main = new Main(luggageName,size,need);
			boolean selectList[] = main.pack(backSize);
			
			int needSum = 0;
			int packCount = 0;
			for(int j = 0 ; j < selectList.length ; j++){
				if(selectList[j] == true) {
					needSum += need[j];
					packCount++;
				}
			}
			System.out.println(needSum + " " + packCount);
			
			for(int j = 0 ; j < selectList.length ; j++){
				if(selectList[j] == true) System.out.println(luggageName[j]);
			}
	
		}		
	}

}

