package com.maurofokker.common.util;

/**
 * Created by mgaldamesc on 03-08-2017.
 */
public enum  SearchField {
    id, name, // common
    uuid, // for Tenant only
    loginName, email, tenant, locked, // for User only
    description // for Privilege only
}
