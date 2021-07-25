package ucf.assignments;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    /**
     * Test inventory item
     * 1.	Each inventory item shall have a value representing
     * its monetary value in US dollars
     *
     * 2.	Each inventory item shall have a unique serial number
     * in the format of XXXXXXXXXX where X can be either a letter or digit
     *
     * 3.	Each inventory item shall have a name between
     * 2 and 256 characters in length (inclusive)
     */
    @Test
    public void testInventoryItem(){

        //validate value
        try{
            new Item(-1, "AX00000001", "shield");
            fail();
        }catch(IllegalArgumentException e){

        }

        //validate serial number, length is not 10
        try{
            //length
            new Item(-1, "123456789", "shield");
            fail();
        }catch(IllegalArgumentException e){

        }

        //validate serial number, serial number is null
        try{
            //check null
            new Item(-1, null, "shield");
            fail();
        }catch(IllegalArgumentException e){

        }

        //validate serial number, serial number is not digit or letter
        try{
            //check not digit or letter
            new Item(-1, "!@#$%^&*()", "shield");
            fail();
        }catch(IllegalArgumentException e){

        }

        //validate name, check null
        try{
            //check null
            new Item(-1, "AX00000001", null);
            fail();
        }catch(IllegalArgumentException e){

        }

        //validate name, check length
        try{
            //check null
            new Item(-1, "AX00000001", "A");
            fail();
        }catch(IllegalArgumentException e){

        }

        Item item = new Item(100, "AX00000001", "shield");
        assertEquals(100, item.getValue());
        assertEquals("AX00000001", item.getSerialNumber());
        assertEquals("shield", item.getName());

    }

    /**
     * Test add new inventory item
     * Test the following requirement:
     * The application shall display an error message
     * if the user enters an existing serial number for the new item
     */
    @Test
    public void testAddNewInventoryItem(){
        Item item1 = new Item(100, "AX00000001", "shield");
        Item item2 = new Item(100, "AX00000001", "shield");

        ItemUtil inventory = new ItemUtil();
        inventory.addRecord(item1);

        //validate existing serial number
        try{
            inventory.addRecord(item2);
            fail();
        }catch(IllegalArgumentException e){

        }
        assertEquals(1, inventory.getValues().size());

    }

    /**
     * Test remove an existing inventory item
     */
    @Test
    public void testRemoveInventoryItem(){

        Item item1 = new Item(100, "AX00000001", "shield");
        Item item2 = new Item(102, "AX00000002", "broom");

        ItemUtil inventory = new ItemUtil();
        inventory.addRecord(item1);
        inventory.addRecord(item2);

        assertEquals(2, inventory.getValues().size());
        assertEquals(true, inventory.removeRecord(item1));
        assertEquals(true, inventory.removeRecord(item2));
        assertEquals(0, inventory.getValues().size());
        assertEquals(false, inventory.removeRecord(item2));
    }

    /**
     * Test edit the value of an existing inventory item
     */
    @Test
    public void testEditValueInventoryItem(){

        Item item1 = new Item(100, "AX00000001", "shield");
        //validate value
        try{
            item1.setValue(-10);
            fail();
        }catch(IllegalArgumentException e){

        }
        item1.setValue(300);
        assertEquals(300, item1.getValue());
    }

    /**
     * Test edit the serial number of an existing inventory item
     * Test the following requirement:
     * The application shall prevent the user from duplicating
     * the serial number
     */
    @Test
    public void testEditSerialNumberInventoryItem(){
        Item item1 = new Item(100, "AX00000001", "shield");

        //validate serial number, length is not 10
        try{
            //length
            item1.setSerialNumber("ABC");
            fail();
        }catch(IllegalArgumentException e){

        }

        //validate serial number, serial number is null
        try{
            //check null
            item1.setSerialNumber(null);
            fail();
        }catch(IllegalArgumentException e){

        }

        //validate serial number, serial number is not digit or letter
        try{
            //check not digit or letter
            item1.setSerialNumber("#$%");
            fail();
        }catch(IllegalArgumentException e){

        }
        item1.setSerialNumber("AX00000001");
        assertEquals("AX00000001", item1.getSerialNumber());
    }

    /**
     * Test sort the inventory items by value
     */
    @Test
    public void testSortyByValue(){
        Item item1 = new Item(350, "AX00000001", "shield");
        Item item2 = new Item(567, "AX00000002", "broom");
        Item item3 = new Item(300, "AX00000003", "room");

        ItemUtil inventory = new ItemUtil();
        inventory.addRecord(item1);
        inventory.addRecord(item2);
        inventory.addRecord(item3);

        inventory.sortByValue();

        //test after sorting
        assertEquals(item3, inventory.getValues().get(0));
        assertEquals(item1, inventory.getValues().get(1));
        assertEquals(item2, inventory.getValues().get(2));
    }

    /**
     * Test sort the inventory items by serial number
     */
    @Test
    public void testSortBySerialNumber(){
        Item item1 = new Item(350, "AX00000009", "shield");
        Item item2 = new Item(567, "AX00000008", "broom");
        Item item3 = new Item(300, "AX00000007", "room");

        ItemUtil inventory = new ItemUtil();
        inventory.addRecord(item1);
        inventory.addRecord(item2);
        inventory.addRecord(item3);

        inventory.sortBySerialNumber();

        //test after sorting
        assertEquals(item3, inventory.getValues().get(0));
        assertEquals(item2, inventory.getValues().get(1));
        assertEquals(item1, inventory.getValues().get(2));
    }

    /**
     * Test sort the inventory items by name
     */
    @Test
    public void testSortByName(){
        Item item1 = new Item(350, "AX00000009", "shield");
        Item item2 = new Item(567, "AX00000008", "broom");
        Item item3 = new Item(300, "AX00000007", "room");

        ItemUtil inventory = new ItemUtil();
        inventory.addRecord(item1);
        inventory.addRecord(item2);
        inventory.addRecord(item3);

        inventory.sortByName();

        //test after sorting
        assertEquals(item2, inventory.getValues().get(0));
        assertEquals(item3, inventory.getValues().get(1));
        assertEquals(item1, inventory.getValues().get(2));
    }

    /**
     * Test search for an inventory item by serial number
     */
    @Test
    public void testSearchBySerialNumber(){
        Item item1 = new Item(350, "AX00000009", "shield");
        Item item2 = new Item(567, "AX00000008", "broom");
        Item item3 = new Item(300, "AX00000007", "room");

        ItemUtil inventory = new ItemUtil();
        inventory.addRecord(item1);
        inventory.addRecord(item2);
        inventory.addRecord(item3);

        List<Item> records = inventory.searchBySerialNumber("AX00000007");
        assertEquals(item3, records.get(0));

        records = inventory.searchBySerialNumber("AX00000999");
        assertTrue(records.isEmpty());
    }

    /**
     * Test search for an inventory item by name
     */
    @Test
    public void testSearchByName(){
        Item item1 = new Item(350, "AX00000009", "shield");
        Item item2 = new Item(567, "AX00000008", "broom");
        Item item3 = new Item(300, "AX00000007", "room");
        Item item4 = new Item(300, "AX00000010", "room");

        ItemUtil inventory = new ItemUtil();
        inventory.addRecord(item1);
        inventory.addRecord(item2);
        inventory.addRecord(item3);
        inventory.addRecord(item4);

        List<Item> records = inventory.searchByName("broom");
        assertEquals(item2, records.get(0));

        records = inventory.searchByName("abc");
        assertTrue(records.isEmpty());

        records = inventory.searchByName("room");
        assertEquals(2, records.size());

        assertEquals(item3, records.get(1));
        assertEquals(item4, records.get(0));
    }

    /**
     * Test save inventory items to a file in TSV (tab-separated value) format
     * Test the following requirement:
     * TSV files shall shall list one inventory item per line,
     * separate each field within an inventory item using a tab character,
     * and end with the extension .txt
     *
     * Test loading the TSV file into inventory items
     */
    @Test
    public void testSaveTSVFormatFile(){
        Item item1 = new Item(350, "AX00000009", "shield");
        Item item2 = new Item(567, "AX00000008", "broom");
        Item item3 = new Item(300, "AX00000007", "room");

        ItemUtil inventory = new ItemUtil();
        inventory.addRecord(item1);
        inventory.addRecord(item2);
        inventory.addRecord(item3);

        try {

            /**
             * Save file tsv1.txt (json format)
             * read file into inventory items
             * Test the size of list of items
             * and test the items (compare by toString method)
             */

            FileUtil.saveAsTSVFile(inventory, new File("tsv1.txt"));

            ItemUtil savedInventory = FileUtil.readTSVFile(new File("tsv1.txt"));
            assertEquals(3, savedInventory.getValues().size());
            assertEquals(item3.toString(), savedInventory.getValues().get(0).toString());
            assertEquals(item2.toString(), savedInventory.getValues().get(1).toString());
            assertEquals(item1.toString(), savedInventory.getValues().get(2).toString());

            //delete file after testing
            File savedFile = new File("tsv1.txt");
            assertTrue(savedFile.delete());

        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Test save inventory items to a file in HTML
     * Test the following requirement:
     * HTML files shall contain valid HTML and end with the extension .html
     *
     * Test loading the HTML file into inventory items
     */
    @Test
    public void testSaveHTMLFormatFile(){
        Item item1 = new Item(350, "AX00000009", "shield");
        Item item2 = new Item(567, "AX00000008", "broom");
        Item item3 = new Item(300, "AX00000007", "room");

        ItemUtil inventory = new ItemUtil();
        inventory.addRecord(item1);
        inventory.addRecord(item2);
        inventory.addRecord(item3);

        try {

            /**
             * Save file html1.html
             * read file into inventory items
             * Test the size of list of items
             * and test the items (compare by toString method)
             */

            FileUtil.saveAsHTMLFile(inventory, new File("html1.html"));

            ItemUtil savedInventory = FileUtil.readHTMLFile(new File("html1.html"));
            assertEquals(3, savedInventory.getValues().size());

            assertEquals(item3.toString(), savedInventory.getValues().get(0).toString());
            assertEquals(item2.toString(), savedInventory.getValues().get(1).toString());
            assertEquals(item1.toString(), savedInventory.getValues().get(2).toString());

            //delete file after testing
            File savedFile = new File("html1.html");
            assertTrue(savedFile.delete());

        } catch (IOException e) {
            fail();
        }
    }

    /**
     * Test save inventory items to a file in JSON format
     * Test the following requirement:
     * JSON files shall contain valid JSON and end with the extension .json
     *
     * Test loading the JSON file into inventory items
     */
    @Test
    public void testSaveJSONFormatFile(){

        Item item1 = new Item(350, "AX00000009", "shield");
        Item item2 = new Item(567, "AX00000008", "broom");
        Item item3 = new Item(300, "AX00000007", "room");

        ItemUtil inventory = new ItemUtil();
        inventory.addRecord(item1);
        inventory.addRecord(item2);
        inventory.addRecord(item3);

        try {

            /**
             * Save file json1.json
             * read file into inventory items
             * Test the size of list of items
             * and test the items (compare by toString method)
             */
            FileUtil.saveAsJsonFile(inventory, new File("json1.json"));

            ItemUtil savedInventory = FileUtil.readJsonFile(new File("json1.json"));
            assertEquals(3, savedInventory.getValues().size());

            assertEquals(item3.toString(), savedInventory.getValues().get(0).toString());
            assertEquals(item2.toString(), savedInventory.getValues().get(1).toString());
            assertEquals(item1.toString(), savedInventory.getValues().get(2).toString());

            //delete file after testing
            File savedFile = new File("json1.json");
            assertTrue(savedFile.delete());

        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }



}