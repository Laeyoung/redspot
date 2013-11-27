#include <cstdio>
#include <cstring>
#include <functional>
#include <algorithm>
#include <iostream>

#define sqr(x) ((x)*(x))

using namespace std;

void process(void) {
    int n, m;
    int arr[105];
    int quant[105][105];
    scanf("%d %d", &n, &m);
    for(int i=0;i<n;i++) scanf("%d", &arr[i]);
    sort(arr, arr+n);

    for(int i=0;i<n;i++) {
        int now = arr[i];
        int sum = 0;
        for(int j=i+1;j<=n;j++) {
            quant[i][j] = sum;
            sum += sqr(arr[j] - now);
            while(true) {
                int tmp = 0;
                for(int k=i;k<=j;k++) {
                    tmp += sqr(arr[k] - (now+1));
                }
                if(tmp > sum) break;
                now++;
                sum = tmp;
            }
        }
    }

    int memo[105][15];
    memset(memo, -1, sizeof(memo));
    function<int(int, int)> go = [&](int a, int b) -> int {
        if(a<b) return 0;
        if(b == 1) return quant[0][a];
        if(memo[a][b] != -1) return memo[a][b];

        for(int i=0;i<=a;i++) { 
            int tmp = go(i, b-1) + quant[i][a];
            if(memo[a][b] == -1 || memo[a][b] > tmp) memo[a][b] = tmp;
        }

        return memo[a][b];
    };

    cout << go(n, m) << endl;
}

int main(void) {
    int t;
    scanf("%d", &t);
    while(t--) process();
}
