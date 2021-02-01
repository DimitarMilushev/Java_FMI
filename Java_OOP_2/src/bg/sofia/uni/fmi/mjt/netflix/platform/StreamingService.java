package bg.sofia.uni.fmi.mjt.netflix.platform;

import bg.sofia.uni.fmi.mjt.netflix.account.Account;
import bg.sofia.uni.fmi.mjt.netflix.content.Streamable;
import bg.sofia.uni.fmi.mjt.netflix.content.enums.PgRating;
import bg.sofia.uni.fmi.mjt.netflix.exceptions.ContentUnavailableException;

public interface StreamingService {

    /**
     *     * @throws ContentUnavailableException if the content is age restricted and the user is not yet permitted to access it.
     *     * @throws UserNotFoundException if the user is not registered in the platform.
     *     * @throws ContentNotFoundException if the content is not present in the platform
     */

    void watch(Account user, String videoContentName) throws ContentUnavailableException;

    /**
     * @param videoContentName the exact name of the video content: movie or series
     * @return the Streamable resource with name that matches the provided name or null if no such content exists in the platform.
     */
    Streamable findByName(String videoContentName);

    /**
     * @return the most watched Streamable resource available in the platform or null if no streams were done yet.
     */
    Streamable mostViewed();

    /**
     * @return the minutes spent by all users registered in the platform while watching streamable content.
     */
    int totalWatchedTimeByUsers();

    Boolean isAgeAdvisable(int age, PgRating rating);

    Boolean isAccountValid(Account acc);
}
