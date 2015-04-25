package alvin.core.repositories;

import javax.persistence.Query;

public interface Statement {
    void prepare(Query query);
}
