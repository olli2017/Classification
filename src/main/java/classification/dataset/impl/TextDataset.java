package main.java.classification.dataset.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import main.java.classification.dataset.Dataset;

public class TextDataset extends Dataset {

  @Override
  public Dataset load(String fileName, String countMatrix) throws IOException {
    List<String> labels = new ArrayList<>();

    Files.lines(Paths.get(fileName), StandardCharsets.UTF_8)
        .forEach(line -> {
          String[] content = line.split("\t");
          labels.add(content[0]);
        });

    for (int i = 0; i < 5; i++) {
      System.out.println(labels.get(i));
    }

    List<Integer[]> features = new ArrayList<>();

    Files.lines(Paths.get(countMatrix), StandardCharsets.UTF_8)
        .forEach(line -> {
          String[] splitRow = line.split(" ");
          features.add(Arrays.stream(splitRow)
              .map(Integer::parseInt)
              .toArray(Integer[]::new));
        });

    for (int i = 0; i < labels.size(); i++) {
      addEntry(features.get(i), labels.get(i));
    }

    return this;
  }

  public Dataset loadTest(String countMatrix) throws IOException {

    List<Integer[]> features = new ArrayList<>();

    Files.lines(Paths.get(countMatrix), StandardCharsets.UTF_8)
        .forEach(line -> {
          String[] splitRow = line.split(" ");
          features.add(Arrays.stream(splitRow)
              .map(Integer::parseInt)
              .toArray(Integer[]::new));
        });

    for (int i = 0; i < features.size(); i++) {
      addEntry(features.get(i), null);
    }

    return this;
  }
}
