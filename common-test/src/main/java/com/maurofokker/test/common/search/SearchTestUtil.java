package com.maurofokker.test.common.search;

import com.maurofokker.client.util.SearchUriBuilder;
import com.maurofokker.common.search.ClientOperation;
import com.maurofokker.common.util.SearchField;
import org.apache.commons.lang3.tuple.Triple;

public final class SearchTestUtil {

    private SearchTestUtil() {
        throw new UnsupportedOperationException();
    }

    // API

    public static String constructQueryString(final String idVal, final String nameVal) {
        return new SearchUriBuilder().consume(ClientOperation.EQ, SearchField.id.toString(), idVal, false).consume(ClientOperation.EQ, SearchField.name.toString(), nameVal, false).build();
    }

    public static String constructQueryString(final String idVal, final boolean negatedId, final String nameVal, final boolean negatedName) {
        return new SearchUriBuilder().consume(ClientOperation.EQ, SearchField.id.toString(), idVal, negatedId).consume(ClientOperation.EQ, SearchField.name.toString(), nameVal, negatedName).build();
    }

    public static String constructQueryString(final Triple<String, ClientOperation, String> idConstraint, final Triple<String, ClientOperation, String> nameConstraint) {
        return new SearchUriBuilder().consume(idConstraint).consume(nameConstraint).build();
    }

}
