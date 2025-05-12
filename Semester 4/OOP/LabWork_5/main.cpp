#include <iostream>
#include <limits>
#include "DynamicArray.h"
#include "Vec2.h"

using namespace std;

void displayMenu() {
    cout << "\nMenu:\n";
    cout << "1. Add element to end (+ operator)\n";
    cout << "2. Remove element (- operator)\n";
    cout << "3. Insert element at position\n";
    cout << "4. Remove element at position\n";
    cout << "5. Sort array (ascending)\n";
    cout << "6. Sort array (descending)\n";
    cout << "7. Compare with another array\n";
    cout << "8. Display array\n";
    cout << "9. Exit\n";
    cout << "Choose option: ";
}

template <typename T, size_t N>
void testArray() {
    DynamicArray<T, N> arr;
    DynamicArray<T, N> arr2;
    int choice;
    T elem;
    size_t pos;
    
    do {
        displayMenu();
        cin >> choice;
        
        try {
            switch (choice) {
                case 1:
                    cout << "Enter element to add: ";
                    cin >> elem;
                    arr = arr + elem;
                    cout << "Element added.\n";
                    break;
                case 2:
                    cout << "Enter element to remove: ";
                    cin >> elem;
                    arr = arr - elem;
                    cout << "Element removed if present.\n";
                    break;
                case 3:
                    cout << "Enter element and position to insert: ";
                    cin >> elem >> pos;
                    if (arr.insert(elem, pos)) {
                        cout << "Element inserted.\n";
                    } else {
                        cout << "Failed to insert (invalid position or array full).\n";
                    }
                    break;
                case 4:
                    cout << "Enter position to remove: ";
                    cin >> pos;
                    if (arr.remove(pos)) {
                        cout << "Element removed.\n";
                    } else {
                        cout << "Failed to remove (invalid position).\n";
                    }
                    break;
                case 5:
                    arr.sort(true);
                    cout << "Array sorted in ascending order.\n";
                    break;
                case 6:
                    arr.sort(false);
                    cout << "Array sorted in descending order.\n";
                    break;
                case 7: {
                    arr2.clear();
                    size_t max_elements = arr.getLength() + arr.getLength() / 2;
                    cout << "Create another array for comparison:\n";
                    cout << "How many elements do you want to add? (max " << max_elements << "): ";
                    size_t count;
                    cin >> count;
                    
                    if (count > max_elements) {
                        cout << "Too many elements! Maximum possible: " << max_elements << endl;
                        break;
                    }
                    
                    cout << "Enter " << count << " elements:\n";
                    for (size_t i = 0; i < count; ++i) {
                        cout << "Element " << i << ": ";
                        if (!(cin >> elem)) {
                            cin.clear();
                            cin.ignore(numeric_limits<streamsize>::max(), '\n');
                            cout << "Invalid input, stopping input.\n";
                            break;
                        }
                        arr2 = arr2 + elem;
                    }
                    cin.ignore(numeric_limits<streamsize>::max(), '\n');
                    
                    cout << "First array: " << arr << endl;
                    cout << "Second array: " << arr2 << endl;
                    
                    if (arr.getLength() != arr2.getLength()) {
                        cout << "Arrays have different sizes, cannot compare element-wise.\n";
                    } else if (arr < arr2) {
                        cout << "All elements in first array are less than in second array.\n";
                    } else if (arr > arr2) {
                        cout << "All elements in first array are greater than in second array.\n";
                    } else {
                        cout << "Arrays are not strictly less than or greater than each other.\n";
                    }
                    break;
                }
                case 8:
                    cout << "Current array: " << arr << endl;
                    break;
                case 9:
                    cout << "Exiting...\n";
                    break;
                default:
                    cout << "Invalid choice. Try again.\n";
            }
        } catch (const exception& e) {
            cerr << "Error: " << e.what() << endl;
            cin.clear();
            cin.ignore(numeric_limits<streamsize>::max(), '\n');
        }
    } while (choice != 9);
}

void testStringArray() {
    const size_t N = 10;
    DynamicArray<char*, N> arr;
    DynamicArray<char*, N> arr2;
    int choice;
    char buffer[256];
    size_t pos;
    
    do {
        displayMenu();
        cin >> choice;
        cin.ignore();
        
        try {
            switch (choice) {
                case 1:
                    cout << "Enter string to add: ";
                    cin.getline(buffer, 256);
                    arr = arr + buffer;
                    cout << "String added.\n";
                    break;
                case 2:
                    cout << "Enter string to remove: ";
                    cin.getline(buffer, 256);
                    arr = arr - buffer;
                    cout << "String removed if present.\n";
                    break;
                case 3:
                    cout << "Enter string and position to insert: ";
                    cin.getline(buffer, 256);
                    cout << "Enter position: ";
                    cin >> pos;
                    cin.ignore();
                    if (arr.insert(buffer, pos)) {
                        cout << "String inserted.\n";
                    } else {
                        cout << "Failed to insert (invalid position or array full).\n";
                    }
                    break;
                case 4:
                    cout << "Enter position to remove: ";
                    cin >> pos;
                    cin.ignore();
                    if (arr.remove(pos)) {
                        cout << "String removed.\n";
                    } else {
                        cout << "Failed to remove (invalid position).\n";
                    }
                    break;
                case 5:
                    arr.sort(true);
                    cout << "Array sorted by length in ascending order.\n";
                    break;
                case 6:
                    arr.sort(false);
                    cout << "Array sorted by length in descending order.\n";
                    break;
                case 7: {
                    arr2.clear();
                    size_t max_elements = arr.getLength() + arr.getLength() / 2;
                    cout << "Create another array for comparison:\n";
                    cout << "How many strings do you want to add? (max " << max_elements << "): ";
                    size_t count;
                    cin >> count;
                    cin.ignore();
                    
                    if (count > max_elements) {
                        cout << "Too many strings! Maximum possible: " << max_elements << endl;
                        break;
                    }
                    
                    cout << "Enter " << count << " strings:\n";
                    for (size_t i = 0; i < count; ++i) {
                        cout << "String " << i << ": ";
                        cin.getline(buffer, 256);
                        if (strlen(buffer) == 0) break;
                        arr2 = arr2 + buffer;
                    }
                    
                    cout << "First array: " << arr << endl;
                    cout << "Second array: " << arr2 << endl;
                    
                    if (arr.getLength() != arr2.getLength()) {
                        cout << "Arrays have different sizes, cannot compare element-wise.\n";
                    } else if (arr < arr2) {
                        cout << "All strings in first array are shorter than in second array.\n";
                    } else if (arr > arr2) {
                        cout << "All strings in first array are longer than in second array.\n";
                    } else {
                        cout << "Arrays are not strictly shorter or longer than each other.\n";
                    }
                    break;
                }
                case 8:
                    cout << "Current array: " << arr << endl;
                    break;
                case 9:
                    cout << "Exiting...\n";
                    break;
                default:
                    cout << "Invalid choice. Try again.\n";
            }
        } catch (const exception& e) {
            cerr << "Error: " << e.what() << endl;
            cin.clear();
            cin.ignore(numeric_limits<streamsize>::max(), '\n');
        }
    } while (choice != 9);
}

