package com.sungness.core.util.tools;
/*
 **************************************************************************
 *                                                                        *
 *          General Purpose Hash Function Algorithms Library              *
 *          常用哈希函数算法库                                              *
 * Author: Arash Partow - 2002                                            *
 * URL: http://www.partow.net                                             *
 * URL: http://www.partow.net/programming/hashfunctions/index.html        *
 *                                                                        *
 * Copyright notice:                                                      *
 * Free use of the General Purpose Hash Function Algorithms Library is    *
 * permitted under the guidelines and in accordance with the most current *
 * version of the Common Public License.                                  *
 * http://www.opensource.org/licenses/cpl1.0.php                          *
 *                                                                        *
 **************************************************************************
*/

public class HashLibrary {
    /**
     * A simple hash function from Robert Sedgwicks Algorithms in C book
     * @param str String 要进行哈希的目标字符串
     * @return long 哈希结果
     */
    public static long RSHash(String str) {
        int b = 378551;
        int a = 63689;
        long hash = 0;
        for(int i = 0; i < str.length(); i++) {
            hash = hash * a + str.charAt(i);
            a    = a * b;
        }
        return hash;
    }

    /**
     * A bitwise hash function written by Justin Sobel
     * @param str String 要进行哈希的目标字符串
     * @return long 哈希结果
     */
    public static long JSHash(String str) {
        long hash = 1315423911;
        for(int i = 0; i < str.length(); i++) {
            hash ^= ((hash << 5) + str.charAt(i) + (hash >> 2));
        }
        return hash;
    }

    /**
     * This hash algorithm is based on work by Peter J. Weinberger of AT&T Bell Labs.
     * @param str String 要进行哈希的目标字符串
     * @return long 哈希结果
     */
    public static long PJWHash(String str) {
        long BitsInUnsignedInt = (long)(4 * 8);
        long ThreeQuarters     = (long)((BitsInUnsignedInt  * 3) / 4);
        long OneEighth         = (long)(BitsInUnsignedInt / 8);
        long HighBits          = (long)(0xFFFFFFFF) << (BitsInUnsignedInt - OneEighth);
        long hash              = 0;
        long test              = 0;

        for(int i = 0; i < str.length(); i++) {
            hash = (hash << OneEighth) + str.charAt(i);
            if((test = hash & HighBits)  != 0) {
                hash = (( hash ^ (test >> ThreeQuarters)) & (~HighBits));
            }
        }
        return hash;
    }

    /**
     * Similar to the PJW Hash function, but tweaked for 32-bit processors.
     * @param str String 要进行哈希的目标字符串
     * @return long 哈希结果
     */
    public static long ELFHash(String str) {
        long hash = 0;
        long x    = 0;

        for(int i = 0; i < str.length(); i++) {
            hash = (hash << 4) + str.charAt(i);
            if((x = hash & 0xF0000000L) != 0) {
                hash ^= (x >> 24);
            }
            hash &= ~x;
        }
        return hash;
    }

    /**
     * This hash function comes from Brian Kernighan and Dennis Ritchie's book "The C Programming Language".
     * @param str String 要进行哈希的目标字符串
     * @return long 哈希结果
     */
    public static long BKDRHash(String str) {
        long seed = 131; // 31 131 1313 13131 131313 etc..
        long hash = 0;
        for(int i = 0; i < str.length(); i++) {
            hash = (hash * seed) + str.charAt(i);
        }
        return hash;
    }

    /**
     * This is the algorithm of choice which is used in the open source SDBM project.
     * @param str String 要进行哈希的目标字符串
     * @return long 哈希结果
     */
    public static long SDBMHash(String str) {
        long hash = 0;
        for(int i = 0; i < str.length(); i++) {
            hash = str.charAt(i) + (hash << 6) + (hash << 16) - hash;
        }
        return hash;
    }

    /**
     * An algorithm produced by Professor Daniel J. Bernstein and
     * shown first to the world on the usenet newsgroup comp.lang.c.
     * It is one of the most efficient hash functions ever published.
     * 目前公布的最有效的哈希算法。
     * @param str String 要进行哈希的目标字符串
     * @return long 哈希结果
     */
    public static long DJBHash(String str) {
        long hash = 5381;
        for(int i = 0; i < str.length(); i++) {
            hash = ((hash << 5) + hash) + str.charAt(i);
        }
        return hash;
    }

    /**
     * An algorithm proposed by Donald E. Knuth in The Art Of Computer Programming Volume 3
     * @param str String 要进行哈希的目标字符串
     * @return long 哈希结果
     */
    public static long DEKHash(String str) {
        long hash = str.length();
        for(int i = 0; i < str.length(); i++) {
            hash = ((hash << 5) ^ (hash >> 27)) ^ str.charAt(i);
        }
        return hash;
    }

    /**
     * BP hash function
     * @param str String 要进行哈希的目标字符串
     * @return long 哈希结果
     */
    public static long BPHash(String str) {
        long hash = 0;
        for(int i = 0; i < str.length(); i++) {
            hash = hash << 7 ^ str.charAt(i);
        }
        return hash;
    }

    /**
     * FNV hash function
     * @param str String 要进行哈希的目标字符串
     * @return long 哈希结果
     */
    public static long FNVHash(String str) {
        long fnv_prime = 0x811C9DC5;
        long hash = 0;
        for(int i = 0; i < str.length(); i++) {
            hash *= fnv_prime;
            hash ^= str.charAt(i);
        }
        return hash;
    }

    /**
     * An algorithm produced by me Arash Partow
     * @param str String 要进行哈希的目标字符串
     * @return long 哈希结果
     */
    public static long APHash(String str) {
        long hash = 0xAAAAAAAA;
        for(int i = 0; i < str.length(); i++) {
            if ((i & 1) == 0) {
                hash ^= ((hash << 7) ^ str.charAt(i) * (hash >> 3));
            } else {
                hash ^= (~((hash << 11) + str.charAt(i) ^ (hash >> 5)));
            }
        }
        return hash;
    }
}
