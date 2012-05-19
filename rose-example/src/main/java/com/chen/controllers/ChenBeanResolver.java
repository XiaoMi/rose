/**
 * @author 54chen(陈臻) [chenzhen@xiaomi.com czhttp@gmail.com]
 * @since 2012-5-19 下午02:27:24
 */
package com.chen.controllers;

import com.chen.model.Chen;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.paramresolver.ParamMetaData;
import net.paoding.rose.web.paramresolver.ParamResolver;

public class ChenBeanResolver implements ParamResolver {

    @Override
    public Object resolve(Invocation inv, ParamMetaData metaData) throws Exception {
        for (String paramName : metaData.getParamNames()) {
            if (paramName != null) {
                Chen chen = new Chen();
                String value1 = inv.getParameter("chen1");
                String value2 = inv.getParameter("chen2");
                chen.setChen1(value1);
                chen.setChen2(value2);
                return chen;
            }
        }
        return null;

    }

    @Override
    public boolean supports(ParamMetaData metaData) {
        return Chen.class == metaData.getParamType();
    }

}
