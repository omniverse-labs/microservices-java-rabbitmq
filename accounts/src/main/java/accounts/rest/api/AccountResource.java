package accounts.rest.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import accounts.businesslogic.interfaces.AccountService;
import accounts.businesslogic.services.SimpleAccountService;
import accounts.rest.AccountServiceFactory;

@Path("accounts/{id}")
public class AccountResource {

    private AccountService accountService;

    public AccountResource() {
        this.accountService = new AccountServiceFactory().getService();
    }

    @GET
    public Response getAccount(@PathParam("id") String id) {
        var account = this.accountService.getAccount(id);

        if (account == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(account).build();
    }
}
