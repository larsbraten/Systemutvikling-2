package v5.gidd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import v5.gidd.entities.token.StrictToken;
import v5.gidd.entities.user.User;
import v5.gidd.repo.TokenRepo;
import v5.gidd.usecase.TokenInteractor;

import java.time.Duration;

@org.springframework.stereotype.Service
public class TokenService extends Service<TokenRepo> {

    @Autowired
    private TokenRepo tokenRepo;

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * Handles user verification process during user registration. Will send email to provided username.
     * @param user The user to generate token for.
     */

    public void sendVerificationEmail(User user){
        var token = TokenInteractor.generateToken(tokenRepo, user, Duration.ofHours(24));
        TokenInteractor.sendVerificationEmail(javaMailSender, token);
        TokenInteractor.saveToken(tokenRepo, token);
    }

    /**
     * Verifies username which have clicked provided email link.
     * @param strictToken Token embedded in email url.
     */

    public void verifyUser(StrictToken strictToken){
        TokenInteractor.verifyUser(tokenRepo, strictToken);
    }


    @Override
    protected TokenRepo getRepo() {
        return tokenRepo;
    }
}
