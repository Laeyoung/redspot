#include "cute_algostudy.h"

#include <iostream>
#include <cstring>

namespace tiling2 {

class Solver {
public:

	const static int MOD = 1000000007;

	int cache[101];

	void init(){
        // note that,
        // int cached_tiling_at_n[MAX_N] = {-1, }
        // does not fill as -1 (accually, fill as -1, 0, 0, ...)
        // and global variable is filled 0 as default.
        // and memset is useful only -1 and 0
        // because -1 is 11111111 bit and 0 is 00000000 bit
        // memset is set by byte unit
		std::memset(cache, -1, sizeof(cache));
	}

	int N;
	void make_input(){
		std::cin >> N;
	}

	int tiling(int _n){
		if(_n == 1){
			return 1;
		}
		if(_n == 2){
			return 2;
		}
		int& updatable_cache = cache[_n];
		if(updatable_cache != -1){
			return updatable_cache;
		}

		updatable_cache = (tiling(_n-1) + tiling(_n-2)) % MOD;
		return updatable_cache;
	}

	void solve(){
		int ret = tiling(N);
		std::cout << ret << std::endl;
	}

	int solve_test(int _n){
		init();
		return tiling(_n);
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

void test_tiling2(){
	Solver solver;
	ASSERT_EQUAL(1, solver.solve_test(1));
	ASSERT_EQUAL(8, solver.solve_test(5));
	ASSERT_EQUAL(782204094, solver.solve_test(100));
}

//CUTE_TEST(test_tiling2);
//CUTE_MAIN(main);

}
