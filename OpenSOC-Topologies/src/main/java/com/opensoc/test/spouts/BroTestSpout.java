/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.opensoc.test.spouts;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.opensoc.test.filereaders.FileReader;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;


public class BroTestSpout extends BaseRichSpout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String JsonFilename = "BroExampleOutput";
	List<String> jsons;

	SpoutOutputCollector _collector;
	FileReader Reader;
	int cnt = 0;

	@SuppressWarnings("rawtypes") 
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		
		_collector = collector;
		try {
			Reader =  new FileReader();
			jsons = Reader.readFromFile(JsonFilename);

			
		} catch (IOException e) 
		{
			System.out.println("Could not read sample JSONs");
			e.printStackTrace();
		}
		
	}

	public void nextTuple() {
		Utils.sleep(100);
		
		if(cnt < jsons.size())
		{
			_collector.emit(new Values(jsons.get(cnt)));
		}
		cnt ++;
		
		if(cnt == jsons.size() -1 )
			cnt = 0;
	}

	@Override
	public void ack(Object id) {
	}

	@Override
	public void fail(Object id) {
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("message"));
	}

}