var kotlin = require("kotlin"); 
(function (_, Kotlin) {
  'use strict';
  var throwCCE = Kotlin.throwCCE;
  var Regex_init = Kotlin.kotlin.text.Regex_init_61zpoe$;
  var client;
  var http;
  var fs;
  var URL;
  var request;
  var url;
  function main(args) {
    console.log(__dirname);
    return;
  }
  function download$lambda$lambda(closure$outFile) {
    return function () {
      closure$outFile.close();
      console.log('ok');
    };
  }
  function download$lambda(closure$outFile) {
    return function (res) {
      res.pipe(closure$outFile);
      res.on('end', download$lambda$lambda(closure$outFile));
    };
  }
  function download(url, savePath, callback) {
    var outFile = fs.createWriteStream(savePath);
    var req = http.get(url, download$lambda(outFile));
  }
  function getpage$lambda(closure$url, closure$saveDir) {
    return function (err, doc, res) {
      var tmp$, tmp$_0, tmp$_1, tmp$_2, tmp$_3, tmp$_4;
      if (err != null) {
        console.log('Error:', err);
        return;
      }
      var imgTags = doc('img');
      tmp$_0 = (typeof (tmp$ = imgTags.length) === 'number' ? tmp$ : throwCCE()) - 1 | 0;
      for (var i = 0; i <= tmp$_0; i++) {
        tmp$_2 = typeof (tmp$_1 = doc(imgTags[i]).attr('src')) === 'string' ? tmp$_1 : null;
        if (tmp$_2 == null) {
          continue;
        }
        var src = tmp$_2;
        src = typeof (tmp$_3 = URL.resolve(closure$url.v, src)) === 'string' ? tmp$_3 : throwCCE();
        console.log(src);
        var fileName = typeof (tmp$_4 = URL.parse(src).pathname) === 'string' ? tmp$_4 : throwCCE();
        var tmp$_5 = closure$saveDir.v + '/';
        var $receiver = fileName;
        fileName = tmp$_5 + Regex_init('[^a-zA-Z0-9\\.]+').replace_x2uqeu$($receiver, '_');
        request(src).pipe(fs.createWriteStream(fileName));
      }
    };
  }
  function getpage() {
    var saveDir = {v: 'img'};
    if (!fs.existsSync(saveDir.v)) {
      fs.mkdirSync(saveDir.v);
    }
    var url = {v: 'http://www.aozora.gr.jp/index_pages/person81.html'};
    url.v = 'https://stocks.finance.yahoo.co.jp/stocks/chart/?code=3323.T&ct=b';
    url.v = 'https://ja.wikipedia.org/wiki/%E3%82%A4%E3%83%8C';
    var param = [];
    client.fetch(url.v, param, getpage$lambda(url, saveDir));
  }
  var package$node = _.node || (_.node = {});
  Object.defineProperty(package$node, 'client', {
    get: function () {
      return client;
    }
  });
  Object.defineProperty(package$node, 'http', {
    get: function () {
      return http;
    }
  });
  Object.defineProperty(package$node, 'fs', {
    get: function () {
      return fs;
    }
  });
  Object.defineProperty(package$node, 'URL', {
    get: function () {
      return URL;
    }
  });
  Object.defineProperty(package$node, 'request', {
    get: function () {
      return request;
    }
  });
  Object.defineProperty(package$node, 'url', {
    get: function () {
      return url;
    }
  });
  package$node.main_vqirvp$ = main;
  package$node.download_hgzy0z$ = download;
  package$node.getpage = getpage;
  client = require('cheerio-httpcli');
  http = require('http');
  fs = require('fs');
  URL = require('url');
  request = require('request');
  url = 'http://www.aozora.gr.jp/index_pages/person81.html';
  main([]);
  Kotlin.defineModule('node', _);
  return _;
}(module.exports, require('kotlin')));

//# sourceMappingURL=node.js.map
