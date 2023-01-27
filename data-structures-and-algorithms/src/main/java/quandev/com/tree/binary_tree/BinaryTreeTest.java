package quandev.com.tree.binary_tree;

import lombok.Data;
import org.junit.jupiter.api.Test;

public class BinaryTreeTest {

    @Data
    public class Node<T> {
        T data;
        Node<T> left;
        Node<T> right;
    }

    <T> T inorder(Node<T> root) {
        if (root == null) {
            return null;
        }

        inorder(root.left);
        System.out.printf("%s ", root.data);
        inorder(root.right);

        return null;
    }

    <T> T preorder(Node<T> root) {
        if (root == null)
            return null;

        System.out.printf("%s ", root.data);
        preorder(root.left);
        preorder(root.right);
        return null;
    }

    <T> T postorder(Node<T> root) {
        if (root == null)
            return null;
        postorder(root.left);
        postorder(root.right);
        System.out.printf("%s ", root.data);
        return null;
    }

    <T> Node<T> create(T a) {
        Node<T> newNode = new Node<>();
        newNode.data = a;
        newNode.left = null;
        newNode.right = null;
        return newNode;
    }

    <T> Node<T> addLeftNode(Node<T> root, T a) {
        root.left = create(a);
        return root.left;
    }

    <T> Node<T> addRightBode(Node<T> root, T a) {
        root.right = create(a);
        return root.right;
    }

    @Test
    public void main() {
        Node<Integer> root = create(34);
        addLeftNode(root , 32);
        addLeftNode(root.left , 42);
        addRightBode(root.left , 93);

        System.out.println("\nDuyệt theo cách Inorder\n");
        inorder(root);

        System.out.println("\nDuyệt theo cách Preorder\n");
        preorder(root);

        System.out.println("\nDuyệt theo cách Postorder\n");
        postorder(root);
    }

}
