package com.gumlayudong.gumlayudongbackend;

import com.gumlayudong.gumlayudongbackend.post.service.PostService;
import com.gumlayudong.gumlayudongbackend.user.application.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.function.Consumer;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

@AutoConfigureRestDocs
@WebFluxTest
public class ControllerMockTest {
    @Autowired
    protected WebTestClient webTestClient;

    @MockBean
    protected JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @MockBean
    protected PostService postService;

    @MockBean
    protected UserService userService;

    protected static OperationRequestPreprocessor getDocumentRequest() {
        return preprocessRequest(
                modifyUris()
                        .scheme("http")
                        .host("localhost")
                        .removePort(),
                prettyPrint());
    }

    protected static OperationResponsePreprocessor getDocumentResponse() {
        return preprocessResponse(prettyPrint());
    }

    protected Consumer<EntityExchangeResult<byte[]>> toDocument(String title) {
        return document(title, getDocumentRequest(), getDocumentResponse());
    }
}
