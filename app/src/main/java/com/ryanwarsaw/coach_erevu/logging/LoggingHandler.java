package com.ryanwarsaw.coach_erevu.logging;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoggingHandler {

  private PrintWriter printWriter;
  private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss z");

  public LoggingHandler(String fileName) {
    try {
      File externalFile = new File(Environment.getExternalStoragePublicDirectory(
              Environment.DIRECTORY_DOWNLOADS), fileName);
      externalFile.createNewFile();
      this.printWriter = new PrintWriter(new FileOutputStream(externalFile, true), false);
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException("An error occurred when trying to create log file: " + fileName);
    }
  }

  public void write(String className, String eventName, Object... details) {
    printWriter.write(
        dateFormat.format(new Date(System.currentTimeMillis())) + "," +
        className + "," +
        eventName + "," +
        TextUtils.join(",", details) + '\n');
    printWriter.flush();
  }
}
