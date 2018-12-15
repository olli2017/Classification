package main.java.classification.classifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import main.java.classification.dataset.Dataset;
import main.java.classification.distance.Distance;

public abstract class Classifier {

  private final Distance distance;

  public Classifier(final Distance distance) {
    this.distance = distance;
  }

  public final void fit(final Dataset dataset) {
    for (int i = 0; i < dataset.getFeaturesList().size(); i++) {
      fit(dataset.getFeaturesList().get(i), dataset.getLabelsList().get(i));
    }
  }

  public abstract void fit(final Integer[] features, final String label);

  public final double accuracy(final Dataset dataset, final int k) {
    double correct = 0;
    for (int i = 0; i < dataset.getFeaturesList().size(); i++) {
      if (classify(dataset.getFeaturesList().get(i), k).equals(dataset.getLabelsList().get(i))) {
        correct++;
      }
    }
    return correct / dataset.getFeaturesList().size();
  }

  public abstract String classify(Integer[] features, int k);

  protected Distance getDistance() {
    return distance;
  }

  protected <T> T mode(final List<T> list) {
    final Map<T, Integer> map = new HashMap<>();
    int maxFreq = 0;
    T maxFreqLabel = null;
    for (final T s : list) {
      final int freq = map.getOrDefault(s, 0) + 1;
      if (freq > maxFreq) {
        maxFreq = freq;
        maxFreqLabel = s;
      }
      map.put(s, freq);
    }
    return maxFreqLabel;
  }
}
