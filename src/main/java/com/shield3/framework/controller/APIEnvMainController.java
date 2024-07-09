package com.shield3.framework.controller;
import java.io.*;
import java.sql.SQLException;
import java.util.Properties;

import com.shield3.framework.uitlls.UtilsFunction;
import io.qameta.allure.Allure;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import org.testng.annotations.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

public class APIEnvMainController extends UtilsFunction {

    protected static String baseUri;
    protected static String dir = "user.dir";
    protected static String prodUri = "https://rest-prod.shield3.com";
    protected static String stagingUri = "https://rest-prod.dev.shield3.com";
    protected static String emailOTP;
    protected static String accessToken;

    @BeforeSuite
    public static void setReport() {
        String folderPath = "allure-results";
        File folder = new File(folderPath);
        if(folder.exists()){
            deleteFolder(folder);
            folder.mkdirs();
        }else {
            folder.mkdirs();
        }
    }
    public static boolean deleteFolder(File folder) {
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteFolder(file);
                }
            }
        }
        return folder.delete();
    }

    @AfterSuite
    public static void generateReport(){
        String allureCommand = "allure serve";
        try {
            String[] commandParts = allureCommand.split(" ");
            ProcessBuilder processBuilder = new ProcessBuilder(commandParts);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            System.out.println("Process started...");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            String serverUrl = null;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Server started at")) {
                    serverUrl = line;
                    break; // Stop processing once the URL is found
                }
            }
            if (serverUrl != null) {
                System.out.println("Server URL: " + serverUrl);
            } else {
                System.out.println("Failed to capture server URL.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @BeforeTest
    @Parameters({ "environment" })
    public static void init(@Optional("Staging") String environment) throws IOException, SQLException {
        if (environment.equalsIgnoreCase("prod")) {
            baseUri = prodUri;
            Properties properties = new Properties();
            properties.setProperty("Base URL", baseUri);
            properties.setProperty("Environment", "prod");
            try (FileOutputStream output = new FileOutputStream("allure-results/environment.properties")) {
                properties.store(output, null);
            } catch (IOException e) {
                e.printStackTrace();
            }


        } else if (environment.equalsIgnoreCase("Staging")) {
            baseUri = stagingUri;
            Properties properties = new Properties();
            properties.setProperty("Base URL", baseUri);
            properties.setProperty("Environment", "Staging");
            try (FileOutputStream output = new FileOutputStream("allure-results/environment.properties")) {
                properties.store(output, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @BeforeClass
    public void genrateToken() throws InterruptedException {
        System.out.println("Come here to generate token");
        if(accessToken == null){
            accessToken = getAccessToken();
        }
    }

    public String getAccessToken() throws InterruptedException {
        given()
                .header("Content-Type", "application/json")
                .body(readTextFileAndGetAsString(System.getProperty(dir) + "/Input/Dashboard/Auth/ValidEmailandDomain.json"))
                .when()
                .post(stagingUri+"/auth/otp");
        Thread.sleep(2000);
        Response res = given()
                .header("Content-Type", "application/json")
                .body(readTextFileAndGetAsString(System.getProperty(dir) + "/Input/Dashboard/Auth/ValidOTPCode.json").replace("validOTP",readEmailOTP()))
                .when()
                .post(stagingUri+"/auth/otp");
        String token = res.jsonPath().get("accessToken");
        System.out.println(token);
        return token;
    }
}