void testVec2Array() {
    const size_t N = 10;
    DynamicArray<Vec2, N> arr;
    DynamicArray<Vec2, N> arr2;
    int choice;
    Vec2 vec;
    size_t pos;
    
    do {
        displayMenu();
        cin >> choice;
        
        try {
            switch (choice) {
                case 1:
                    cout << "Enter vector coordinates (x y): ";
                    cin >> vec.x >> vec.y;
                    arr = arr + vec;
                    cout << "Vector added.\n";
                    break;
                case 2:
                    cout << "Enter vector coordinates to remove (x y): ";
                    cin >> vec.x >> vec.y;
                    arr = arr - vec;
                    cout << "Vector removed if present.\n";
                    break;
                case 3:
                    cout << "Enter vector coordinates (x y) and position to insert: ";
                    cin >> vec.x >> vec.y >> pos;
                    if (arr.insert(vec, pos)) {
                        cout << "Vector inserted.\n";
                    } else {
                        cout << "Failed to insert (invalid position or array full).\n";
                    }
                    break;
                case 4:
                    cout << "Enter position to remove: ";
                    cin >> pos;
                    if (arr.remove(pos)) {
                        cout << "Vector removed.\n";
                    } else {
                        cout << "Failed to remove (invalid position).\n";
                    }
                    break;
                case 5:
                    arr.sort(true);
                    cout << "Array sorted by vector length in ascending order.\n";
                    break;
                case 6:
                    arr.sort(false);
                    cout << "Array sorted by vector length in descending order.\n";
                    break;
                case 7: {
                    arr2.clear();
                    size_t max_elements = arr.getLength() + arr.getLength() / 2;
                    cout << "Create another array for comparison:\n";
                    cout << "How many vectors do you want to add? (max " << max_elements << "): ";
                    size_t count;
                    cin >> count;
                    
                    if (count > max_elements) {
                        cout << "Too many vectors! Maximum possible: " << max_elements << endl;
                        break;
                    }
                    
                    cout << "Enter " << count << " vectors (x y):\n";
                    for (size_t i = 0; i < count; ++i) {
                        cout << "Vector " << i << ": ";
                        if (!(cin >> vec.x >> vec.y)) {
                            cin.clear();
                            cin.ignore(numeric_limits<streamsize>::max(), '\n');
                            cout << "Invalid input, stopping input.\n";
                            break;
                        }
                        arr2 = arr2 + vec;
                    }
                    cin.ignore(numeric_limits<streamsize>::max(), '\n');
                    
                    cout << "First array: " << arr << endl;
                    cout << "Second array: " << arr2 << endl;
                    
                    if (arr.getLength() != arr2.getLength()) {
                        cout << "Arrays have different sizes, cannot compare element-wise.\n";
                    } else if (arr < arr2) {
                        cout << "All vectors in first array are shorter than in second array.\n";
                    } else if (arr > arr2) {
                        cout << "All vectors in first array are longer than in second array.\n";
                    } else {
                        cout << "Arrays are not strictly shorter or longer than each other.\n";
                    }
                    break;
                }
                case 8:
                    cout << "Current array: " << arr << endl;
                    break;
                case 9:
                    cout << "Exiting...\n";
                    break;
                default:
                    cout << "Invalid choice. Try again.\n";
            }
        } catch (const exception& e) {
            cerr << "Error: " << e.what() << endl;
            cin.clear();
            cin.ignore(numeric_limits<streamsize>::max(), '\n');
        }
    } while (choice != 9);
}

int main() {
    int typeChoice;
    cout << "Choose array type to test:\n";
    cout << "1. int\n";
    cout << "2. float\n";
    cout << "3. char* (strings)\n";
    cout << "4. Vec2 (2D vectors)\n";
    cout << "Enter your choice: ";
    cin >> typeChoice;
    
    switch (typeChoice) {
        case 1:
            testArray<int, 10>();
            break;
        case 2:
            testArray<float, 10>();
            break;
        case 3:
            testStringArray();
            break;
        case 4:
            testVec2Array();
            break;
        default:
            cout << "Invalid choice.\n";
    }
    
    return 0;
}