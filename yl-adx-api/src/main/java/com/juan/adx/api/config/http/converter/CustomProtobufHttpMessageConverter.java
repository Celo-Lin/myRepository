package com.juan.adx.api.config.http.converter;
import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;

import com.google.protobuf.Message;

public class CustomProtobufHttpMessageConverter extends ProtobufHttpMessageConverter {

    protected void writeInternal(Message message, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        HttpHeaders headers = outputMessage.getHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        super.writeInternal(message, outputMessage);
    }
}
