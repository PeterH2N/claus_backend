package claus.backend.elements;

import claus.backend.domain.elements.CategoryDAO;

import java.util.ArrayList;
import java.util.List;


public class Tree<T> {
    private final Node<T> root;

    public Tree() {
        root = new Node<>();
    }

    public Tree(T rootData) {
        root = new Node<>();
        root.data = rootData;
        root.children = new ArrayList<>();
    }

    @Override
    public String toString()
    {
        return root.toString();
    }

    public Node<T> getRoot()
    {
        return root;
    }

    public static class Node<T> {
        private T data;
        private Node<T> parent;
        private List<Node<T>> children = new ArrayList<>();


        public Node<T> addChild(T childData) {
            Node<T> child = new Node<>();
            child.parent = this;
            child.data = childData;
            children.add(child);
            return child;
        }

        public T getData()
        {
            return data;
        }

        public Node<T> getParent()
        {
            return parent;
        }

        public List<Node<T>> getChildren()
        {
            return children;
        }

        @Override
        public String toString()
        {
            var builder = new StringBuilder();
            if (data != null)
                builder.append(data);

            for (var child : children) {
                builder.append(child);
            }

            return builder.toString();
        }
    }
}