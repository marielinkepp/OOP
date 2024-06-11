package main.java.sample;

import java.util.List;

public class AverageCalculator {

  public double averageOf(List<Double> values) {
    if (values == null || values.isEmpty())
      throw new IllegalArgumentException("no values provided");

    double sum = 0;
    for (double value : values)
      sum += value;
    return sum / values.size();
  }
}
