/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities_new;

import controllers.ProfileController;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.ResultSet;

/**
 *
 * @author Dinesh
 */
public class CheckConnection {

    public static boolean isInternetReachable() {
        try {
            //make a URL to a known source
            ResultSet rst = ProfileController.getDealerDetails();
            rst.next();
            String serverURL = rst.getString(9);
            URL url = new URL(serverURL);

            //open a connection to that source
            HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();

            //trying to retrieve data from the source. If there
            //is no connection, this line will failhttp://localhost/
            Object objData = urlConnect.getContent();

        } catch (UnknownHostException e) {
           // e.printStackTrace();
            // TODO Auto-generated catch block

            return false;
        } catch (IOException e) {
            //e.printStackTrace();
            // TODO Auto-generated catch block

            return false;
        } catch (Exception ex) {
           // ex.printStackTrace();
            return false;
        }
        return true;
    }

}
