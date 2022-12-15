package Controller;

import Model.FileProcessor;

import java.io.IOException;

public class BooleanFormulaGeneratorController {
    public static void main(String[] args) throws IOException {
        /**
         * This controller will process the source file and generate an output file in the same dir with
         * the source file, it will generate the result in console as well.
         * @param filePath the path to the source file.
         */
        FileProcessor.getFileProcessor().processFile("src/Util/input_graph_1.txt");
    }
}
