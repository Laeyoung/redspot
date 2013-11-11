import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {
	
	public static void main(String args[]) throws FileNotFoundException {
//		Scanner sc = new Scanner(new File("input.txt"));
		Scanner sc = new Scanner(System.in);
		int cases = sc.nextInt();
		
		Wildcard wildcard = new Main().new Wildcard();

		while (cases-- > 0) {
			String exp = sc.next();
			List<String> results = new LinkedList<String>();
			
			// find filename which is matched.
			int numFiles = sc.nextInt();
			while (numFiles-- > 0) {
				String target = sc.next();
				
				if (wildcard.match(target, exp)) {
					results.add(target);
				}
			}
			
			
			Collections.sort(results, new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					return o1.compareTo(o2);
				}
			});
			
			
			//print result
			for (String string : results) {
				System.out.println(string);
			}
		}
	}
	
	
	public class Wildcard {
		private String target;
		private ResultCache cache;
		
		public boolean match(String target, String exp) {
			this.target = target;
			cache = new ResultCache();
			
			return subMatch(0, exp);
		}
		
		private boolean subMatch(int offset, String remainExp) {
			if (remainExp.length() == 0 || remainExp.compareTo("") == 0) {
				if (offset == target.length()) {
					return true;
				} else {
					return false;
				}
			}
			
			if (offset > target.length()) {
				return false;
			}
			
			
			if (cache.hasCachedResult(offset, remainExp)) {
				return cache.check(offset, remainExp);
			}
			
			
			char startTargetChar;
			if (offset < target.length()) {
				startTargetChar = target.charAt(offset);
			} else {
				startTargetChar = ' ';
			}
			
			char startExpChar = remainExp.charAt(0);
			boolean result;
			if (startExpChar == '*') {
				result = subMatch(offset, remainExp.substring(1))
					|| subMatch(offset + 1, remainExp.substring(0))
					|| subMatch(offset, remainExp.substring(1));
			} else if (startExpChar == '?') {
				result = subMatch(offset + 1, remainExp.substring(1));
			} else if (startTargetChar == startExpChar) {
				result = subMatch(offset + 1, remainExp.substring(1));
			} else {
				result = false;
			}
			
			// memoization subResult.
			cache.addResult(result, offset, remainExp);
			
			return result;
		}
	}
	
	public class ResultCache {
		private HashMap<Integer, Set<String>> totalResult;
		private HashMap<Integer, Set<String>> trueResult; 
		
		public ResultCache() {
			totalResult = new HashMap<Integer, Set<String>>();
			trueResult = new HashMap<Integer, Set<String>>();
		}
		
		public void addResult(boolean isTrue, int indexTarget, String remainExp) {
			if (totalResult.containsKey(indexTarget) == false) {
				totalResult.put(indexTarget, new HashSet<String>());
				trueResult.put(indexTarget, new HashSet<String>());
			}
			
			totalResult.get(indexTarget).add(remainExp);
			if (isTrue) {
				trueResult.get(indexTarget).add(remainExp);
			}
		}
		
		public boolean hasCachedResult(int indexTarget, String remainExp) {
			if (totalResult.get(indexTarget) == null) {
				return false;
			}
			
			return totalResult.get(indexTarget).contains(remainExp);
		}
		
		public boolean check(int indexTarget, String remainExp) {
			return trueResult.get(indexTarget).contains(remainExp);
		}
	}
	
}
