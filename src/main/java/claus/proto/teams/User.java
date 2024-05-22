package claus.proto.teams;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;
import java.util.regex.Pattern;

public class User
{
    UUID id;
    String name;
    String email;
    String phoneNumber;
    Date birthDate;
    Timestamp createdOn;

    public UUID getId()
    {
        return id;
    }

    public void setId(UUID id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {

        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        // validate string as email
        String regexPattern = "[A-Za-z0-9._%+\\-]+@[A-Za-z0-9\\.\\-]+\\.[A-Za-z]{2,}";
        if (!Pattern.compile(regexPattern).matcher(email).matches())
            throw new RuntimeException("Email address not valid");

        this.email = email;
    }

    public String getPhoneNumber()
    {

        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        String regexPattern = "^[0-9]*$";
        if (!Pattern.compile(regexPattern).matcher(phoneNumber).matches())
            throw new RuntimeException("Phone number not valid");

        this.phoneNumber = phoneNumber;
    }

    public Date getBirthDate()
    {
        return birthDate;
    }

    public void setBirthDate(Date birthDate)
    {
        this.birthDate = birthDate;
    }

    public Timestamp getCreatedOn()
    {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn)
    {
        this.createdOn = createdOn;
    }
}
