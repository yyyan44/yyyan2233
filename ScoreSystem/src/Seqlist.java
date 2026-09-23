/**
 * 顺序表（顺序存储结构）的泛型实现。
 * 底层用 Object[] 数组存放元素，逻辑上相邻的元素物理地址也相邻。
 */
class SeqList<T> {
    private Object[] data;   // 存储数据的数组
    private int length;      // 当前表长

    public SeqList(int capacity) {
        if (capacity <= 0) capacity = 10;
        data = new Object[capacity];
        length = 0;
    }

    /** 求表长 */
    public int size() {
        return length;
    }

    /** 判空 */
    public boolean isEmpty() {
        return length == 0;
    }

    /** 判满，满了自动扩容为原来的 2 倍 */
    private void ensureCapacity() {
        if (length == data.length) {
            Object[] newData = new Object[data.length * 2];
            for (int i = 0; i < length; i++) newData[i] = data[i];
            data = newData;
        }
    }

    /** 按位序 i（从 1 开始）取元素 */
    @SuppressWarnings("unchecked")
    public T get(int i) {
        if (i < 1 || i > length)
            throw new IndexOutOfBoundsException("位序非法：" + i);
        return (T) data[i - 1];
    }

    /**
     * 按位序 i（从 1 开始）插入元素 e：
     * 第 i 个及以后的元素从后往前依次后移一位，再把 e 放入第 i 个位置。
     */
    public void insert(int i, T e) {
        if (i < 1 || i > length + 1)
            throw new IndexOutOfBoundsException("插入位序非法：" + i);
        ensureCapacity();
        for (int j = length; j >= i; j--)
            data[j] = data[j - 1];
        data[i - 1] = e;
        length++;
    }

    /** 尾插：追加元素 */
    public void add(T e) {
        insert(length + 1, e);
    }

    /**
     * 按位序 i（从 1 开始）删除元素：
     * 第 i+1 个及以后的元素依次前移一位，表长减 1。返回被删元素。
     */
    @SuppressWarnings("unchecked")
    public T remove(int i) {
        if (i < 1 || i > length)
            throw new IndexOutOfBoundsException("删除位序非法：" + i);
        T old = (T) data[i - 1];
        for (int j = i; j < length; j++)
            data[j - 1] = data[j];
        data[length - 1] = null;
        length--;
        return old;
    }

    /** 按内容查找，返回位序（从 1 开始），找不到返回 -1 */
    public int indexOf(T e) {
        for (int i = 0; i < length; i++) {
            if (data[i].equals(e)) return i + 1;
        }
        return -1;
    }

    /** 遍历输出 */
    public void print(String sep) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < length; i++) {
            sb.append(data[i]);
            if (i < length - 1) sb.append(sep);
        }
        sb.append("]");
        System.out.println(sb);
    }
}
