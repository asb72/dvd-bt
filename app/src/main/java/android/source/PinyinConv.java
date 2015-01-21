package android.source;

//import com.android.internal.telephony.SmsHeader;

public class PinyinConv {
    private static final int BEGIN = 45217;
    private static final int END = 63486;
    private static final char[] chartable;
    private static final char[] initialtable;
    private static final int[] table;

    static {
        chartable = new char[]{'\u554a', '\u82ad', '\u64e6', '\u642d', '\u86fe', '\u53d1', '\u5676', '\u54c8', '\u54c8', '\u51fb', '\u5580', '\u5783', '\u5988', '\u62ff', '\u54e6', '\u556a', '\u671f', '\u7136', '\u6492', '\u584c', '\u584c', '\u584c', '\u6316', '\u6614', '\u538b', '\u531d'};
        table = new int[27];
        initialtable = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'h', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 't', 't', 'w', 'x', 'y', 'z'};
        for (int i = 0; i < 26; i++) {
            table[i] = gbValue(chartable[i]);
        }
        table[26] = 63486;
    }

    public static String cn2py(String SourceStr) {
        String Result = "";
        try {
            int StrLength = SourceStr.length();
            for (int i = 0; i < StrLength; i++) {
                Result = Result + Char2Initial(SourceStr.charAt(i));
            }
            return Result;
        } catch (Exception e) {
            return "";
        }
    }

    private static char Char2Initial(char ch) {
        if (ch >= 'a' && ch <= 'z') {
            return (char) ((ch - 97) + 65);
        }
        if (ch >= 'A' && ch <= 'Z') {
            return ch;
        }
        int gb = gbValue(ch);
        if (gb < 45217 || gb > 63486) {
            return ch;
        }
        int i = 0;
        while (i < 26) {
            if (gb >= table[i] && gb < table[i + 1]) {
                break;
            }
            i++;
        }
        if (gb == 63486) {
            i = 25;
        }
        char initial = initialtable[i];
        if (i == 25) {
            switch (gb) {
                case 59075:
                    initial = 't';
                    break;
            }
        }
        return (char) ((initial - 97) + 65);
    }

    private static int gbValue(char ch) {
        try {
            byte[] bytes = (new String() + ch).getBytes("GB2312");
            if (bytes.length < 2) {
                return 0;
            }
            return (bytes[1] & 255) + ((bytes[0] << 8) & 65280);
        } catch (Exception e) {
            return 0;
        }
    }
}