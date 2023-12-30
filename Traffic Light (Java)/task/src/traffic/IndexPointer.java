package traffic;

public class IndexPointer {
    private int current;
    private final int size;


    public IndexPointer(int size) {
        this(size, 0);
    }

    public IndexPointer(int size, int current) {
        this.current = current;
        this.size = size;
    }

    public int get() {
        return current;
    }

    public void next() {
        if (current + 1 >= size) {
            current = 0;
        } else {
            current++;
        }

    }
}
