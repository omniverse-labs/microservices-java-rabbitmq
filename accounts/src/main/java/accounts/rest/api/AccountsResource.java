package accounts.rest.api;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.ValidationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import accounts.businesslogic.exceptions.QueueException;
import accounts.businesslogic.interfaces.AccountService;
import accounts.businesslogic.models.Account;
import accounts.businesslogic.models.AccountType;
import accounts.rest.AccountServiceFactory;
import accounts.rest.models.CreateAccountRequest;
import accounts.rest.models.ErrorModel;

@Path("/accounts")
public class AccountsResource {

    private AccountService accountService;

    public AccountsResource() {
        this.accountService = new AccountServiceFactory().getService();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAccount(CreateAccountRequest createAccountRequest) throws URISyntaxException {

        try {
            createAccountRequest.validate();

            var account = new Account(createAccountRequest.getFirstName(), createAccountRequest.getLastName(),
                    createAccountRequest.getAccountType().equals("customer") ? AccountType.Customer
                            : AccountType.Merchant);

            var createdAccount = accountService.createAccount(account);

            return Response.created(new URI(String.format("accounts/%s", createdAccount.getId()))).build();

        } catch (ValidationException e) {

            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorModel(e.getMessage())).build();

        } catch (QueueException e) {

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorModel(e.getMessage()))
                    .build();
        }

    }

}
