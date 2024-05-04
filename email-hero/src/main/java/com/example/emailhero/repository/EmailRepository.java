package com.example.emailhero.repository;


import com.example.emailhero.db.Database;
import com.example.emailhero.db.InMemoryDatabaseImpl;
import com.example.emailhero.exceptions.DataNotFoundException;
import com.example.emailhero.models.EmailData;
import com.example.emailhero.models.EmailDataContainer;
import com.example.emailhero.models.NonProfit;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class EmailRepository {
    private final Database<EmailDataContainer> store;

    public EmailRepository() {
        this.store = new InMemoryDatabaseImpl<>();
    }

    public void addEmailMessage(String foundationEmail, NonProfit nonProfit, String emailMessage) throws DataNotFoundException {
        EmailData data = new EmailData(nonProfit.getEmail(), emailMessage, Date.from(Instant.now()));
        if (this.store.containsKey(foundationEmail)) {
            EmailDataContainer emailContainer = store.get(foundationEmail);
            emailContainer.addEmail(data);
        } else {
            EmailDataContainer emailDataContainer = new EmailDataContainer();
            emailDataContainer.addEmail(data);
            this.store.put(foundationEmail, emailDataContainer);
        }
    }


    public ArrayList<EmailData> getAllEmails(String foundationEmail) throws DataNotFoundException {
        EmailDataContainer emailDataContainer = store.get(foundationEmail);
        return emailDataContainer.getAllEmails();
    }
}

/*
* foundation:
* SQL
* Email: id, parentId, message, sender, receiver, reply
*  Email01, NULL, Some message, abcd@gmail.com, np@gmail.com, False
*  Email02, Email01, Reply, np@gmail.com, abcd@gmail.com, True
*  Email03, Email02, Send second message, abcd@gmail.com, np@gmail.com, False
*
*
*   SELECT * FROM Email where parentId is NULL; -- Top level
*
*   SELECT * FROM Email where parentId = "Email01"; -- Thread
*
*   Email--Thread--> Email2 --Thread --> Email3
*                      ------Thread---> Email4
*
*
*    Email1
*      > Email2
*          > Email3
*      > Email5
*      > Email6
*      > Email7
* */