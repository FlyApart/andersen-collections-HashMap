package com.andersenlab;

import java.util.Objects;

public class HashMapa<K,V> {

    private double loadFactor = 0.75;
    private int capacity = 16;
    private Node<K,V>[] table;
    private int size;

    public HashMapa(double loadFactor, int capacity) {
        this.loadFactor = loadFactor;
        this.capacity = capacity;
        this.capacity = (int)(capacity * loadFactor);
        table = new Node[capacity];
    }

    public HashMapa(){
        table = new Node[capacity];
    }

    public V put(K key, V value) {
        if (table != null){
            resize();
            if (key == null){
                return putForNullKey(value);
            }

            int hash = hash(key);
            int bucket = indexFor(hash, capacity);
            Node<K,V> currentNode = table[bucket];

            while (currentNode != null){
                if(currentNode.getKey() != null && currentNode.getHash() == hash && (currentNode.getKey() == key || currentNode.getKey().equals(key))){
                    currentNode.setValue(value);
                    return value;
                }
                currentNode = currentNode.getNext();
            }
            addEntry(hash,key,value,bucket);
            size++;
            return value;
        }
        return null;
    }

    private int hash(K key) {
        int h = 0;
        if(key != null){
            h = key.hashCode() ^ (h >>> 16);
        }
        return h;
    }

    private int indexFor(int h, int length) {
        return h & (length - 1);
    }

    private V putForNullKey(V value){
        Node<K,V> currentNode = table[0];

        while (currentNode != null){
            if(currentNode.getKey() == null){
                currentNode.setValue(value);
                return value;
            }
            currentNode = currentNode.getNext();
        }
        addEntry(0,null,value,0);
        size++;
        return value;
    }

    private void addEntry(int hash, K key, V value, int index) {
        Node<K,V> currentNode = table[index];
        table[index] = new Node<>(hash, key, value, currentNode);
    }

    public V get(K key) {
        Node<K,V> node = getNode(hash(key), key);
        return node == null ? null : node.getValue();
    }

    private Node<K,V> getNode(int hash, K key){
        if (table != null && size > 0){
            int bucket = indexFor(hash, capacity);
            Node<K,V> currentNode = table[bucket];
            while (currentNode != null){
                if(currentNode.getHash() == hash && (currentNode.getKey() == key || currentNode.getKey().equals(key))){
                    return currentNode;
                }
                currentNode = currentNode.getNext();
            }
        }
        return null;
    }

    private void resize() {
        if(size > capacity * loadFactor){
            capacity = capacity * 2;
            Node<K,V>[] newTable = new Node[capacity];
            transfer(newTable);
            table = newTable;
        }
    }

    private void transfer(Node<K,V>[] newTable) {
        int bucket;
        Node<K,V> node;
        Node<K,V> nextNode;
        for (Node<K, V> kvNode : table) {
            node = kvNode;

            while (node != null){
                bucket = indexFor(node.getHash(), capacity);
                nextNode = node.getNext();
                node.setNext(newTable[bucket]);
                newTable[bucket] = node;
                node = nextNode;
            }
        }
    }

    public V remove(K key) {
        if (table != null && size > 0){
            int hash = hash(key);
            int bucket = indexFor(hash, capacity);
            Node<K,V> currentNode = table[bucket];
            Node<K,V> previousNode = null;

            while (currentNode != null){
                if (currentNode.getHash() == hash && (currentNode.getKey() == key || currentNode.getKey().equals(key))){
                    if (previousNode == null){
                        table[bucket] = currentNode.getNext();
                    }else {
                        previousNode.setNext(currentNode.getNext());
                    }
                    size--;
                    return currentNode.getValue();
                }
                previousNode = currentNode;
                currentNode = currentNode.getNext();
            }
        }
        return null;
    }

    public int size() {
        return table != null ? size : 0;
    }

    public boolean isEmpty() {
        return table == null || size == 0;
    }


    public boolean containsKey(K key) {
        return getNode(hash(key), key) != null;
    }

    public boolean containsValue(V value) {

        if (table != null && size > 0) {
            for (Node<K,V> e : table) {
                for (; e != null; e = e.next) {
                    if (Objects.equals(value, e.value))
                        return true;
                }
            }
        }
        return false;
    }

    public void clear() {
        table = new Node[capacity];
        size = 0;
    }

    static class Node<K,V> {
        private final int hash;
        private final K key;
        private V value;
        private Node<K,V> next;

        Node(int hash, K key, V value, Node<K,V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public int getHash() {
            return hash;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public Node<K, V> getNext() {
            return next;
        }

        public void setNext(Node<K, V> next) {
            this.next = next;
        }

        public final String toString() { return key + "=" + value; }
    }
}