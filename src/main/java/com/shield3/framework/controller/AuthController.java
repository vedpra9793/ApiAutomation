package com.shield3.framework.controller;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.*;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;


public class AuthController extends APIEnvMainController {

    static String otpEndpoint = "/auth/otp";
    static String payload;



    public static void testcases(String testCase){
        switch (testCase) {
            case "OTP-init Valid Email and Domain":
                payload = readTextFileAndGetAsString(System.getProperty(dir) + "/Input/Dashboard/Auth/ValidEmailandDomain.json");
                break;
            case "OTP-init with Invalid Email Format":
                payload = readTextFileAndGetAsString(System.getProperty(dir) + "/Input/Dashboard/Auth/InvalidEmailFormat.json");
                break;
            case "OTP-init with Whitespace in Email":
                payload = readTextFileAndGetAsString(System.getProperty(dir) + "/Input/Dashboard/Auth/OTPinitWithWhitespaceinEmail.json");
                break;
            case "Empty Email and Domain Fields":
                payload = readTextFileAndGetAsString(System.getProperty(dir) + "/Input/Dashboard/Auth/EmptyEmailandDomainFields.json");
                break;
            case "OTP-init with Missing Email":
                payload = readTextFileAndGetAsString(System.getProperty(dir) + "/Input/Dashboard/Auth/MissingEmail.json");
                break;
            case "OTP-init with Missing Domain":
                payload = readTextFileAndGetAsString(System.getProperty(dir) + "/Input/Dashboard/Auth/OTPinitwithMissingDomain.json");
                break;
            case "OTP-init with Empty Payload":
                payload = readTextFileAndGetAsString(System.getProperty(dir) + "/Input/Dashboard/Auth/OTPinitwithEmptyPayload.json");
                break;
            case "OTP-init with Different Case Email":
                payload = readTextFileAndGetAsString(System.getProperty(dir) + "/Input/Dashboard/Auth/OTPinitwithDifferentCaseEmail.json");
                break;
            case "OTP-init with Extra Fields in Payload":
                payload = readTextFileAndGetAsString(System.getProperty(dir) + "/Input/Dashboard/Auth/OTPinitWithExtraFieldsinPayload.json");
                break;
            case "Valid OTP Code":
                emailOTP = readEmailOTP();
                payload = readTextFileAndGetAsString(System.getProperty(dir) + "/Input/Dashboard/Auth/ValidOTPCode.json").replace("validOTP",emailOTP);
                break;
            case "Valid Request with Invalid OTP Code":
                payload = readTextFileAndGetAsString(System.getProperty(dir) + "/Input/Dashboard/Auth/OTPverifywithInvalidOTPCode.json");
                break;
            case "Valid Request with Missing OTP Code":
                payload = readTextFileAndGetAsString(System.getProperty(dir) + "/Input/Dashboard/Auth/OTPverifywithMissingOTPCode.json");
                break;
            case "Valid Request with OTP Code Already Used":
                payload = readTextFileAndGetAsString(System.getProperty(dir) + "/Input/Dashboard/Auth/ValidOTPCode.json").replace("validOTP",emailOTP);
                break;
            case "Valid Request with OTP Code Expired":
                payload = readTextFileAndGetAsString(System.getProperty(dir) + "/Input/Dashboard/Auth/OTPverifywithOTPCodeExpired.json");
                break;
            default:
                throw new IllegalArgumentException("Invalid test case: " + testCase);
        }
    }

    public static Response otpInitVerify(){

        Response res = given()
                .filter(new AllureRestAssured())
                .header("Content-Type", "application/json")
                .body(payload)
                .when()
                .post(baseUri+otpEndpoint);
        return res;
    }

}
