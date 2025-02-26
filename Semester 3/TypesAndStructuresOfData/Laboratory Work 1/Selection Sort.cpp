#include <iostream>
#include <vector>

using namespace std;

void SelectionSort(vector<int>& data) {
    int n;
    n = data.size();
    
    for (int i = 0; i < n - 1; i++) {
        int key = data[i];
        int ind = i;
        for (int j = i + 1; j < n; j++) {
            if (data[j] > key) {
                key = data[j];
                ind = j;
            }
        }
        if (i != ind) {
            swap(data[i], data[ind]);
        }
    }
}

int main() {
    int x;
    vector<int> data;
    while (cin >> x) {
        data.push_back(x);
    }

    SelectionSort(data);

    for (int i = 0; i < data.size(); i++)
    {
        cout << data[i] << " ";
    }
    cout << endl;

    return 0;
}
