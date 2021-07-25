/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 first_name last_name
 */
package ucf.assignments;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * ItemUtil stores the list of items
 * and method to manipulate them
 */
public class ItemUtil {

    /**
     * inventory items
     */
    private List<Item> values = new ArrayList<>();

    /**
     * constructor
     */
    public ItemUtil() {

    }

    /**
     * constructor
     * @param values
     */
    public ItemUtil(List<Item> values) {
        this.values = values;
    }

    /**
     * getter method of values
     * @return values
     */
    public List<Item> getValues() {
        return values;
    }

    /**
     * file record by name
     * @param name
     * @return list of items by name
     */
    public List<Item> searchByName(String name) {
        List<Item> records = new ArrayList<>();
        for (Item item : values) {
            if (item.getName().equals(name)) {
                records.add(item);
            }
        }

        return records;
    }

    /**
     * search items by serial numbers
     * @param serialNumber serial number
     * @return list of found items
     */
    public List<Item> searchBySerialNumber(String serialNumber) {

        List<Item> records = new ArrayList<>();

        //find record by serial number
        Item item = findRecord(serialNumber);
        if (item != null) {
            records.add(item);
        }
        return records;
    }

    /**
     * sort items by value
     */
    public void sortByValue() {
        Collections.sort(values, new Comparator<Item>() {
            @Override
            public int compare(Item item1, Item item2) {
                return item1.getValue() - item2.getValue();
            }
        });
        //show();
    }

    /**
     * sort items by serial number
     */
    public void sortBySerialNumber() {
        Collections.sort(values, new Comparator<Item>() {
            @Override
            public int compare(Item item1, Item item2) {
                return item1.getSerialNumber().compareTo(item2.getSerialNumber());
            }
        });
        //show();
    }

    /**
     * sort items by name
     */
    public void sortByName() {
        Collections.sort(values, new Comparator<Item>() {
            @Override
            public int compare(Item item1, Item item2) {
                return item1.getName().compareTo(item2.getName());
            }
        });
        //show();
    }

    /**
     * find record by serial number
     * @param serialNumber serial number
     * @return item
     */
    public Item findRecord(String serialNumber) {
        for (Item value : values) {
            if (serialNumber.equals(value.getSerialNumber())) {
                return value;
            }
        }
        return null;
    }

    /**
     * delete record by serial number
     * @param serialNumber serial number
     */
    public void deleteRecord(String serialNumber) {
        for (int i = 0; i < values.size(); i++) {
            Item value = values.get(i);
            if (serialNumber.equals(value.getSerialNumber())) {
                values.remove(i);
            }
        }
    }

    /**
     * remove record from the inventory items
     * @return  true/false
     */
    public boolean removeFirstRecord(){

        //check existing
        if (values.isEmpty()){
            return false;
        }

        values.remove(0);
        return true;
    }

    /**
     * remove record from the inventory items
     * @param item item
     * @return  true/false
     */
    public boolean removeRecord(Item item){

        //check existing
        if (testSerialNumberPresence(item.getSerialNumber())){
            values.remove(item);
            return true;
        }

        return false;
    }

    /**
     * add record to the inventory items
     * @param item new item
     * @throws IllegalArgumentException if serial number existing
     */
    public void addRecord(Item item) throws IllegalArgumentException {

        //check existing
        if (testSerialNumberPresence(item.getSerialNumber())){
            throw new IllegalArgumentException("Serial number is existing");
        }

        values.add(0, item);
    }

    /**
     * test if the serialNumber is existing in inventory items
     * @param serialNumber serial number
     * @return true/false
     */
    public boolean testSerialNumberPresence(String serialNumber) {

        //iterate the inventory item and compare serial number
        for (Item value : values) {
            if (value.getSerialNumber().equals(serialNumber)) {
                return true;
            }
        }

        //not found
        return false;
    }

}
