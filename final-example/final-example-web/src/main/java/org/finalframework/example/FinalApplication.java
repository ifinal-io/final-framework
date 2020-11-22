package org.finalframework.example;

import com.taobao.arthas.core.command.klass100.JadCommand;
import com.taobao.arthas.core.shell.command.Command;
import com.taobao.arthas.core.shell.command.impl.AnnotatedCommandImpl;
import com.taobao.arthas.core.util.ClassUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.CodeSource;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/17 16:01:43
 * @since 1.0
 */
@SpringBootApplication
public class FinalApplication {
    public static void main(String[] args) {

        Class clazz = SpringBootApplication.class;
        CodeSource codeSource = clazz.getProtectionDomain().getCodeSource();
        String file = codeSource.getLocation().getFile();

        System.out.println(ClassUtils.getCodeSource(codeSource));

        System.out.println(file);

        Command command = AnnotatedCommandImpl.create(JadCommand.class);


        org.benf.cfr.reader.Main.main(new String[]{"--analyseas", "JAR", file + "/" + clazz.getCanonicalName().replace("\\.", "/")});
//        org.benf.cfr.reader.Main.main(new String[]{file + clazz.getCanonicalName().replace("\\.","/")});


//        org.benf.cfr.reader.Main.main(new String[]{"--help"});
//        SpringApplication.run(FinalApplication.class);
    }
}
