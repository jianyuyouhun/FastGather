package com.jianyuyouhun.mobile.fastgather.library.utils;


import com.jianyuyouhun.mobile.okrequester.library.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 流处理
 */
public class StreamUtil {
    public static String toHex(int value, int length) {
        StringBuilder hex = new StringBuilder(Integer.toHexString(value));
        hex = new StringBuilder(hex.toString().toUpperCase());

        if (hex.length() < length) {
            while (hex.length() < length)
                hex.insert(0, "0");
        } else if (hex.length() > length) {
            hex = new StringBuilder(hex.substring(hex.length() - length));
        }
        return hex.toString();
    }

    public static byte[] streamToBytes(InputStream stream) throws IOException,
            OutOfMemoryError {
        byte[] buff = new byte[1024];
        int read;
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        while ((read = stream.read(buff)) != -1) {
            bao.write(buff, 0, read);
        }
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bao.toByteArray();
    }

    public static File writeBytesToFile(InputStream is, File file) throws IOException {
        FileOutputStream fos = null;
        try {
            byte[] data = new byte[2048];
            int nbread = 0;
            fos = new FileOutputStream(file);
            while ((nbread = is.read(data)) > -1) {
                fos.write(data, 0, nbread);
            }
        } catch (Exception ex) {
            Logger.e("Exception", ex.getMessage());
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
        return file;
    }
}
