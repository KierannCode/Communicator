package fr.orsys.communicator.model.request_data.util;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;

@CreationData
@Getter
@Setter
public class Creation<T> {
    @JsonUnwrapped
    @Valid
    private T data;
}
