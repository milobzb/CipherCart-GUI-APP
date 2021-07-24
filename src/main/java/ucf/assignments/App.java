package ucf.assignments;
/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 first_name last_name
 */

import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class App {

    //These are fields that can be used through the file, not just in one method.
    private static JFrame frame;
    private static JTextField textField;
    private static JTextField textField_1;
    private static JTextField textField_2;
    private static JTextField userTextField;
    private static JTextField formatField;
    static JTextArea textArea;

    /*you want to use an arraylist. These are alway good to use because you can modify it.
     * Here we will add and remove values.*/
    static List<Item> values = new ArrayList<>();


    public List<Item> getValues() {
        return this.values;
    }
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
        textField = new JTextField();
        textField.setBounds(28, 48, 113, 20);
        frame.getContentPane().add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setBounds(151, 48, 96, 20);
        frame.getContentPane().add(textField_1);
        textField_1.setColumns(10);

        textField_2 = new JTextField();
        textField_2.setBounds(273, 48, 123, 20);
        frame.getContentPane().add(textField_2);
        textField_2.setColumns(10);
        /**********The three boxes at the top Value, Serial Number, and Name. Ends here*****/


        /**********The big box that is a text area, starts here.*****/
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(28, 89, 368, 138);
        frame.getContentPane().add(scrollPane);

        textArea = new JTextArea();
        scrollPane.setViewportView(textArea);
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

                //call to method
                show();

            }
        });
        btnNewButton_2.setBounds(425, 115, 145, 23);
        frame.getContentPane().add(btnNewButton_2);

        /***********************The Export as TSV button **************/
        JFrame newFrame = new JFrame();
        userTextField = new JTextField();
        userTextField.setBounds(28, 48, 113, 20);
        userTextField.setColumns(10);

        JButton btnNewButton_3 = new JButton("Export as TSV");
        btnNewButton_3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //call to export as tsv file

                newFrame.setVisible(true);
                newFrame.setBounds(100, 100, 625, 300);
                newFrame.getContentPane().setLayout(null);

                JLabel lblNewLabel = new JLabel("File name");
                lblNewLabel.setBounds(28, 23, 79, 14);
                newFrame.getContentPane().add(lblNewLabel);

                newFrame.getContentPane().add(userTextField);

                System.out.println(userTextField);

                JFileChooser f = new JFileChooser();
                f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                f.showSaveDialog(null);

                File filePath = f.getSelectedFile();


                //add button to dialog box
                JButton btnNewButton_export = new JButton("Export");
                btnNewButton_export.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                            String nameOfFile = userTextField.getText();
                            //System.out.println("Filename is " + nameOfFile);
                            saveAsFormattedFile(nameOfFile, filePath, "tsv");
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        newFrame.dispose();
                        show();
                    }
                });
                btnNewButton_export.setBounds(178, 48, 96, 14);
                newFrame.getContentPane().add(btnNewButton_export);
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

                newFrame.setVisible(true);
                newFrame.setBounds(100, 100, 625, 300);
                newFrame.getContentPane().setLayout(null);

                JLabel lblNewLabel = new JLabel("File name");
                lblNewLabel.setBounds(28, 23, 79, 14);
                newFrame.getContentPane().add(lblNewLabel);

                newFrame.getContentPane().add(userTextField);

                System.out.println(userTextField);

                JFileChooser f = new JFileChooser();
                f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                f.showSaveDialog(null);

                File filePath = f.getSelectedFile();


                //add button to dialog box
                JButton btnNewButton_export = new JButton("Export");
                btnNewButton_export.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                            String nameOfFile = userTextField.getText();
                            //System.out.println("Filename is " + nameOfFile);
                            saveAsHTMLFile(nameOfFile, filePath);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        newFrame.dispose();
                        show();
                    }
                });
                btnNewButton_export.setBounds(178, 48, 96, 14);
                newFrame.getContentPane().add(btnNewButton_export);
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

                newFrame.setVisible(true);
                newFrame.setBounds(100, 100, 625, 300);
                newFrame.getContentPane().setLayout(null);

                JLabel lblNewLabel = new JLabel("File name");
                lblNewLabel.setBounds(28, 23, 79, 14);
                newFrame.getContentPane().add(lblNewLabel);

                newFrame.getContentPane().add(userTextField);

                System.out.println(userTextField);

                JFileChooser f = new JFileChooser();
                f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                f.showSaveDialog(null);

                File filePath = f.getSelectedFile();

                //add button to dialog box
                JButton btnNewButton_export = new JButton("Export");
                btnNewButton_export.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                            String nameOfFile = userTextField.getText();
                            //System.out.println("Filename is " + nameOfFile);
                            FileUtil.saveAsJsonFile(nameOfFile, filePath);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        newFrame.dispose();
                        show();
                    }
                });
                btnNewButton_export.setBounds(178, 48, 96, 14);
                newFrame.getContentPane().add(btnNewButton_export);
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
                sortByValue();
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
                sortBySerialNumber();
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
                sortByName();
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
                searchBySerialNumber();
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
        String name = textField_2.getText(); //for the second box
        //Item record = findRecord(serialNumber);

        List<Item> records = new ArrayList<>();
        records = findRecordByName(name);
        showRecords(records);
    }

    public static List<Item> findRecordByName(String name) {
        List<Item> records = new ArrayList<>();
        for(Item item: values) {
            if(item.getName().equals(name)) {
                records.add(item);
            }
        }

        return records;
    }

    public static void searchBySerialNumber() {
        String serialNumber = textField_1.getText(); //for the second box
        //Item record = findRecord(serialNumber);

        List<Item> records = new ArrayList<>();
        records.add(findRecord(serialNumber));
        showRecords(records);
    }

    public static void sortByValue() {
        Collections.sort(values, new Comparator<Item>() {
            @Override
            public int compare(Item item1, Item item2) {
                return item1.getValue().compareTo(item2.getValue());
            }
        });
        show();
    }

    public static void sortBySerialNumber() {
        Collections.sort(values, new Comparator<Item>() {
            @Override
            public int compare(Item item1, Item item2) {
                return item1.getSerialNumber().compareTo(item2.getSerialNumber());
            }
        });
        show();
    }

    public static void sortByName() {
        Collections.sort(values, new Comparator<Item>() {
            @Override
            public int compare(Item item1, Item item2) {
                return item1.getName().compareTo(item2.getName());
            }
        });
        show();
    }

    public static Item findRecord(String serialNumber) {
        for(Item value: values) {
            if(serialNumber.equals(value.getSerialNumber())) {
                return value;
            }
        }

        return null;
    }

    public static void deleteRecord(String serialNumber) {
        for(int i = 0; i<values.size(); i++) {
            Item value = values.get(i);
            if(serialNumber.equals(value.getSerialNumber())) {
                values.remove(i);
            }
        }
    }

    //The edit method
    public static void edit() {
        String value = textField.getText();  //for the first box
        String serialNumber = textField_1.getText(); //for the second box
        String name = textField_2.getText(); //for the third box

        //we will create one big string, we will tweak this one so don't worry
        Item item = new Item(value, serialNumber, name);

        Item olderRecord = findRecord(item.getSerialNumber());
        if(olderRecord!=null) {
            deleteRecord(olderRecord.getSerialNumber()); //serial number passed to function
            values.add(0, item);
            System.out.println("Row can be added. Unique row");
        }
        else {
            JOptionPane.showMessageDialog(frame, "Serial Number record doesn't exist");
        }

        show();
        try {
            //we will want to save values to regular text file either way
            save();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //for the input boxes at top we want to clear them once we have added to the text area
        textField.setText("");
        textField_1.setText("");
        textField_2.setText("");
    }

    public static void showRecords(List<Item> recordsToShow) {
        textArea.setText("");
        for(Item item: recordsToShow) {
            textArea.append(item + "\n");
        }
        textArea.setCaretPosition(0);
    }

    //The show method
    public static void show() {

        //clear text area first
        textArea.setText("");

        //read values in arraylist and put them in textArea
        for(Item value : values) {
            textArea.append(value + "\n");
        }

        //this is for the cursor, we want to set it back to the top position
        textArea.setCaretPosition(0);
    }

    public static boolean testSerialNumberPresence(String serialNumber) {
        for(Item value: values) {
            if(value.getSerialNumber().equals(serialNumber))
                return true;
        }
        return false;
    }

    public static void add() {
        String value = textField.getText(); //+ "\t";  //for the first box
        String serialNumber = textField_1.getText(); //+ "\t"; //for the second box
        String name = textField_2.getText(); //+ "\t"; //for the third box

        Item item = new Item(value, serialNumber, name);
        //we will create one big string, we will tweak this one so don't worry
        //String d = a + b + c;
        //System.out.println(" " + d); //testing string
        boolean alreadyExists = testSerialNumberPresence(item.getSerialNumber());
        //adding values to the arraylist
        if(!alreadyExists) {
            System.out.println("Row can be added. Unique row");
            values.add(0, item);
        }
        else {
            System.out.println("Serial number already exists");
            JOptionPane.showMessageDialog(frame, "Serial Number already exists");
        }
        show();
        try {
            //we will want to save values to regular text file either way
            save();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //for the input boxes at top we want to clear them once we have added to the text area
        textField.setText("");
        textField_1.setText("");
        textField_2.setText("");
    }

    public static void delete() {

        //this removes the first value of the arraylist
        values.remove(0);
        //then we call show to add the values again to the text area.
        show();
        try {
            save();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void save() throws IOException {

        //Here we are saving to a regular text file. This is alway important because you may loose data
        //if you don't do this step.
        File file = new File("data/values.txt");
        FileWriter writer = new FileWriter(file);
        file.createNewFile();

        //Reading values from arraylist values and writing to file.
        for (Item value : values) {
            writer.write(value + "\t" + "\n");
        }
        writer.flush();
        writer.close();

    }

    public static void saveAsFormattedFile(String nameOfFile, File filePath, String format) throws IOException {

        System.out.println(nameOfFile + " " + filePath);
        //I'm still working on this.
        File file = new File(filePath + "/" + nameOfFile + "." + format);

        System.out.println(file);
        FileWriter writer = new FileWriter(file);

        //Reading values from arraylist values and writing to file.
        for (Item value : values) {
            System.out.println("Start writing to file");
            writer.write(value + "\n");
        }
        writer.flush();
        writer.close();
    }

    public static void saveAsHTMLFile(String nameOfFile, File filePath) throws IOException {

        //I'm still working on this.
        File file = new File(filePath + "/" + nameOfFile + ".html");
        FileWriter writer = new FileWriter(file);

        writer.write("<html><body><h1>Value, Serial Number, Name</h1>");
        //Reading values from arraylist values and writing to file.
        for (Item value : values) {
            writer.write("<h2>" + value.getValue() + "\t" + value.getSerialNumber() + "\t" + value.getName() + "</h2>" + "<br>");
        }

        writer.write("</body></html>");
        writer.flush();
        writer.close();

    }


/*
    public static void readFromFileFirst() {

        //Here if you have values already stored in the file
        //and you close the software and reopen it. You want to
        //reload so that you have a starting point. Not necessary but leave in here.
        File file = new File("data/values.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                values.add(line);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

 */
}
