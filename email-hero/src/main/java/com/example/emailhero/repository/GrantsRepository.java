package com.example.emailhero.repository;

import com.example.emailhero.db.Database;
import com.example.emailhero.db.InMemoryDatabaseImpl;
import com.example.emailhero.domain.PaginatedResponse;
import com.example.emailhero.exceptions.DataNotFoundException;
import com.example.emailhero.models.EmailDataContainer;
import com.example.emailhero.models.GrantRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GrantsRepository {
    private final Database<List<GrantRecord>> store;

    public GrantsRepository() {
        store = new InMemoryDatabaseImpl<>();
    }

    public void insertGrantRecords(String foundationEmail, List<GrantRecord> records) {
        store.put(foundationEmail, records);
    }

    public PaginatedResponse<GrantRecord> getGrantRecords(String foundationEmail,
                                                          int offset, int numRecords) throws DataNotFoundException {
        List<GrantRecord> allRecords = store.get(foundationEmail);
        List<GrantRecord> pageRecords = allRecords.subList(offset, offset + numRecords);
        return new PaginatedResponse<>(
                pageRecords,
                offset + numRecords,
                numRecords
        );
    }
}
