package org.example.biz;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lxcecho 909231497@qq.com
 * @since 13:18 25-02-2023
 */
public class CecMsgTransformUtil {

    private static final String UNKNOWN = "unknown";

    private static String getPropertyValue(String propertyKey) {
        String result;
        try {
            Properties properties = new Properties();
            InputStream is = CecMsgTransformUtil.class.getClassLoader().getResourceAsStream("command.properties");
            properties.load(is);
            result = properties.getProperty(propertyKey);
        } catch (IOException e) {
            throw new RuntimeException("Something's wrong. Call the engineer dad~");
        }
        return result;
    }

    private static String transform(String commandKey) { // S048f
        if (StringUtils.isBlank(commandKey)) {
            return UNKNOWN;
        }

        String first = commandKey.substring(0, 1).toUpperCase();

        String physicalAddr = commandKey.substring(1, commandKey.length() - 2).toUpperCase();

        String temp = commandKey.substring(commandKey.length() - 2);

        String commandValue = StringUtils.defaultString(getPropertyValue(StringUtils.join("0x", temp.toUpperCase())), UNKNOWN);

        commandValue = StringUtils.join("[", first, "]", commandValue, " ", physicalAddr);

        return commandValue;
    }

    public static String parseCecMessage(String line) {
        String[] msg = StringUtils.split(line, "|");
        List<String> commands = Arrays.asList(msg);
//        System.out.println(commands);

        String result = commands.stream().map(CecMsgTransformUtil::transform)
                .collect(Collectors.joining("\n"));

//        System.out.println(result);

        return result;
    }

}
