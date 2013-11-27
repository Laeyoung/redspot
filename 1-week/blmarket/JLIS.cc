#include <cstdio>
#include <cstring>
#include <functional>
#include <algorithm>
#include <iostream>

using namespace std;

void process(void) {
    int nn[2];
    int v[2][105];
    scanf("%d %d", &nn[0], &nn[1]);
    for(int i=0;i<2;i++) for(int j=0;j<nn[i];j++) scanf("%d", &v[i][j]);

    int memo[105][105];
    memset(memo, -1, sizeof(memo));

    function<int(int, int)> go = [&](int a, int b) -> int {
        if(memo[a][b] != -1) return memo[a][b];
        int ret = 0;
        for(int i=a;i<nn[0];i++) {
            if(a != 0 && v[0][a-1] >= v[0][i]) continue;
            if(b != 0 && v[1][b-1] >= v[0][i]) continue;
            ret = max(ret, go(i+1, b) + 1);
        }
        for(int i=b;i<nn[1];i++) {
            if(a != 0 && v[0][a-1] >= v[1][i]) continue;
            if(b != 0 && v[1][b-1] >= v[1][i]) continue;
            ret = max(ret, go(a, i+1) + 1);
        }
        return memo[a][b] = ret;
    };

    cout << go(0, 0) << endl;
}

int main(void) {
    int t;
    scanf("%d", &t);
    while(t--) process();
}
