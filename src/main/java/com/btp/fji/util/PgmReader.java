package com.btp.fji.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class PgmReader {

  public static double[][] read(String fileName) throws IOException {
    double[][] matrix;

    BufferedReader br = openBufferedReader(fileName);

    br.readLine(); // ignore P2
    StringTokenizer dimensions = new StringTokenizer(br.readLine());
    int height = Integer.parseInt(dimensions.nextToken());
    int width = Integer.parseInt(dimensions.nextToken());
    double maxValue = Double.parseDouble(br.readLine());

    matrix = new double[height][width];

    try { // read values
      for (int i = 0; i < height; i++) {
        StringTokenizer row = new StringTokenizer(br.readLine());

        for (int j = 0; j < width; j++) {
          matrix[i][j] = Double.parseDouble(row.nextToken()) / maxValue;
        }
      }
    } catch (IOException e) {
      System.err.println("Invalid double found!");
      System.exit(1);
    }

    return matrix;
  }

  private static BufferedReader openBufferedReader(String fileName) {
    try {
      return new BufferedReader(new FileReader(fileName));
    } catch (FileNotFoundException e) {
      System.err.println("Could not open buffered reader!");
      System.exit(1);
    }

    return null; // ignore
  }

}
