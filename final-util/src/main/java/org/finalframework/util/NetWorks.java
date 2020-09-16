package org.finalframework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.net.InetAddress;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-26 16:36:23
 * @since 1.0
 */
public abstract class NetWorks {
    private static final Logger logger = LoggerFactory.getLogger(NetWorks.class);

    public static String localHost() {
        try {
            InetAddress address = InetAddress.getLocalHost();
            byte[] ipAddr = address.getAddress();
            StringBuilder ipAddrStr = new StringBuilder();
            for (int i = 0; i < ipAddr.length; i++) {
                if (i > 0) {
                    ipAddrStr.append(".");
                }
                ipAddrStr.append(ipAddr[i] & 0xFF);
            }
            return ipAddrStr.toString();
        } catch (Exception e) {
            logger.error("parse local host exception: ", e);
            return null;
        }
    }

    public static String localPort() {
        try {
            String listenAddr = null;
            String port = null;
            try {
                Context ctx = new InitialContext();
                MBeanServer tMBeanServer = (MBeanServer) ctx
                        .lookup("java:comp/env/jmx/runtime");
                ObjectName tObjectName = new ObjectName(
                        "com.beaName=RuntimeService,Type=weblogic.management.mbeanservers.runtime.RuntimeServiceMBean");
                ObjectName serverrt = (ObjectName) tMBeanServer.getAttribute(
                        tObjectName, "ServerRuntime");
                port = String.valueOf(tMBeanServer.getAttribute(serverrt,
                        "ListenPort"));
                listenAddr = (String) tMBeanServer.getAttribute(serverrt,
                        "ListenAddress");
                String[] tempAddr = listenAddr.split("/");
                if (tempAddr.length == 1) {
                    listenAddr = tempAddr[0];
                } else if (tempAddr[tempAddr.length - 1].trim().length() != 0) {
                    listenAddr = tempAddr[tempAddr.length - 1];
                } else if (tempAddr.length > 2) {
                    listenAddr = tempAddr[tempAddr.length - 2];
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 防止为空导致后续请求失败
            if (listenAddr.isEmpty()) {
                listenAddr = "127.0.0.1";
            }
            if (port.isEmpty()) {
                port = "7001";
            }
            return listenAddr + ":" + port;
        } catch (Exception e) {
            return null;
        }
    }


}
