/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.iotdb.rpc;

import org.apache.iotdb.rpc.TimeoutChangeableTFastFramedTransport.Factory;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportFactory;

public class RpcTransportFactory extends TTransportFactory {

  // TODO: make it a config
  public static final boolean USE_SNAPPY = true;
  public static final RpcTransportFactory INSTANCE;
  static {
    INSTANCE = USE_SNAPPY ?
        new RpcTransportFactory(new TimeoutChangeableTSnappyFramedTransport.Factory()) :
        new RpcTransportFactory(new Factory());
  }

  private TTransportFactory inner;

  public RpcTransportFactory(TTransportFactory inner) {
    this.inner = inner;
  }

  @Override
  public TTransport getTransport(TTransport trans) {
    return inner.getTransport(trans);
  }
}