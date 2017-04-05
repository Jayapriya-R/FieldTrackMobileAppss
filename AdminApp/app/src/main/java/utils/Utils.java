package utils;

/**
 * Created by mitosis on 1/12/16.
 */

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class Utils {

    static String respCallBack;
    static InputStream is = null;

    static HttpClient httpClient;

    public static String WebCall(String uRL, String body) {

        System.out.println("Request "+uRL);
        System.out.println("Body "+body);

        httpClient = new DefaultHttpClient();
        HttpGet httpGet=null;
        HttpResponse response=null;
        StringEntity s;
        HttpPost httpPost;
        HttpDelete httpDelete;
        HttpPut httpput;

        try {

            if(body.equals("GET"))
            {
				httpGet = new HttpGet(uRL);
				response = httpClient.execute(httpGet);

            }else if(body.equals("PUT")){
				/*httpput = new HttpPut(uRL);
				s = new StringEntity(Constants.PutMethodReques, HTTP.UTF_8);
				httpput.setEntity(s);
				httpput.setHeader("Content-Type", "application/json");
				response = httpClient.execute(httpput);*/
            }
            else if(body.equals("DELETE")){
                httpDelete = new HttpDelete(uRL);
                httpDelete.setHeader("Content-Type", "application/json");
                response = httpClient.execute(httpDelete);
            }else{
                httpPost = new HttpPost(uRL);
                s = new StringEntity(body, HTTP.UTF_8);
                httpPost.setEntity(s);
                httpPost.setHeader("Content-Type", "application/json");
                response = httpClient.execute(httpPost);
            }


            HttpEntity httpEntity = response.getEntity();
            is = httpEntity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            respCallBack = sb.toString();

        } catch (UnsupportedEncodingException e) {
            // writing error to Log
            e.printStackTrace();
            respCallBack = "UnsupportedEncoding Exception";

        } catch (ClientProtocolException e) {
            // writing exception to log
            e.printStackTrace();
            respCallBack = "ClientProtocol Exception";

        } catch (IOException e) {

            respCallBack = "IO Exception";
        }

        return respCallBack;
    }
}
