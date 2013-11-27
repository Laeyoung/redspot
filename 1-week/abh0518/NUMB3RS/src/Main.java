/**
 * 두니발 박사 탈옥 알고스팟 통과
 * 1. 간당간단하게 통과함
 * 2. 메모이제이션을 쓰는데 다른 해결책과 날리 getProba 메소드를 실행할때마다 메모이제이션 배열을 초기화 해줘여 답이 제대로 나옴
 * 3. 메모이제이션 저장 부분 로직을 뭔가 잘못짠건가?
 * 4. 일단 매번 메모이제이션 부분을 초기화 하면 시간내에 품
 * 5. 캐시 배열을 객체 배열로 쓴것도 시간을 잡아먹는 문제중 하나였음, 프리미티브 배열을 써야함
 */

import java.io.IOException;
import java.util.Scanner;


public class Main {
	
	private int town[][];
	private int prison;
	private int days;
	
	int[] degree;
	private double cache[][];
	
	public Main(int town[][], int prison, int days){
		this.town = town;
		this.prison = prison;
		this.days = days;
		
		this.degree = new int[town.length];
		for(int i = 0 ; i < town.length ; i++){
			int count = 0 ;
			for(int j = 0 ; j < town[i].length ; j++){
				if(town[i][j] == 1) count++;	
			}
			degree[i] = count;
		}
		
		
	}
	
	public double getProbability(int end){
		cache = new double[50][100];
		for(int i = 0 ; i < cache.length ; i++){
			for(int j = 0 ; j < cache[i].length ; j++){
				cache[i][j] = -1;
			}
		}
		
		return getProbability(prison, end , 0);
	}
	
	private double getProbability(int start, int end, int curDay){
		
		if(this.days == curDay){
			if(start != end) return 0;
			return 1;
		}
		
		if(cache[start][curDay] != -1) return cache[start][curDay];
		
		double probablility = 0;
		for(int i = 0 ; i <  town[start].length ; i++){
			if(town[start][i]==1) {
				probablility = probablility + getProbability(i ,end, curDay+1)/degree[start];
			}
		}
		
		cache[start][curDay] = probablility;
		return probablility;
	}
	
	public static void main(String args[]) throws NumberFormatException, IOException{
		//Date startTime = new Date();
		
		Scanner in = new Scanner(System.in);
		//Scanner in = new Scanner(new FileInputStream("test.txt"));
		
		int testCount = in.nextInt();
		
		for(int i = 0 ; i < testCount ; i++){
			int townCount = in.nextInt();
			int days = in.nextInt();
			int start = in.nextInt();
			
			int town[][] = new int[townCount][townCount];
			for(int j = 0 ; j < townCount ; j++){
				for(int x = 0 ; x < townCount ; x++){
					town[j][x] = in.nextInt();
				}
			}
			
			Main numb = new Main(town, start, days);
			
			int endCount = in.nextInt();
			
			int[] end = new int[endCount];
			for(int j = 0 ; j < endCount ; j++){
				end[j] = in.nextInt();
			}	
			
			
			for(int j = 0 ; j < endCount ; j++){
				if(j+1 != endCount){
					System.out.print(Double.parseDouble(String.format("%.8f",  numb.getProbability(end[j])))+" ");
				}
				else{
					System.out.println(Double.parseDouble(String.format("%.8f",  numb.getProbability(end[j]))));				
				}
			}
		}
		
		//Date endTime = new Date();
		//System.out.println(endTime.getTime() - startTime.getTime());
	
	}
	

}
	

	
	

