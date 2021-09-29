package v5.gidd.entities.projection;

/**
 * Projection model for Leaderboard queries
 */
public interface LeaderboardUserModel {
    long getId();
    String getFirstName();
    String getSurName();
    int getPoints();
}