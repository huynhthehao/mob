package vn.homecredit.hcvn.helpers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public final class FileHelper {

    public static boolean saveFile(ByteArrayOutputStream stream, String filePath) {
        deleteFile(filePath);
        FileOutputStream outputStream = null;
        try{
            outputStream = new FileOutputStream(filePath);
            stream.writeTo(outputStream);
            stream.flush();
            stream.close();
            outputStream.flush();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }finally {
            if(outputStream != null) {
                try {outputStream.close();}
                catch (IOException e) {return false;}
            }
        }
    }

    public static void deleteFile(String filePath) {
        try {
            File myFile = new File(filePath);
            if (myFile.exists())
                myFile.delete();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
