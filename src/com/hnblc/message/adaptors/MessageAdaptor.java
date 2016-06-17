package com.hnblc.message.adaptors;

import java.io.File;

import org.apache.log4j.Logger;

import com.hnblc.message.common.Controller;
import com.hnblc.message.utils.MessageUtils;
import com.hnblc.message.utils.Utils;

/**
 * 报文转换器： 将接收到的外围报文转换为可用的报文，分为三种 1. 协同系统 2. 海关 3. 检验检译
 *
 * @author rechel
 *
 */
public class MessageAdaptor {

    private static Logger log = Logger.getLogger(MessageAdaptor.class);
//	private static org.slf4j.Logger log=LoggerFactory.getLogger(MessageAdaptor.class);

    public String[] convertDeclareMessage(String xml, String target) {
        String type = null;
        try {
            type = MessageUtils.getMessageType(xml, "DATATYPE");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertDeclareMessage(xml, target, type);
    }

    public String[] convertDeclareMessage(String xml, String target, String type) {
        try {
            if (target == null || "".equals(target)) {
                log.info("Undefined Target Message Type ,eg [ ciq,coo,cus ], Checked it!");
                return new String[]{""};
            }
            if (type == null) {
                return new String[]{xml};
            }
            log.debug("Message Type: " + type);

            type = Utils.getMsgTypeConfigs(type);
            if (type == null) {
                log.info("Undefined Target Message type[ " + type + " ] .");
                return new String[]{xml};
            }
            type = type.concat("_").concat(target.toUpperCase());
//            log.info("Message Config File prefix: [ " + type + " ]");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Controller.getInstance().getRespXmlFromConvertsTasks(xml, type);
    }

//    public String convertDeclareMessage(File file, String target) {
//        String xml = Utils.readFromFile(file);
//        String type = null;
//        try {
//            type = MessageUtils.getMessageType(xml, "DATATYPE");
//            log.debug("Message Type: " + type);
//            if (target == null || "".equals(target)) {
//                log.info("Undefined Target Message Type ,eg [ ciq,coo,cus ], Checked it!");
//                return "";
//            }
//            if (type == null) {
//                return xml;
//            }
//
//            type = Utils.getMsgTypeConfigs(type);
//            if (type == null) {
//                log.info("Undefined Target Message type[ " + type + " ] .");
//                return xml;
//            }
//            type = type.concat("_").concat(target.toUpperCase());
////            log.info("Message Config File prefix: [ " + type + " ]");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        String[] msgs = Controller.getInstance().getRespXmlFromConvertsTasks(xml, type);
//        if (msgs != null) {
//            return msgs[0];
//        }
//        return xml;
//    }

}
