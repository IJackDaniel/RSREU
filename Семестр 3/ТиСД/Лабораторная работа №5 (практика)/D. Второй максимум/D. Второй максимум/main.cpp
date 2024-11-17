#include <iostream>
#include <vector>

class Node {
public:
    int data;
    Node* left;
    Node* right;

    Node(int key) : data(key), left(nullptr), right(nullptr) {}
};

class BinarySearchTree {
public:
    Node* root;

    BinarySearchTree() : root(nullptr) {}

    void insert(int key) {
        root = _insert(root, key);
    }

    bool search(int key) {
        return _search(root, key);
    }

    int findMax() {
        if (root == nullptr) {
            return -1; // Или можно вернуть какое-то другое значение, если дерево пустое
        }
        return _maxNode(root)->data;
    }

    int findMin() {
        if (root == nullptr) {
            return -1; // Или другое значение по аналогии
        }
        return _minNode(root)->data;
    }

    void deleteKey(int key) {
        root = _delete(root, key);
    }

    void printTree(Node* node, int level) {
        if (node == nullptr) {
            return;
        }
        printTree(node->left, level + 1);
        for (int i = 0; i < level; ++i) {
            std::cout << ".";
        }
        std::cout << node->data << std::endl;
        printTree(node->right, level + 1);
    }

private:
    Node* _insert(Node* node, int key) {
        if (node == nullptr) {
            return new Node(key);
        }
        if (key < node->data) {
            node->left = _insert(node->left, key);
        }
        else if (key > node->data) {
            node->right = _insert(node->right, key);
        }
        return node;
    }

    bool _search(Node* node, int key) {
        if (node == nullptr) {
            return false;
        }
        if (node->data == key) {
            return true;
        }
        if (key < node->data) {
            return _search(node->left, key);
        }
        return _search(node->right, key);
    }

    Node* _maxNode(Node* node) {
        Node* current = node;
        while (current->right != nullptr) {
            current = current->right;
        }
        return current;
    }

    Node* _minNode(Node* node) {
        Node* current = node;
        while (current->left != nullptr) {
            current = current->left;
        }
        return current;
    }

    Node* _delete(Node* node, int key) {
        if (node == nullptr) {
            return node;
        }
        if (key < node->data) {
            node->left = _delete(node->left, key);
        }
        else if (key > node->data) {
            node->right = _delete(node->right, key);
        }
        else {
            if (node->left == nullptr) {
                return node->right;
            }
            else if (node->right == nullptr) {
                return node->left;
            }
            Node* maxNode = _maxNode(node->left);
            node->data = maxNode->data;
            node->left = _delete(node->left, maxNode->data);
        }
        return node;
    }
};

int main() {
    BinarySearchTree b_tree;
    std::vector<int> numbers;
    int number;

    while (std::cin >> number) {
        if (number == 0) break; // Завершаем ввод на нуле
        if (!b_tree.search(number)) {
            b_tree.insert(number);
        }
    }

    b_tree.deleteKey(b_tree.findMax());
    std::cout << b_tree.findMax() << std::endl;

    return 0;
}
