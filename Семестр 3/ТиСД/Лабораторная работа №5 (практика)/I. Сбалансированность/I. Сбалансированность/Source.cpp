#include <iostream> 
#include <queue> 

using namespace std;

struct Node {
    int data;
    Node* left;
    Node* right;

    Node(int data) : data(data), left(nullptr), right(nullptr) {}
};

class BinarySearchTree {
private:
    Node* root;

    // Вставка нового узла в дерево 
    Node* insert(Node* node, int data) {
        if (node == nullptr) {
            return new Node(data);
        }
        else if (data < node->data) {
            node->left = insert(node->left, data);
        }
        else if (data > node->data) {
            node->right = insert(node->right, data);
        }
        return node;
    }

    // Вычисление высоты дерева 
    int height(Node* node) {
        if (node == nullptr) {
            return 0;
        }
        else {
            int leftHeight = height(node->left);
            int rightHeight = height(node->right);
            return max(leftHeight, rightHeight) + 1;
        }
    }

    // Проверка сбалансированности дерева 
    bool isBalanced(Node* node) {
        if (node == nullptr) {
            return true;
        }

        int leftHeight = height(node->left);
        int rightHeight = height(node->right);

        if (abs(leftHeight - rightHeight) > 1) {
            return false;
        }

        return isBalanced(node->left) && isBalanced(node->right);
    }

public:
    BinarySearchTree() : root(nullptr) {}

    void insert(int data) {
        root = insert(root, data);
    }

    bool isBalanced() {
        return isBalanced(root);
    }
};

int main() {
    int data;
    BinarySearchTree tree;

    std::cin >> data;
    while (data != 0) {
        tree.insert(data);
        std::cin >> data;
    }

    if (tree.isBalanced()) {
        std::cout << "YES" << std::endl;
    }
    else {
        std::cout << "NO" << std::endl;
    }

    return 0;
}
