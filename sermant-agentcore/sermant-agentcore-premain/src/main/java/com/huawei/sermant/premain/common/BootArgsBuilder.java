/*
 * Copyright (C) 2021-2021 Huawei Technologies Co., Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.huawei.sermant.premain.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.huawei.sermant.core.common.CommonConstant;
import com.huawei.sermant.core.utils.FieldUtils;

/**
 * 启动参数构建器
 *
 * @author HapThorin
 * @version 1.0.0
 * @since 2021/11/12
 */
public abstract class BootArgsBuilder {
    /**
     * 构建启动参数
     *
     * @param agentArgs 程序入参
     * @return 启动参数
     */
    public static Map<String, Object> build(String agentArgs) {
        final Properties configMap = loadConfig();
        final Map<String, Object> argsMap = toArgsMap(agentArgs);

        addPathEntries(argsMap);
        return argsMap;
    }

    /**
     * 构建入参集
     *
     * @param args 程序入参
     * @return 入参集
     */
    private static Map<String, Object> toArgsMap(String args) {
        final Map<String, Object> argsMap = new HashMap<String, Object>();
        if (args == null) {
            return argsMap;
        }
        for (String arg : args.trim().split(",")) {
            final int index = arg.indexOf('=');
            if (index >= 0) {
                argsMap.put(arg.substring(0, index).trim(), arg.substring(index + 1).trim());
            }
        }
        return argsMap;
    }

    /**
     * 读取启动配置
     *
     * @return 启动配置
     */
    private static Properties loadConfig() {
        final Properties properties = new Properties();
        InputStream configStream = null;
        try {
            configStream = new FileInputStream(PathDeclarer.getBootConfigPath());
            properties.load(configStream);
        } catch (IOException e) {
            if (configStream != null) {
                try {
                    configStream.close();
                } catch (IOException ignored) {
                }
            }
        }
        return properties;
    }

    /**
     * 通用获取参数值方式，优先从配置获取，在从环境变量获取
     *
     * @param key       参数键
     * @param configMap 配置集
     * @return 参数值
     */
    private static String getCommonValue(String key, Properties configMap) {
        String value = configMap.getProperty(FieldUtils.toUnderline(key, '.', false));
        value = value == null ? System.getProperty(key) : value;
        return value;
    }


    /**
     * 添加路径键值对
     *
     * @param argsMap 参数集
     */
    private static void addPathEntries(Map<String, Object> argsMap) {
       // argsMap.put(CommonConstant.AGENT_ROOT_DIR_KEY, PathDeclarer.getAgentPath());

        argsMap.put(CommonConstant.CORE_CONFIG_FILE_KEY, PathDeclarer.getConfigPath());
        argsMap.put(CommonConstant.PLUGIN_SETTING_FILE_KEY, PathDeclarer.getPluginSettingPath());
        argsMap.put(CommonConstant.PLUGIN_PACKAGE_DIR_KEY, PathDeclarer.getPluginPackagePath());
        argsMap.put(CommonConstant.LOG_SETTING_FILE_KEY, PathDeclarer.getLogbackSettingPath());
    }
}
