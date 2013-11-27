#include <algorithm>
#include <functional>
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

string pat;
int n;

bool valid(const string &str) {
    int vv[105][105];
    memset(vv, -1, sizeof(vv));
    vv[0][0] = 1;
    function<bool(int, int)> ff = [&](int a, int b) -> bool {
        if(vv[a][b] != -1) return vv[a][b];
        switch(pat[b]) {
            case '*':
                return vv[a][b] = ff(a-1, b) || ff(a-1, b-1);
            case '?':
                return vv[a][b] = ff(a-1, b-1);
            default:
                return vv[a][b] = ff(a-1, b-1) && str[a] == pat[b];
        }
    };

    return ff(size(str)-1, size(pat)-1);
}

void process() {
    vector<string> out;
    cin >> pat;
    cin >> n;
    for(int i=0;i<n;i++) {
        string target;
        cin >> target;
        if(valid(target)) {
            out.pb(target);
        }
    }
    sort(out.begin(), out.end());
    for(int i=0;i<size(out);i++) cout << out[i] << endl;
}

int main(void)
{
    int t;
    cin >> t;
    for(int i=0;i<t;i++) process();
}
