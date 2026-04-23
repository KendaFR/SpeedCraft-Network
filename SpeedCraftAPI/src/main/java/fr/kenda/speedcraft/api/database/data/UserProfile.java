package fr.kenda.speedcraft.api.database.data;

import fr.kenda.speedcraft.api.enumeration.Rank;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserProfile(String playerName, UUID uuid, LocalDateTime lastConnection, LocalDateTime firstConnection, Rank rank) {

}