// E. Коровы - в стойла
#include <iostream>
#include <vector>

bool good(std::vector<int>& boxes, int k, int r) {
    int cows_count = 1;
    int last_box = boxes[0];
    for (int j = 1; j < boxes.size(); j++) {
        int box = boxes[j];
        if (box - last_box >= r) {
            cows_count++;
            last_box = box;
        }
    }
    return cows_count >= k;
}

int main()
{
    int n, k;
    std::cin >> n >> k;
    
    std::vector<int> arr;
    int x;
    for (int i = 0; i < n; i++) {
        std::cin >> x;
        arr.push_back(x);
    }

    int l = 0;
    int r = arr[n - 1] - arr[0] + 1;
    while (r - l > 1) {
        int m = (l + r) / 2;
        if (good(arr, k, m)) {
            l = m;
        }
        else {
            r = m;
        }
    }
    std::cout << l;
}

