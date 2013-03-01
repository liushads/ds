package com.ppsea.ds.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

//3DES
//包含补0和去0
//压缩和解压
//标准的base16和base64转码
public class DESUtil
{
    private String Algorithm = "DESede";
    private byte[] keyBytes = null;
    
    public DESUtil(byte[] keyBytes) throws Exception
    {
        if(keyBytes.length != 24)
        {
            throw new Exception("the keys's length must be 24!");
        }
        this.keyBytes = keyBytes;
    }
    
    private String trimAlgorithm(String alg)
    {
    	int p = alg.indexOf('/');
    	if(p == -1)return alg;
    	return alg.substring(0, p);
    }
    
    public DESUtil(byte[] keyBytes, String Algorithm) throws Exception
    {
        if(keyBytes.length != 24)
        {
            throw new Exception("the keys's length must be 24!");
        }
        
        this.keyBytes = keyBytes;
        this.Algorithm = Algorithm;
    }    
      
    public byte[] encode(byte[] src) 
    {
        try 
        {
        	byte[] new_src = null;
        	
        	int padLen = src.length % 8;
        	if (0 != padLen)
        	{
        		//不够8的倍数，填0
        		int new_len = ((src.length - 1)/8 + 1) * 8;
        		new_src = new byte[new_len];
        		System.arraycopy(src, 0, new_src, 0, src.length);
        		/*for (int i = 0 ; i < 8 - padLen; i++)
        		{
        			new_src[src.length + i] = 0;
        		}*/
        	}
        	else
        	{
        		new_src = src;
        	}
        	
            SecretKey deskey = new SecretKeySpec(this.keyBytes, trimAlgorithm(Algorithm));

            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(new_src);
        }
        catch (java.security.NoSuchAlgorithmException e1) 
        {
        	System.out.println("NoSuchAlgorithmException: " + e1);
            return null;
        }
        catch (javax.crypto.NoSuchPaddingException e2) 
        {
        	System.out.println("NoSuchPaddingException: " + e2);
            return null;
        }
        catch (java.lang.Exception e3) 
        {
        	System.out.println("Exception: " + e3);
            return null;
        }
    }

    public byte[] decode(byte[] src) 
    { 
        try 
        {
            SecretKey deskey = new SecretKeySpec(this.keyBytes, trimAlgorithm(Algorithm));

            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            byte[] out = c1.doFinal(src);
            
            int zeorNum = 0;
			/*for ( int i = 1; i <+ out.length; i++)
			{
				if (' ' == out[out.length - i])zeorNum++;
			}
			*/
			byte[] ret = new byte[out.length - zeorNum];
			System.arraycopy(out, 0, ret, 0, ret.length);
			return ret;
        }
        catch (java.security.NoSuchAlgorithmException e1) 
        {
        	System.out.println("NoSuchAlgorithmException: " + e1);
            return null;
        }
        catch (javax.crypto.NoSuchPaddingException e2) 
        {
        	System.out.println("NoSuchPaddingException: " + e2);
            return null;
        }
        catch (java.lang.Exception e3) 
        {
        	System.out.println("Exception: " + e3);
            return null;
        }
    }
 
    //---------------static----------------
    private static final char[] digits = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
   
    public static String bytes2HexStr(byte[] bytes)
    {
        if (bytes == null || bytes.length == 0)
        {
            return null;
        }

        char[] buf = new char[2 * bytes.length];
        for (int i = 0; i < bytes.length; i++)
        {
            byte b = bytes[i];
            buf[2 * i + 1] = digits[b & 0xF];
            b = (byte) (b >>> 4);
            buf[2 * i + 0] = digits[b & 0xF];
        }
        return new String(buf);
    }
    
