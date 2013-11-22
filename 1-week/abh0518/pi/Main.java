import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	
	static class Pi {

		int[] numbers;
		Integer[] resultCache;
		
		public Pi(int[] numbers){
			this.numbers = numbers;
			resultCache = new Integer[numbers.length];
		}
		
		public int start(){
			return find(0);
		}
		
		private int find(int start){
			if( start >= numbers.length ) return 0;
			if(resultCache[start] != null) return resultCache[start];
			
			int result = 987654321;
			
			for(int i = 2 ; i < 5 ; i++){
				int end = start+i;
				if(end < numbers.length){
					int score = score(start, end) + find(end+1);
					result = Math.min(result, score);
				}
			}
				
			resultCache[start] = result;
			return resultCache[start];
		}
		
		private int score(int start, int end){
			if (isRepeat(start,end)){
				return 1;
			}
			else if(isOneProgression(start, end)){
				return 2;
			}
			else if(isRotation(start,end)){
				return 4;
			}
			else if(isNProgression(start, end)){
				return 5;
			}
			return 10;
		}
		
		private boolean isRepeat(int start, int end){
			for(int i = start ; i <= end ; i++ ){
				 if (numbers[i] != numbers[start] ){
					 return false;
				 }
			}
			return true;
		}
		
		private boolean isOneProgression(int start, int end){
			for(int i = start+1 ; i <= end ; i++ ){
				if (numbers[i] != numbers[i-1] + 1 ){
					return false;
				}
			}
			return true;
		}
		
		private boolean isRotation(int start, int end){
			for(int i = start; i <= end ; i=i+2){
				if (numbers[i] != numbers[start]){
				    return false;
				}
			}
			for(int i = start+1; i <= end ; i=i+2){
				if (numbers[i] != numbers[start+1]){
				    return false;
				}
			}
			return true;
		}
		
		private boolean isNProgression(int start, int end){
			int n = numbers[start+1] - numbers[start];
			if (n == 0) return false;
			for(int i = start+1 ; i <= end ; i++ ){
				if (numbers[i] != numbers[i-1] + n ){
					 return false;
				}
			}
			return true;
		}	
	}

	
	
	public static void main(String args[]) throws IOException{
		
//		Random rand = new Random();
//		int count = 50;
//		int result[] = new int[count];
//		
//		Date start = new Date();
//		
//		for(int i = 0 ; i < count ; i++){
//			int datasize = 10000;
//			int data[] = new int[datasize];
//			for(int j = 0 ; j < datasize; j++){
//				data[j] = rand.nextInt(9)+1;
//				
//			}
//			
//			result[i] = new Pi(data).start();
//		}
//		
//		Date end = new Date();
//		
//		System.out.println("Time : " +  (end.getTime() - start.getTime()));
		
		
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		String input = reader.readLine();
		int count = Integer.parseInt(input);
		System.out.println(count);
		int result[] = new int[count];
		
		for(int i = 0 ; i < count ; i++){
			String data_str = reader.readLine();
			System.out.println(data_str);
			int data[] = new int[data_str.length()];
			
			for(int j = 0 ;j < data_str.length() ; j++){
				data[j] = Character.getNumericValue(data_str.charAt(j));
			}
			result[i] = new Pi(data).start();
		}
	
		for(int i = 0 ; i < result.length ; i++){
			System.out.println(result[i]);
		}
		
		
		
//		Pi p1 = new Pi(new int[]{1,2,3,4,1,2,3,4});
//		Pi p2 = new Pi(new int[]{1,1,1,1,1,2,2,2});
//		Pi p3 = new Pi(new int[]{1,2,1,2,2,2,2,2});
//		Pi p4 = new Pi(new int[]{2,2,2,2,2,2,2,2});
//		Pi p5 = new Pi(new int[]{1,2,6,7,3,9,3,9});
//				  
//		System.out.println(p1.start());
//		System.out.println(p2.start());
//		System.out.println(p3.start());
//		System.out.println(p4.start());
//		System.out.println(p5.start());
	}

	
	
}
