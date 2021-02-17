package com.github.novikovmn.architect.utils.datamapper.identitymap;

import com.github.novikovmn.architect.domain.Domain;
import com.github.novikovmn.architect.utils.datamapper.DataMapper;

import java.util.HashMap;
import java.util.Map;

public abstract class IdentityMap<DomainType extends Domain, IdType> {
    private Map<IdType, DomainType> map = new HashMap<>();
    private DataMapper<DomainType, IdType> dataMapper;

    protected IdentityMap(DataMapper<DomainType, IdType> dataMapper) {
        this.dataMapper = dataMapper;
    }

    public Map getMap() { return map; }

    public DomainType getById(IdType id) {
        DomainType domain = map.get(id);
        if (domain == null) {
            domain = dataMapper.getById(id);
            map.put(id, domain);
        } else {
            // Логирование для тестирования identity map - отслеживать, когда производилось обращение к БД,
            // а когда объект получался из кэша.
            System.out.println("Получение объекта из кэша identity map по id...");
        }
        return domain;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("IdentityMap(\n");
        for (DomainType domain : map.values()) {
            sb.append(domain.toString()).append("\n");
        }
        sb.append(")");
        return sb.toString();
    }
}
