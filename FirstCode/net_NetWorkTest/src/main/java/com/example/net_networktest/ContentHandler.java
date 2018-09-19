package com.example.net_networktest;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ContentHandler extends DefaultHandler {
    private static final String TAG = "ContentHandler";
    private StringBuilder id;
    private StringBuilder name;
    private StringBuilder version;
    private String nodeName;
    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        id = new StringBuilder();
        name = new StringBuilder();
        version = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        nodeName = localName;  // TODO 记录当前节点名
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        //TODO 根据当前节点名判断将内容加到哪个StringBuilder 中
        if("id".equals(nodeName)){
            id.append(ch,start,length);
        }else if("name".equals(nodeName)){
            name.append(ch,start,length);
        }else if("version".equals(nodeName)){
            version.append(ch,start,length);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if("app".equals(localName)){
            Log.d(TAG,"id is "+id);
            Log.d(TAG,"name is "+ name);
            Log.d(TAG,"version is "+ version);
            id.setLength(0);
            //TODO 最后将StringBuilder 清空，否则影响下一次节点获取
            name.setLength(0);
            version.setLength(0);
        }

    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }
}
