package com.implementLife.BankMock.data;

import com.implementLife.BankMock.entity.BusinessApp;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class InMemoryBusinessAppRepo implements BusinessAppRepo {
    private final Map<UUID, BusinessApp> businessAppsById = new TreeMap<>();

    private void saveIndexes(BusinessApp app) {
        businessAppsById.put(app.getId(), app);
    }
    @Override
    public BusinessApp save(BusinessApp app) {
        app.setId(UUID.randomUUID());
        app.setAccessApiToken(UUID.randomUUID());
        saveIndexes(app);
        return app;
    }
    @Override
    public BusinessApp find(UUID id) {
        return businessAppsById.get(id);
    }
}
