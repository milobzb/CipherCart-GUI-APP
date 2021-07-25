package ucf.assignments;
/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Emanuel Botros
 */
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * App is the JAVA GUI application that manipulates the inventory items
 */
public class App {

    /**
     * Frame object to store GUI components
     */
    private static JFrame frame;

    /**
     * value text field
     */
    private static JTextField valueTextField;

    /**
     * serial number text field
     */
    private static JTextField serialNumberTextField;

    /**
     * name text field
     */
    private static JTextField nameTextField;

    /**
     * display text area
     */
    private static JTextArea displayTextArea;

    /**
     * the object contains the inventory items
     */
    private static ItemUtil itemUtil = new ItemUtil();

    /*This is your main method. */
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    App window = new App();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * Create the application.
     */
    public App() {
        initialize();
    }

    //These are the components of the GUI
    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 625, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        /**********The three boxes at the top Value, Serial Number, and Name. Starts here*****/
        valueTextField = new JTextField();
        valueTextField.setBounds(28, 48, 113, 20);
        frame.getContentPane().add(valueTextField);
        valueTextField.setColumns(10);

        serialNumberTextField = new JTextField();
        serialNumberTextField.setBounds(151, 48, 96, 20);
        frame.getContentPane().add(serialNumberTextField);
        serialNumberTextField.setColumns(10);

        nameTextField = new JTextField();
        nameTextField.setBounds(273, 48, 123, 20);
        frame.getContentPane().add(nameTextField);
        nameTextField.setColumns(10);
        /**********The three boxes at the top Value, Serial Number, and Name. Ends here*****/


        /**********The big box that is a text area, starts here.*****/
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(28, 89, 368, 138);
        frame.getContentPane().add(scrollPane);

        displayTextArea = new JTextArea();
        scrollPane.setViewportView(displayTextArea);
        /**********The big box that is a text area, ends here.*****/

        /********************These are the labels at the top, starts here***************/
        JLabel lblNewLabel = new JLabel("Value");
        lblNewLabel.setBounds(28, 23, 79, 14);
        frame.getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Serial Number");
        lblNewLabel_1.setBounds(151, 23, 96, 14);
        frame.getContentPane().add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Name");
        lblNewLabel_2.setBounds(273, 23, 123, 14);
        frame.getContentPane().add(lblNewLabel_2);
        /********************These are the labels at the top, ends here***************/

