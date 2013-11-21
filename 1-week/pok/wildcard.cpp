#include <iostream>
#include <vector>
#include <string>
#include <map>
#include <set>

class Solver {
public:

	std::string pattern;
	int n;
	std::vector<std::string> names;

	void make_input(){
		std::cin >> pattern >> n;
		for(int i = 0; i < n; ++i){
			std::string name;
			std::cin >> name;
			names.push_back(name);
		}
	}

	typedef std::pair<std::string, std::string> KEY;
	std::map<KEY, bool > cache;
	bool updateCacheAndReturnValue(const KEY& key, bool value){
		cache.insert( std::pair<KEY, bool>(key, value) );
		return value;
	}
	bool match(const std::string& _pattern, const std::string& _name){

		int i = 0;
		int pattern_size = (int)_pattern.size();
		int name_size = (int)_name.size();

		KEY key = std::pair<std::string, std::string>(_pattern, _name);
		std::map< KEY, bool >::iterator value_itr = cache.find(key);
		if(value_itr != cache.end()){
			return value_itr->second;
		}

		// check pattern and name
		while(i < pattern_size && i < name_size && (_pattern[i] == _name[i] || _pattern[i] == '?') ){
			i++;
		}

		// pattern check가 마지막을 넘어선 경우
		if(i == pattern_size){
			if(i == name_size){
				return updateCacheAndReturnValue(key, true);
			}
			return updateCacheAndReturnValue(key, false);
		}

		// name check 가 마지막을 넘어선 경우
		if(i == name_size){
			for(int j = i; j < pattern_size; ++j){
				if(_pattern[j] != '*'){
					return updateCacheAndReturnValue(key, false);
				}
			}
			return updateCacheAndReturnValue(key, true);
		}

		// * 인 경우
		if(_pattern[i] == '*'){
			if(i+1 == pattern_size){
				return updateCacheAndReturnValue(key, true);
			}
			std::string sub_pattern = _pattern.substr(i+1);
			for(int j = i; j < name_size; ++j){
				std::string sub_name = _name.substr(j);
				if(match(sub_pattern, sub_name)){
					return updateCacheAndReturnValue(key, true);
				}
			}
		}

		return updateCacheAndReturnValue(key, false);
	}

	void solve(){
		std::set<std::string> output;
		for(int i = 0; i < n; ++i){
			if(match(pattern, names[i])){
				output.insert(names[i]);
			}
		}
		for(std::set<std::string>::iterator itr = output.begin(); itr != output.end(); ++itr){
			std::cout << *itr << std::endl;
		}
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

