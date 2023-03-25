package atlib;

import java.io.IOException;
public class Input {

    public static final     String  CR      = System.getProperty("line.separator");
    public  static  final   int     CR_LEN  =   CR.length();

    /**
     * �R���\�[�����當������͂���
     * 
     * @return  ���͂��������i�P�����j
     */
    public  static  char getChar(){
        return  getChar("char");
    }
    public  static  char getChar(String s){
        
        char    c   =   0;
        String  str =   getStr(s);
        try{
            c   =   str.charAt(0);
            
        }catch(Exception e){
            c   =   0;
        }
        return  c;
    }
    /**
     * �R���\�[�����當������͂���
     * 
     * @return  ���͂��������i�P�����j
     */
    public  static  byte getByte(){
        return  getByte("byte");
    }
    public  static  byte getByte(String s){
        
        byte    b   =   0;
        String  str =   getStr(s);
        try{
            b   =   Byte.parseByte(str);
            
        }catch(Exception e){
            b   =   0;
        }
        return  b;

    }
    /**
     * �R���\�[�����當�������͂���
     * 
     * @return  ���͂���������
     */
    public  static  String  getString(){
        
        return  getString("String");
    }
    public  static  String  getString(String s){
        
        String str  =   getStr(s);
        if(str.length()==0){
            return  null;
            
        }else{
            return  str;
        }

    }
    /**
     * �R���\�[������short��������͂���
     * 
     * @return  ���͂�������
     */
    public  static  short   getShort(){
        return  getShort("short");
    }
    public  static  short   getShort(String s){
        
        short       n   =   0;
        String  str =   getStr(s);
        try{
            n   =   Short.parseShort(str);
            
        }catch(Exception e){
            n   =   0;
        }
        return  n;
        
    }
    /**
     * �R���\�[�����琮������͂���
     * 
     * @return  ���͂�������
     */
    public  static  int getInt(){
        return  getInt("int");
    }
    public  static  int getInt(String s){
        
        int     n   =   0;
        String  str =   getStr(s);
        try{
            n   =   Integer.parseInt(str);
            
        }catch(Exception e){
            n   =   0;
        }
        return  n;
        
    }
    /**
     * �R���\�[������long��������͂���
     * 
     * @return  ���͂�������
     */
    public  static  long    getLong(){
        return  getLong("long");
    }
    public  static  long    getLong(String s){
        
        long        n   =   0;
        String  str =   getStr(s);
        try{
            n   =   Long.parseLong(str);
            
        }catch(Exception e){
            n   =   0;
        }
        return  n;
        
    }
    /**
     * �R���\�[�������������͂���
     * 
     * @return  ���͂�������
     */
    public  static  double  getDouble(){
        return  getDouble("double");
    }
    public  static  double  getDouble(String s){
        
        double  x   =   0;
        String  str =   getStr(s);
        try{
            x   =   Double.parseDouble(str);
            
        }catch(Exception e){
            x   =   0.0;
        }
        return  x;
        
    }
    /**
     * �R���\�[�����當�������͂���
     * @param s
     * @return
     */
    public  static  String  getStr(String s){
        
        byte[]  buf =   new byte[1000];
        int     len =   0;
        
        try {
            guide(s);
            len =   System.in.read(buf);
            
        } catch (IOException e) {
            System.out.println(e);
            
        }
        return  new String(buf, 0, len-CR_LEN);
    }
    public  static  void    guide(String s){

        System.out.print(   "[" + s + "] > ");
    }
    
    public  Input(){
        
    }

}

