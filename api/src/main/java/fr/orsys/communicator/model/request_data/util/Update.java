package fr.orsys.communicator.model.request_data.util;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

@UpdateData
@Getter
@Setter
public class Update<T> {
    @NotNull
    private Set<String> updatedFields;

    @JsonUnwrapped
    @Valid
    private T data;
}
