package me.minutz.site.udate;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import me.minutz.site.Main;

public class Update
{
  private String url;

  private boolean out = true;

  public Update(String pluginurl)
  {
    this.url = pluginurl;
  }

  public void externalUpdate()
  {
    try
    {
      URL download = new URL(url);

      BufferedInputStream in = null;
      FileOutputStream fout = null;
      try {
        if (this.out) {
          System.out.println("Updating...");
        }
        in = new BufferedInputStream(download.openStream());
    	String s = new java.io.File(Main.class.getProtectionDomain()
  			  .getCodeSource()
  			  .getLocation()
  			  .getPath())
  			.getName();
        fout = new FileOutputStream(System.getProperty("user.dir")+"\\"+ s);

        byte[] data = new byte[1024];
        int count;
        while ((count = in.read(data, 0, 1024)) != -1)
          fout.write(data, 0, count);
      }
      finally {
        if (in != null) {
          in.close();
        }
        if (fout != null) {
          fout.close();
        }
      }

      if (this.out) {
        System.out.println("Done!");
        System.exit(1);
      }
    }
    catch (IOException localIOException) {
    	localIOException.printStackTrace();
    }
  }

}