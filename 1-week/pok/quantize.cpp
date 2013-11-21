#include <iostream>
#include <vector>
#include <algorithm>

class CacheMap {
public:
	int cache_map[101][101];
	CacheMap(){
		for(int i = 0; i < 101; ++i){
			for(int j = 0; j < 101; ++j){
				cache_map[i][j] = -1;
			}
		}
	}

	int getCached(int first_cache_key, int second_cache_key){
		return cache_map[first_cache_key][second_cache_key];
	}

	void updateCache(int first_cache_key, int second_cache_key, int value){
		cache_map[first_cache_key][second_cache_key] = value;
	}
};

class Solver {
public:
	int N; // num of nums
	int S; //num of partition

	std::vector<int> nums;

	void make_input(){
		std::cin >> N >> S;
		for(int i = 0; i < N; ++i){
			int n_i;
			std::cin >> n_i;
			nums.push_back(n_i);
		}
	}

	CacheMap accum_sum_cache_map;
	CacheMap accum_sum_square_cache_map;
	void sort_and_sum_cache(){
		std::sort(nums.begin(), nums.end());

		int sum = 0;
		int sum_squre = 0;
		for(int i = 0; i < (int)nums.size(); ++i){
			sum = sum + nums[i];
			sum_squre = sum_squre + (nums[i] * nums[i]);
			accum_sum_cache_map.updateCache(0, i, sum);
			accum_sum_square_cache_map.updateCache(0, i, sum_squre);
		}
	}

	int choose_min(int from, int size){
		if(size == 1){
			return 0;
		}
		int range_end = from+size-1;
		int sum = accum_sum_cache_map.getCached(0, range_end);
		int sum_squre = accum_sum_square_cache_map.getCached(0, range_end);
		if(from > 0){
			sum = sum - accum_sum_cache_map.getCached(0, from-1);
			sum_squre = sum_squre - accum_sum_square_cache_map.getCached(0, from-1);
		}
		float avrgf = (float)sum / (float)size;
		int avrg = (int)(avrgf + 0.5f);
		return sum_squre - 2 * avrg * sum + size * avrg * avrg;
	}

	CacheMap sub_quant_cache_map;
	int quant(int from, int parts){

		if(from == N){
			return 0;
		}

		if(parts == 0){
			return -1;
		}

		int cached = sub_quant_cache_map.getCached(from, parts);
		if(cached != -1){
			return cached;
		}

		int min = -1;
		for(int size = 1; size <= N-from; ++size){
			int sub_quant = quant(from + size, parts - 1);
			if(sub_quant < 0){
				continue;
			}
			int sub_min = choose_min(from, size);
			int min_candidation = sub_min + sub_quant;
			if(min < 0 || min > min_candidation){
				min = min_candidation;
			}
		}

		sub_quant_cache_map.updateCache(from, parts, min);
		return min;
	}

	void solve(){
		sort_and_sum_cache();
		int min = quant(0, S);
		std::cout << min << std::endl;
	}
};

int main(){

	int C;
	std::cin >> C;

	for(int i = 0; i < C; ++i){
		Solver solver;
		solver.make_input();
		solver.solve();
	}

	return 0;
}

