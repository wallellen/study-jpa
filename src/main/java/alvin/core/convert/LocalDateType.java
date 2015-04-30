package alvin.core.convert;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

public class LocalDateType implements UserType, Serializable {

    private static final int[] SQL_TYPES = new int[]{Types.DATE};

    @Override
    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    @Override
    public Class returnedClass() {
        return LocalDate.class;
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
        Object value = StandardBasicTypes.DATE.nullSafeGet(resultSet, names, session, owner);
        if (value == null) {
            return null;
        }
        Date date = (Date) value;
        Instant instant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant, ZoneOffset.UTC).toLocalDate();
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index, SessionImplementor session)
            throws HibernateException, SQLException {
        if (value == null) {
            StandardBasicTypes.DATE.nullSafeSet(preparedStatement, null, index, session);
        } else {
            LocalDate localDate = (LocalDate) value;
            Instant instant = localDate.atStartOfDay().toInstant(ZoneOffset.UTC);
            Date date = new Date(Date.from(instant).getTime());
            StandardBasicTypes.DATE.nullSafeSet(preparedStatement, date, index, session);
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
