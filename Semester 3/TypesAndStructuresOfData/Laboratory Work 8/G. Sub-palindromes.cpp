#include <iostream>
#include <string>

using namespace std;

int count_palindroms(const string& s, int left, int right) 
{
    int count = 0;
    while (left >= 0 && right < s.length() && s[left] == s[right]) 
    {
        count++;
        left--;
        right++;
    }
    return count;
}

int main() 
{
    string input;
    cin >> input;

    int result = 0;
    for (int i = 0; i < input.length(); i++) 
    {
        result += count_palindroms(input, i, i);
        result += count_palindroms(input, i, i + 1);
    }

    cout << result << endl;

    return 0;
}
