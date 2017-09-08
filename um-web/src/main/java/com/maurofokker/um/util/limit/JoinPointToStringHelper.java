package com.maurofokker.um.util.limit;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.reflect.SourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class JoinPointToStringHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(JoinPointToStringHelper.class);

    public static String toString(JoinPoint jp) {
        StringBuilder sb = new StringBuilder();
        appendType(sb, getType(jp));
        LOGGER.debug("Append types in sb [{}]", sb.toString());
        Signature signature = jp.getSignature();
        if (signature instanceof MethodSignature) {
            MethodSignature ms = (MethodSignature) signature;
            sb.append("#");
            sb.append(ms.getMethod().getName());
            sb.append("(");
            appendTypes(sb, ms.getMethod().getParameterTypes());
            sb.append(")");
        }
        LOGGER.debug("Final toString in sb [{}]", sb.toString());
        return sb.toString();
    }

    //

    private static Class<?> getType(JoinPoint jp) {
        return Optional.ofNullable(jp.getSourceLocation()).map(SourceLocation::getWithinType).orElse(jp.getSignature().getDeclaringType());
    }

    private static void appendTypes(StringBuilder sb, Class<?>[] types) {
        for (int size = types.length, i = 0; i < size; i++) {
            appendType(sb, types[i]);
            if (i < size - 1) {
                sb.append(",");
            }
        }
    }

    private static void appendType(StringBuilder sb, Class<?> type) {
        if (type.isArray()) {
            appendType(sb, type.getComponentType());
            sb.append("[]");
        } else {
            sb.append(type.getName());
        }
    }

}