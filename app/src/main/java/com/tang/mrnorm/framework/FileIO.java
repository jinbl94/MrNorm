package com.tang.mrnorm.framework;

import android.content.SharedPreferences;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by tang on 4/14/16.
 */
public interface FileIO {
    public InputStream readAsset(String fileName) throws IOException;
    public InputStream readFile(String fileName) throws IOException;
    public OutputStream writeFile(String fileName) throws IOException;
    public SharedPreferences getPreferences();
}
