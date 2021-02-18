package tokens.rest.models;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.validation.ValidationException;

public class CreateTokensRequest {

    private Integer count;

    public CreateTokensRequest() {

    }

    @JsonbCreator
    public CreateTokensRequest(@JsonbProperty("count") int count) {
        this.count = count;
    }

    public void validate() throws ValidationException {
        if (this.count == null) {
            throw new ValidationException("count is required");
        }
    }

    public Integer getCount() {
        return this.count;
    }
}
