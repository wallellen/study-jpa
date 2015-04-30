package alvin.core.convert;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

public class LocalDateTimeType implements UserType, Serializable {

    private static final int[] SQL_TYPES = new int[]{Types.TIMESTAMP};

    @Override
    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    @Override
    public Class returnedClass() {
        return LocalDateTime.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return Objects.equals(x, y);
    }

    @Override
    public int hashCode(Object object) throws HibernateException {
        return Objects.hashCode(object);
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor session, Object owner)
            throws HibernateException, SQLException {
        Object value = StandardBasicTypes.TIMESTAMP.nullSafeGet(resultSet, names, session, owner);
        if (value == null) {
            return null;
        }
        Timestamp timestamp = (Timestamp) value;
        Instant instant = Instant.ofEpochMilli(timestamp.getTime());
        return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index, SessionImplementor session)
            throws HibernateException, SQLException {
        if (value == null) {
            StandardBasicTypes.TIMESTAMP.nullSafeSet(preparedStatement, null, index, session);
        } else {
            Timestamp ts = Timestamp.valueOf((LocalDateTime) value);
            StandardBasicTypes.TIMESTAMP.nullSafeSet(preparedStatement, ts, index, session);
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    @Override
    public Object assemble(Serializable cached, Object value) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }

}
