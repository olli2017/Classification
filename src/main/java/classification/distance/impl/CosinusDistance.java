package main.java.classification.distance.impl;

import main.java.classification.distance.Distance;

public class CosinusDistance implements Distance {

  @Override
  public double getDistance(Integer[] features1, Integer[] features2) {
    double dotProd = 0;
    double sqA = 0;
    double sqB = 0;
    for (int i = 0; i < features1.length; i++) {
      dotProd += features1[i] * features2[i];
      sqA += features1[i] * features1[i];
      sqB += features2[i] * features2[i];
    }
    return dotProd / (Math.sqrt(sqA) * Math.sqrt(sqB));
  }
}
