const CACHE_NAME = 'noorpath-v1';
const urlsToCache = [
  '/noorpath/',
  '/noorpath/index.html',
  '/noorpath/prayer.html',
  '/noorpath/quran.html',
  '/noorpath/hadith.html',
  '/noorpath/dua.html',
  '/noorpath/tasbih.html',
  '/noorpath/names.html',
  '/noorpath/zakat.html',
  '/noorpath/ramadan.html',
  '/noorpath/manifest.json'
];

// ইন্সটল হওয়ার সময় ফাইলগুলো ক্যাশ করা
self.addEventListener('install', event => {
  event.waitUntil(
    caches.open(CACHE_NAME)
      .then(cache => {
        console.log('Opened cache');
        return cache.addAll(urlsToCache);
      })
  );
  // নতুন সার্ভিস ওয়ার্কার সাথে সাথে অ্যাক্টিভ করা
  self.skipWaiting();
});

// ক্যাশ থেকে ফাইল দেখানো (অফলাইনে কাজ করার জন্য)
self.addEventListener('fetch', event => {
  event.respondWith(
    caches.match(event.request)
      .then(response => {
        if (response) {
          return response; // ক্যাশে থাকলে ক্যাশ থেকে দাও
        }
        return fetch(event.request); // না থাকলে ইন্টারনেট থেকে আনো
      })
  );
});

// পুরনো ক্যাশ মুছে ফেলা (নতুন আপডেট আসার সময়)
self.addEventListener('activate', event => {
  const cacheWhitelist = [CACHE_NAME];
  event.waitUntil(
    caches.keys().then(cacheNames => {
      return Promise.all(
        cacheNames.map(cacheName => {
          if (cacheWhitelist.indexOf(cacheName) === -1) {
            return caches.delete(cacheName);
          }
        })
      );
    })
  );
  self.clients.claim();
});