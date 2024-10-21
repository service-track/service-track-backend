package pl.servicetrack.db;

import org.springframework.jdbc.core.RowMapper;
import pl.servicetrack.model.ClientModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ClientMapper implements RowMapper<ClientModel> {

    @Override
    public ClientModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ClientModel(
                UUID.fromString(rs.getString(Fields.ID)),
                rs.getString(Fields.NAME),
                rs.getString(Fields.EMAIL),
                rs.getString(Fields.PHONE_NUMBER)
        );
    }

    private static final class Fields {
        private static final String ID = "id";
        private static final String NAME = "name";
        private static final String EMAIL = "email";
        private static final String PHONE_NUMBER = "phone_number";
    }
}
