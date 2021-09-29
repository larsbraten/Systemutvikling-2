package v5.gidd.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import v5.gidd.entities.token.VerificationToken;
import v5.gidd.entities.token.StrictToken;

import java.util.Optional;

@Repository
public interface TokenRepo extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> getVerificationTokenByStrictToken(StrictToken strictToken);
}
