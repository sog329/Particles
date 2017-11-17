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

  private static final String NONE = "";

  private static final String WIDTH_HEIGHT = "width_height";
  private static final String MAX = "max";

  private static final String MODEL = "model";
  private static final String CHANCE_RANGE = "chance_range";
  private static final String ACTIVE_TIME = "active_time";
  private static final String SRC_LTWH = "src_ltwh";
  private static final String LAYOUT_TYPE = "layout_type";
  private static final String DURATION = "duration";

  private static final String MOVE_FROM = "move_from";
  private static final String MOVE_TO = "move_to";
  private static final String MOVE_INTERPOLATOR = "move_interpolator";
  private static final String MATCH_PARENT = "match_parent";
  private static final String OFFSET = "offset:";

  private static final String ROTATE = "rotate";
  private static final String ROTATE_INTERPOLATOR = "rotate_interpolator";
  private static final String ALPHA = "alpha";
  private static final String ALPHA_INTERPOLATOR = "alpha_interpolator";
  private static final String SCALE = "scale";
  private static final String SCALE_INTERPOLATOR = "scale_interpolator";

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
      String[] ary = Tool.getAry(bd.toString());
      scene.scriptSize.width = Integer.parseInt(ary[0]);
      scene.scriptSize.height = Integer.parseInt(ary[1]);
    } else if (tag.equals(MAX)) {
      int max = Integer.parseInt(bd.toString());
      scene.setMaxParticle(max);
    } else if (tag.equals(DURATION)) {
      scene.duration = Integer.parseInt(bd.toString());
    } else if (tag.equals(LAYOUT_TYPE)) {
      scene.layoutType = bd.toString();
    } else if (tag.equals(CHANCE_RANGE)) {
      ParticleModel pm = scene.getLastParticleModel();
      String[] ary = Tool.getAry(bd.toString());
      pm.chanceRange.set(Float.parseFloat(ary[0]), Float.parseFloat(ary[1]));
    } else if (tag.equals(ACTIVE_TIME)) {
      ParticleModel pm = scene.getLastParticleModel();
      String[] ary = Tool.getAry(bd.toString());
      pm.activeTime.set(Integer.parseInt(ary[0]), Integer.parseInt(ary[1]));
    } else if (tag.equals(SRC_LTWH)) {
      ParticleModel pm = scene.getLastParticleModel();
      String[] ary = Tool.getAry(bd.toString());
      pm.rcBmp.left = Integer.parseInt(ary[0]);
      pm.rcBmp.top = Integer.parseInt(ary[1]);
      pm.size.width = Integer.parseInt(ary[2]);
      pm.rcBmp.right = pm.rcBmp.left + pm.size.width;
      pm.size.height = Integer.parseInt(ary[3]);
      pm.rcBmp.bottom = pm.rcBmp.top + pm.size.height;
    } else if (tag.equals(MOVE_FROM)) {
      ParticleModel pm = scene.getLastParticleModel();
      String[] ary = Tool.getAry(bd.toString());
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
      String[] ary = Tool.getAry(bd.toString());
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
    } else if (tag.equals(MOVE_INTERPOLATOR)) {
      ParticleModel pm = scene.getLastParticleModel();
      String[] ary = Tool.getAry(bd.toString());
      pm.interpolatorMove[0] = ary[0];
      pm.interpolatorMove[1] = ary[1];
    } else if (tag.equals(ROTATE)) {
      ParticleModel pm = scene.getLastParticleModel();
      String[] ary = Tool.getAry(bd.toString());
      pm.rotateBegin.set(Integer.parseInt(ary[0]), Integer.parseInt(ary[1]));
      if (ary.length == 6) {
        pm.rotateEnd.set(Integer.parseInt(ary[2]), Integer.parseInt(ary[3]));
        pm.ptRotate.x = Integer.parseInt(ary[4]);
        pm.ptRotate.y = Integer.parseInt(ary[5]);
      } else {
        pm.rotateEnd = null;
        pm.ptRotate.x = Integer.parseInt(ary[2]);
        pm.ptRotate.y = Integer.parseInt(ary[3]);
      }
    } else if (tag.equals(ROTATE_INTERPOLATOR)) {
      ParticleModel pm = scene.getLastParticleModel();
      pm.interpolatorRotate = bd.toString();
    } else if (tag.equals(ALPHA)) {
      ParticleModel pm = scene.getLastParticleModel();
      String[] ary = Tool.getAry(bd.toString());
      pm.alphaBegin.set(Integer.parseInt(ary[0]), Integer.parseInt(ary[1]));
      if (ary.length == 2) {
        pm.alphaEnd = null;
      } else {
        pm.alphaEnd.set(Integer.parseInt(ary[2]), Integer.parseInt(ary[3]));
      }
    } else if (tag.equals(ALPHA_INTERPOLATOR)) {
      ParticleModel pm = scene.getLastParticleModel();
      pm.interpolatorAlpha = bd.toString();
    } else if (tag.equals(SCALE)) {
      ParticleModel pm = scene.getLastParticleModel();
      String[] ary = Tool.getAry(bd.toString());
      pm.scaleBegin.set(Float.parseFloat(ary[0]), Float.parseFloat(ary[1]));
      if (ary.length == 4) {
        pm.scaleEnd.set(Float.parseFloat(ary[2]), Float.parseFloat(ary[3]));
      } else {
        pm.scaleEnd = null;
      }
    } else if (tag.equals(SCALE_INTERPOLATOR)) {
      ParticleModel pm = scene.getLastParticleModel();
      pm.interpolatorScale = bd.toString();
    }
  }
}
