class FibonacciHeapNode {
    int key;
    FibonacciHeapNode pt;
    FibonacciHeapNode cd;
    FibonacciHeapNode lt;
    FibonacciHeapNode rt;
    int deg;
    boolean marked;
    boolean isMin;
    public FibonacciHeapNode(int key) {
        this.key = key;
        this.pt = null;
        this.cd = null;
        this.lt = this;
        this.rt = this;
        this.deg = 0;
        this.marked = false;
        this.isMin = false;
    }
}
public class FibonacciHeap {
    private FibonacciHeapNode minNode;
    private int n;
    public FibonacciHeap() {
        this.minNode = null;
        this.n = 0;
    }
    public boolean isEmpty() {
        return minNode == null;
    }
    public void insert(int key) {
        FibonacciHeapNode newNode = new FibonacciHeapNode(key);
        if (isEmpty()) {
            minNode = newNode;
        }
        else {
            newNode.rt = minNode.rt;
            newNode.lt = minNode;
            minNode.rt.lt = newNode;
            minNode.rt = newNode;
            if (key < minNode.key) {
                minNode = newNode;
            }
        }
        n++;
    }
    public int getMin() {
        return minNode.key;
    }
    public int deleteMin() {
        if (isEmpty()) return Integer.MIN_VALUE;
        FibonacciHeapNode min = minNode;
        if (minNode.cd != null) {
            FibonacciHeapNode cd = minNode.cd;
            do {
                FibonacciHeapNode next = cd.rt;
                minNode.lt.rt = cd;
                cd.rt = minNode.rt;
                minNode.rt.lt = cd;
                cd.lt = minNode.lt;
                cd.pt = null;
                cd = next;
            } while (cd != minNode.cd);
        }
        minNode.lt.rt = minNode.rt;
        minNode.rt.lt = minNode.lt;
        if (minNode == minNode.rt) {
            minNode = null;
        } else {
            minNode = minNode.rt;
            consolidate();
        }
        n--;
        return min.key;
    }
    private void consolidate() {
        int maxDegree = (int) Math.floor(Math.log(n) / Math.log(2));
        FibonacciHeapNode[] array = new FibonacciHeapNode[maxDegree + 1];
        FibonacciHeapNode st = minNode;
        FibonacciHeapNode cur = st;
        do {
            FibonacciHeapNode next = cur.rt;
            int deg = cur.deg;
            while (array[deg] != null) {
                FibonacciHeapNode other = array[deg];
                if (cur.key > other.key) {
                    FibonacciHeapNode temp = cur;
                    cur = other;
                    other = temp;
                }
                link(other, cur);
                array[deg] = null;
                deg++;
            }
            array[deg] = cur;
            cur = next;
        }
        while (cur != st);
        minNode = null;
        for (FibonacciHeapNode node : array) {
            if (node != null) {
                if (minNode == null) {
                    minNode = node;
                } else {
                    node.lt.rt = node.rt;
                    node.rt.lt = node.lt;
                    node.lt = minNode;
                    node.rt = minNode.rt;
                    minNode.rt = node;
                    node.rt.lt = node;
                    if (node.key < minNode.key) {
                        minNode = node;
                    }
                }
            }
        }
    }
    private void link(FibonacciHeapNode cd, FibonacciHeapNode pt) {
        cd.lt.rt = cd.rt;
        cd.rt.lt = cd.lt;
        cd.pt = pt;
        if (pt.cd == null) {
            pt.cd = cd;
            cd.rt = cd;
            cd.lt = cd;
        }
        else {
            cd.lt = pt.cd;
            cd.rt = pt.cd.rt;
            pt.cd.rt = cd;
            cd.rt.lt = cd;
        }
        pt.deg++;
        cd.marked = false;
    }
    public void decreaseKey(FibonacciHeapNode node, int newKey) {
        if (newKey > node.key) {
            System.out.println("New key is greater than current key");
            return;
        }
        node.key = newKey;
        FibonacciHeapNode pt = node.pt;
        if (pt != null && node.key < pt.key) {
            cut(node, pt);
            cascadingCut(pt);
        }
        if (node.key < minNode.key) {
            minNode = node;
        }
    }
    private void cut(FibonacciHeapNode node, FibonacciHeapNode pt) {
        node.lt.rt = node.rt;
        node.rt.lt = node.lt;
        pt.deg--;
        if (pt.cd == node) {
            pt.cd = node.rt;
        }
        if (pt.deg == 0) {
            pt.cd = null;
        }
        node.lt = minNode;
        node.rt = minNode.rt;
        minNode.rt = node;
        node.rt.lt = node;
        node.pt = null;
        node.marked = false;
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
    public static void main(String[] args) {
        FibonacciHeap fibonacciHeap = new FibonacciHeap();
        fibonacciHeap.insert(8);
        fibonacciHeap.insert(16);
        fibonacciHeap.insert(5);
        fibonacciHeap.insert(9);
        fibonacciHeap.insert(6);
        fibonacciHeap.insert(3);
        int minElement = fibonacciHeap.getMin();
        System.out.println("Minimum element of fibonacci heap: " + minElement);
        int deletedMin = fibonacciHeap.deleteMin();
        System.out.println("Deletion of minimum element: " + deletedMin);
        FibonacciHeapNode node = new FibonacciHeapNode(5);
        fibonacciHeap.decreaseKey(node, 20);
        System.out.println("New key node: " + node.key);
    }
}


OUTPUT:
Minimum element of fibonacci heap: 3
Deletion of minimum element: 3
New key is greater than current key
New key node: 5
