#include <iostream> 
#include <queue> 
#include <map> 

struct Node {
    int data;
    int count;
    Node* left;
    Node* right;

    Node(int data) : data(data), count(1), left(nullptr), right(nullptr) {}
};

class BinarySearchTree {
private:
    Node* root;

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
        else {
            node->count++;
        }
        return node;
    }

    void inorderTraversal(Node* node) {
        if (node != nullptr) {
            inorderTraversal(node->left);
            std::cout << node->data << " " << node->count << std::endl;
            inorderTraversal(node->right);
        }
    }

public:
    BinarySearchTree() : root(nullptr) {}

    void insert(int data) {
        root = insert(root, data);
    }

    void printInorder() {
        inorderTraversal(root);
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

    tree.printInorder();

    return 0;
}
