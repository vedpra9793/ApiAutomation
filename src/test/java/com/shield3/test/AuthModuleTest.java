package com.shield3.test;

import com.shield3.framework.controller.AuthController;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Auth module")
@Feature("Verifying Auth module")
public class AuthModuleTest extends AuthController {
    public void genrateToken() {
        // Override this function to ignore token creation
    }

    @Description("Verify OTP-init with Valid Email and Domain")
    @Test(priority = 0)
    public void verifyWithValidEmailandDomain() {
        testcases("OTP-init Valid Email and Domain");
        Response res = otpInitVerify();
        Assert.assertEquals(res.getStatusCode(),200);
        Assert.assertEquals(res.print(),"{}");
    }

    @Description("Verify OTP-init with Different Case Email")
    @Test(priority = 1)
    public void verifyOTPinitwithDifferentCaseEmail() {
        testcases("OTP-init with Different Case Email");
        Response res = otpInitVerify();
        Assert.assertEquals(res.getStatusCode(),200);
        Assert.assertEquals(res.print(),"{}");
    }

    @Description("Verify OTP-init with Whitespace in Email")
    @Test(priority = 2)
    public void verifyOTPinitwithWhitespaceinEmail() {
        testcases("OTP-init with Whitespace in Email");
        Response res = otpInitVerify();
        Assert.assertEquals(res.getStatusCode(),200);
        Assert.assertEquals(res.print(),"{}");
    }

    @Description("Verify OTP-init with Extra Fields in Payload")
    @Test(priority = 3)
    public void verifyOTPinitwithExtraFieldsinPayload() {
        testcases("OTP-init with Extra Fields in Payload");
        Response res = otpInitVerify();
        Assert.assertEquals(res.getStatusCode(),200);
        Assert.assertEquals(res.print(),"{}");
    }

    @Description("Verify OTP-init Invalid Email Format")
    @Test(priority = 4)
    public void verifyWithInvalidEmailFormat() {
        testcases("OTP-init with Invalid Email Format");
        Response res = otpInitVerify();
        Assert.assertEquals(res.getStatusCode(),400);
        res.prettyPrint();
        String errorMsg = res.jsonPath().get("errors[0]");
        Assert.assertEquals(errorMsg,"email must be a valid email");
    }

    @Description("Verify OTP-init with Missing Email")
    @Test(priority = 5)
    public void verifyWithMissingEmail() {
        testcases("OTP-init with Missing Email");
        Response res = otpInitVerify();
        Assert.assertEquals(res.getStatusCode(),400);
        res.prettyPrint();
        String errorMsg = res.jsonPath().get("errors[0]");
        Assert.assertEquals(errorMsg,"email is a required field");
    }

    @Description("Verify OTP-init with Missing Domain")
    @Test(priority = 6)
    public void verifyOTPinitwithMissingDomain() {
        testcases("OTP-init with Missing Domain");
        Response res = otpInitVerify();
        Assert.assertEquals(res.getStatusCode(),200);
        Assert.assertEquals(res.print(),"{}");
    }

    @Description("Verify OTP-init with Empty Payload")
    @Test(priority = 7)
    public void verifyOTPinitwithEmptyPayload() {
        testcases("OTP-init with Empty Payload");
        Response res = otpInitVerify();
        Assert.assertEquals(res.getStatusCode(),400);
        res.prettyPrint();
        String errorMsg = res.jsonPath().get("errors[0]");
        Assert.assertEquals(errorMsg,"email is a required field");
    }

    @Description("Verify OTP-init with Empty Email and Domain Fields")
    @Test(priority = 8)
    public void verifyOTPinitWithEmptyEmailandDomainFields() {
        testcases("Empty Email and Domain Fields");
        Response res = otpInitVerify();
        Assert.assertEquals(res.getStatusCode(),400);
        res.prettyPrint();
        String errorMsg = res.jsonPath().get("errors[0]");
        Assert.assertEquals(errorMsg,"email is a required field");
    }

    // OTP-verify feature
    @Description("Verify OTP-verify with Valid OTP Code")
    @Test(priority = 9)
    public void verifyOTPverifyWithValidOTPCode() {
        testcases("Valid OTP Code");
        Response res = otpInitVerify();
        Assert.assertEquals(res.getStatusCode(),200);
        res.prettyPrint();
        String token = res.jsonPath().get("accessToken");
        accessToken = token;
        Assert.assertNotEquals(token,"");
    }

    @Description("Verify OTP-verify with Invalid OTP Code")
    @Test(priority = 10)
    public void verifyOTPverifyWithInvalidOTPCode() {
        testcases("Valid Request with Invalid OTP Code");
        Response res = otpInitVerify();
        Assert.assertEquals(res.getStatusCode(),401);
        res.prettyPrint();
        Assert.assertEquals(res.asString(),"Email or Code is not valid!");
    }

    @Description("Verify OTP-verify with Missing OTP Code")
    @Test(priority = 11)
    public void verifyOTPverifyWithMissingOTPCode() {
        testcases("Valid Request with Missing OTP Code");
        Response res = otpInitVerify();
        Assert.assertEquals(res.getStatusCode(),400);
        res.prettyPrint();
        String errorMsg = res.jsonPath().get("errors[0]");
        Assert.assertEquals(errorMsg,"code is a required field");
    }

    @Description("Verify OTP-verify with OTP Code Already Used")
    @Test(priority = 12)
    public void verifyOTPverifyWithOTPCodeAlreadyUsed() {
        testcases("Valid Request with OTP Code Already Used");
        Response res = otpInitVerify();
        Assert.assertEquals(res.getStatusCode(),401);
        res.prettyPrint();
        Assert.assertEquals(res.asString(),"Email or Code is not valid!");
    }

    @Description("Verify OTP-verify with OTP Code Expired")
    @Test(priority = 13)
    public void verifyOTPverifyWithOTPCodeExpired() {
        testcases("Valid Request with OTP Code Expired");
        Response res = otpInitVerify();
        Assert.assertEquals(res.getStatusCode(),401);
        res.prettyPrint();
        Assert.assertEquals(res.asString(),"Email or Code is not valid!");
    }

}
