package bg.sofia.uni.fmi.mjt.netflix.account;

import java.time.LocalDateTime;

public class Account {
    private String username;
    private LocalDateTime birhdayDate;

    public Account(String username, LocalDateTime birthdayDate)
    {
        this.username = username;
        this.birhdayDate = birthdayDate;
    }

    public String getUsername(){
        return this.username;
    }

    public LocalDateTime getBirhdayDate(){
        return this.birhdayDate;
    }
}
