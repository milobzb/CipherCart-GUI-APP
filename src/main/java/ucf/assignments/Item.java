/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 first_name last_name
 */
package ucf.assignments;

import java.io.Serializable;

/**
 * Item represents the item
 * in the inventory
 */
public class Item implements Serializable {

    /**
     * amount in dollars
     */
    private int value;

    /**
     * serial number
     * format of XXXXXXXXXX where X can be either a letter or digit
     */
    private String serialNumber;

    /**
     * name of item
     * (2 and 256 characters in length)
     */
    private String name;

    /**
     * default constructor
     */
    public Item() {
    }

    /**
     * constructor
     *
     * @param value amount in dollars
     * @param serialNumber serial number
     * @param name name of item
     */
    public Item(int value, String serialNumber, String name) throws IllegalArgumentException {
        setValue(value);
        setSerialNumber(serialNumber);
        setName(name);
    }

    public int getValue() {
        return value;
    }

    /**
     * set amount in dollars
     * @param value
     * @throws IllegalArgumentException
     */
    public void setValue(int value) throws IllegalArgumentException {
        if (value < 0){
            throw new IllegalArgumentException("Amount must greater than or equal zero");
        }
        this.value = value;
    }

    /**
     * getter method of serial number
     * @return serial number
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * setter of serial number
     * format: XXXXXXXXXX where X can be either a letter or digit
     * @param serialNumber serial number
     * @throws IllegalArgumentException
     */
    public void setSerialNumber(String serialNumber) throws IllegalArgumentException {

        //check format
        //XXXXXXXXXX where X can be either a letter or digit
        if (serialNumber == null || serialNumber.length() !=  10){
            throw new IllegalArgumentException("Serial number is invalid format");
        }
        for (int i = 0; i < serialNumber.length(); i++){
            if (!Character.isLetter(serialNumber.charAt(i)) &&
                    !Character.isDigit(serialNumber.charAt(i))){

            }
        }

        this.serialNumber = serialNumber;
    }

    /**
     * get name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * set name
     *
     * 2 and 256 characters in length (inclusive)
     *
     * @param name name
     * @throws IllegalArgumentException
     */
    public void setName(String name) throws IllegalArgumentException {
        if (name == null || name.length() < 2 || name.length() > 256){
            throw new IllegalArgumentException("Name must be 2 and 256 characters in length (inclusive)");
        }
        this.name = name;
    }

    @Override
    public String toString() {
        return value + "\t"+ serialNumber + "\t" + name;
    }
}
