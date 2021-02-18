package tokens.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import tokens.businesslogic.interfaces.TokenService;

@ApplicationPath("/")
public class RestApplication extends Application {

    private TokenService tokenService;

    public RestApplication() {
        this.tokenService = new TokenServiceFactory().getService();
    }

}
