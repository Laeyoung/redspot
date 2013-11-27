#include <cstdio>
#include <cstring>
#include <functional>
#include <algorithm>
#include <iostream>

using namespace std;

int diffi(char *str, int l) {
    auto a1 = [&]() {
        for(int i=0;i<l;i++) if(str[i] != str[0]) return false;
        return true;
    };
    if(a1()) return 1;
    auto a2 = [&]() -> int {
        int s = str[1] - str[0];
        for(int i=2;i<l;i++) {
            if(str[i] - str[i-1] != s) return -999;
        }
        return s;
    };
    int ss = a2();
    if(ss == -1 || ss == 1) return 2;
    auto a4 = [&]() {
        for(int i=2;i<l;i++) {
            if(str[i] != str[i-2]) return false;
        }
        return true;
    };
    if(a4()) return 4;
    if(ss != -999) return 5;
    return 10;
}

void process(void) {
    char str[10005];
    scanf("%s", str);
    int n = strlen(str);

    int memo[10005];
    memset(memo, -1, sizeof(memo));
    function<int(int)> go = [&](int a) -> int {
        if(a == 0) return 0;
        if(a<3) return 9000000;
        if(memo[a] != -1) return memo[a];
        memo[a] = go(a-3) + diffi(str+a-3, 3);
        if(a<4) return memo[a];
        memo[a] = min(memo[a], go(a-4) + diffi(str+a-4, 4));
        if(a<5) return memo[a];
        memo[a] = min(memo[a], go(a-5) + diffi(str+a-5, 5));
        return memo[a];
    };
    cout << go(n) << endl;
}

int main(void) {
    int t;
    scanf("%d", &t);
    while(t--) process();
}
