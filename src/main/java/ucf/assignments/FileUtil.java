package ucf.assignments;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {

    public static void saveAsJsonFile(String nameOfFile, File filePath) throws IOException {
        System.out.println(nameOfFile + " " + filePath);
        ObjectMapper objectMapper = new ObjectMapper();
        //I'm still working on this.
        File file = new File(filePath + "/" + nameOfFile + ".json");

        System.out.println(file);
        FileWriter writer = new FileWriter(file);
        //Reading values from arraylist values and writing to file.
        writer.write("{");
        for (Item value : values) {
            System.out.println("Start writing to file");
            writer.write(objectMapper.writeValueAsString(value) + ",\n");
        }
        writer.write("}");
        writer.flush();
        writer.close();
    }
}
