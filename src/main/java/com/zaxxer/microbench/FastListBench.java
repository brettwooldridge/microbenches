/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.zaxxer.microbench;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@State(Scope.Thread)
@Fork(value = 3)
@Warmup(iterations = 3)
@Measurement(iterations = 6)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class FastListBench
{
   @Param({ "base", "new", "orig" })
   private String listImpl;

   @Param({ "32" })
   private int initCapacity = 32;
   
   private String[] strings;
   private ArrayList<String> fastList;

   @Benchmark
   public boolean testList()
   {
      boolean b = true;
      b &= fastList.add(strings[0]);
      b &= fastList.add(strings[1]);
      b &= fastList.add(strings[2]);
      b &= fastList.add(strings[3]);
      b &= fastList.add(strings[4]);
      b &= fastList.add(strings[5]);
      b &= fastList.add(strings[6]);
      b &= fastList.add(strings[7]);
      b &= fastList.add(strings[8]);
      b &= fastList.add(strings[9]);
      b &= fastList.add(strings[10]);
      b &= fastList.add(strings[11]);
      b &= fastList.add(strings[12]);
      b &= fastList.add(strings[13]);
      b &= fastList.add(strings[14]);

      b &= fastList.remove(strings[14]);
      b &= fastList.remove(strings[13]);
      b &= fastList.remove(strings[12]);
      b &= fastList.remove(strings[11]);
      b &= fastList.remove(strings[10]);
      b &= fastList.remove(strings[9]);
      b &= fastList.remove(strings[8]);
      b &= fastList.remove(strings[7]);
      b &= fastList.remove(strings[6]);
      b &= fastList.remove(strings[5]);
      b &= fastList.remove(strings[4]);
      b &= fastList.remove(strings[3]);
      b &= fastList.remove(strings[2]);
      b &= fastList.remove(strings[1]);
      b &= fastList.remove(strings[0]);

      return b;
   }

   @Setup(Level.Trial)
   public void setup()
   {
      this.strings = new String[31];
      for (int i = 0; i < strings.length; i++) {
         strings[i] = String.valueOf(i);
      }

      switch (listImpl) {
      case "base":
         this.fastList = new ArrayList<>(initCapacity);
         break;
      case "new":
         this.fastList = new FastList<>(String.class, initCapacity);
         break;
      case "orig":
         this.fastList = new FastList2<>(String.class, initCapacity);
         break;
      }
   }
}
