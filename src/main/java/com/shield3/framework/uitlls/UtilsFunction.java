package com.shield3.framework.uitlls;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;
import javax.mail.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

public class UtilsFunction {


    public static String readEmailOTP() {
        String host = "imap.gmail.com";
        String username = "testingwits02@gmail.com";
        String password = "hpoz eqxj wsnm xwaa";

        // Set properties and their values
        Properties properties = new Properties();
        properties.put("mail.imap.host", host);
        properties.put("mail.imap.port", "993");
        properties.put("mail.imap.starttls.enable", "true");
        properties.put("mail.imap.ssl.trust", host);
        properties.put("mail.imap.ssl.enable", "true");

        // Get the session object
        Session emailSession = Session.getInstance(properties);

        try {
            // Create the IMAP store object and connect with the server
            Store store = emailSession.getStore("imap");
            store.connect(host, username, password);

            // Create the folder object and open it in your mailbox
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            // Fetch messages from the folder
            Message[] messages = emailFolder.getMessages();

            for (int i=messages.length-1; i>messages.length-10; i--) {
                //System.out.println("Verification Code: "+messages[i].getSubject());//5558
                if (messages[i].getSubject().contains("Your code for Shield3")) {

                    String code = "";
                    Pattern pattern = Pattern.compile("Your code for Shield3 is here: ([A-Z0-9-]+)");
                    Matcher matcher = pattern.matcher(messages[i].getSubject());
                    if (matcher.find()) {
                        code = matcher.group(1);
                    }
                    return code.replace("-","");
                }
            }

            // Close the store and folder objects
            emailFolder.close(false);
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String readTextFileAndGetAsString(String strFilePath) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(strFilePath))) {
            StringBuilder str = new StringBuilder();
            String strCurrentLine = "";
            while ((strCurrentLine = bufferedReader.readLine()) != null) {

                str.append(strCurrentLine);
            }
            return str.toString();
        } catch (Exception e) {
            return null;
        }
    }
}