#include <cstdio>
#include <cstring>
#include <functional>
#include <algorithm>
#include <iostream>

using namespace std;

int memo[105];

void process(void) {
    function<int(int)> go = [&](int a) -> int {
        if(a<0) return 0;
        if(memo[a] != -1) return memo[a];
        return memo[a] = ((go(a-2) + go(a-1)) % 1000000007);
    };
    int a;
    scanf("%d", &a);
    cout << go(a) << endl;
}

int main(void) {
    memset(memo, -1, sizeof(memo));
    memo[0] = 1;
    int t;
    scanf("%d", &t);
    while(t--) process();
}
