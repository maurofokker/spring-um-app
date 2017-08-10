package com.maurofokker.test.common.client.template;

import com.jayway.restassured.specification.RequestSpecification;
import com.maurofokker.client.marshall.IMarshaller;
import com.maurofokker.client.template.IRestClientWithUri;
import com.maurofokker.common.interfaces.IDto;
import com.maurofokker.common.interfaces.IOperations;

public interface IRestClient<T extends IDto> extends IOperations<T>, IRestClientAsResponse<T>, IRestClientWithUri<T> {

    // template

    RequestSpecification givenReadAuthenticated();

    RequestSpecification givenDeleteAuthenticated();

    IMarshaller getMarshaller();

    String getUri();

}
