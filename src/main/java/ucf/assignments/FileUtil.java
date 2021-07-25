package ucf.assignments;
/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 first_name last_name
 */
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * FileUtil manipulate file
 * load file or save to file in different format
 */
public class FileUtil {

    /**
     * save file in Json format
     * @param itemUtil
     * @throws IOException
     */
    public static void saveAsJsonFile(ItemUtil itemUtil, File filePath) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        if (!filePath.getName().endsWith(".json")){
            filePath = new File(filePath.getAbsolutePath() + ".json");
        }

        String arrayToJson = objectMapper.writeValueAsString(itemUtil.getValues());

        FileWriter writer = new FileWriter(filePath);
        writer.write(arrayToJson);
        writer.flush();
        writer.close();
    }

    /**
     * read file in Json format
     * @param filePath file path
     * @throws IOException
     */
    public static ItemUtil readJsonFile(File filePath) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        List<Item> items = objectMapper.readValue(
                filePath,
                new TypeReference<List<Item>>(){});


        return new ItemUtil(items);
    }

    /**
     * save file in data/values.txt
     * @param itemUtil
     * @throws IOException
     */
    public static void save(ItemUtil itemUtil) throws IOException {

        //Here we are saving to a regular text file. This is alway important because you may loose data
        //if you don't do this step.
        File file = new File("data/values.txt");
        FileWriter writer = new FileWriter(file);
        file.createNewFile();

        //Reading values from arraylist values and writing to file.
        for (Item value : itemUtil.getValues()) {
            writer.write(value + "\t" + "\n");
        }
        writer.flush();
        writer.close();
    }

    /**
     * save file
     * @param itemUtil
     * @param filePath
     * @throws IOException
     */
    public static void saveAsTSVFile(ItemUtil itemUtil, File filePath) throws IOException {

        if (!filePath.getName().endsWith(".txt")){
            filePath = new File(filePath.getAbsolutePath() + ".txt");
        }

        //System.out.println(file);
        FileWriter writer = new FileWriter(filePath);

        //Reading values from arraylist values and writing to file.
        for (Item value : itemUtil.getValues()) {
            //System.out.println("Start writing to file");
            writer.write(value + "\n");
        }
        writer.flush();
        writer.close();
    }

    /**
     * read file in TSV format
     * @param filePath file path
     * @throws IOException
     */
    public static ItemUtil readTSVFile(File filePath) throws IOException {

        List<Item> items = new ArrayList<>();

        Scanner fileScanner = new Scanner(filePath);
        while (fileScanner.hasNext()){

            String[] data = fileScanner.nextLine().split("\t");
            int value = Integer.parseInt(data[0]);
            String serialNumber = data[1];
            String name = data[2];

            items.add(new Item(value, serialNumber, name));
        }

        //close file
        fileScanner.close();

        return new ItemUtil(items);
    }

    /**
     * save file in html format
     * @param itemUtil
     * @param filePath
     * @throws IOException
     */
    public static void saveAsHTMLFile(ItemUtil itemUtil, File filePath) throws IOException {

        if (!filePath.getName().endsWith(".html")){
            filePath = new File(filePath.getAbsolutePath() + ".html");
        }

        FileWriter writer = new FileWriter(filePath);

        writer.write("<html><body><h1>Value, Serial Number, Name</h1>\n");
        //Reading values from arraylist values and writing to file.
        for (Item value : itemUtil.getValues()) {
            writer.write("<h2>" + value.getValue() + "\t" + value.getSerialNumber() + "\t" + value.getName() + "</h2>" + "<br>\n");
        }

        writer.write("</body></html>\n");
        writer.flush();
        writer.close();

    }

    /**
     * read file in HTML format
     * @param filePath file path
     * @throws IOException
     */
    public static ItemUtil readHTMLFile(File filePath) throws IOException {

        List<Item> items = new ArrayList<>();

        Scanner fileScanner = new Scanner(filePath);
        fileScanner.nextLine(); //ignore header line
        while (fileScanner.hasNext()){

            String line = fileScanner.nextLine();
            if (line.startsWith("</body>")){ //ignore last line
                break;
            }

            //remove <h2>
            line = line.substring(4);
            //remove </h2><br>
            line = line.substring(0, line.length() - 9);

            String[] data = line.split("\t");
            int value = Integer.parseInt(data[0]);
            String serialNumber = data[1];
            String name = data[2];

            items.add(new Item(value, serialNumber, name));
        }

        //close file
        fileScanner.close();

        return new ItemUtil(items);
    }
}
