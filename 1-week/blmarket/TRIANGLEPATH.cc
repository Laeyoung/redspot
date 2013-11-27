#include <algorithm>
#include <iostream>
#include <cstdlib>
#include <cstring>
#include <cstdio>
#include <sstream>
#include <numeric>
#include <iterator>
#include <queue>
#include <set>
#include <map>
#include <vector>

#define mp make_pair
#define pb push_back
#define sqr(x) ((x)*(x))
#define foreach(it,c) for(typeof((c).begin()) it = (c).begin(); it != (c).end(); ++it)

using namespace std;

typedef vector<int> VI;
typedef vector<VI> VVI;
typedef vector<string> VS;
typedef pair<int,int> PII;
typedef long long LL;

template<typename T> int size(const T &a) { return a.size(); } 

void process() {
    int n;
    int board[105][105];
    int memo[105][105];
    memset(memo, -1, sizeof(memo));

    scanf("%d", &n);
    for(int i=0;i<n;i++) {
        for(int j=0;j<=i;j++) {
            scanf("%d", &board[i][j]);
        }
    }
    function<int(int, int)> go = [&](int a, int b) -> int {
        if(a == n) return 0;
        if(memo[a][b] != -1) return memo[a][b];
        return memo[a][b] = board[a][b] + max(go(a+1, b), go(a+1, b+1));
    };

    cout << go(0,0) << endl;
}

int main(void)
{
    int t;
    scanf("%d", &t);
    while(t--) process();
}
