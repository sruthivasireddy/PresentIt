/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.presentit.app.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.users.User;

import javax.inject.Named;

/** An endpoint class we are exposing */
@Api(
  name = "myApi",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "backend.app.presentit.com",
    ownerName = "backend.app.presentit.com",
    packagePath=""
  )
)
public class MyEndpoint {

    /** A simple endpoint method that takes a name and says Hi back */
    @ApiMethod(name = "sayHi")
    public MyBean sayHi(@Named("name") String name) {
        MyBean response = new MyBean();
        response.setData("Hi, " + name);

        return response;
    }

    public static class HelloClass{
        private String message;


        public HelloClass(String message){
            this.message = message;
        }

        /**
         * @return the message
         */
        public String getMessage() {
            return message;
        }

        /**
         * @param message the message to set
         */
        public void setMessage(String message) {
            this.message = message;
        }
    }

    /**
     * This is just a hello method that tests if user authentication is woking
     * @param user
     * @return
     * @throws UnauthorizedException
     */
    @ApiMethod(
            name = "sayHello",
            path = "sayHello",
            httpMethod = ApiMethod.HttpMethod.GET
    )
    public HelloClass sayHello(final User user)
            throws UnauthorizedException {
        if (user == null) {
            throw new UnauthorizedException("Authorization required");
        }
        String ret;
        HelloClass h;
        ret = user.getEmail();
        h = new HelloClass(ret);
        return h;
    }
}
