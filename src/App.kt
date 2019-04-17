package node

import org.w3c.dom.Element
import org.w3c.dom.NodeList
import org.w3c.dom.asList
import kotlin.browser.document

external fun require(moduleName: String): dynamic

val client = require("cheerio-httpcli")
val http = require("http")
val fs = require("fs")


//class App {
fun main(vararg args: String) {
    getpage()
    return
//    download(
//        "http://www.aozora.gr.jp/index_pages/person82.html",
//        "miyazawakenji.html"
//    ) { console.log("ok, kenji."); }
//    download(
//        "http://www.aozora.gr.jp/index_pages/person148.html",
//        "natumesoseki.html"
//    ) { console.log("ok, soseki."); }
}

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
//}
fun getpage() {
    var url = "http://www.aozora.gr.jp/index_pages/person81.html"
    var param = arrayOf<String>()
    client.fetch(url, param, fun(err: dynamic, doc: dynamic, res: dynamic) {
        if (err != null) { console.log("Error:", err); return; }
        var d = doc("a") as NodeList
        d.asList().forEachIndexed { id, it ->
            var text = doc(it).text() as String
            var href = doc(it).attr("href") as String
            console.log("$text : $href")
        }
//        var body = doc.html()
//        console.log(doc)
    })
}
