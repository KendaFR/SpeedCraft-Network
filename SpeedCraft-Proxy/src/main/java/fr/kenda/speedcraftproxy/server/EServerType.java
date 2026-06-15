package fr.kenda.speedcraftproxy.server;

import java.util.ArrayList;
import java.util.List;

public enum EServerType
{
    HUB;

    public static List<String> getAllTypes()
    {
        List<String> result = new ArrayList<>();
        for (EServerType value : values()) {
            result.add(value.name());
        }
        return result;
    }
}
