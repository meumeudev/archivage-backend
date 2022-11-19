package sn.webg.archivage.service.services;

import sn.webg.archivage.service.models.request.AuthUsername;
import sn.webg.archivage.service.models.response.SignInAuthentication;

public interface AuthenticationService {

    SignInAuthentication authenticate(AuthUsername authUsername);

}
