package bg.sofia.uni.fmi.mjt.netflix.platform;

import bg.sofia.uni.fmi.mjt.netflix.account.Account;
import bg.sofia.uni.fmi.mjt.netflix.content.Streamable;
import bg.sofia.uni.fmi.mjt.netflix.content.enums.PgRating;
import bg.sofia.uni.fmi.mjt.netflix.exceptions.ContentNotFoundException;
import bg.sofia.uni.fmi.mjt.netflix.exceptions.ContentUnavailableException;
import bg.sofia.uni.fmi.mjt.netflix.exceptions.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.stream.Stream;

public class Netflix implements StreamingService {

    private Account[] accounts;
    private Streamable[] streamables;

    public Netflix(Account[] accounts, Streamable[] streamableContent)
    {
        this.accounts = accounts;
        this.streamables = streamableContent;
    }

    @Override
    public void watch(Account user, String videoContentName) throws ContentUnavailableException {

        if(!isAccountValid(user))
            throw new UserNotFoundException(String.format("Current user '%s' is not found.", user.getUsername()));

        Streamable content = findByName(videoContentName);
        PgRating rating = content.getRating();
        int userAge = LocalDateTime.now().getYear() - user.getBirhdayDate().getYear();

        if(!isAgeAdvisable(userAge, rating))
            throw new ContentUnavailableException(String.format("Content with rating '%s' is restricted.", rating.name()));

        content.setViews(content.getViews() + 1);
    }

    @Override
    public Boolean isAccountValid(Account acc)
    {
        for(Account a : this.accounts)
        {
            if(a.equals(acc))
                return true;
        }

        return false;
    }

    @Override
    public Streamable findByName(String videoContentName) throws ContentNotFoundException {
        for(Streamable content : streamables)
        {
            if(content.getTitle().equals(videoContentName))
            {
                return content;
            }
        }

        throw new ContentNotFoundException(String.format("No content with name '%s' found.", videoContentName));
    }

    @Override
    public Streamable mostViewed() {

        Streamable content = null;
        int mostWatched = 0;
        for (Streamable s : streamables)
        {
            if(s.getViews() > mostWatched)
            {
                mostWatched = s.getViews();
                content = s;
            }
        }

        return content;
    }

    @Override
    public int totalWatchedTimeByUsers() {
        int totalWatchTime = 0;

        for(Streamable s : streamables) {
            totalWatchTime += (s.getDuration() * s.getViews());
        }
        return totalWatchTime;
    }

    @Override
    public Boolean isAgeAdvisable(int age, PgRating rating) {
        if(rating.equals(PgRating.G))
            return true;

        if(rating.equals(PgRating.PG13) && age >= 13)
            return true;

        if(rating.equals(PgRating.NC17) && age > 17)
            return true;

        return false;
    }
}
