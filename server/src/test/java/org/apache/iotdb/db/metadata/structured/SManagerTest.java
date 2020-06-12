/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements.  See the NOTICE file distributed with this work for additional information regarding copyright ownership.  The ASF licenses this file to you under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at      http://www.apache.org/licenses/LICENSE-2.0  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the specific language governing permissions and limitations under the License.
 */

package org.apache.iotdb.db.metadata.structured;

import org.apache.iotdb.db.qp.physical.crud.InsertPlan;
import org.apache.iotdb.tsfile.file.metadata.enums.CompressionType;
import org.apache.iotdb.tsfile.file.metadata.enums.TSDataType;
import org.apache.iotdb.tsfile.file.metadata.enums.TSEncoding;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class SManagerTest {

    @Test
    public void translateSimplePlan() {
        SManager sManager = new SManager();

        sManager.register("gps", gpsType());

        InsertPlan plan = sManager.translate(new InsertPlan("root.sg1.d1", 0, new String[]{"gps"}, new String[]{"{ \"lat\" : 40.0, \"long\" : 20.0}::gps"}));

        assertArrayEquals(new String[]{ "gps.lat", "gps.long" }, plan.getMeasurements());
        assertArrayEquals(new TSDataType[]{ TSDataType.DOUBLE, TSDataType.DOUBLE }, plan.getTypes());
        assertArrayEquals(new Object[]{ 40.0, 20.0 }, plan.getValues());
    }

    private StructuredType gpsType() {
        HashMap<String, StructuredType> children = new HashMap<>();
        children.put("lat", new PrimitiveType(TSDataType.DOUBLE, TSEncoding.GORILLA, CompressionType.SNAPPY));
        children.put("long", new PrimitiveType(TSDataType.DOUBLE, TSEncoding.GORILLA, CompressionType.SNAPPY));

        return new MapType(children);
    }
}