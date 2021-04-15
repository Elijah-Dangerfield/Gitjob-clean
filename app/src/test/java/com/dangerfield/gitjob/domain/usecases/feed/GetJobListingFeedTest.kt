package com.dangerfield.gitjob.domain.usecases.feed

import junit.framework.TestCase

/*
Test cases:

- test that when current feed is expired, we get a new one else old one
- test that when current feed is empty, we get a new one else old one
- test that when we havent fetched a feed in this session, we fetch else old one
- test that when theres already feed in the cache, we show cache first while loading the network, and then show the network
- test that when cache throws error, we fetch from network
- test that when network throws error we show what was in cache

 */
class GetJobListingFeedTest {



}