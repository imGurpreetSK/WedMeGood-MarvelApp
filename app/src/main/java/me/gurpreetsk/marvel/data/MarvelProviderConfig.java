package me.gurpreetsk.marvel.data;

import ckm.simple.sql_provider.UpgradeScript;
import ckm.simple.sql_provider.annotation.ProviderConfig;
import ckm.simple.sql_provider.annotation.SimpleSQLConfig;

/**
 * Created by gurpreet on 14/04/17.
 */

@SimpleSQLConfig(
        name = "MarvelProvider",
        authority = "me.gurpreetsk.marvel",
        database = "marvel.db",
        version = 1)
public class MarvelProviderConfig implements ProviderConfig {

    @Override
    public UpgradeScript[] getUpdateScripts() {
        return new UpgradeScript[0];
    }
}
