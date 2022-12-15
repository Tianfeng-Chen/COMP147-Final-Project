package Model;

import java.io.*;
import java.util.*;

public class FileProcessor {
    private Map<Integer, List<Integer>> graph;
    private List<List<Integer>> booleanFormula;

    private static FileProcessor instance = null;

    private FileProcessor() {
        this.graph = new HashMap<>();
        this.booleanFormula = new ArrayList<>();
    }

    public static FileProcessor getFileProcessor() {
        if (instance == null) {
            synchronized (FileProcessor.class) {
                if (instance == null) {
                    instance = new FileProcessor();
                }
            }
        }
        return instance;
    }

    public void processFile(String filePath) throws IOException {
        File inputFile = new File(filePath);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
        String line = bufferedReader.readLine();
        int count = 1;

        while (line != null) {
            String[] children = line.split(" ");
            List<Integer> temp = new ArrayList<>();
            for (String node : children) {
                temp.add(Integer.valueOf(node));
            }
            graph.put(count, temp);
            line = bufferedReader.readLine();
            count++;
        }
        bufferedReader.close();
        processNodes();
        processEdges();
        for (List<Integer> formula : booleanFormula) {
            System.out.println(formula);
        }
        printOutput(filePath);
    }

    private void processNodes() {
        if (graph == null || graph.size() == 0) {
            throw new IllegalArgumentException();
        }
        int nodeCount = graph.size();

        for (int i = 1; i <= nodeCount; i++) {
            int baseIndicator = i * 10 + i;
            booleanFormula.add(Arrays.asList(baseIndicator, baseIndicator + 1, baseIndicator + 2, 0));
            booleanFormula.add(Arrays.asList(-baseIndicator, -(baseIndicator + 1), 0));
            booleanFormula.add(Arrays.asList(-baseIndicator, -(baseIndicator + 2), 0));
            booleanFormula.add(Arrays.asList(-(baseIndicator + 1), -(baseIndicator + 2), 0));
        }
    }

    private void processEdges() {
        if (graph == null || graph.size() == 0) {
            throw new IllegalArgumentException();
        }
        for (int node : graph.keySet()) {
            int baseIndicator1 = node * 10 + node;
            for (int child : graph.get(node)) {
                int baseIndicator2 = child * 10 + child;
                booleanFormula.add(Arrays.asList(-baseIndicator1, -baseIndicator2, 0));
                booleanFormula.add(Arrays.asList(-(baseIndicator1 + 1), -(baseIndicator2 + 1), 0));
                booleanFormula.add(Arrays.asList(-(baseIndicator1 + 2), -(baseIndicator2 + 2), 0));
            }
        }
    }

    private void printOutput(String filePath) throws IOException {
        String[] path = filePath.split("/");
        StringBuilder outputPath = new StringBuilder();
        for (int i = 0; i < path.length - 1; i++) {
            outputPath.append(path[i]);
            outputPath.append("/");
        }
        String[] fileName = path[path.length - 1].split("\\.");
        outputPath.append(fileName[0] + "_output.txt");
        FileWriter fileWriter = new FileWriter(new File(outputPath.toString()), true);
        for (List<Integer> formula : booleanFormula) {
            for (int i = 0; i < formula.size(); i++) {
                fileWriter.write(String.valueOf(formula.get(i)));
                if (i < formula.size() - 1) {
                    fileWriter.write(" ");
                } else
                    fileWriter.write("\n");
            }
        }
        fileWriter.close();
    }
}
