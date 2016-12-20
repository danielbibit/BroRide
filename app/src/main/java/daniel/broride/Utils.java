package daniel.broride;

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
}
