#include <iostream>
#include <vector>

using namespace std;

int BubbleSort(vector<int>& data) {
    int n;
    n = data.size();
    int cnt = 0;

    for (int iter = 0; iter < n - 1; iter++) {
        bool swapped = false;
        for (int i = 0; i < n - iter - 1; i++) {
            if (data[i] > data[i + 1]) {
                swap(data[i], data[i + 1]);
                swapped = true;
                cnt++;
            }
        }
        if (!swapped) {
            break;
        }
    }

    return cnt;
}

// Задание по подсчёту количества перестановок
int main() {
    int ln, x;
    cin >> ln;
    vector<int> data(ln);
    for (int iter = 0; iter < ln; iter++) {
        cin >> x;
        data[iter] = x;
    }

    int cnt = BubbleSort(data);

    cout << cnt;
    return 0;
}

/*
#include <iostream>;
#include <vector>

using namespace std;

void BubbleSort(vector<int>& data) {
    int n;
    n = data.size();

    for (int iter = 0; iter < n - 1; iter++) {
        bool swapped = false;
        for (int i = 0; i < n - iter - 1; i++) {
            if (data[i] < data[i + 1]) {
                swap(data[i], data[i + 1]);
                swapped = true;
            }
        }
        if (!swapped) {
            break;
        }
    }
}

int main() {
    int x;
    vector<int> data;
    while (cin >> x) {
        data.push_back(x);
    }

    BubbleSort(data);

    for (int i = 0; i < data.size(); i++)
    {
        cout << data[i] << " ";
    }
    cout << endl;

    return 0;
}

*/