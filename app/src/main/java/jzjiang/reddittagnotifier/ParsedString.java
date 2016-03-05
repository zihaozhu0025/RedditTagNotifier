package jzjiang.reddittagnotifier;

/**
 * Created by zihao on 2016-03-05.
 */
import android.util.*;
public class ParsedString  {
    String[]thread;
    public ParsedString(String[]thread)
    {
        this.thread=thread;
    }
    public String toString()
    {
        //TYPE ARTIST TITLE UPVOTES URL
        StringBuilder str = new StringBuilder("");
        str.append(thread[1]);
        str.append("-");
        str.append(thread[2]);
        str.append(" #Upvotes: ");
        str.append(thread[3]);
        Log.i("PARSED STRINGG", thread[4]);
        return str.toString();
    }
    public String returnUrl()
    {
        StringBuilder st = new StringBuilder("https://www.reddit.com");
        st.append(thread[4]);
        return st.toString();
    }


}
