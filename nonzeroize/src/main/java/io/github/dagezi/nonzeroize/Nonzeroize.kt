package io.github.dagezi.nonzeroize

import org.slf4j.Logger
import org.w3c.dom.Node
import java.io.File
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

private const val androidNsUri = "http://schemas.android.com/apk/res/android"

class Nonzeroize(val logger: Logger) {
    val pathCreator = PathCreator()
    val pathFormatter = PathFormatter()
    val documentBuilder: DocumentBuilder
    val transformer: Transformer

    init {
        val documentBuilderFactory = DocumentBuilderFactory.newInstance()
        documentBuilderFactory.isNamespaceAware = true
        documentBuilder = documentBuilderFactory.newDocumentBuilder()

        transformer = TransformerFactory.newInstance().newTransformer()
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
    }

    fun handle(src: File, dest: File) {
        val drawable = documentBuilder.parse(src)
        val pathElements = drawable.getElementsByTagName("path")
        var modified = false

        for (i in 0 until pathElements.length) {
            val pathElement = pathElements.item(i)
            val attribute: Node? = pathElement.attributes.getNamedItemNS(androidNsUri, "pathData")
            val pathData: String? = attribute?.nodeValue
            if (pathData != null) {
                val adjuster = PathDirectionAdjuster(pathCreator.parse(pathData))
                logger.debug(adjuster.dump(StringBuilder()).toString())
                if (!adjuster.isFollowingWinding()) {
                    attribute.nodeValue = pathFormatter.format(adjuster.adjust())
                    modified = true
                }
            }
        }

        if (modified) {
            logger.info("$src is not following winding rule, adjusted")
            transformer.transform(DOMSource(drawable), StreamResult(dest))
        } else {
            logger.info("$src is following winding rule.")
        }
    }
}
