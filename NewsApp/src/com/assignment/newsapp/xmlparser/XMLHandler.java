package com.assignment.newsapp.xmlparser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.assignment.newsapp.model.NewsModel;

public class XMLHandler extends DefaultHandler {
	private ArrayList<NewsModel> listNews;
	private String tempVal;
	// to maintain context
	private NewsModel newsObj;
	private String newsType;

	public XMLHandler(String newsType) {
		this.newsType = newsType;
		listNews = new ArrayList<NewsModel>();
	}

	public ArrayList<NewsModel> getNews() {
		return listNews;
	}

	// Event Handlers
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// reset
		tempVal = "";
		if (qName.equalsIgnoreCase("item")) {
			// create a new instance of Laptop
			newsObj = new NewsModel();
			newsObj.setNewsType(newsType);
		} else if (qName.equalsIgnoreCase("media")) {

			if (newsObj != null) {
				Log.e("media", "media" + attributes.getValue("url"));
				newsObj.setImageUrl(attributes.getValue("url"));
			}
		}

		else if (qName.equalsIgnoreCase("media:thumbnail")) {

			if (newsObj != null) {
				Log.e("media:thumbnail",
						"media:thumbnail" + attributes.getValue("url"));
				newsObj.setImageUrl(attributes.getValue("url"));
			}
		}
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		tempVal = new String(ch, start, length);
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (newsObj != null) {
			if (qName.equalsIgnoreCase("item")) {
				// add it to the list
				listNews.add(newsObj);
			} else if (qName.equalsIgnoreCase("title")) {
				newsObj.setTitle(tempVal);
			} else if (qName.equalsIgnoreCase("description")) {
				newsObj.setDetail(tempVal);
			} else if (qName.equalsIgnoreCase("guid")) {
				newsObj.setGuId(tempVal);
			} else if (qName.equalsIgnoreCase("pubDate")) {
				newsObj.setDate(tempVal);
			} else if (qName.equalsIgnoreCase("media")) {

			}
		}
	}
}