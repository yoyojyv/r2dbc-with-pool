package me.jerry.example.r2dbcwithpool.user;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class User {

    @Id
    private Long id;
    private String username;
    private String name;

}
