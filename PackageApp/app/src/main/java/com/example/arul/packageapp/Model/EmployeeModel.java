package com.example.arul.packageapp.Model;

public class EmployeeModel {
    private  static  EmployeeModel instance;
    public EmployeeModel() {
    }

    public static void setInstance(EmployeeModel instance) {
        EmployeeModel.instance = instance;
    }

    public static synchronized EmployeeModel getInstance() {
        if (instance == null) {
            instance = new EmployeeModel();
        }
        return instance;
    }

    public EmployeeModel(String token, String empId, String userType, String fullName, String contactNo, String emailId, String userRole,
                         String adhaarNo, String panNo, String address1, String address2, String city, String state,String country, String attempt,
                         String status) {
        Token = token;
        EmpId = empId;
        UserType = userType;
        FullName = fullName;
        ContactNo = contactNo;
        EmailId = emailId;
        UserRole = userRole;
        AdhaarNo = adhaarNo;
        PanNo = panNo;
        Address1 = address1;
        Address2 = address2;
        City = city;
        State = state;
        Country = country;
        Attempt = attempt;
        Status = status;
    }

    String Token;
    String EmpId;
    String UserType;
    String FullName;
    String ContactNo;
    String EmailId;
    String UserRole;
    String AdhaarNo;
    String PanNo;
    String Address1;
    String Address2;
    String City;
    String State;
    String Country;
    String Attempt;
    String Status;

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getEmpId() {
        return EmpId;
    }

    public void setEmpId(String empId) {
        EmpId = empId;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public String getUserRole() {
        return UserRole;
    }

    public void setUserRole(String userRole) {
        UserRole = userRole;
    }

    public String getAdhaarNo() {
        return AdhaarNo;
    }

    public void setAdhaarNo(String adhaarNo) {
        AdhaarNo = adhaarNo;
    }

    public String getPanNo() {
        return PanNo;
    }

    public void setPanNo(String panNo) {
        PanNo = panNo;
    }

    public String getAddress1() {
        return Address1;
    }

    public void setAddress1(String address1) {
        Address1 = address1;
    }

    public String getAddress2() {
        return Address2;
    }

    public void setAddress2(String address2) {
        Address2 = address2;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getAttempt() {
        return Attempt;
    }

    public void setAttempt(String attempt) {
        Attempt = attempt;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }


}
