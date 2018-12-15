package main.java.classification.classifier.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import main.java.classification.classifier.Classifier;
import main.java.classification.distance.Distance;

public class KNNClassifier extends Classifier {

  private final List<Integer[]> trainingFeatures = new ArrayList<>();
  private final List<String> trainingLabels = new ArrayList<>();

  public KNNClassifier(Distance distance) {
    super(distance);
  }

  private static List<String> kNearestNeighbors(final Map<Double, List<String>> map, final int k) {
    final List<String> list = new ArrayList<>();
    for (final List<String> labels : map.values()) {
      if (list.addAll(labels) && list.size() > k) {
        break;
      }
    }
    return list;
  }

  @Override
  public void fit(final Integer[] features, final String label) {
    trainingFeatures.add(features);
    trainingLabels.add(label);
  }

  @Override
  public String classify(final Integer[] features, final int k) {
    final Map<Double, List<String>> distanceLabelMap = distanceLabelMap(features);
    final List<String> kNearestNeighbors = kNearestNeighbors(distanceLabelMap, k);
    return mode(kNearestNeighbors);
  }

  private Map<Double, List<String>> distanceLabelMap(final Integer[] features) {
    final Map<Double, List<String>> map = new TreeMap<>(Collections.reverseOrder());
    for (int i = 0; i < trainingLabels.size(); i++) {
      final double distance = getDistance().getDistance(features, trainingFeatures.get(i));
      final List<String> labels = map.getOrDefault(distance, new ArrayList<>());
      labels.add(trainingLabels.get(i));
      map.put(distance, labels);
    }
    return map;
  }

}
