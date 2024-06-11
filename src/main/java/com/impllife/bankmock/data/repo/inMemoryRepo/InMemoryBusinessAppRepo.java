//package com.impllife.bankmock.services.inMemoryRepo;
//
//import com.impllife.bankmock.services.interfaces.BusinessAppRepo;
//import com.impllife.bankmock.data.entity.BusinessApp;
//
//import java.util.Map;
//import java.util.TreeMap;
//import java.util.UUID;
//
//public class InMemoryBusinessAppRepo implements BusinessAppRepo {
//    private final Map<UUID, BusinessApp> businessAppsById = new TreeMap<>();
//
//    private void saveIndexes(BusinessApp app) {
//        businessAppsById.put(app.getId(), app);
//    }
//    @Override
//    public BusinessApp save(BusinessApp app) {
//        app.setId(UUID.randomUUID());
//        app.setAccessApiToken(UUID.randomUUID());
//        saveIndexes(app);
//        return app;
//    }
//    @Override
//    public BusinessApp findById(UUID id) {
//        return businessAppsById.get(id);
//    }
//}
