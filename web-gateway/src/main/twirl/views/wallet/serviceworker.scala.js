
var cacheName = 'v1:static';

@defining(s"wallet-client") { name =>
@Seq(s"$name-opt-library.js", s"$name-fastopt-library.js",s"$name-opt.js", s"$name-fastopt.js").filter(asset => getClass.getResource(s"/public/$asset") != null).map(name => s"${routes.Assets.versioned(name).toString}")
}

self.addEventListener('install', function (e) {
    // once the SW is installed, go ahead and fetch the resources to make this work offline
    e.waitUntil(
        caches.open(cacheName).then(function (cache) {
            return cache.addAll([
                './manifest.json',
                "https://fonts.googleapis.com/css?family=Montserrat",
                './serviceworker.js',
                '@routes.Assets.versioned("stylesheets/wallet/wallet-app-main.min.css")',
                "@routes.Assets.versioned("stylesheets/wallet/themes/wallet-main-theme-default.min.css")",
                "@routes.Assets.versioned("stylesheets/wallet/themes/wallet-main-theme-light.min.css")",
                "@routes.Assets.versioned("images/favicon.ico")"
            ]).then(function () {
                self.skipWaiting();
            });
        })
    );
});

// When the browser fetches a url
self.addEventListener('fetch', function (event) {
    // Either respond with the cached object or go ahead and fetch the actual url
    event.respondWith(
        caches.match(event.request).then(function (response) {
            if (response) {
                return response;
            }
            return fetch(event.request);
        })
    );
});