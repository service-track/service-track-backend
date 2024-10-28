package pl.servicetrack.db;

import org.yaml.snakeyaml.events.Event;

public class ClientQuery {

    private static final String CLIENTS_TABLE = "client_client";
    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";
    private static final String EMAIL = "email";
    private static final String PHONE_NUMBER_COLUMN = "phone_number";

    public static final String SAVE_CLIENT = "INSERT INTO %s VALUES (?, ?, ?, ?)"
            .formatted(CLIENTS_TABLE);
    public static final String FETCH_CLIENTS = "SELECT * FROM %s"
            .formatted(CLIENTS_TABLE);
    public static final String FETCH_CLIENT = "SELECT * FROM %s WHERE %s = ?"
            .formatted(CLIENTS_TABLE, ID_COLUMN);

    public static final String DELETE_CLIENT = "DELETE FROM %s WHERE %s = ?"
            .formatted(CLIENTS_TABLE, ID_COLUMN);
}
