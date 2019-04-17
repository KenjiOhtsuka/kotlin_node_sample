package node

import org.w3c.dom.Element
import org.w3c.dom.NodeList
import org.w3c.dom.asList
import kotlin.browser.document

external fun require(moduleName: String): dynamic
external val __dirname: String

val client = require("cheerio-httpcli")
val http = require("http")
val fs = require("fs")
val URL = require("url")
val request = require("request")
val path = require("path")

const val LINK_LEVEL = 3
const val TARGET_URL = "http://nodejs.jp/nodejs.org_ja/docs/v0.10/api/"
var map = mutableMapOf<String, Boolean>()

//class App {
fun main(vararg args: String) {
    console.log(__dirname)
    //getpage()
    return
}

/**
 * Download web page
 *
 * ## Usage
 *
 * download(
 *     "http://www.aozora.gr.jp/index_pages/person82.html",
 *     "miyazawakenji.html"
 * ) { console.log("ok, kenji."); }
 * download(
 *     "http://www.aozora.gr.jp/index_pages/person148.html",
 *     "natumesoseki.html"
 * ) { console.log("ok, soseki."); }
 */
fun download(url: String, savePath: String, callback: () -> Unit) {
    val outFile = fs.createWriteStream(savePath)
    val req = http.get(url, fun(res: dynamic) {
        res.pipe(outFile)
        res.on("end", fun() {
            outFile.close()
            console.log("ok")
        })
    })
}

fun getpage() {
    var saveDir = "img"
    if (!fs.existsSync(saveDir)) {
        fs.mkdirSync(saveDir)
    }

    var url = "http://www.aozora.gr.jp/index_pages/person81.html"
    url = "https://stocks.finance.yahoo.co.jp/stocks/chart/?code=3323.T&ct=b"
    url = "https://ja.wikipedia.org/wiki/%E3%82%A4%E3%83%8C"

    var param = arrayOf<String>()
    client.fetch(url, param, fun(err: dynamic, doc: dynamic, res: dynamic) {
        if (err != null) { console.log("Error:", err); return; }

//        var aTags = doc("a")
//        for (i in 0..(aTags.length - 1)) {
//            var text = doc(aTags[i]).text() as String
//            var href = doc(aTags[i]).attr("href") as? String ?: continue
//            var href2 = URL.resolve(url, href)
//
//            console.log("$text : $href")
//            console.log("  => $href2")
//        }

        var imgTags = doc("img")
        for (i in 0..(imgTags.length as Int - 1)) {
            var src = doc(imgTags[i]).attr("src") as? String ?: continue
            src = URL.resolve(url, src) as String
            console.log(src)
            var fileName = URL.parse(src).pathname as String
            fileName = saveDir + "/" + fileName.replace(Regex("[^a-zA-Z0-9\\.]+"), "_")
            request(src).pipe(fs.createWriteStream(fileName))
        }

//        d.asList().forEachIndexed { id, it ->
//            var text = doc(it).text() as String
//            var href = doc(it).attr("href") as String
//            console.log("$text : $href")
//        }
//        var body = doc.html()
//        console.log(doc)

    })
}

fun downloadRec(url: String, level: Int) {
    if (level >= LINK_LEVEL) return
    if (map[url] == true) return
    map[url] = true

    var us = TARGET_URL.split("/")
    us.subList(0, us.lastIndex - 1)
    val base = us.joinToString("/")

    if (url.indexOf(base) < 0) return

    client.fetch(
        url,
        arrayOf<String>(),
        fun (err: dynamic, doc: dynamic, res: dynamic) {
            val aTags = doc("a")
            for (aTag in aTags) {
                var href =
                    doc(aTag).attr("href") as? String ?: continue
                href = URL.resolve(url, href) as String
                href = href.replace(Regex("#.+\$"), "")
                downloadRec(href, level + 1)
            }
        })
    if (url.substring(url.lastIndex) == "/")
        url += "index.html"
}