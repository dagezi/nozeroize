package io.github.dagezi.vbw

import org.w3c.dom.Node
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

class BeWinding {
    val pathCreator = PathCreator()
    val pathFormatter = PathFormatter()

    fun handle(src: File, dest: File) {
        val drawable = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(src)
        val pathElements = drawable.getElementsByTagName("path")
        var modified = false

        for (i in 0 until pathElements.length) {
            val pathElement = pathElements.item(i)
            val attribute: Node? = pathElement.attributes.getNamedItem("pathData")
            val pathData: String? = attribute?.nodeValue
            if (pathData != null) {
                val path = pathCreator.parse(pathData)
                val newPath = PathDirectionAdjuster(path).adjust()
                if (newPath != path) {
                    attribute.nodeValue = pathFormatter.format(path)
                    modified = true
                }
            }
        }

        if (modified) {
            val transformer: Transformer = TransformerFactory.newInstance().newTransformer()
            transformer.transform(DOMSource(drawable), StreamResult(dest))
        }
    }
}

fun main(args: Array<String>) {
    val beWinding = BeWinding()
    beWinding.handle(File(args[0]), File(args[1]))
}