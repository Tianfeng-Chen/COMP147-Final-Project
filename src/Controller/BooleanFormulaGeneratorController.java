package Controller;

import Model.FileProcessor;

import java.io.IOException;

public class BooleanFormulaGeneratorController {
    public static void main(String[] args) throws IOException {
        FileProcessor.getFileProcessor().processFile("src/Util/input_graph_4.txt");
    }
}
