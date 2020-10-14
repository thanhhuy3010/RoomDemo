
package com.example.demoroomdb.Common.Logger;

public class LogcatConfigurator {
  private final long maxFileSize = 5120;
  private String fileName = "SSSUService.log";
  public LogcatConfigurator(final String fileName) {
    setFileName(fileName);
  }

  public void setFileName ( String fileName) {
    this.fileName = fileName;
  }

  public long getMaxSizeData () {
    return maxFileSize;
  }







}
