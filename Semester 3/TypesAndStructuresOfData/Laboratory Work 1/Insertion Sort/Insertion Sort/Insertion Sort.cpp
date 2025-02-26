#include <iostream>;
#include <vector>

using namespace std;

void InsertionSort(vector<int>& data) {
    int n;
    n = data.size();

    for (int i = 1; i < n; i++) {
        int key = data[i];
        int j = i;
        while (j >= 1 and data[j - 1] > key) {
            data[j] = data[j - 1];
            j -= 1;
        }
        data[j] = key;
    }
}

int main() {
    int x;
    vector<int> data;
    while (cin >> x) {
        data.push_back(x);
    }

    InsertionSort(data);

    for (int i = 0; i < data.size(); i++)
    {
        cout << data[i] << " ";
    }
    cout << endl;

    return 0;
}
