#include <iostream>;
#include <vector>

using namespace std;

void CountingSort(vector<int>& data) {
    int n;
    n = data.size();

    int mx = data[0];
    for (int i = 0; i < n; i++) {
        if (data[i] > mx) {
            mx = data[i];
        }
    }

    vector<int> counter(mx + 1);

    for (int x = 0; x < n; x++) {
        counter[data[x]] += 1;
    }

    data.clear();
    for (int k = 0; k < mx + 1; k++) {
        for (int i = 0; i < counter[k]; i++) {
            data.push_back(k);
        }
        
    }
}

int main() {
    int x;
    vector<int> data;
    while (cin >> x) {
        data.push_back(x);
    }

    CountingSort(data);

    for (int i = 0; i < data.size(); i++)
    {
        cout << data[i] << " "; 
    }
    cout << endl;

    return 0;
}
