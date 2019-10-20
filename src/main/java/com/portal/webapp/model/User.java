package com.portal.webapp.model;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "utenti")
@Data
public class User
{
    @Id
    private String id;

    @Indexed(unique = true)
    @Size(min = 5, max = 80, message = "{Size.UtentiSecurity.userId.Validation}")
    @NotNull(message = "{NotNull.Article.userId.Validation}")
    private String userId;

    @Size(min = 8, max = 80, message = "{Size.UtentiSecurity.password.Validation}")
    private String password;

    private String attivo;

    private List<String> ruoli;

}
