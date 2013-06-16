# play-twitter-cache
This is an example [Play 2.1.x][play] application of how to cache asynchronously loaded [Twitter api v1.1][twitter-api] results to prevent from hitting the [rate limit][twitter-limit].

There are examples of how to limit the tweets or remap the values into a new json object.

# Configuration
Add your Twitter applications Consumer Key / Secret and Access Token / Secret to the `application.conf` file.

    tw.consumer_key="YOUR_CONSUMER_KEY"
    tw.consumer_secret="YOUR_CONSUMER_SECRET"
    tw.access_token="YOUR_ACCESS_TOKEN"
    tw.access_secret="YOUR_ACCESS_SECRET"

Adjust the `Cache` time in seconds:

    Cache.set("tweets", tweets, 10) //caching is for 10 seconds in this case

## License
MIT: <http://jmparsons.mit-license.org> - [@jmparsons](http://twitter.com/jmparsons)

[play]: http://www.playframework.org/
[twitter-limit]: https://dev.twitter.com/docs/rate-limiting/1.1
[twitter-api]: https://dev.twitter.com/docs/api/1.1