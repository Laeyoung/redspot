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
    cin >> n;

    VI arr;

    int num;
    for(int i=0;i<n;i++) {
        cin >> num;
        bool done = false;
        for(int i=0;i<size(arr);i++) {
            if(arr[i] >= num) {
                arr[i] = num;
                done = true;
                break;
            }
        }
        if(!done) arr.pb(num);
    }
    cout << size(arr) << endl;
}

int main(void)
{
    int t;
    cin >> t;
    while(t--) process();
}
