package com.github.davidmoten.odata.test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import com.github.davidmoten.guavamini.Sets;
import com.github.davidmoten.odata.client.HttpMethod;
import com.github.davidmoten.odata.client.RequestHeader;

import test1.a.entity.Product;
import test1.b.container.Test1Service;

public class Test1ServiceTest {

    @Test
    public void testCanReferenceEntityFromEntityContainerInAnotherSchema() {
        Test1Service client = Test1Service.test().build();
        client.products(1);
    }

    @Test
    public void testChangedFieldsAreSet() {
        Product p = Product.builder().name("bingo").build();
        assertEquals(Sets.newHashSet("Name"), p.getChangedFields().toSet());
        p = p.withName("joey").withID(124);
        assertEquals(Sets.newHashSet("Name", "ID"), p.getChangedFields().toSet());
    }

    @Test
    public void testPost() {
        Test1Service client = Test1Service //
                .test() //
                .expectRequest("/Products", "/request-post.json", HttpMethod.POST,
                        RequestHeader.ACCEPT_JSON_METADATA_MINIMAL,
                        RequestHeader.CONTENT_TYPE_JSON_METADATA_MINIMAL,
                        RequestHeader.ODATA_VERSION) //
                .expectResponse("/Products", "/response-post.json", HttpMethod.POST,
                        RequestHeader.ACCEPT_JSON_METADATA_MINIMAL,
                        RequestHeader.CONTENT_TYPE_JSON_METADATA_MINIMAL,
                        RequestHeader.ODATA_VERSION) //
                .build();
        Product p = Product.builder().name("bingo").build();
        client.products().post(p);
    }

    @Test
    public void testDelete() {
        Test1Service client = Test1Service //
                .test() //
                .expectDelete("/Products/1", RequestHeader.ACCEPT_JSON_METADATA_MINIMAL,
                        RequestHeader.CONTENT_TYPE_JSON_METADATA_MINIMAL,
                        RequestHeader.ODATA_VERSION) //
                .build();
        client.products().id("1").delete();
    }

}
