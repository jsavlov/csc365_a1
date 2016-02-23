package com.jasonsavlov;

/**
 * Created by jason on 2/23/16.
 */
public class JSHashTable
{
    // The default bucket count, in case one isn't provided in the constructor
    public static final int DEFAULT_BUCKET_COUNT = 128;

    // Instance variables
    private int entry_count = 0;
    private int bucket_count = 0;

    // buckets for Nodes
    private Node[] buckets;

    // Internal Node class used as buckets for maintaining word frequency
    private static class Node
    {
        String key;
        int frequency = 0;
        Node next;

        public Node(String k) {
            key = k;
            //frequency++;
        }

        public Node addNode(String k) {
            if (this.next != null) {
                return this.next.addNode(k);
            } else {
                this.next = new Node(k);
                return this.next;
            }
        }

        public Node(Node n) {
            key = n.key;
            frequency = n.frequency;
        }
    }

    public JSHashTable()
    {
        bucket_count = DEFAULT_BUCKET_COUNT;
        buckets = new Node[bucket_count];
    }

    public JSHashTable(int initial_buckets)
    {
        bucket_count = initial_buckets;
        buckets = new Node[bucket_count];
    }

    public int getHash(String k)
    {
        return Math.abs(k.hashCode());
    }

    public boolean add(String k)
    {
        if ((float) entry_count / (float) bucket_count > 0.66666667) {
            this.resizeTable();
        }

        int hash = getHash(k);
        int bucket_pos = Math.abs(hash % bucket_count);

        Node node = buckets[bucket_pos];
        if (node != null) {
            while (node.next != null) {
                if (node.key.equalsIgnoreCase(k)) {
                    node.frequency++;
                    break;
                }
                node = node.next;
            }
            node.next = new Node(k);
            node.next.frequency++;
            this.entry_count++;
            return true;
        } else {
            buckets[bucket_pos] = new Node(k);
            buckets[bucket_pos].frequency++;
            this.entry_count++;
            return true;
        }
    }

    private int resizeTable()
    {
        int newSize = bucket_count * 2;
        Node[] newBuckets = new Node[newSize];

        for (Node n : this.buckets)
        {
            if (n == null) continue;

            String nKey = n.key;
            Node cNode = n;
            do {
                int newHash = getHash(nKey);
                newBuckets[newHash % newSize] = new Node(n);

                cNode = cNode.next;
            } while (cNode != null);
        }

        this.buckets = newBuckets;
        this.bucket_count = newSize;

        return newSize;
    }

}
