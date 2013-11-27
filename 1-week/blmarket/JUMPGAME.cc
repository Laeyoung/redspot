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

int n;
int board[105][105];
int check[105][105];

bool go(int a, int b) {
    if(a>=n || b>=n) return false;
    if(check[a][b] != -1) return check[a][b];
    return check[a][b] = go(a+board[a][b], b) || go(a, b + board[a][b]);
}

void prozess() {
    memset(check, -1, sizeof(check));
    scanf("%d", &n);
    for(int i=0;i<n;i++) {
        for(int j=0;j<n;j++) scanf("%d",&board[i][j]);
    }

    check[n-1][n-1] = 1;
    if(go(0,0)) {
        cout << "YES" << endl;
    } else {
        cout << "NO" << endl;
    }
}

int main(void)
{
    int t;
    scanf("%d", &t);
    for(int i=0;i<t;i++) {
        prozess();
    }
}
