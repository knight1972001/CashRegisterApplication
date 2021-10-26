package com.example.cashregister;

import java.util.ArrayList;

public class Utils {
    public ArrayList<Item> createItemData() {
        ArrayList<Item> itemList = new ArrayList<Item>();
        Item pante = new Item("Pante", 10, 20.44);
        Item shoes = new Item("Shoes", 100, 10.44);
        Item hats = new Item("Hats", 30, 5.9);
        itemList.add(pante);
        itemList.add(shoes);
        itemList.add(hats);
        return itemList;
    }

    public String popCharacter(String str) {
        return str.substring(0, str.length() - 1);
    }
}
