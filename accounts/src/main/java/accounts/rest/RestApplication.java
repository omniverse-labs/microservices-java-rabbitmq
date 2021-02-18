package accounts.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import accounts.businesslogic.interfaces.AccountService;

@ApplicationPath("/")
public class RestApplication extends Application {

    private AccountService accountService;

    public RestApplication() {
        this.accountService = new AccountServiceFactory().getService();
    }

}
