package v5.gidd.usecase;

import org.springframework.mail.javamail.JavaMailSender;
import v5.gidd.entities.message.Status;
import v5.gidd.entities.token.VerificationToken;
import v5.gidd.entities.token.StrictToken;
import v5.gidd.entities.user.User;
import v5.gidd.repo.TokenRepo;
import v5.gidd.throwable.GiddException;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import java.time.Duration;

public class TokenInteractor {

    /**
     * Attempts to generate unique verification token a maximum of 3 times. Failing to generate unique
     * even 1 time should be extremely unlikely.
     * @param tokenRepo Token-repo to check if already exist or not.
     * @param user User to send email to.
     * @param expiresIn Duration of validity.
     * @return The verification token.
     * @throws GiddException if token could not be generated after 3 times.
     */

    public static VerificationToken generateToken(TokenRepo tokenRepo, User user, Duration expiresIn){
        for(int i = 0; i < 3; i++){
            var token = new VerificationToken(user, expiresIn);
            if(tokenRepo.getVerificationTokenByStrictToken(token.getStrictToken()).isEmpty()) {
                return token;
            }
        }
        throw new GiddException(Status.COULD_NOT_GENERATE_TOKEN, "Server could not generate unique token.");
    }

    /**
     * Sends verification email to user.
     * @param javaMailSender Object necessary to send email.
     * @param verificationToken Token to embed in email.
     * @throws GiddException if email could not be sended.
     */

    public static void sendVerificationEmail(JavaMailSender javaMailSender, VerificationToken verificationToken){
        try{
            javaMailSender.send(mime -> {
                mime.setRecipient(Message.RecipientType.TO, new InternetAddress(verificationToken.getUser().getUsername()));
                mime.setSubject("Please verify your account");
                mime.setText("Please verify your registration here: http://localhost:3000/verify/" + verificationToken.getStrictToken().getKey());
            });
        } catch (Exception e){
            throw new GiddException(Status.REGISTRATION_ERROR, "Could not send verification email.");
        }
    }

    /**
     * Saves token in database.
     * @param tokenRepo Token-repo to persist token in.
     * @param verificationToken The object to persist.
     */

    public static void saveToken(TokenRepo tokenRepo, VerificationToken verificationToken){
        tokenRepo.save(verificationToken);
    }

    /**
     * Verifies user after url has been clicked.
     * @param tokenRepo Token-repo with copy of supplied token.
     * @param strictToken Token provided by client-agent.
     * @throws GiddException if token does not exist.
     * @throws GiddException if token is expired.
     */

    public static void verifyUser(TokenRepo tokenRepo, StrictToken strictToken){

        var secureToken = tokenRepo
                .getVerificationTokenByStrictToken(strictToken)

                .orElseThrow(() -> new GiddException(Status.NON_EXISTENT_TOKEN, "Token does not exist!"));

        if(secureToken.isExpired()){
            tokenRepo.delete(secureToken);
            throw new GiddException(Status.VERIFICATION_TOKEN_IS_EXPIRED, "Token is expired.");
        }

        secureToken.getUser().setVerified(true);
        tokenRepo.delete(secureToken);
    }
}
