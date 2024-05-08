class FibonacciHeapNode {
    int key;
    int deg;
    boolean marked;
    FibonacciHeapNode pt;
    FibonacciHeapNode cd;
    FibonacciHeapNode lt;
    FibonacciHeapNode rt;
    public FibonacciHeapNode(int key) {
        this.key = key;
        deg = 0;
        marked = false;
        pt = null;
        cd = null;
        lt = this;
        rt = this;
    }
}
public class FibonacciHeap {
    private FibonacciHeapNode min;
    private int n;

    public FibonacciHeap() {
        min = null;
        n = 0;
    }
    public void insert(int key) {
        FibonacciHeapNode newNode = new FibonacciHeapNode(key);
        if (min == null) {
            min = newNode;
        } else {
            newNode.lt = min.lt;
            newNode.rt = min;
            min.lt.rt = newNode;
            min.lt = newNode;
            if (key < min.key) {
                min = newNode;
            }
        }
        n++;
    }
    public void decreaseKey(FibonacciHeapNode node, int newKey) {
        if (newKey > node.key) {
            System.out.println("New key is greater than the current key.");
            return;
        }
        node.key = newKey;
        FibonacciHeapNode pt = node.pt;
        if (pt != null && node.key < pt.key) {
            cut(node, pt);
            cascadingCut(pt);
        }
        if (node.key < min.key) {
            min = node;
        }
    }
    private void cut(FibonacciHeapNode node, FibonacciHeapNode pt) {
        node.pt = null;
        node.marked = false;
        if (pt.cd == node) {
            pt.cd = node.rt;
        }
        if (pt.deg == 1) {
            pt.cd = null;
        }
        node.lt.rt = node.rt;
        node.rt.lt = node.lt;
        node.lt = min;
        node.rt = min.rt;
        min.rt = node;
        node.rt.lt = node;
        pt.deg--;
    }
    private void cascadingCut(FibonacciHeapNode node) {
        FibonacciHeapNode pt = node.pt;
        if (pt != null) {
            if (!node.marked) {
                node.marked = true;
            } else {
                cut(node, pt);
                cascadingCut(pt);
            }
        }
    }
    public void deleteNode(FibonacciHeapNode node) {
        if (node == min) {
            min = node.rt;
        }
        node.lt.rt = node.rt;
        node.rt.lt = node.lt;
        n--;
    }
    public void printHeap() {
        System.out.print("Fibonacci Heap: ");
        if (min != null) {
            FibonacciHeapNode current = min;
            do {
                System.out.print(current.key + " ");
                current = current.rt;
            }
            while (current != min);
            System.out.println();
        }
        else {
            System.out.println("Empty");
        }
    }
    public static void main(String[] args) {
        FibonacciHeap heap = new FibonacciHeap();
        heap.insert(20);
        heap.insert(10);
        heap.insert(30);
        heap.insert(40);
        heap.insert(5);
        heap.printHeap();
        FibonacciHeapNode nodeToDecrease = heap.min.rt.rt.rt;
        System.out.println("Decreasing key of node with value " + nodeToDecrease.key);
        heap.decreaseKey(nodeToDecrease, 5);
        heap.printHeap();
        FibonacciHeapNode nodeToDelete = heap.min.rt.rt;
        System.out.println("Deleting node with value " + nodeToDelete.key);
        heap.deleteNode(nodeToDelete);
        heap.printHeap();
    }
}



OUTPUT:
Fibonacci Heap: 5 10 20 30 40 
Decreasing key of node with value 30
Fibonacci Heap: 5 10 20 5 40 
Deleting node with value 20
Fibonacci Heap: 5 10 5 40 
