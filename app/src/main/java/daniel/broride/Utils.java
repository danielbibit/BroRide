package daniel.broride;


import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.support.v7.app.AlertDialog;


import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Utils {
    private final static String strSeparator = "__,__";
    final static String fileName = "cache";

    private Utils(){}

    public static String ArrayToString(String[] array){
        String str = "";
        for (int i = 0;i<array.length; i++) {
            str = str+array[i];
            if(i<array.length-1){
                str = str+strSeparator;
            }
        }
        return str;
    }

    public static String[] StringToArray(String str){
        String[] arr = str.split(strSeparator);
        return arr;
    }


    public static String ListToString(ArrayList<String> userArrayList){

        String str = "";
        for(int i = 0; i<userArrayList.size(); i++){
            str = str+userArrayList.get(i);

            if(i<userArrayList.size()-1){
                str = str+strSeparator;
            }
        }

        return str;
    }

    public static void saveCache(String string,Context context){
        try {
            FileOutputStream fileOutputStream =  context.openFileOutput(fileName,MODE_PRIVATE);
            fileOutputStream.write(string.getBytes());
            fileOutputStream.close();
            Log.d("Salvo :",string);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readCache(Context context){
        String string;
        try {
            FileInputStream fileInputStream = context.openFileInput(fileName);

            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();

            string = bufferedReader.readLine();
            stringBuffer.append(string);

            return stringBuffer.toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }



    public static void showMessage(String title, String message, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context.getApplicationContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}
