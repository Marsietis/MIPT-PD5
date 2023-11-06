package com.example.mipt_pd5

import android.util.Log
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory

const val NODE = "Cube"

class Parser {

    fun parseXml(inputStream: InputStream): ArrayList<HashMap<String, String>> {
        Log.d("Parser", "parseXml")

        val currencyRatesList = ArrayList<HashMap<String, String>>()

        try {
            val builderFactory = DocumentBuilderFactory.newInstance()
            val docBuilder = builderFactory.newDocumentBuilder()
            val doc: Document = docBuilder.parse(inputStream)
            val nodeList = doc.getElementsByTagName(NODE)

            for (i in 0 until nodeList.length) {
                if (nodeList.item(i).nodeType == Node.ELEMENT_NODE) {
                    val currencyRate = HashMap<String, String>()
                    val element = nodeList.item(i) as Element

                    if (element.hasAttribute("currency") && element.hasAttribute("rate")) {
                        val currency = element.getAttribute("currency")
                        val rate = element.getAttribute("rate")
                        currencyRate["currency"] = currency
                        currencyRate["rate"] = rate
                        currencyRatesList.add(currencyRate)

                        Log.d("Parser", "Currency: $currency, Rate: $rate")
                    }
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return currencyRatesList

    }
}
