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
        int startIndex = offset * numRecords;
        int endIndex = (offset + 1) * numRecords;
        if (store.containsKey(foundationEmail)) {
            List<GrantRecord> allRecords = store.get(foundationEmail);
            List<GrantRecord> pageRecords = allRecords.subList(startIndex, Math.min(endIndex, allRecords.size()));
            boolean hasNextPage = endIndex < allRecords.size() - 1;
            return new PaginatedResponse<>(
                    pageRecords,
                    offset + 1,
                    allRecords.size(),
                    hasNextPage
            );
        } else {
            return new PaginatedResponse<>(
                    List.of(),
                    offset,
                    0,
                    false
            );
        }
    }
}
