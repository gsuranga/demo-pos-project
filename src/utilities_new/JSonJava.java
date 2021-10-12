/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities_new;

/**
 *
 * @author SHDINESH
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSonJava {

    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
//--------------------------------------GET Method----------------------------------------------------

    public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {

        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            //JSONObject json = new JSONObject(jsonText);
            JSONObject json = new JSONObject(jsonText);
            //json.put("val", jsonText);
            return json;
        } finally {
            is.close();
        }
    }
//------------------------------------------POST Method-----------------------------------------------

    public JSONObject postJSONArray(JSONArray ja, String url, String param) throws Exception {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        JSONObject returnedObject = null;
        try {
            HttpPost httppost = new HttpPost(url);
            StringBody json = new StringBody(ja.toString(), ContentType.APPLICATION_JSON);
            HttpEntity reqEntity = MultipartEntityBuilder.create().addPart(param, json).build();
            httppost.setEntity(reqEntity);
            HttpHost proxy = new HttpHost("192.168.1.1", 3128, "http");
            System.setProperty("java.net.useSystemProxies", "true");
            // System.out.println("executing request " + httppost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httppost);
            //System.out.println(response.getStatusLine());
            StringBuilder responseString = new StringBuilder();
            try {
                //System.out.println("----------------------------------------");
                //System.out.println(response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    //System.out.println("Response content length: " + resEntity.getContentLength());
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resEntity.getContent()));
                    String currentLine;
                    while ((currentLine = bufferedReader.readLine()) != null) {
                        responseString.append(currentLine);
                    }
                    returnedObject = new JSONObject(responseString.toString());

                  //  System.out.println(returnedObject);
                }
                EntityUtils.consume(resEntity);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                response.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            httpclient.close();
        }
        return returnedObject;
    }

    public JSONObject postJSONObject(JSONObject ja, String url, String param) throws Exception {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        JSONObject returnedObject = null;
        try {
            HttpPost httppost = new HttpPost(url);
            StringBody json = new StringBody(ja.toString(), ContentType.APPLICATION_JSON);
            HttpEntity reqEntity = MultipartEntityBuilder.create().addPart(param, json).build();
            httppost.setEntity(reqEntity);
            //System.out.println("executing request " + httppost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httppost);
            //System.out.println(response.getStatusLine());
            StringBuilder responseString = new StringBuilder();
            try {
                //System.out.println("----------------------------------------");
                //System.out.println(response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    //System.out.println("Response content length: " + resEntity.getContentLength());
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resEntity.getContent()));
                    String currentLine;
                    while ((currentLine = bufferedReader.readLine()) != null) {
                        responseString.append(currentLine);
                    }
                    returnedObject = new JSONObject(responseString.toString());

                   // System.out.println(returnedObject);
                }
                EntityUtils.consume(resEntity);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                response.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            httpclient.close();
        }
        return returnedObject;
    }
}
