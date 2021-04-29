package main.java.exercise;

import main.java.exercise.tree.AVLNode;
import main.java.exercise.tree.Node;
import main.java.framework.StudentInformation;
import main.java.framework.StudentSolution;

public class StudentSolutionImplementation implements StudentSolution {
    @Override
    public StudentInformation provideStudentInformation() {
        return new StudentInformation(
                "Anna", // Vorname
                "Surkova", // Nachname
                "12042425" // Matrikelnummer
        );
    }

    // Implementieren Sie hier Ihre Lösung für das Einfügen in einen einfachen binären Suchbaum
    public void insertSimpleBinarySearchTree(Node root, Node newNode) {
        if (root == null && newNode != null) {
            root = newNode;
        } else if (root != null && newNode != null) {
            Node current = root;
            while (true) {
                if (current.getKey() > newNode.getKey()) {
                    if (current.getLeftChild() != null) {
                        current = current.getLeftChild();
                    } else {
                        current.attachNodeLeft(newNode);
                        break;
                    }
                } else if (current.getKey() < newNode.getKey()) {
                    if (current.getRightChild() != null) {
                        current = current.getRightChild();
                    } else {
                        current.attachNodeRight(newNode);
                        break;
                    }
                } else {
                    break;
                }
            }
        }
    }

    public void insertAVLTree(AVLNode p, AVLNode newNode) {
        if (p == null && newNode != null) {
            p = newNode;
        } else if (p != null && newNode != null) {
            _insertAVLTree(p, newNode);
        }
    }

    private AVLNode _insertAVLTree(AVLNode root, AVLNode newNode) {
        if (root == null) {
            return newNode;
        }

        if (root.getKey() > newNode.getKey()) {
            root.attachNodeLeft(_insertAVLTree(root.getLeftChild(), newNode));
        } else if (root.getKey() < newNode.getKey()) {
            root.attachNodeRight(_insertAVLTree(root.getRightChild(), newNode));
        } else {
            return root;
        }

        root.setHeight(1 + Math.max(AVLNode.height(root.getLeftChild()), AVLNode.height(root.getRightChild())));
        int balance = AVLNode.height(root.getLeftChild()) - AVLNode.height(root.getRightChild());

        if (balance > 1 && root.getLeftChild().getKey() > newNode.getKey()) {
            root.rotateToRight();
        } else if (balance < -1 && root.getRightChild().getKey() < newNode.getKey()) {
            root.rotateToLeft();
        } else if (balance > 1 && root.getLeftChild().getKey() < newNode.getKey()) {
            root.doubleRotateLeftRight();
        } else if (balance < -1 && root.getRightChild().getKey() > newNode.getKey()) {
            root.doubleRotateRightLeft();
        }

        return root;
    }

}
