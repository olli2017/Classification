package main.java.classification.dataset;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Dataset {

  private final List<Integer[]> featuresList = new ArrayList<>();
  private final List<String> labelsList = new ArrayList<>();

  public abstract Dataset load(String fileName, String countMatrix) throws IOException;

  protected final void addEntry(final Integer[] features, final String label) {
    featuresList.add(features);
    labelsList.add(label);
  }

  public List<Integer[]> getFeaturesList() {
    return featuresList;
  }

  public List<String> getLabelsList() {
    return labelsList;
  }
}
