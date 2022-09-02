package com.epam.rd.autocode.bstprettyprint;

public interface PrintableTree {

    void add(int i);
    String prettyPrint();

    static PrintableTree getInstance() {

        return new PrintableTree() {
            Node root;

            @Override
            public void add(int i) {
                root = insert(root, i);
            }

            private Node insert(Node root, int data) {
                if (root == null) {
                    root = new Node(data);
                    return root;
                }
                if (data < root.data)
                    root.leftChildren = insert(root.leftChildren, data);
                else if (data > root.data)
                    root.rightChildren = insert(root.rightChildren, data);
                return root;
            }

            @Override
            public String prettyPrint() {
                String toSplit = pretty(root,"",1);
                String[] lines = toSplit.split("\n");
                StringBuilder builder = new StringBuilder();
                for (String line : lines) {
                    if(line.charAt(line.length() - 1)==' ')
                        builder.append(line, 1, line.length()-1);
                    else
                        builder.append(line.substring(1));
                    builder.append("\n");
                }
                return builder.toString();
            }

            private String pretty(Node root, String prefix, int dir) {
                if (root == null) {
                    return "";
                }

                String space = " ".repeat(("" + root.data).length());
                
                return pretty(root.leftChildren, prefix + "│  ".charAt(dir) + space, 2)
                        + prefix + "└ ┌".charAt(dir) + root.data
                        + " ┘┐┤".charAt((root.rightChildren != null ? 2 : 0)
                        + (root.leftChildren != null ? 1 : 0)) + "\n"
                        + pretty(root.rightChildren, prefix + "  │".charAt(dir) + space, 0);
            }
        };
    }

    class Node {
        int data;
        Node leftChildren, rightChildren;

        Node(int data) {
            this.data = data;
            this.leftChildren = null;
            this.rightChildren = null;
        }
    }
}
