package main.java.classification;

import java.io.FileWriter;
import java.io.IOException;
import main.java.classification.classifier.Classifier;
import main.java.classification.classifier.impl.KNNClassifier;
import main.java.classification.dataset.Dataset;
import main.java.classification.dataset.impl.TextDataset;
import main.java.classification.distance.impl.CosinusDistance;

public class Main {

  public static final String TRAIN_FILE_NAME =
      "D:\\Aidar\\IdeaProjects\\GITHUB\\KNN\\jknn\\src\\main\\java\\aydus\\knn\\data\\news_train_small.txt";
  public static final String VALIDATE_FILE_NAME =
      "D:\\Aidar\\IdeaProjects\\GITHUB\\KNN\\jknn\\src\\main\\java\\aydus\\knn\\data\\news_test_small.txt";

  public static final String TRAIN_COUNT_MATRIX =
      "D:\\Aidar\\IdeaProjects\\GITHUB\\KNN\\jknn\\src\\main\\java\\aydus\\knn\\data\\train_count_matrix_small.txt";
  public static final String TEST_COUNT_MATRIX =
      "D:\\Aidar\\IdeaProjects\\GITHUB\\KNN\\jknn\\src\\main\\java\\aydus\\knn\\data\\TEST_count_matrix.txt";
  public static final String VALIDATE_COUNT_MATRIX =
      "D:\\Aidar\\IdeaProjects\\GITHUB\\KNN\\jknn\\src\\main\\java\\aydus\\knn\\data\\test_count_matrix_small.txt";

  private static final String ANSWER =
      "D:\\Aidar\\IdeaProjects\\GITHUB\\KNN\\jknn\\src\\main\\java\\aydus\\knn\\data\\answer.txt";


  public static void main(String[] args) throws IOException {

    Dataset trainDataset = new TextDataset().load(TRAIN_FILE_NAME, TRAIN_COUNT_MATRIX);
    Dataset testDataset = new TextDataset().loadTest(TEST_COUNT_MATRIX);
    Dataset validateDataset = new TextDataset().load(VALIDATE_FILE_NAME, VALIDATE_COUNT_MATRIX);

    Classifier classifier = new KNNClassifier(new CosinusDistance());

    classifier.fit(trainDataset);

//    for (int k = 6; k < 12; k++) {
//      System.out.println(k + " : " + classifier.accuracy(validateDataset, k));
//    }
    int k = 13;
    printPredictionToFile(classifier, testDataset, k);

  }

  private static void printPredictionToFile(Classifier classifier, Dataset test, int k)
      throws IOException {
    FileWriter fw = new FileWriter(ANSWER);
    int step = 1;

    for (Integer[] features : test.getFeaturesList()) {
      String predict = classifier.classify(features, k);
      fw.write(predict);
      fw.write("\n");
      System.out.println(step++);
    }
    fw.close();
  }
}
