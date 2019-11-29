package dk.digitalidentity.app.model;

import org.joda.time.DateTime;

public class User {
    private String objectclass;

    private String distinguishedname;

    private String userPassword;

    private String cn;

    private String telephoneNumber;

    private String userprincipalname;

    private String department;

    private String company;

    private String mail;

    private String streetAddress;

    private String st;

    private String postalCode;

    private String l;

    private String description;

    private String c;

    private String countryCode;

    private String sn;

    private String employeeId;

    private DateTime lastLogon;

    private String memberof;

    private String givenname;

    private String logoncount;

    private String displayname;

    public String getObjectclass() {
        return objectclass;
    }

    public void setObjectclass(String objectclass) {
        this.objectclass = objectclass;
    }

    public String getDistinguishedname() {
        return distinguishedname;
    }

    public void setDistinguishedname(String distinguishedname) {
        this.distinguishedname = distinguishedname;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getUserprincipalname() {
        return userprincipalname;
    }

    public void setUserprincipalname(String userprincipalname) {
        this.userprincipalname = userprincipalname;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getL() {
        return l;
    }

    public void setL(String l) {
        this.l = l;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public DateTime getLastLogon() {
        return lastLogon;
    }

    public void setLastLogon(DateTime lastLogon) {
        this.lastLogon = lastLogon;
    }

    public String getMemberof() {
        return memberof;
    }

    public void setMemberof(String memberof) {
        this.memberof = memberof;
    }

    public String getGivenname() {
        return givenname;
    }

    public void setGivenname(String givenname) {
        this.givenname = givenname;
    }

    public String getLogoncount() {
        return logoncount;
    }

    public void setLogoncount(String logoncount) {
        this.logoncount = logoncount;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    @Override
    public String toString() {
        return "User{" +
                "objectclass='" + objectclass + '\'' +
                ", distinguishedname='" + distinguishedname + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", cn='" + cn + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", userprincipalname='" + userprincipalname + '\'' +
                ", department='" + department + '\'' +
                ", company='" + company + '\'' +
                ", mail='" + mail + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", st='" + st + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", l='" + l + '\'' +
                ", description='" + description + '\'' +
                ", c='" + c + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", sn='" + sn + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", lastLogon=" + lastLogon +
                ", memberof='" + memberof + '\'' +
                ", givenname='" + givenname + '\'' +
                ", logoncount='" + logoncount + '\'' +
                ", displayname='" + displayname + '\'' +
                '}';
    }
}
