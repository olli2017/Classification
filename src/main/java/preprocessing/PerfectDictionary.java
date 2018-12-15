package main.java.preprocessing;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PerfectDictionary {

  public static final String TRAIN_FILE_NAME =
      "D:\\Aidar\\IdeaProjects\\GITHUB\\KNN\\jknn\\src\\main\\java\\aydus\\knn\\data\\news_train_small.txt";
  public static final String PERFECT_DICTIONARY =
      "D:\\Aidar\\IdeaProjects\\GITHUB\\KNN\\jknn\\src\\main\\java\\aydus\\knn\\data\\perfect_dict.txt";

  public static final Set<String> POPULAR_WORDS = new HashSet<>(Arrays.asList(
      "год", "что", "эт", "он", "котор", "для", "был", "как", "ег", "так", "такж", "сво"));

  public static void main(String[] args) throws IOException {

    Map<String, Map<String, Integer>> dictByThemes = new HashMap();

    List<String> labels = new ArrayList<>();
    List<String> texts = new ArrayList<>();

    Files.lines(Paths.get(TRAIN_FILE_NAME), StandardCharsets.UTF_8)
        .forEach(line -> {
          String[] content = line.split("\t");
          labels.add(content[0]);
          dictByThemes.putIfAbsent(content[0], new HashMap<>());
          texts.add(content[2]);
        });

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

    for (int i = 0; i < labels.size(); i++) {
      dictByThemes
          .put(labels.get(i), fillingMap(textByWords.get(i), dictByThemes.get(labels.get(i))));
    }

    Set<String> dictionarySet = new HashSet<>();

    dictByThemes.forEach((label, concreteDict) -> {
      concreteDict = concreteDict.entrySet().stream()
          .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
          .limit(1000)
          .collect(Collectors.toMap(
              Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
//            System.out.println(label + " : " + concreteDict);
      dictionarySet.addAll(concreteDict.keySet());
    });

    System.out.println(dictionarySet.size());

    FileWriter fw = new FileWriter(PERFECT_DICTIONARY);
    dictionarySet.forEach(word -> {
      try {
        fw.write(word);
        fw.write("\n");
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    fw.close();
  }

  private static Map<String, Integer> fillingMap(List<String> list, Map<String, Integer> map) {
    list.forEach(keyword -> {
      if (map.containsKey(keyword)) {
        map.put(keyword, map.get(keyword) + 1);
      } else {
        map.put(keyword, 1);
      }
    });
    return map;
  }
}
