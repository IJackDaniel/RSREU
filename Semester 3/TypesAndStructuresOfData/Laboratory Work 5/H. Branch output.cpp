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
            return -1;
        }
        return _maxNode(root)->data;
    }

    int findMin() {
        if (root == nullptr) {
            return -1;
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

    std::vector<int> find_leaves(Node* node, std::vector<int> leaves) {
        if (node == nullptr) {
            return {};
        }
        else if (node->left == nullptr and node->right == nullptr) {
            return { node->data };
        }
        std::vector<int> arr1 = find_leaves(node->left, leaves);
        std::vector<int> arr2 = find_leaves(node->right, leaves);
        leaves.insert(leaves.end(), arr1.begin(), arr1.end());
        leaves.insert(leaves.end(), arr2.begin(), arr2.end());
        return leaves;
    }

    void find_forks(Node* node, std::vector<int>& result) {
        if (node == nullptr) {
            return;
        }

        find_forks(node->left, result);

        if (node->left != nullptr && node->right != nullptr) {
            result.push_back(node->data);
        }

        find_forks(node->right, result);
    }

    void find_branch(Node* node, std::vector<int>& result) {
        if (node == nullptr) {
            return;
        }

        find_branch(node->left, result);

        if ((node->left != nullptr or node->right != nullptr) and !(node->left != nullptr and node->right != nullptr)) {
            result.push_back(node->data);
        }

        find_branch(node->right, result);
    }

    int count_elem(Node* node) {
        if (node == nullptr) {
            return 1;
        }
        return count_elem(node->left) + count_elem(node->right);
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
        if (number == 0) break;
        if (!b_tree.search(number)) {
            b_tree.insert(number);
        }
    }

    std::vector<int> result;
    b_tree.find_branch(b_tree.root, result);
    for (int i = 0; i < result.size(); i++) {
        std::cout << result[i] << std::endl;
    }

    return 0;
}
