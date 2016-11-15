/*
 * ============================================================================
 * GNU General Public License
 * ============================================================================
 *
 * Copyright (C) 2006-2011 Serotonin Software Technologies Inc. http://serotoninsoftware.com
 * @author Matthew Lohbihler
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * When signing a commercial license with Serotonin Software Technologies Inc.,
 * the following extension to GPL is made. A special exception to the GPL is 
 * included to allow you to distribute a combined work that includes BAcnet4J 
 * without being obliged to provide the source code for any proprietary components.
 */
package com.serotonin.bacnet4j.base;

import com.serotonin.util.queue.ByteQueue;

/**
 * 提供了一些基本的工具
 * 主要涉及 ByteQueue 的压栈出栈操作，以及一些简单的数据转换
 * */
public class BACnetUtils {
	
	/**
	 * 向 queue 中压入 16 位的 short 数据
	 * */
    public static void pushShort(ByteQueue queue, long value) {
        queue.push((byte) (0xff & (value >> 8)));
        queue.push((byte) (0xff & value));
    }

	/**
	 * 向 queue 中压入 32 位的 int 数据
	 * */
    public static void pushInt(ByteQueue queue, long value) {
        queue.push((byte) (0xff & (value >> 24)));
        queue.push((byte) (0xff & (value >> 16)));
        queue.push((byte) (0xff & (value >> 8)));
        queue.push((byte) (0xff & value));
    }

	/**
	 * 向 queue 中压入 64 位的 long 数据
	 * */
    public static void pushLong(ByteQueue queue, long value) {
        queue.push((byte) (0xff & (value >> 56)));
        queue.push((byte) (0xff & (value >> 48)));
        queue.push((byte) (0xff & (value >> 40)));
        queue.push((byte) (0xff & (value >> 32)));
        queue.push((byte) (0xff & (value >> 24)));
        queue.push((byte) (0xff & (value >> 16)));
        queue.push((byte) (0xff & (value >> 8)));
        queue.push((byte) (0xff & value));
    }

	/**
	 * 从 queue 中弹入 16 位的 short 数据
	 * */
    public static int popShort(ByteQueue queue) {
        return (short) ((toInt(queue.pop()) << 8) | toInt(queue.pop()));
    }

	/**
	 * 从 queue 中弹入 32 位的 int 数据
	 * */
    public static int popInt(ByteQueue queue) {
        return (toInt(queue.pop()) << 24) | (toInt(queue.pop()) << 16) | (toInt(queue.pop()) << 8) | toInt(queue.pop());
    }

	/**
	 * 从 queue 中弹入 64 位的 long 数据
	 * */
    public static long popLong(ByteQueue queue) {
        return (toLong(queue.pop()) << 56) | (toLong(queue.pop()) << 48) | (toLong(queue.pop()) << 40)
                | (toLong(queue.pop()) << 32) | (toLong(queue.pop()) << 24) | (toLong(queue.pop()) << 16)
                | (toLong(queue.pop()) << 8) | toLong(queue.pop());
    }

    /**
	 * 将 8 位的 byte 型数据转换位 32 位的 int 型数据
	 * */
    public static int toInt(byte b) {
        return b & 0xff;
    }

    /**
	 * 将 8 位的 byte 型数据转换位 64 位的 long 型数据
	 * */
    public static long toLong(byte b) {
        return (b & 0xff);
    }

    /**
	 * 将 boolean 型数组按顺序转换为 byte 型数组数据
	 * */
    public static byte[] convertToBytes(boolean[] bdata) {
        int byteCount = (bdata.length + 7) / 8;
        byte[] data = new byte[byteCount];
        for (int i = 0; i < bdata.length; i++)
            data[i / 8] |= (bdata[i] ? 1 : 0) << (7 - (i % 8));
        return data;
    }
    
    /**
	 * 将 byte 型数组按顺序转换为长度为 length 的  byte 型数组数据
	 * */
    public static boolean[] convertToBooleans(byte[] data, int length) {
        boolean[] bdata = new boolean[length];
        for (int i = 0; i < bdata.length; i++)
            bdata[i] = ((data[i / 8] >> (7 - (i % 8))) & 0x1) == 1;
        return bdata;
    }

    /**
     * 将以.分割的数据转换为 byte 型数组数据
     * */
    public static byte[] dottedStringToBytes(String s) throws NumberFormatException {
        String[] parts = s.split("\\.");
        byte[] b = new byte[parts.length];
        for (int i = 0; i < b.length; i++)
            b[i] = (byte) Integer.parseInt(parts[i]);
        return b;
    }

    /**
     * 将  byte 型数组数据转换为以.分割的数据
     * */
    public static String bytesToDottedString(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            if (i > 0)
                sb.append('.');
            sb.append(0xff & b[i]);
        }
        return sb.toString();
    }
}
