@TypeDefs({
        @TypeDef(name = "localDateTimeType", defaultForType = LocalDateTime.class, typeClass = LocalDateTimeType.class),
        @TypeDef(name = "localDateType", defaultForType = LocalDate.class, typeClass = LocalDateType.class)
}) package alvin;

import alvin.core.convert.LocalDateTimeType;
import alvin.core.convert.LocalDateType;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import java.time.LocalDate;
import java.time.LocalDateTime;