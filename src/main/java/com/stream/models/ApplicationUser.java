package com.stream.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user")
public class ApplicationUser {
    @Id
    private String _id ;

    private String name;
    private String email;
    private int age;
    private String gender;
    private List<String> contactNumber;
    private String password;
    private Date sqlDate = new Date() ;
    private String accountStatus = "active";
    private boolean isEmailVerified;
    private boolean isPhoneVerified;
}
