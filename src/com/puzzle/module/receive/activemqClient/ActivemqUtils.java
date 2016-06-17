package com.puzzle.module.receive.activemqClient;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.logging.Level;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;


import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQBytesMessage;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.puzzle.util.ConfigUtils;
import com.puzzle.util.FileDto;

public class ActivemqUtils {

    private static Logger log = Logger.getLogger(ActivemqUtils.class);
    private static final Boolean NON_TRANSACTED = false;
    private static final Boolean TRANSACTED = true;
    private static final int NUM_MESSAGES_TO_SEND = 100;
    private static final long DELAY = 100;
    private static final long TIMEOUT = 20000;
    private static ActiveMQConnectionFactory factory = null;

    private static ActiveMQConnectionFactory getsingleton() {
        synchronized (ActivemqUtils.class) {
            if (factory == null) {
                factory = new ActiveMQConnectionFactory(
                        ConfigUtils.getSingleInstance().configActivemq.getProperty("mq.user"),
                        ConfigUtils.getSingleInstance().configActivemq.getProperty("mq.passwd"),
                        ConfigUtils.getSingleInstance().configActivemq.getProperty("mq.url"));
            }
        }

        return factory;
    }

    public static void main(String[] args) {
        ActivemqUtils.getsingleton();

    }

    /**
     * 发送消息
     *
     * @param sendConnectionFactory
     * @param host
     * @param xmlMessage
     */
    public static boolean sendMessage(String queue, String xmlMessage) {
        Connection connection = null;
        Session session = null;
        try {

            connection = getsingleton().createConnection();
            connection.start();
            session = connection.createSession(TRANSACTED,
                    Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(queue);
            MessageProducer producer = session.createProducer(destination);
            // TextMessage message = session.createTextMessage(xmlMessage);

            /**
             * 写入字节流 start
             */
            BytesMessage bytein = session.createBytesMessage();
            bytein.writeBytes(xmlMessage.getBytes("UTF-8"));

            producer.send(bytein);
            session.commit();

            /**
             * 写入字符流 stop
             */
            // producer.send(message);
            producer.close();
            session.close();
            return true;
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;

    }

    /**
     * 把activemq上的消息写入指定的文件夹下
     *
     * @param connectionFactory
     * @param host
     * @param fileRootPath
     */
    public static void pollMessageFromCiqQueue() {
        Connection connection = null;
        String strResult = null;
        byte[] byteresult = null;
        MessageConsumer consumer = null;
        try {
            if (!FileDto.getSingleInstance().receCiqisSelected) {
   
                return;
            }
            connection = getsingleton().createConnection();
            connection.start();
            Session session = connection.createSession(NON_TRANSACTED,
                    Session.CLIENT_ACKNOWLEDGE);
            Destination destination = session.createQueue(FileDto.getSingleInstance().receQueue);
            consumer = session.createConsumer(destination);

            Message message = consumer.receive(TIMEOUT);

            if (message != null) {
                if (message instanceof TextMessage) {
                    strResult = ((TextMessage) message).getText();
                } else if (message instanceof ActiveMQBytesMessage) {
                    byteresult = ((ActiveMQBytesMessage) message).getContent()
                            .getData();
                    strResult = new String(byteresult, "UTF-8");

                }

                FileUtils.writeStringToFile(new File(FileDto.getSingleInstance().fileReceCiqPath, UUID
                        .randomUUID().toString() + ".xml"), strResult, "UTF-8");
                message.acknowledge();
            }

            consumer.close();
            session.close();
        } catch (Exception e) {
            log.error(e.getMessage(), e);

        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void pollMessageFromCusQueue() {

        Connection connection = null;
        String strResult = null;
        byte[] byteresult = null;
        MessageConsumer consumer = null;
        try {
            if (!FileDto.getSingleInstance().receCusisSelected) {
                return;
            }
            connection = getsingleton().createConnection();
            connection.start();
            Session session = connection.createSession(NON_TRANSACTED,
                    Session.CLIENT_ACKNOWLEDGE);
            Destination destination = session.createQueue(FileDto.getSingleInstance().receCusQueue);
            consumer = session.createConsumer(destination);
            Message message = consumer.receive(TIMEOUT);
            if (message != null) {
                if (message instanceof TextMessage) {
                    strResult = ((TextMessage) message).getText();
                } else if (message instanceof ActiveMQBytesMessage) {
                    byteresult = ((ActiveMQBytesMessage) message).getContent()
                            .getData();
                    strResult = new String(byteresult, "UTF-8");

                }
                FileUtils.writeStringToFile(new File(FileDto.getSingleInstance().fileReceCusPath, UUID
                        .randomUUID().toString() + ".xml"), strResult, "UTF-8");

                message.acknowledge();
               
            }

            consumer.close();
            session.close();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    // public static void main(String[] args) {
    //
    // ActivemqUtils.pollMessageFromQueue("test__lijinshan", new
    // File("C:\\test"));
    //
    // }

    // public static void main(String[] args) {
    //
    // Connection connection = null;
    // String strResult = null;
    // byte[] byteresult = null;
    // String url = "tcp://192.168.103.195:61616";
    // try {
    // ActiveMQConnectionFactory sendConnectionFactory = new
    // ActiveMQConnectionFactory(
    // ActiveMQConnection.DEFAULT_USER,
    // ActiveMQConnection.DEFAULT_PASSWORD, url);
    // connection = sendConnectionFactory.createConnection();
    // connection.start();
    // Session session = connection.createSession(NON_TRANSACTED,
    // Session.CLIENT_ACKNOWLEDGE);
    // Destination destination = session.createQueue("test__lijinshan");
    // MessageConsumer consumer = session.createConsumer(destination);
    // Message message = consumer.receive(TIMEOUT);
    //
    // if (message != null) {
    // if (message instanceof TextMessage) {
    // strResult = ((TextMessage) message).getText();
    // System.out.println("Got. message: " + strResult);
    // } else if (message instanceof ActiveMQBytesMessage) {
    // byteresult = ((ActiveMQBytesMessage) message).getContent()
    // .getData();
    // strResult = new String(byteresult, "UTF-8");
    // System.out.println("Got. message: " + strResult);
    // }
    // }
    //
    // message.acknowledge();
    // consumer.close();
    // session.close();
    // } catch (JMSException ex) {
    // ex.printStackTrace();
    // log.error(ex.getMessage(), ex);
    // // throw ex;
    // } catch (UnsupportedEncodingException ex) {
    // log.error(ex.getMessage(), ex);
    // ex.printStackTrace();
    // } catch (Exception ex) {
    // log.error(ex.getMessage(), ex);
    // ex.printStackTrace();
    // }
    //
    // finally {
    // if (connection != null) {
    // try {
    // connection.close();
    // } catch (JMSException e) {
    // e.printStackTrace();
    // }
    // }
    // }
    //
    // }
    // public static void main(String[] args) {
    //
    // Connection connection = null;
    // String url ="tcp://192.168.103.195:61616";
    //
    // try { ActiveMQConnectionFactory sendConnectionFactory = new
    // ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
    // ActiveMQConnection.DEFAULT_PASSWORD, url);
    //
    // connection = sendConnectionFactory.createConnection();
    // connection.start();
    // Session session =
    // connection.createSession(TRANSACTED,Session.AUTO_ACKNOWLEDGE);
    // Destination destination = session.createQueue("test__lijinshan");
    // MessageProducer producer = session.createProducer(destination);
    //
    // TextMessage message = session.createTextMessage("DSS身上你sdf所得税水水水水");
    // producer.send(message);
    //
    //
    // session.commit();
    // producer.close();
    // session.close();
    //
    // } catch ( JMSException e) {
    // e.printStackTrace();
    //
    // }catch (Exception e){
    // e.printStackTrace();
    //
    //
    // } finally {
    // if (connection != null) {
    // try {
    // connection.close();
    // } catch (JMSException e) {
    // e.printStackTrace();
    //
    // }
    // }
    // }
    //
    //
    // }
    //
    /**
     *
     * @param host
     * @return 接收到消息返回消息,接收不到返回null;
     */
    // public static String receMessage( String queue)
    // throws JMSException {
    //
    // // receConnectionFactory.setUserName(host.userName);
    // // receConnectionFactory.setPassword(host.passwd);
    // // receConnectionFactory.setBrokerURL(host.url);
    // Connection connection = null;
    // String strResult = null;
    // byte[] byteresult = null;
    //
    // try {
    // connection = factory.createConnection();
    // connection.start();
    // Session session = connection.createSession(NON_TRANSACTED,
    // Session.CLIENT_ACKNOWLEDGE);
    // Destination destination = session.createQueue(queue);
    // MessageConsumer consumer = session.createConsumer(destination);
    // Message message = consumer.receive(TIMEOUT);
    //
    // if (message != null) {
    // if (message instanceof TextMessage) {
    // strResult = ((TextMessage) message).getText();
    // System.out.println("Got. message: " + strResult);
    // } else if (message instanceof ActiveMQBytesMessage) {
    // byteresult = ((ActiveMQBytesMessage) message).getContent()
    // .getData();
    // strResult = new String(byteresult, "UTF-8");
    //
    // }
    //
    // }
    //
    // System.out.println(strResult);
    // message.acknowledge();
    // consumer.close();
    //
    // session.close();
    // } catch (JMSException ex) {
    // ex.printStackTrace();
    // log.error(ex.getMessage(), ex);
    // throw ex;
    // } catch (UnsupportedEncodingException ex) {
    // log.error(ex.getMessage(), ex);
    // ex.printStackTrace();
    // } catch (Exception ex) {
    // log.error(ex.getMessage(), ex);
    // ex.printStackTrace();
    // }
    //
    // finally {
    // if (connection != null) {
    // try {
    // connection.close();
    //
    // } catch (JMSException e) {
    // e.printStackTrace();
    // }
    // }
    // }
    // return strResult;
    // }
}
