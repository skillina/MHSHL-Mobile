package net.skillina.mhshl_android;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;

/**
 * Created by Alex on 7/28/2016.
 */
public final class NetManager {

    Context context;

    ArrayList<String> queue = new ArrayList<String>();

    public NetManager(Context c) {
        context = c;
    }

    public void queueData(String page, String division, String season){

        String url = "http://skillina.net/MHSHL-Transfer/" + page + ".php?db="+division + "&season=" + season;
        queue.add(url);
    }

    public void startTransactions(){
        ConnectivityManager connMgr = (ConnectivityManager) App.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){

            String[] urls = Arrays.copyOf(queue.toArray(), queue.toArray().length, String[].class);
            new DownloadWebpageTask().execute(urls);
        }else{
            // throw error
        }
    }

    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls){
            for(int i = 0; i < urls.length; i++) {
                try {
                    System.out.println("Sending order to download " + urls[i]);
                    SyncManager.process(downloadURL(urls[i]));
                    // SyncManager.process(getURLAsInputStream(urls[i]));
                } catch (IOException e) {
                    // IDK
                    System.out.println("Caught IOException in DownloadWebpageTask.doInBackground");
                    return "ERR";
                }
            }
            return "Success";
        }

        @Override
        protected void onPostExecute(String result){
            System.out.println(result);
        }
    }

    private InputStream getURLAsInputStream(String myURL) throws IOException{
        InputStream is = null;
        int len = 100000;
        System.out.println("Attempting to download" + myURL);
        try{
            URL url = new URL(myURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept-Encoding", "gzip");
            conn.setDoInput(true);
            conn.connect();
            return new GZIPInputStream(conn.getInputStream())  ;

            /*
             * String contentAsString = readIt(is, len);
             * contentAsString=contentAsString.replace(",,", ",0,");
             * contentAsString=contentAsString.replace(",)", ",0)");
             * System.out.print(contentAsString);
             * return contentAsString.split("]")[0] + "]";
             */
        }finally {
            if(is != null){
                is.close();
            }
        }
    }

    private String downloadURL(String myURL) throws IOException{
        InputStream is = null;
        int len = 100000;
        System.out.println("Attempting to download" + myURL);
        try{
            URL url = new URL(myURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept-Encoding", "gzip");
            conn.setDoInput(true);
            conn.connect();
            is = new GZIPInputStream(conn.getInputStream())  ;

            String contentAsString = readIt(is, len);
            contentAsString=contentAsString.replace(",,", ",0,");
            contentAsString=contentAsString.replace(",)", ",0)");
            return contentAsString.split("]")[0] + "]";
        }finally {
            if(is != null){
                is.close();
            }
        }
    }

    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }
}
