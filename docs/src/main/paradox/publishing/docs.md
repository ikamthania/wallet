# Publishing Docs


This project uses two sbt plugins [paradox](https://developer.lightbend.com/docs/paradox/latest/index.html) and [sbt-ghpages](https://github.com/sbt/sbt-ghpages)

The source for the documentation is [here](https://github.com/LivelyGig/wallet/tree/master/docs/src/main/paradox)

After you are done in making changes here are the commands that you need to run in application root

```
sbt // start sbt

[WebGateway] $ project doc // select doc project

sbt:doc> paradox // generate the site in target/paradox/site/main

sbt:doc> ghpagesPushSite // push site to gh-pages
```

Note: Make sure you have write access on the `gh-pages` branch. [Connecting with ssh](https://help.github.com/articles/connecting-to-github-with-ssh/) is recommended.