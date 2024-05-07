class MergeableHeap<FH extends Comparable<FH>> {
    private Node<FH> root;
    private int N;
    private static class Node<FH> {
        FH key;
        int deg;
        boolean marked;
        Node<FH> pt;
        Node<FH> cd;
        Node<FH> lt;
        Node<FH> rt;
        public Node(FH key) {
            this.key = key;
            this.deg = 0;
            this.marked = false;
            this.pt = null;
            this.cd = null;
            this.lt = this;
            this.rt = this;
        }
    }
    public MergeableHeap() {
        this.root = null;
        this.N = 0;
    }
    public void merge(MergeableHeap<FH> other) {
        if (other == null || other.root == null) return;
        if (this.root == null) {
            this.root = other.root;
            this.N = other.N;
        } else {
            Node<FH> thisLast = this.root.lt;
            Node<FH> otherLast = other.root.lt;
            thisLast.rt = other.root;
            other.root.lt = thisLast;
            this.root.lt = otherLast;
            otherLast.rt = this.root;
            if (other.root.key.compareTo(this.root.key) < 0)
                this.root = other.root;
            this.N = this.N + other.N;
        }
    }
    public void union(MergeableHeap<FH> other) {
        merge(other);
    }
    public void printHeap() {
        System.out.print("Elements in heap:");
        if (this.root != null)
            printHeap(this.root);
        System.out.println();
    }
    private void printHeap(Node<FH> node) {
        Node<FH> current = node;
        do {
            System.out.print(current.key + " ");
            if (current.cd != null) {
                printHeap(current.cd);
            }
            current = current.rt;
        } while (current != node && current != null);
    }
    public static void main(String[] args) {
        MergeableHeap<Integer> heap1 = new MergeableHeap<>();
        heap1.insert(1);
        heap1.insert(3);
        heap1.insert(5);
        heap1.insert(7);
        heap1.insert(9);
        MergeableHeap<Integer> heap2 = new MergeableHeap<>();
        heap2.insert(2);
        heap2.insert(4);
        heap2.insert(6);
        heap2.insert(8);
        heap2.insert(10);
        System.out.println("1st Heap:");
        heap1.printHeap();
        System.out.println("2nd Heap:");
        heap2.printHeap();
        heap1.union(heap2);
        System.out.println("Merging of two heaps elements:");
        heap1.printHeap();
    }
    public void insert(FH key) {
        Node<FH> newNode = new Node<>(key);
        if (this.root == null) {
            this.root = newNode;
        } else {
            Node<FH> current = this.root.lt;
            newNode.lt = current;
            newNode.rt = current.rt;
            current.rt = newNode;
            newNode.rt.lt = newNode;
            if (key.compareTo(this.root.key) < 0)
                this.root = newNode;
        }
        this.N++;
    }
}


OUTPUT:
1st Heap:
Elements in heap:1 3 5 7 9 
2nd Heap:
Elements in heap:2 4 6 8 10 
Merging of two heaps elements:
Elements in heap:1 3 5 7 9 2 4 6 8 10 
