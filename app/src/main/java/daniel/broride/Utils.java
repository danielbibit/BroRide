package daniel.broride;

import android.content.ContentValues;
import android.content.Context;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;

public class Utils {
    private final static String strSeparator = "__,__";

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

    public static void showMessage(String title, String message, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context.getApplicationContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
