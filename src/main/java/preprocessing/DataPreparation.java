package main.java.preprocessing;

import static main.java.preprocessing.PerfectDictionary.POPULAR_WORDS;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataPreparation {

  private static final String FILE_NAME =
      "D:\\Aidar\\IdeaProjects\\GITHUB\\KNN\\jknn\\src\\main\\java\\aydus\\knn\\data\\news_train_small.txt";
  private static final String COUNT_MATRIX =
      "D:\\Aidar\\IdeaProjects\\GITHUB\\KNN\\jknn\\src\\main\\java\\aydus\\knn\\data\\train_count_matrix_small.txt";

  public static void main(String[] args) throws IOException {

    List<String> labels = new ArrayList<>();
    List<String> texts = new ArrayList<>();

    Files.lines(Paths.get(FILE_NAME), StandardCharsets.UTF_8)
        .forEach(line -> {
          String[] content = line.split("\t");
          labels.add(content[0]);
          texts.add(content[2]);
        });

    for (int i = 0; i < 5; i++) {
      System.out.println(labels.get(i) + " " + texts.get(i));
    }

    List<List<String>> textByWords = new ArrayList<>();

    texts.forEach(article -> {
      article = article.replaceAll("[()/=*^|\\\\_+.»\\?«\\\"~,:;\\'\\s\\d]", " ");
      article = article.replaceAll("\\s+", " ");
      String[] splitArticle = article.split(" ");
      textByWords.add(
          Stream.of(splitArticle)
              .filter(word -> word.length() > 2)
              .map(StemmerPorterRU::stem)
              .filter(word -> !POPULAR_WORDS.contains(word))
              .collect(Collectors.toList()));
    });

    for (int j = 0; j < 5; j++) {
      System.out.println(textByWords.get(j));
    }

    List<String> dictionaryList = new ArrayList<>(3500);
    Files.lines(Paths.get(PerfectDictionary.PERFECT_DICTIONARY), StandardCharsets.UTF_8)
        .forEach(dictionaryList::add);

    FileWriter fw = new FileWriter(COUNT_MATRIX);
    int counter = 0;
    for (List<String> document : textByWords) {
      System.out.println(counter++);
      int[] rowOfMatrix = new int[dictionaryList.size()];

      for (int j = 0; j < document.size(); j++) {
        int positionInDictionary = dictionaryList.indexOf(document.get(j));
        if (positionInDictionary > -1) {
          ++rowOfMatrix[positionInDictionary];
        }
      }

      StringBuilder sb = new StringBuilder();
      for (int el : rowOfMatrix) {
        sb.append(el)
            .append(" ");
      }
      String countVector = sb.append("\n").toString();

      fw.write(countVector);
    }

    fw.close();

  }

}
