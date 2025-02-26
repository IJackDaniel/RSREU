#include <iostream>
#include <vector>
#include <unordered_map>

using namespace std;

int main() {
    int n;
    cin >> n;

    unordered_map<int, vector<int>> graph;
    vector<int> inp1(n);

    for (int i = 0; i < n; ++i) {
        cin >> inp1[i];
    }

    for (int i = 1; i <= n; ++i) {
        if (inp1[i - 1] == 0) {
            graph[i] = {};
        }
        else {
            graph[i].insert(graph[i].begin(), inp1[i - 1]);
            for (const int& parent : graph[inp1[i - 1]]) {
                graph[i].push_back(parent);
            }
        }
    }

    int m;
    cin >> m;

    for (int j = 0; j < m; ++j) {
        int pred, child;
        cin >> pred >> child;

        int result = 0;
        if (find(graph[child].begin(), graph[child].end(), pred) != graph[child].end()) {
            result = 1;
        }

        cout << result << endl;
    }

    return 0;
}
