package com.example.destinity;

public class Users {
   public String id, livingPlace, userInfo, brukerNavn,alder,bildet;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLivingPlace() {
        return livingPlace;
    }

    public void setLivingPlace(String livingPlace) {
        this.livingPlace = livingPlace;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public String getBildet() {
        return bildet;
    }

    public void setBildet(String bildet) {
        this.bildet = bildet;
    }

    public String getBrukerNavn() {
        return brukerNavn;
    }

    public void setBrukerNavn(String brukerNavn) {
        this.brukerNavn = brukerNavn;
    }

    public String getAlder() {
        return alder;
    }

    public void setAlder(String alder) {
        this.alder = alder;
    }

    public Users(String alder, String bildet, String brukerNavn, String id, String livingPlace,String userInfo){
        this.brukerNavn = brukerNavn;
        this.id= id;
        this.alder= alder;
        this.livingPlace =livingPlace;
        this.userInfo =userInfo;
        this.bildet = bildet;


    }
    public Users() { }
}
