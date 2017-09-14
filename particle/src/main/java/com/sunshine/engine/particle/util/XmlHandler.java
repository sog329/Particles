package com.sunshine.engine.particle.util;

import com.sunshine.engine.particle.logic.ParticleModel;
import com.sunshine.engine.particle.logic.Scene;
import com.sunshine.engine.particle.model.Area;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class XmlHandler extends DefaultHandler {
  private Scene scene = null;
  private StringBuilder bd = null;

  public static final String NONE = "";

  public static final String WIDTH_HEIGHT = "width_height";
  public static final String MAX = "max";

  public static final String MODEL = "model";
  public static final String CHANCE_RANGE = "chance_range";
  public static final String ACTIVE_TIME = "active_time";
  public static final String SRC_LTWH = "src_ltwh";

  public static final String MOVE_FROM = "move_from";
  public static final String MOVE_TO = "move_to";
  public static final String MATCH_PARENT = "match_parent";
  public static final String OFFSET = "offset:";

  public static final String ROTATE = "rotate";
  public static final String ALPHA = "alpha";
  public static final String SCALE = "scale";

  public static boolean parse(InputStream is, Scene st) {
    boolean success = false;
    try {
      SAXParserFactory sf = SAXParserFactory.newInstance();
      SAXParser sp = sf.newSAXParser();
      XmlHandler hd = new XmlHandler(st);
      sp.parse(is, hd);
      success = true;
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    } catch (SAXException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return success;
  }

  private XmlHandler(Scene sc) {
    scene = sc;
  }

  @Override
  public void startDocument() throws SAXException {
    super.startDocument();
    bd = new StringBuilder();
    bd.setLength(0);
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes atr)
      throws SAXException {
    super.startElement(uri, localName, qName, atr);
    String tag = localName.length() != 0 ? localName : qName;
    if (tag.equals(MODEL)) {
      scene.addParticleModel(new ParticleModel());
    }
    bd.setLength(0);
  }

  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
    super.characters(ch, start, length);
    bd.append(ch, start, length);
  }

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    super.endElement(uri, localName, qName);
    String tag = localName.length() != 0 ? localName : qName;
    if (tag.equals(WIDTH_HEIGHT)) {
      String[] ary = ParticleTool.getAry(bd.toString());
      scene.scriptSize.width = Integer.parseInt(ary[0]);
      scene.scriptSize.height = Integer.parseInt(ary[1]);
    } else if (tag.equals(MAX)) {
      int max = Integer.parseInt(bd.toString());
      scene.setMaxParticle(max);
    } else if (tag.equals(CHANCE_RANGE)) {
      ParticleModel pm = scene.getLastParticleModel();
      String[] ary = ParticleTool.getAry(bd.toString());
      pm.chanceRange.set(Float.parseFloat(ary[0]), Float.parseFloat(ary[1]));
    } else if (tag.equals(ACTIVE_TIME)) {
      ParticleModel pm = scene.getLastParticleModel();
      String[] ary = ParticleTool.getAry(bd.toString());
      pm.activeTime.set(Integer.parseInt(ary[0]), Integer.parseInt(ary[1]));
    } else if (tag.equals(SRC_LTWH)) {
      ParticleModel pm = scene.getLastParticleModel();
      String[] ary = ParticleTool.getAry(bd.toString());
      pm.rcBmp.left = Integer.parseInt(ary[0]);
      pm.rcBmp.top = Integer.parseInt(ary[1]);
      pm.size.width = Integer.parseInt(ary[2]);
      pm.rcBmp.right = pm.rcBmp.left + pm.size.width;
      pm.size.height = Integer.parseInt(ary[3]);
      pm.rcBmp.bottom = pm.rcBmp.top + pm.size.height;
      pm.ptCenter.x = pm.size.width / 2;
      pm.ptCenter.y = pm.size.height / 2;
    } else if (tag.equals(MOVE_FROM)) {
      ParticleModel pm = scene.getLastParticleModel();
      String[] ary = ParticleTool.getAry(bd.toString());
      pm.areaFrom.l = Integer.parseInt(ary[0]);
      pm.areaFrom.t = Integer.parseInt(ary[1]);
      if (MATCH_PARENT.equals(ary[2])) {
        pm.areaFrom.w = Area.MATCH_PARENT;
      } else {
        pm.areaFrom.w = Integer.parseInt(ary[2]);
      }
      pm.areaFrom.h = Integer.parseInt(ary[3]);
    } else if (tag.equals(MOVE_TO)) {
      ParticleModel pm = scene.getLastParticleModel();
      String[] ary = ParticleTool.getAry(bd.toString());
      if (ary[0].contains(OFFSET)) {
        pm.areaTo.isOffsetLeft = true;
        ary[0] = ary[0].replace(OFFSET, NONE);
        pm.areaTo.l = Integer.parseInt(ary[0]);
      } else {
        pm.areaTo.isOffsetLeft = false;
        pm.areaTo.l = Integer.parseInt(ary[0]);
      }
      if (ary[1].contains(OFFSET)) {
        pm.areaTo.isOffsetTop = true;
        ary[1] = ary[1].replace(OFFSET, NONE);
        pm.areaTo.t = Integer.parseInt(ary[1]);
      } else {
        pm.areaTo.isOffsetTop = false;
        pm.areaTo.t = Integer.parseInt(ary[1]);
      }
      if (MATCH_PARENT.equals(ary[2])) {
        pm.areaTo.w = Area.MATCH_PARENT;
      } else {
        pm.areaTo.w = Integer.parseInt(ary[2]);
      }
      pm.areaTo.h = Integer.parseInt(ary[3]);
    } else if (tag.equals(ROTATE)) {
      ParticleModel pm = scene.getLastParticleModel();
      String[] ary = ParticleTool.getAry(bd.toString());
      pm.rotateBegin.set(Integer.parseInt(ary[0]), Integer.parseInt(ary[1]));
      if (ary[2].contains(OFFSET)) {
        pm.rotateOffset = true;
        ary[2] = ary[2].replace(OFFSET, NONE);
      } else {
        pm.rotateOffset = false;
      }
      if (ary[3].contains(OFFSET)) {
        pm.rotateOffset = true;
        ary[3] = ary[3].replace(OFFSET, NONE);
      } else {
        pm.rotateOffset = false;
      }
      pm.rotateEnd.set(Integer.parseInt(ary[2]), Integer.parseInt(ary[3]));
      pm.ptRotate.x = Integer.parseInt(ary[4]);
      pm.ptRotate.y = Integer.parseInt(ary[5]);
    } else if (tag.equals(ALPHA)) {
      ParticleModel pm = scene.getLastParticleModel();
      String[] ary = ParticleTool.getAry(bd.toString());
      pm.alpha.set(Integer.parseInt(ary[0]), Integer.parseInt(ary[1]));
      pm.alphaDuration.set(Float.parseFloat(ary[2]), Float.parseFloat(ary[3]));
    } else if (tag.equals(SCALE)) {
      ParticleModel pm = scene.getLastParticleModel();
      String[] ary = ParticleTool.getAry(bd.toString());
      pm.scaleBegin.set(Float.parseFloat(ary[0]), Float.parseFloat(ary[1]));
      pm.scaleEnd.set(Float.parseFloat(ary[2]), Float.parseFloat(ary[3]));
    }
  }
}