    public static byte[] hexStr2Bytes(String str)
    {
        if (str == null || str.equals(""))
        {
            return null;
        }

        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < bytes.length; i++)
        {
            char high = str.charAt(i * 2);
            char low = str.charAt(i * 2 + 1);
            bytes[i] = (byte) (char2Byte(high) * 16 + char2Byte(low));
        }
        return bytes;
    }
    
    public static byte char2Byte(char ch)
    {
        if (ch >= '0' && ch <= '9')
        {
            return (byte) (ch - '0');
        }
        else if (ch >= 'a' && ch <= 'f')
        {
            return (byte) (ch - 'a' + 10);
        }
        else if (ch >= 'A' && ch <= 'F')
        {
            return (byte) (ch - 'A' + 10);
        }
        else
        {
            return 0;
        }
    }
    
    public static String Bytes2base64Str(byte[] bytes)
    {
        if (bytes == null || bytes.length == 0)
        {
            return null;
        }
    	
        String out = new BASE64Encoder().encode(bytes);
        String ret = DESUtil.replaceAll(out, "\r\n", "$");
        return ret;
    }
    
    public static byte[] base64Str2Bytes(String str)
    {
    	if (str == null || str.equals(""))
        {
            return null;
        }
    	
    	String in = DESUtil.replaceAll(str, "$", "\r\n");
    	try
        {
            return new BASE64Decoder().decodeBuffer(in);
        }
        catch(Exception e)
        {
            return null;
        }
    }
    
    private static String replaceAll(String s, String src, String dest)
    {
    	if(s == null || src == null || dest == null || src.length() == 0)
    		return s;
    	int pos = s.indexOf(src);
    	if(pos < 0)
    		return s;
    	int capacity = dest.length() > src.length() ? s.length() * 2: s.length();
    	StringBuilder sb = new StringBuilder(capacity);
    	int writen = 0;
    	for(; pos >= 0; )
    	{
    		sb.append(s, writen, pos);
    		sb.append(dest);			
    		writen = pos + src.length();
    		pos = s.indexOf(src, writen);
    	}
    	sb.append(s, writen, s.length());	
    	return sb.toString();
    }
    
    // 压缩
    public static byte[] compress(byte[] src) throws IOException 
    {
      if (src == null || src.length == 0) 
      {
        return src;
      }
      
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      GZIPOutputStream gzip = new GZIPOutputStream(out);
      gzip.write(src);
      gzip.close();
      return out.toByteArray();
    }

    // 解压缩
    public static byte[] uncompress(byte[] src) throws IOException
    {
      if (src == null || src.length == 0)
      {
        return src;
      }
      
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      ByteArrayInputStream in = new ByteArrayInputStream(src);
      GZIPInputStream gunzip = new GZIPInputStream(in);
      byte[] buffer = new byte[256];
      int n;
      while ((n = gunzip.read(buffer)) >= 0)
      {
        out.write(buffer, 0, n);
      }

      gunzip.close();
      return out.toByteArray();
    }
    
    public static void main(String[] args)
	{

	}
    
//    private static void testDesCompressBase64(String ss)
//    {
//    	System.out.println(ss);
//    	try
//		{
//    		byte[] key = "123456789012345678901234".getBytes();
//    		System.out.println("len 0 :   " + ss.length());
//    		
//    		byte[] info = null;
//    		try
//			{
//				info = new DESUtil(key, "DESede/ECB/NoPadding").encode(ss.getBytes("UTF-8"));
//			} 
//    		catch (Exception e)
//			{
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//    		
//    		System.out.println("len 1 :   " + info.length);
//    		
//			byte[] out = DESUtil.compress(info);
//			System.out.println("len 2 :   " + out.length);
//			
//			String str_info = DESUtil.Bytes2base64Str(out);
//			System.out.println("len 3 :   " + str_info.length());
//			
//			System.out.println(str_info);
//			
//			byte[] zipBytes = DESUtil.base64Str2Bytes(str_info);
//			
//			byte[] oout = DESUtil.uncompress(zipBytes);
//			
//			byte[] encryptedInfoBytes = null;
//			try
//			{
//				encryptedInfoBytes = new DESUtil(key, "DESede/ECB/NoPadding").decode(oout);
//			} catch (Exception e)
//			{
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} 
//			
//			System.out.println(new String(encryptedInfoBytes));
//		} 
//    	catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//    }
    

}
