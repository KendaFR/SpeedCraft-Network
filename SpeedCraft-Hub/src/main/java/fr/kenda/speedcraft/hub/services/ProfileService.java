package fr.kenda.speedcraft.hub.services;

import fr.kenda.speedcraft.api.database.data.UserProfile;
import fr.kenda.speedcraft.api.service.IService;

import java.util.ArrayList;
import java.util.List;

public class ProfileService implements IService {
    private static ProfileService instance;
    private final List<UserProfile> profiles = new ArrayList<>();

    public static ProfileService getInstance() {
        if (instance == null)
            instance = new ProfileService();
        return instance;
    }

    @Override
    public void register() {
    }

    @Override
    public void unregister() {
    }

    public void addProfile(UserProfile profile) {

    }
}
