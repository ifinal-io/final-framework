/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.test.jvm;


/**
 * @author likly
 * @version 1.0
 * @date 2020-08-10 14:09:08
 * @since 1.0
 */
public class TenuringThreshold {
    private static final int MB = 1024 * 1024;

    /**
     * VM参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1 -XX:+PrintTenuringDistribution
     *
     * @param args
     */
    public static void main(String[] args) {
        byte[] allocation1 = new byte[MB / 4];
        byte[] allocation2 = new byte[MB * 4];
        byte[] allocation3 = new byte[MB * 4];
        allocation3 = null;
        allocation3 = new byte[MB * 4];
    }
}

