#ifndef DYNAMICARRAY_H
#define DYNAMICARRAY_H

#include <iostream>
#include <algorithm>
#include <cstring>
#include <stdexcept>
#include <limits>
#include "Vec2.h"

template <typename T, size_t MaxSize>
class DynamicArray {
private:
    T* data;
    size_t currentSize;

public:
    // Конструкторы и деструктор
    DynamicArray() : data(new T[MaxSize]), currentSize(0) {}
    
    DynamicArray(const DynamicArray& other) : data(new T[MaxSize]), currentSize(other.currentSize) {
        std::copy(other.data, other.data + currentSize, data);
    }
    
    ~DynamicArray() {
        delete[] data;
    }

    // Методы
    void sort(bool bAscending = true) {
        if (bAscending) {
            std::sort(data, data + currentSize);
        } else {
            std::sort(data, data + currentSize, std::greater<T>());
        }
    }
    
    size_t getMaxSize() const { return MaxSize; }
    size_t getLength() const { return currentSize; }
    
    bool insert(const T& elem, size_t n) {
        if (currentSize >= MaxSize || n > currentSize) return false;
        
        if (n == currentSize) {
            data[currentSize++] = elem;
        } else {
            for (size_t i = currentSize; i > n; --i) {
                data[i] = data[i-1];
            }
            data[n] = elem;
            currentSize++;
        }
        return true;
    }
    
    bool remove(size_t n) {
        if (n >= currentSize) return false;
        
        for (size_t i = n; i < currentSize - 1; ++i) {
            data[i] = data[i+1];
        }
        currentSize--;
        return true;
    }

    void clear() {
        currentSize = 0;
    }

    // Перегрузка операторов
    DynamicArray& operator=(const DynamicArray& other) {
        if (this != &other) {
            currentSize = other.currentSize;
            std::copy(other.data, other.data + currentSize, data);
        }
        return *this;
    }
    
    DynamicArray& operator+(const T& elem) {
        insert(elem, currentSize);
        return *this;
    }
    
    DynamicArray& operator-(const T& elem) {
        for (size_t i = 0; i < currentSize; ++i) {
            if (data[i] == elem) {
                remove(i);
                break;
            }
        }
        return *this;
    }
    
    T& operator[](size_t index) {
        if (index >= currentSize) throw std::out_of_range("Index out of range");
        return data[index];
    }
    
    const T& operator[](size_t index) const {
        if (index >= currentSize) throw std::out_of_range("Index out of range");
        return data[index];
    }
    
    friend std::ostream& operator<<(std::ostream& os, const DynamicArray& arr) {
        os << "Array (size: " << arr.currentSize << "/" << arr.getMaxSize() << "): [";
        for (size_t i = 0; i < arr.currentSize; ++i) {
            os << arr.data[i];
            if (i != arr.currentSize - 1) os << ", ";
        }
        os << "]";
        return os;
    }
};

// Специализация для char*
template <size_t MaxSize>
class DynamicArray<char*, MaxSize> {
private:
    char** data;
    size_t currentSize;

public:
    DynamicArray() : data(new char*[MaxSize]), currentSize(0) {
        for (size_t i = 0; i < MaxSize; ++i) {
            data[i] = nullptr;
        }
    }
    
    DynamicArray(const DynamicArray& other) : data(new char*[MaxSize]), currentSize(other.currentSize) {
        for (size_t i = 0; i < currentSize; ++i) {
            data[i] = new char[strlen(other.data[i]) + 1];
            strcpy(data[i], other.data[i]);
        }
        for (size_t i = currentSize; i < MaxSize; ++i) {
            data[i] = nullptr;
        }
    }
    
    ~DynamicArray() {
        clear();
        delete[] data;
    }

    void sort(bool bAscending = true) {
        auto compare = [bAscending](const char* a, const char* b) {
            size_t lenA = strlen(a);
            size_t lenB = strlen(b);
            return bAscending ? lenA < lenB : lenA > lenB;
        };
        std::sort(data, data + currentSize, compare);
    }
    
    size_t getMaxSize() const { return MaxSize; }
    size_t getLength() const { return currentSize; }
    
    bool insert(const char* elem, size_t n) {
        if (currentSize >= MaxSize || n > currentSize) return false;
        
        char* newElem = new char[strlen(elem) + 1];
        strcpy(newElem, elem);
        
        if (n == currentSize) {
            data[currentSize++] = newElem;
        } else {
            for (size_t i = currentSize; i > n; --i) {
                data[i] = data[i-1];
            }
            data[n] = newElem;
            currentSize++;
        }
        return true;
    }
    
    bool remove(size_t n) {
        if (n >= currentSize) return false;
        
        delete[] data[n];
        for (size_t i = n; i < currentSize - 1; ++i) {
            data[i] = data[i+1];
        }
        currentSize--;
        data[currentSize] = nullptr;
        return true;
    }

    void clear() {
        for (size_t i = 0; i < currentSize; ++i) {
            delete[] data[i];
            data[i] = nullptr;
        }
        currentSize = 0;
    }

    DynamicArray& operator=(const DynamicArray& other) {
        if (this != &other) {
            clear();
            currentSize = other.currentSize;
            for (size_t i = 0; i < currentSize; ++i) {
                data[i] = new char[strlen(other.data[i]) + 1];
                strcpy(data[i], other.data[i]);
            }
        }
        return *this;
    }
    
    DynamicArray& operator+(const char* elem) {
        insert(elem, currentSize);
        return *this;
    }
    
    DynamicArray& operator-(const char* elem) {
        for (size_t i = 0; i < currentSize; ++i) {
            if (strcmp(data[i], elem) == 0) {
                remove(i);
                break;
            }
        }
        return *this;
    }
    
    char*& operator[](size_t index) {
        if (index >= currentSize) throw std::out_of_range("Index out of range");
        return data[index];
    }
    
    const char* operator[](size_t index) const {
        if (index >= currentSize) throw std::out_of_range("Index out of range");
        return data[index];
    }
    
    friend std::ostream& operator<<(std::ostream& os, const DynamicArray& arr) {
        os << "Array (size: " << arr.currentSize << "/" << arr.getMaxSize() << "): [";
        for (size_t i = 0; i < arr.currentSize; ++i) {
            os << "\"" << arr.data[i] << "\"";
            if (i != arr.currentSize - 1) os << ", ";
        }
        os << "]";
        return os;
    }
};

// Операторы сравнения для DynamicArray
template <typename T, size_t N>
bool operator<(const DynamicArray<T, N>& lhs, const DynamicArray<T, N>& rhs) {
    if (lhs.getLength() != rhs.getLength()) return false;
    for (size_t i = 0; i < lhs.getLength(); ++i) {
        if (!(lhs[i] < rhs[i])) return false;
    }
    return true;
}

template <typename T, size_t N>
bool operator>(const DynamicArray<T, N>& lhs, const DynamicArray<T, N>& rhs) {
    if (lhs.getLength() != rhs.getLength()) return false;
    for (size_t i = 0; i < lhs.getLength(); ++i) {
        if (!(lhs[i] > rhs[i])) return false;
    }
    return true;
}

#endif // DYNAMICARRAY_H