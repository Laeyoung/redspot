#include <iostream>
#include <vector>
#include <string>
#include <limits>
#include <cstring>

class Solver {
public:

	const static int SAME = 1;
	const static int MONO_INC_DEC = 2;
	const static int REPEATE_TWO = 4;
	const static int ARITHMETIC_SEQ = 5;
	const static int OTHERS = 10;

	int cache[10001];

	void init(){
		std::memset(cache, -1, sizeof(cache));
	}

	std::string input;
	void make_input(){
		std::cin >> input;
	}

	int calc_sub(const std::string& s){
		char first = s[0];
		char second = s[1];

		int size = (int)s.size();
		if(first == second){
			// check same case;
			for(int i = 0; i < size; ++i){
				if(s[i] != first){
					return OTHERS;
				}
			}
			return SAME;
		}else{
			// check repeat two
			for(int i = 2; i < size; ++i){
				int compare_idx = i % 2;
				if(s[compare_idx] != s[i]){
					break;
				}
				if(i == size-1){
					return REPEATE_TWO;
				}
			}
			// check mono_inc_dec or arithmetic_seq
			int diff = (int)(second - '0') - (int)(first - '0');
			for(int i = 1; i < size; ++i){
				int diff_next = (int)(s[i] - '0') - (int)(s[i-1] - '0');
				if(diff != diff_next){
					break;
				}
				if(i == size-1){
					if(diff == -1 || diff == 1){
						return MONO_INC_DEC;
					}else{
						return ARITHMETIC_SEQ;
					}
				}
			}
		}

		return OTHERS;
	}

	int calc_point(int pos){

		if(input.size()-pos < 3){
			return -1;
		}

		if(input.size()-pos <= 5){
			int leaf = calc_sub(input.substr(pos));
			return leaf;
		}

		if(cache[pos] != -1){
			return cache[pos];
		}

		// 3, 4, 5  묶음
		int min = -1;
		for(int i = 3; i <=5; ++i){
			int point = calc_point(pos+i);
			if(point < 0){
				continue;
			}
			point = calc_sub(input.substr(pos, i)) + point;
			if(min < 0 || min > point){
				min = point;
			}
		}

		cache[pos] = min;
		return min;
	}

	void solve(){
		int point = calc_point(0);
		std::cout << point << std::endl;
	}

	int solve_test(const std::string& _input){
		init();
		input = _input;
		return calc_point(0);
	}
};

int main(){

	int C;
	std::cin >> C;

	Solver solver;
	for(int i = 0; i < C; ++i){
		solver.init();
		solver.make_input();
		solver.solve();
	}

	return 0;
}

