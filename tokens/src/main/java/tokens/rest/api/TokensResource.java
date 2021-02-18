package tokens.rest.api;

import java.net.URISyntaxException;

import javax.validation.ValidationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tokens.businesslogic.exceptions.QueueException;
import tokens.businesslogic.interfaces.TokenService;
import tokens.rest.TokenServiceFactory;
import tokens.rest.models.CreateTokensRequest;
import tokens.rest.models.ErrorModel;

@Path("/tokens")
public class TokensResource {

    private TokenService tokenService;

    public TokensResource() {
        this.tokenService = new TokenServiceFactory().getService();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAccount(CreateTokensRequest createTokensRequest) throws URISyntaxException {

        try {
            createTokensRequest.validate();

            var tokens = tokenService.createTokens(createTokensRequest.getCount());

            return Response.ok(tokens).build();

        } catch (ValidationException e) {

            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorModel(e.getMessage())).build();

        } catch (QueueException e) {

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorModel(e.getMessage()))
                    .build();
        }

    }

}
