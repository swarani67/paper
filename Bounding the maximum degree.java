import java.util.ArrayList;
import java.util.List;

class FibonacciHeapNode {
    int key;
    int deg;
    boolean marked;
    FibonacciHeapNode cd;
    FibonacciHeapNode next;
    FibonacciHeapNode prev;
    FibonacciHeapNode pt;
}
class FibonacciHeap {
    private FibonacciHeapNode min;
    private int n;
    private int maxDegree;
    private List<Integer> keys;
    public FibonacciHeap(int maxDegree) {
        this.maxDegree = maxDegree;
        this.min = null;
        this.n = 0;
        keys = new ArrayList<>();
    }
    public void insert(int key) {
        FibonacciHeapNode newNode = new FibonacciHeapNode();
        newNode.key = key;
        newNode.deg = 0;
        newNode.marked = false;
        if (min == null) {
            min = newNode;
            min.next = min;
            min.prev = min;
        }
        else {
            newNode.next = min.next;
            min.next.prev = newNode;
            min.next = newNode;
            newNode.prev = min;
            if (newNode.key < min.key)
                min = newNode;
        }
        keys.add(key);
        n++;
        consolidate();
    }
    public void union(FibonacciHeap other) {
        if (other == null) return;
        if (other.min == null) return;
        FibonacciHeapNode temp = min.next;
        min.next = other.min.next;
        other.min.next.prev = min;
        other.min.next = temp;
        temp.prev = other.min;
        if (other.min.key < min.key)
            min = other.min;
        n += other.n;
        keys.addAll(other.keys);
        consolidate();
    }
    public int extractMin() {
        FibonacciHeapNode z = min;
        if (z != null) {
            FibonacciHeapNode x = z.cd;
            FibonacciHeapNode temp;
            int numKids = z.deg;
            while (numKids > 0) {
                temp = x.next;
                x.prev.next = x.next;
                x.next.prev = x.prev;
                x.prev = min;
                x.next = min.next;
                min.next = x;
                x.next.prev = x;
                x.pt = null;
                x = temp;
                numKids--;
            }
            z.prev.next = z.next;
            z.next.prev = z.prev;
            if (z == z.next)
                min = null;
            else {
                min = z.next;
                consolidate();
            }
            n--;
        }
        return z.key;
    }
    private void consolidate() {
        List<FibonacciHeapNode> degreeTable = new ArrayList<>();
        for (int i = 0; i < maxDegree + 1; i++)
            degreeTable.add(null);
        int numRoots = 0;
        FibonacciHeapNode x = min;
        if (x != null) {
            numRoots++;
            x = x.next;
            while (x != min) {
                numRoots++;
                x = x.next;
            }
        }
        while (numRoots > 0) {
            int d = x.deg;
            FibonacciHeapNode next = x.next;
            while (degreeTable.get(d) != null) {
                FibonacciHeapNode y = degreeTable.get(d);
                if (x.key > y.key) {
                    FibonacciHeapNode temp = y;
                    y = x;
                    x = temp;
                }
                degreeTable.set(d, null);
                d++;
            }
            degreeTable.set(d, x);
            x = next;
            numRoots--;
        }
        min = null;
        for (int i = 0; i < degreeTable.size(); i++) {
            FibonacciHeapNode y = degreeTable.get(i);
            if (y != null) {
                if (min == null) {
                    min = y;
                    min.next = min;
                    min.prev = min;
                } else {
                    y.prev = min;
                    y.next = min.next;
                    min.next.prev = y;
                    min.next = y;

                    if (y.key < min.key)
                        min = y;
                }
            }
        }
    }
    public List<Integer> getKeys() {
        return keys;
    }
}
public class Main {
    public static void main(String[] args) {
        int maxDegree = 2;
        FibonacciHeap fibonacciHeap = new FibonacciHeap(maxDegree);
        fibonacciHeap.insert(5);
        fibonacciHeap.insert(10);
        fibonacciHeap.insert(3);
        fibonacciHeap.insert(7);
        System.out.println("Inserted elements: " + fibonacciHeap.getKeys());
        FibonacciHeap fibonacciHeap2 = new FibonacciHeap(maxDegree);
        fibonacciHeap2.insert(8);
        fibonacciHeap2.insert(6);
        fibonacciHeap.union(fibonacciHeap2);
        System.out.println("Elements after union: " + fibonacciHeap.getKeys());
        System.out.println("Minimum elements in heap: " + fibonacciHeap.extractMin());
    }
}


OUTPUT:
Inserted elements: [5, 10, 3, 7]
Elements after union: [5, 10, 3, 7, 8, 6]
Minimum elements in heap: 3
