package com.andersenlab;

public class Main {
  public static void main(String[] args) {
    HashMapa<Integer, Integer> mapa = new HashMapa<>();

    mapa.put(null,0);
    putInMap(mapa);

    System.out.println(mapa.size());
    mapa.remove(68);
    mapa.remove(84);
    System.out.println(mapa.size());

    System.out.println(mapa.isEmpty());

    System.out.println("mapa.get(68)    "+mapa.get(68));
    System.out.println("mapa.get(87)    "+mapa.get(87));
    System.out.println("mapa.get(2)    "+mapa.get(2));

    System.out.println("mapa.containsKey(4))    "+mapa.containsKey(4));
    System.out.println("mapa.containsKey(68))    "+mapa.containsKey(68));

    System.out.println("mapa.containsValue(16))    "+mapa.containsValue(16));
    System.out.println("mapa.containsValue(99999))    "+mapa.containsValue(99999));
    mapa.put(null,null);
    mapa.clear();
    System.out.println("mapa.clear(); mapa.size()    "+mapa.size());
  }
  public static void putInMap (HashMapa<Integer,Integer> mapa){
    for (int i = 0; i < 100; i++) {
      mapa.put(i,i*i);
    }
  }
}
