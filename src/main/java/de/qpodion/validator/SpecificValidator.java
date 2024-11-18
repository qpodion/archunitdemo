package de.qpodion.validator;

public class SpecificValidator implements GenericValidator {

    @Override
    public void validate(Object value) {
        if (value == null) {
            throw new IllegalArgumentException();
        }
    }
}
