package com.hehe.demo.common.boot;

import org.springframework.boot.Banner;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.ansi.AnsiStyle;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

/**
 * @author hehe
 * @date 2017/12/27 下午4:12
 * @email qinghe101@qq.com
 */
public class FhblogBanner implements Banner {
    private static final String[] BANNER = {"",
            "                                                                \n" +
                    "   █    █    ████    █    █    ████     \n" +
                    "   █    █    █         █    █     █        \n" +
                    "   █    █    █         █    █     █        \n" +
                    "   ████    ████    ████     ████                   \n" +
                    "   █    █    █         █    █     █                     \n" +
                    "   █    █    █         █    █     █             \n" +
                    "   █    █    ████    █    █     ████                 \n" +
                    "                                                                \n"
    };

    private static final String SPRING_BOOT = " :: Powered by Terminus.inc :: ";

    private static final int STRAP_LINE_SIZE = 42;

    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream printStream) {
        for (String line : BANNER) {
            printStream.println(line);
        }
        String version = "1.0.0";
        version = version == null ? "" : " (v" + version + ")";
        String padding = "";
        while (padding.length() < STRAP_LINE_SIZE
                - (version.length() + SPRING_BOOT.length())) {
            padding += " ";
        }

        printStream.println(AnsiOutput.toString(AnsiColor.GREEN, SPRING_BOOT, AnsiColor.DEFAULT, padding, AnsiStyle.FAINT, version));
        printStream.println();
    }
}
