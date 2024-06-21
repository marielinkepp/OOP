package app;

public class FileResult {
  
  private String fileName;
  private long min;
  private long max;
  private long sum;

  public FileResult() {
    // gson needs a no-arg constructor
  }

  public FileResult(String fileName, long min, long max, long sum) {
    this.fileName = fileName;
    this.min = min;
    this.max = max;
    this.sum = sum;
  }

  public String getFileName() {
    return fileName;
  }

  public long getMin() {
    return min;
  }

  public long getMax() {
    return max;
  }

  public long getSum() {
    return sum;
  }
}
