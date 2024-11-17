#include <iostream>
#include <vector>
#include <algorithm> // дл€ std::max

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
        root = insert(root, key);
    }

    Node* insert(Node* node, int key) {
        if (node == nullptr) {
            return new Node(key);
        }
        if (key < node->data) {
            node->left = insert(node->left, key);
        }
        else if (key > node->data) {
            node->right = insert(node->right, key);
        }
        return node;
    }

    bool search(int key) {
        return search(root, key);
    }

    bool search(Node* node, int key) {
        if (node == nullptr) {
            return false;
        }
        if (node->data == key) {
            return true;
        }
        if (key < node->data) {
            return search(node->left, key);
        }
        return search(node->right, key);
    }

    int find_max() {
        if (root == nullptr) {
            return -1; // ƒл€ обозначени€ отсутстви€ максимального значени€
        }
        return max_node(root)->data;
    }

    Node* max_node(Node* node) {
        Node* current = node;
        while (current->right != nullptr) {
            current = current->right;
        }
        return current;
    }

    int find_min() {
        if (root == nullptr) {
            return -1; // ƒл€ обозначени€ отсутстви€ минимального значени€
        }
        return min_node(root)->data;
    }

    Node* min_node(Node* node) {
        Node* current = node;
        while (current->left != nullptr) {
            current = current->left;
        }
        return current;
    }

    void delete_node(int key) {
        root = delete_node(root, key);
    }

    Node* delete_node(Node* node, int key) {
        if (node == nullptr) {
            return node;
        }
        if (key < node->data) {
            node->left = delete_node(node->left, key);
        }
        else if (key > node->data) {
            node->right = delete_node(node->right, key);
        }
        else {
            // узел с одним или ни одним дочерним узлом
            if (node->left == nullptr) {
                return node->right;
            }
            else if (node->right == nullptr) {
                return node->left;
            }
            // узел с двум€ дочерними узлами
            Node* maxNode = max_node(node->left);
            node->data = maxNode->data;
            node->left = delete_node(node->left, maxNode->data);
        }
        return node;
    }

    void print_tree(Node* node, int level) {
        if (node == nullptr) {
            return;
        }
        print_tree(node->left, level + 1);
        for (int i = 0; i < level; i++) {
            std::cout << ".";
        }
        std::cout << node->data << std::endl;
        print_tree(node->right, level + 1);
    }

    int find_height(Node* node, int level) {
        if (node == nullptr) {
            return 0;
        }
        return std::max(level, std::max(find_height(node->left, level + 1), find_height(node->right, level + 1)));
    }
};

int main() {
    BinarySearchTree b_tree;
    std::vector<int> numbers;
    int number;

    while (std::cin >> number) {
        if (number == 0) break; // «авершаем ввод на нуле
        if (!b_tree.search(number)) {
            b_tree.insert(number);
        }
    }

    std::cout << b_tree.find_height(b_tree.root, 1) << std::endl;

    return 0;
}
