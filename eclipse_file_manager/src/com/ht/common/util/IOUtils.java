/**
 * File Name:IOUtils.java
 * Package Name:com.ht.common.util
 * Date:2017-2-8����10:43:22
 * Copyright (c) 2016, huobao_accp@163.com All Rights Reserved.
 *
*/

package com.ht.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * ClassName:IOUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017-2-8 ����10:43:22 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class IOUtils {

    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    /**
     * copyLarge:(������һ�仰�����������������). <br/>
     * TODO(�����������������ʹ�÷��� �C ��ѡ).<br/>
     * Date: 2017-2-8 ����10:47:07
     * @author Administrator
     * @param input
     * @param output
     * @return
     * @throws IOException
     */
    public static long copyLarge(Reader input, Writer output) throws IOException {
        char[] buffer = new char[DEFAULT_BUFFER_SIZE];
        long count = 0;
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    /**
     * copy:(������һ�仰�����������������). <br/>
     * TODO(�����������������ʹ�÷��� �C ��ѡ).<br/>
     * Date: 2017-2-8 ����10:47:35
     * @author Administrator
     * @param input
     * @param output
     * @return
     * @throws IOException
     */
    public static int copy(Reader input, Writer output) throws IOException {
        long count = copyLarge(input, output);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }

    /**
     * copy:(������һ�仰�����������������). <br/>
     * TODO(�����������������ʹ�÷��� �C ��ѡ).<br/>
     * Date: 2017-2-8 ����10:47:37
     * @author Administrator
     * @param input
     * @param output
     * @throws IOException
     */
    public static void copy(InputStream input, Writer output)
            throws IOException {
        InputStreamReader in = new InputStreamReader(input);
        copy(in, output);
    }

    /**
     * toString:(������һ�仰�����������������). <br/>
     * TODO(�����������������ʹ�÷��� �C ��ѡ).<br/>
     * Date: 2017-2-8 ����10:47:40
     * @author Administrator
     * @param input
     * @return
     * @throws IOException
     */
    public static String toString(InputStream input) throws IOException {
        StringWriter sw = new StringWriter();
        copy(input, sw);
        return sw.toString();
    }
}