        /***********************The add button **************/
        JButton btnNewButton = new JButton("Add");
        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //call to method add
                add();
            }
        });
        btnNewButton.setBounds(425, 47, 145, 23);
        frame.getContentPane().add(btnNewButton);



        /***********************The Remove button **************/
        JButton btnNewButton_1 = new JButton("Remove");
        btnNewButton_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                delete();
            }
        });
        btnNewButton_1.setBounds(425, 81, 145, 23);
        frame.getContentPane().add(btnNewButton_1);

        /***********************The Show File Contents button **************/
        JButton btnNewButton_2 = new JButton("Show File Contents");
        btnNewButton_2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                JFileChooser f = new JFileChooser();
                if (f.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){

                    File filePath = f.getSelectedFile();

                    try {

                        if (filePath.getName().endsWith(".html")){
                            itemUtil = FileUtil.readHTMLFile(filePath);
                        }else if (filePath.getName().endsWith(".txt")){
                            itemUtil = FileUtil.readTSVFile(filePath);
                        }else if (filePath.getName().endsWith(".json")){
                            itemUtil = FileUtil.readJsonFile(filePath);
                        }else{
                            throw new IOException();
                        }

                        show();

                        JOptionPane.showMessageDialog(frame, "File has been loaded successfully");

                    } catch (IOException ioException) {
                        JOptionPane.showMessageDialog(frame, "Could not load inventory items from file");
                    }

                }

            }
        });
        btnNewButton_2.setBounds(425, 115, 145, 23);
        frame.getContentPane().add(btnNewButton_2);

        /***********************The Export as TSV button **************/

        JButton btnNewButton_3 = new JButton("Export as TSV");
        btnNewButton_3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //call to export as tsv file

                JFileChooser f = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("TSV FILE", "txt", "TXT");
                f.setFileFilter(filter);

                if (f.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

                    File filePath = f.getSelectedFile();

                    try {
                        //System.out.println("Filename is " + nameOfFile);
                        FileUtil.saveAsTSVFile(itemUtil, filePath);

                        JOptionPane.showMessageDialog(frame, "File has been saved successfully");


                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });
        btnNewButton_3.setBounds(425, 149, 145, 23);
        frame.getContentPane().add(btnNewButton_3);

        /***********************The Export As HTML button **************/
        JButton btnNewButton_4 = new JButton("Export As HTML");
        btnNewButton_4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //call to export as tsv file

                JFileChooser f = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("HTML FILE", "html", "HTML");
                f.setFileFilter(filter);
                f.showSaveDialog(null);

                File filePath = f.getSelectedFile();

                try {
                    //System.out.println("Filename is " + nameOfFile);
                    FileUtil.saveAsHTMLFile(itemUtil, filePath);
                    JOptionPane.showMessageDialog(frame, "File has been saved successfully");

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        btnNewButton_4.setBounds(425, 183, 145, 23);
        frame.getContentPane().add(btnNewButton_4);

        //Save as json button
        JButton btnNewButton_json = new JButton("Export As JSON");
        btnNewButton_json.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //call to export as tsv file

                JFileChooser f = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON FILE", "json", "JSON");
                f.setFileFilter(filter);
                f.showSaveDialog(null);

                File filePath = f.getSelectedFile();

                try {
                    //System.out.println("Filename is " + nameOfFile);
                    FileUtil.saveAsJsonFile(itemUtil, filePath);
                    JOptionPane.showMessageDialog(frame, "File has been saved successfully");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        btnNewButton_json.setBounds(425, 217, 145, 23);
        frame.getContentPane().add(btnNewButton_json);


        //Edit button
        JButton btnNewButton_edit = new JButton("Edit");
        btnNewButton_edit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //call to edit item
                edit();
            }
        });
        btnNewButton_edit.setBounds(425, 251, 145, 23);
        frame.getContentPane().add(btnNewButton_edit);

        //Sort by value button
        JButton btnNewButton_sortValue = new JButton("Sort by Value");
        btnNewButton_sortValue.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //call to edit item
                itemUtil.sortByValue();
                show();
            }
        });
        btnNewButton_sortValue.setBounds(425, 285, 145, 23);
        frame.getContentPane().add(btnNewButton_sortValue);

        //Sort by serial number button
        JButton btnNewButton_sortSerialNumber = new JButton("Sort by SN");
        btnNewButton_sortSerialNumber.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //call to edit item
                itemUtil.sortBySerialNumber();
                show();
            }
        });
        btnNewButton_sortSerialNumber.setBounds(425, 319, 145, 23);
        frame.getContentPane().add(btnNewButton_sortSerialNumber);

        //Sort by name
        JButton btnNewButton_sortByName = new JButton("Sort by Name");
        btnNewButton_sortByName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //call to edit item
                itemUtil.sortByName();
                show();
            }
        });
        btnNewButton_sortByName.setBounds(425, 353, 145, 23);
        frame.getContentPane().add(btnNewButton_sortByName);

        //Search by serial number
        JButton btnNewButton_searchBySerialNumber = new JButton("Search by SN");
        btnNewButton_searchBySerialNumber.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //call to edit item
                String serialNumber = serialNumberTextField.getText();
                List<Item> records = itemUtil.searchBySerialNumber(serialNumber);
                showRecords(records);
            }
        });
        btnNewButton_searchBySerialNumber.setBounds(425, 387, 145, 23);
        frame.getContentPane().add(btnNewButton_searchBySerialNumber);

        //Search by serial number
        JButton btnNewButton_searchByName = new JButton("Search by Name");
        btnNewButton_searchByName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //call to edit item
                searchByName();
            }
        });
        btnNewButton_searchByName.setBounds(425, 421, 145, 23);
        frame.getContentPane().add(btnNewButton_searchByName);
    }

    public static void searchByName() {
        String name = nameTextField.getText(); //for the second box

        List<Item> records = new ArrayList<>();
        records = itemUtil.searchByName(name);
        showRecords(records);
    }

    //The edit method
    public static void edit() {
        String svalue = valueTextField.getText();  //for the first box
        String serialNumber = serialNumberTextField.getText(); //for the second box
        String name = nameTextField.getText(); //for the third box

        //value in dollars
        int value = 0;
        try{
            value = Integer.parseInt(svalue);
        }catch (Exception e){
            JOptionPane.showMessageDialog(frame, "Invalid value");
            return;
        }

        //we will create one big string, we will tweak this one so don't worry
        Item item = new Item(value, serialNumber, name);

        Item olderRecord = itemUtil.findRecord(item.getSerialNumber());
        if(olderRecord!=null) {
            itemUtil.deleteRecord(olderRecord.getSerialNumber()); //serial number passed to function
            itemUtil.addRecord(item);
            System.out.println("Row can be added. Unique row");
        }
        else {
            JOptionPane.showMessageDialog(frame, "Serial Number record doesn't exist");
        }

        show();
        try {
            //we will want to save values to regular text file either way
            FileUtil.save(itemUtil);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //for the input boxes at top we want to clear them once we have added to the text area
        valueTextField.setText("");
        serialNumberTextField.setText("");
        nameTextField.setText("");
    }

    public static void showRecords(List<Item> recordsToShow) {
        displayTextArea.setText("");
        for(Item item: recordsToShow) {
            displayTextArea.append(item + "\n");
        }
        displayTextArea.setCaretPosition(0);
    }

    //The show method
    public static void show() {

        //clear text area first
        displayTextArea.setText("");

        //read values in arraylist and put them in textArea
        for(Item value : itemUtil.getValues()) {
            displayTextArea.append(value + "\n");
        }

        //this is for the cursor, we want to set it back to the top position
        displayTextArea.setCaretPosition(0);
    }

    public static void delete() {

        //this removes the first value of the arraylist
        itemUtil.removeFirstRecord();
        //then we call show to add the values again to the text area.
        show();
        try {
            FileUtil.save(itemUtil);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void add() {
        String svalue = valueTextField.getText(); //+ "\t";  //for the first box
        String serialNumber = serialNumberTextField.getText(); //+ "\t"; //for the second box
        String name = nameTextField.getText(); //+ "\t"; //for the third box

        //value in dollars
        int value = 0;
        try{
            value = Integer.parseInt(svalue);
        }catch (Exception e){
            JOptionPane.showMessageDialog(frame, "Invalid value");
            return;
        }

        Item item = null;
        try {
            item = new Item(value, serialNumber, name);
        }catch (Exception e){
            JOptionPane.showMessageDialog(frame, e.getMessage());
            return;
        }
        //we will create one big string, we will tweak this one so don't worry
        //String d = a + b + c;
        //System.out.println(" " + d); //testing string
        boolean alreadyExists = itemUtil.testSerialNumberPresence(item.getSerialNumber());
        //adding values to the arraylist
        if(!alreadyExists) {
            System.out.println("Row can be added. Unique row");
            itemUtil.addRecord(item);
        }
        else {
            System.out.println("Serial number already exists");
            JOptionPane.showMessageDialog(frame, "Serial Number already exists");
        }
        show();
        try {
            //we will want to save values to regular text file either way
            FileUtil.save(itemUtil);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //for the input boxes at top we want to clear them once we have added to the text area
        valueTextField.setText("");
        serialNumberTextField.setText("");
        nameTextField.setText("");
    }

}
