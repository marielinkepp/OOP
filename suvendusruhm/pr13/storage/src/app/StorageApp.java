package app;

import com.google.gson.Gson;

import java.io.IOException;

public class StorageApp {

  public static void main(String[] args) throws IOException {
    Gson gson = new Gson();

    FileResult original = new FileResult("files/input0.txt", -42, 42, 0);
    System.out.println(original);

    String asJson = gson.toJson(original);
    System.out.println(asJson);

    FileResult restored = gson.fromJson(asJson, FileResult.class);
    System.out.println(restored);

    System.out.println("Application finished successfully!");
  }
}
