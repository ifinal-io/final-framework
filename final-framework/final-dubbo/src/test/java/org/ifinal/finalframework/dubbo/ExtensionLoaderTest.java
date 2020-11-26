package org.ifinal.finalframework.dubbo;


import org.apache.dubbo.common.extension.ExtensionFactory;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.Protocol;
import org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol;
import org.junit.jupiter.api.Test;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class ExtensionLoaderTest {

    @Test
    public void testLoad() {
//        System.out.println(ExtensionLoader.getExtensionLoader(Protocol.class).getSupportedExtensions());
//        System.out.println(ExtensionLoader.getExtensionLoader(Protocol.class).getDefaultExtensionName());
        final ExtensionLoader<Protocol> extensionLoader = ExtensionLoader.getExtensionLoader(Protocol.class);
        System.out.println(extensionLoader.getExtension(DubboProtocol.NAME));
    }

    @Test
    void testExtensionFactory() {
        final ExtensionLoader<ExtensionFactory> extensionLoader = ExtensionLoader.getExtensionLoader(ExtensionFactory.class);
        System.out.println(extensionLoader.getSupportedExtensions());
        final ExtensionFactory adaptiveExtension = extensionLoader.getAdaptiveExtension();
    }

}

