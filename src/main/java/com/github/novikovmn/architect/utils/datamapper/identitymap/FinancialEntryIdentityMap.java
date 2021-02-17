package com.github.novikovmn.architect.utils.datamapper.identitymap;

import com.github.novikovmn.architect.domain.FinancialEntry;
import com.github.novikovmn.architect.utils.datamapper.DataMapper;
import com.github.novikovmn.architect.utils.datamapper.FinancialEntryDataMapper;

public class FinancialEntryIdentityMap extends IdentityMap<FinancialEntry, Long>{

    private static FinancialEntryIdentityMap instance;

    public static FinancialEntryIdentityMap getInstance() {
        if (instance == null) instance = new FinancialEntryIdentityMap(FinancialEntryDataMapper.getInstance());
        return instance;
    }

    private FinancialEntryIdentityMap(DataMapper<FinancialEntry, Long> dataMapper) {
        super(dataMapper);
    }
}
