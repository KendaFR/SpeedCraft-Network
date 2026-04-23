package fr.kenda.speecraft.api.database.repository;

import fr.kenda.speecraft.api.database.wrapper.WrapperQuery;

import javax.sql.DataSource;

public abstract class Repository {

    protected final DataSource dataSource;
    protected final WrapperQuery wrapperQuery;

    public Repository(DataSource source, WrapperQuery wrapperQuery) {
        this.dataSource = source;
        this.wrapperQuery = wrapperQuery;
    }
}
