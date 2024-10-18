package pl.servicetrack.db;

import org.springframework.jdbc.core.RowMapper;
import pl.servicetrack.model.TechnicianModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class TechnicianMapper implements RowMapper<TechnicianModel> {
    @Override
    public TechnicianModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new TechnicianModel(
                UUID.fromString(rs.getString(Fields.ID)),
                rs.getString(Fields.FIRST_NAME),
                rs.getString(Fields.LAST_NAME),
                rs.getString(Fields.EMAIL),
                rs.getString(Fields.PHONE)
        );
    }

    private static final class Fields {
        private static final String ID = "id";
        private static final String FIRST_NAME = "first_name";
        private static final String LAST_NAME = "last_name";
        private static final String EMAIL = "email";
        private static final String PHONE = "phone";

    }
}
