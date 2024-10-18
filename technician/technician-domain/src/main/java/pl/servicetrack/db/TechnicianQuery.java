package pl.servicetrack.db;

public class TechnicianQuery {
    private static final String TECHNICIAN_TABLE = "technician_technician";
    private static final String ID_COLUMN = "id";
    private static final String FIRST_NAME_COLUMN = "first_name";
    private static final String LAST_NAME_COLUMN = "last_name";
    private static final String EMAIL_COLUMN = "email";
    private static final String PHONE_NUMBER_COLUMN = "phone_number";

    public static final String SAVE_TECHNICIAN = "INSERT INTO %s VALUES (?, ?, ?, ?, ?)"
            .formatted(TECHNICIAN_TABLE);
}
