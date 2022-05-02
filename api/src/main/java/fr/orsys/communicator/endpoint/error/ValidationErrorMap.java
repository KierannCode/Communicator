package fr.orsys.communicator.endpoint.error;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ValidationErrorMap extends HashMap<String, List<String>> {
    public void put(String field, String errorMessage) {
        List<String> fieldMessages = this.get(field);
        if (fieldMessages != null) {
            fieldMessages.add(errorMessage);
        } else {
            super.put(field, new ArrayList<>(List.of(errorMessage)));
        }
    }
}
